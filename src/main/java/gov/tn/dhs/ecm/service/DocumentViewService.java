package gov.tn.dhs.ecm.service;

import com.box.sdk.BoxAPIException;
import com.box.sdk.BoxDeveloperEditionAPIConnection;
import com.box.sdk.BoxFile;
import gov.tn.dhs.ecm.model.DocumentViewRequest;
import gov.tn.dhs.ecm.model.DocumentViewResult;
import gov.tn.dhs.ecm.util.ConnectionHelper;
import gov.tn.dhs.integration.common.framework.Logging;
import org.apache.camel.Exchange;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class DocumentViewService extends BaseService {

    private Logging log = new Logging(BaseService.class);

    public DocumentViewService(ConnectionHelper connectionHelper) {
        super(connectionHelper);
    }

    public void process(Exchange exchange) {
        DocumentViewRequest documentViewRequest = exchange.getIn().getBody(DocumentViewRequest.class);
        String fileId = documentViewRequest.getFileId();
        String appUserId = documentViewRequest.getAppUserId();

        MDC.put("appUserId", appUserId);

        log.logDebug("folder view request", exchange);

        log.logInfo(String.format("Document View Request = %s", documentViewRequest.toString()), exchange);

        BoxDeveloperEditionAPIConnection api = getBoxApiConnection(exchange, log);

        try {
            log.logInfo(String.format("fileId = %s", fileId), exchange);
            BoxFile file = new BoxFile(api, fileId);
            URL previewUrl = file.getPreviewLink();
            log.logInfo(String.format("previewUrl = %s", previewUrl), exchange);
            DocumentViewResult documentViewResult = new DocumentViewResult();
            documentViewResult.setPreviewUrl(previewUrl.toString());
            log.logInfo(String.format("Document View Result = %s", documentViewResult.toString()), exchange);
            setupResponse(exchange, log, "200", documentViewResult);
        } catch (BoxAPIException e) {
            log.logError(e.toString(), exchange);
            switch (e.getResponseCode()) {
                case 404: {
                    setupError(exchange, log, "409", "Document not found");
                }
                default: {
                    setupError(exchange, log, "500", "Document view error");
                }
            }
        }
    }

}



