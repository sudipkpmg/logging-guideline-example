package gov.tn.dhs.ecm.service;

import com.box.sdk.BoxDeveloperEditionAPIConnection;
import gov.tn.dhs.ecm.exception.ServiceErrorException;
import gov.tn.dhs.ecm.model.ClientError;
import gov.tn.dhs.ecm.model.SimpleMessage;
import gov.tn.dhs.ecm.util.ConnectionHelper;
import gov.tn.dhs.ecm.util.JsonUtil;
import gov.tn.dhs.integration.common.framework.Logging;
import org.apache.camel.Exchange;

public abstract class BaseService {

    private final ConnectionHelper connectionHelper;

    public BaseService(ConnectionHelper connectionHelper) {
        this.connectionHelper = connectionHelper;
    }

    protected abstract void process(Exchange exchange);

    protected void setupResponse(Exchange exchange, Logging log, String code, Object response) {
        exchange.getIn().setBody(response, response.getClass());
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, code);
        exchange.getIn().setHeader("Content-Type", "application/json");
        exchange.getIn().setHeader("Accept", "application/json");
    }

    protected void setupResponse(Exchange exchange, Logging log, String code, Object response, Class clazz) {
        exchange.getIn().setBody(response, clazz);
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, code);
        exchange.getIn().setHeader("Content-Type", "application/json");
        exchange.getIn().setHeader("Accept", "application/json");
    }

    protected void setupOctetStreamResponse(Exchange exchange, Logging log, String code, byte[] data) {
        exchange.getIn().setBody(data, byte[].class);
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, code);
        exchange.getIn().setHeader("Content-Type", "application/octet-stream");
    }

    protected void setupMessage(Exchange exchange, Logging log, String code, String message) {
        SimpleMessage simpleMessage = new SimpleMessage(message);
        exchange.getIn().setBody(simpleMessage, SimpleMessage.class);
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, code);
        exchange.getIn().setHeader("Content-Type", "application/json");
    }

    protected void setupError(Exchange exchange, Logging log, String code, String message) {
        ClientError clientError = new ClientError(code, message);
        log.logInfo(clientError.toString(), exchange);
        throw new ServiceErrorException(code, JsonUtil.toJson(clientError));
    }

    protected BoxDeveloperEditionAPIConnection getBoxApiConnection(Exchange exchange, Logging log) {
        BoxDeveloperEditionAPIConnection api = null;
        try {
            api = connectionHelper.getBoxDeveloperEditionAPIConnection();
        } catch (Exception e) {
            setupError(exchange, log, "500", "Error getting Box connection");
        }
        return api;
    }


}
