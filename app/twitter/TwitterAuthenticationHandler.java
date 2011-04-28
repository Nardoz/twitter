package twitter;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public interface TwitterAuthenticationHandler {
	void authenticationSuccess(AccessToken accessToken);
	void authenticationFail(TwitterException e);
}
