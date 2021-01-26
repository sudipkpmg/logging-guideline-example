package gov.tn.dhs.ecm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.tn.dhs.ecm.util.JsonUtil;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentViewResult {

    @JsonProperty("previewUrl")
    private String previewUrl;

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String toString() {
        return JsonUtil.toJson(this);
    }

}


