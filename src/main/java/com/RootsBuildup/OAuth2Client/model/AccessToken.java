package com.RootsBuildup.OAuth2Client.model;

import com.RootsBuildup.OAuth2Client.annotations.NativeQueryResultEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@NativeQueryResultEntity
public class AccessToken{
	private String id;
	private String username;
	private String clientId;
	private String token;
	private String oAuth2AccessToken;
	private String authentication;
	private String refreshToken;
	private String expiredDateAndTime;

}