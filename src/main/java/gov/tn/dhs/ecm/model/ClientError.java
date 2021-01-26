package gov.tn.dhs.ecm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.tn.dhs.ecm.util.JsonUtil;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientError {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    public ClientError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
