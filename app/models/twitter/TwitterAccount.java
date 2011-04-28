package models.twitter;

import javax.persistence.Entity;

import play.db.jpa.Model;
import twitter.TwitterService;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.User;
import twitter4j.auth.AccessToken;

@Entity
public class TwitterAccount extends Model {
	public Long twitterId;
	public String token;
	public String tokenSecret;
	
	private User user;
	
	public AccessToken getAccessToken() {
		return new AccessToken(token, tokenSecret);
	}
	
	public User user() throws TwitterException {
		if(user == null) {
			user = TwitterService.factory(getAccessToken()).verifyCredentials();
		}
		
		return user;
	}
	
	public TwitterStream getStream() {
		return TwitterService.streamFactory(getAccessToken());
	}
}
