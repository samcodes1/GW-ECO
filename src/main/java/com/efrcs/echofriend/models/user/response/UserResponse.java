package com.efrcs.echofriend.models.user.response;

import com.efrcs.echofriend.models.baseresponse.EcoFriendlyBaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse extends EcoFriendlyBaseResponse {
    
}
