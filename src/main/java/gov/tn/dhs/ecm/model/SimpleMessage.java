package gov.tn.dhs.ecm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.tn.dhs.ecm.util.JsonUtil;

public class SimpleMessage {

    @JsonProperty("message")
    private String message;

    public SimpleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return JsonUtil.toJson(this);
    }

}
