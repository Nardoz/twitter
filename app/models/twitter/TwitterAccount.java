package models.twitter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import twitter.TwitterService;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.User;
import twitter4j.auth.AccessToken;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class TwitterAccount extends Model {
	@Column(unique=true)
	public Long twitterId;
	public String token;
	public String tokenSecret;
	public String screenName;
	public Boolean enabled;
	
	@Transient
	private User user;
	
	public AccessToken getAccessToken() {
		return (token != null && tokenSecret != null)
			? new AccessToken(token, tokenSecret)
			: null;
	}
	
	public User user() throws TwitterException {
		if(user == null) {
			if(token != null && tokenSecret != null) {
				user = TwitterService.factory(getAccessToken()).verifyCredentials();
			}
			else {
				if(twitterId != 0) {
					user = TwitterService.factory().showUser(twitterId);
				}
				else if(screenName != null & screenName.isEmpty()) {
					user = TwitterService.factory().showUser(screenName);
				}
			}
		}
		return user;
	}
	
	public Boolean hasAuthenticationTokens() {
		return token != null && tokenSecret != null;
	}
	
	public TwitterStream getStream() {
		return TwitterService.streamFactory(getAccessToken());
	}
}
