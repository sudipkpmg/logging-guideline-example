package gov.tn.dhs.ecm.route;

import com.fasterxml.jackson.core.JsonParseException;
import gov.tn.dhs.ecm.exception.ServiceErrorException;
import gov.tn.dhs.ecm.model.DocumentViewRequest;
import gov.tn.dhs.ecm.model.DocumentViewResult;
import gov.tn.dhs.ecm.service.DocumentViewService;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
class ApiRoute extends RouteBuilder {

    public final DocumentViewService documentViewService;

    public ApiRoute(DocumentViewService documentViewService) {
        this.documentViewService = documentViewService;
    }

    @Override
    public void configure() {

        onException(JsonParseException.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setBody(constant("{}"))
        ;

        onException(Exception.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setBody(simple("${exception.message}"))
        ;

        onException(ServiceErrorException.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("${exception.code}"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setBody(simple("${exception.message}"))
        ;

        restConfiguration()
                .bindingMode(RestBindingMode.auto)
                .component("undertow")
                .contextPath("/ecm-api/v1")
                .port(9090)
                .skipBindingOnErrorCode(false)
                .enableCORS(true)
                .apiProperty("api.title", "gov-tn-dhs-int BOX Document View API")
                .apiProperty("api.version", "1.0")
                .apiProperty("api.title", "Document View API")
                .apiProperty("api.version", "0.1.0").apiProperty("api.description", "BOX Document View Service")
                .apiProperty("cors", "true")
        ;

        rest("/")
                .consumes("application/json")
                .produces("application/json")
                .post("/view_document")
                .type(DocumentViewRequest.class)
                .outType(DocumentViewResult.class)
                .route()
                .routeId("DocumentView_Route")
                .bean(documentViewService, "process")
                .endRest()
        ;

    }

}
