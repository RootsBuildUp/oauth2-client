package com.RootsBuildup.OAuth2Client.service;


import com.RootsBuildup.OAuth2Client.dao.TokenAccessDao;
import com.RootsBuildup.OAuth2Client.model.AccessToken;
import com.RootsBuildup.OAuth2Client.redis.service.OAthTokenRepoImpl;
import com.RootsBuildup.OAuth2Client.util.VariableName;
import com.service.cblmodel.redis.model.OAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


@Service
@Transactional
public class AccessTokenService {
	

	@Autowired
	private OAthTokenRepoImpl oAthTokenRepo;
	@Autowired
	private TokenAccessDao tokenAccessDao;



	@Async
	public void asyncMethodForTokenTimeUpdate(OAuthToken oAuthToken) {
		LocalDateTime localDateTime = oAuthToken.getExpiredDateAndTime().plusMinutes(VariableName.expiredDate);
		tokenAccessDao.tokenUpdated(oAuthToken.getId(),localDateTime);
		oAthTokenRepo.save(oAuthToken.setExpiredDateAndTime(localDateTime));
	}

	public OAuthToken getOAuthToken(String tokenId){
		return oAthTokenRepo.findById(tokenId);
	}
}

