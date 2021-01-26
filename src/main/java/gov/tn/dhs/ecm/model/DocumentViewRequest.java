package gov.tn.dhs.ecm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.tn.dhs.ecm.util.JsonUtil;

public class DocumentViewRequest {

    @JsonProperty("appUserId")
    private String appUserId;

    @JsonProperty("fileId")
    private String fileId;

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String toString() {
        return JsonUtil.toJson(this);
    }

}

