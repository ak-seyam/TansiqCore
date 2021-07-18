package io.asiam.tansiq.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploaderServerResponse {
    private boolean success;
    private String message;
    public UploaderServerResponse() {}
}
