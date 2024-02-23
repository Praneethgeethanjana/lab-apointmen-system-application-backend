package com.praneeth.lab.config.security.custom;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
/**
 * @author Navishka Darshana - navishka@jevigsoft.com
 * @project  ancybtrading-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/06/2023 - 23.16
 */

@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {

    public CustomOauthException(String msg) {
        super(msg);
    }
}
