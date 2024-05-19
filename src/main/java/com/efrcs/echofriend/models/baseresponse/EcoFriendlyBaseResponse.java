package com.efrcs.echofriend.models.baseresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EcoFriendlyBaseResponse {
    // private int responseCode;
    private String responseMessage;
    private Object data;
}
