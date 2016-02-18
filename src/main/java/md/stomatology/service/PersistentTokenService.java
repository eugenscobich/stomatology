package md.stomatology.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import md.stomatology.model.RememberMeToken;
import md.stomatology.repository.RememberMePersistentTokenRepository;

@Service
public class PersistentTokenService implements PersistentTokenRepository {

	@Autowired
	private RememberMePersistentTokenRepository rememberMePersistentTokenRepository;
	
	@Override
	@Transactional
	public void createNewToken(PersistentRememberMeToken token) {
		RememberMeToken rememberMeToken = new RememberMeToken();
		rememberMeToken.setDate(token.getDate());
		rememberMeToken.setSeries(token.getSeries());
		rememberMeToken.setToken(token.getTokenValue());
		rememberMeToken.setUsername(token.getUsername());
		rememberMePersistentTokenRepository.save(rememberMeToken);
	}

	@Override
	@Transactional
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		RememberMeToken rememberMeToken = rememberMePersistentTokenRepository.findBySeries(series);
		if (rememberMeToken != null) {
			rememberMeToken.setToken(tokenValue);
			rememberMeToken.setDate(lastUsed);
			rememberMePersistentTokenRepository.save(rememberMeToken);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		RememberMeToken rememberMeToken = rememberMePersistentTokenRepository.findBySeries(seriesId);
		if (rememberMeToken != null) {
			return new PersistentRememberMeToken(rememberMeToken.getUsername(), rememberMeToken.getSeries(), rememberMeToken.getToken(), rememberMeToken.getDate());
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void removeUserTokens(String username) {
		rememberMePersistentTokenRepository.removeByUsername(username);
	}

}
