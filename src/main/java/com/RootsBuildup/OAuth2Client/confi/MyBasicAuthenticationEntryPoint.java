package com.RootsBuildup.OAuth2Client.confi;

import com.RootsBuildup.OAuth2Client.confi.WWWAuthenticateHeaderBuilder;
import com.RootsBuildup.OAuth2Client.util.VariableName;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException {
        super.commence(request, response, authException);
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String errorMessage = "Insufficient authentication details";
        Map<String, String> parameters = new LinkedHashMap<>();

        parameters.put("error_description", errorMessage);


        String wwwAuthenticate = WWWAuthenticateHeaderBuilder.computeWWWAuthenticateHeaderValue(parameters);
        response.addHeader("WWW-Authenticate", wwwAuthenticate);
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().append("\"UNAUTHORIZED\"");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("UNAUTHORIZED");
        super.afterPropertiesSet();
    }
//    private String getAuthHeader(){
//        String auth = VariableName.CLIENT_ID + ":" + VariableName.CLIENT_SECRET;
//        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
//        String authHeader = VariableName.BASIC_AUTH + new String(encodedAuth);
//        return authHeader;
//    }
}
