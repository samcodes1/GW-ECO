package com.efrcs.echofriend.models.baseresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EcoFriendlyBaseResponse {
    // private int responseCode;
    private String responseMessage;
    private Object data;
}
