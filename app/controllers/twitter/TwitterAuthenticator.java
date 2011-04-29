package controllers.twitter;

import play.Logger;
import play.Play;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Router;
import twitter.TwitterAuthenticationHandler;
import twitter.TwitterService;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterAuthenticator extends Controller {
	private static final String OAUTH_TOKEN = "oauth_token";
	private static final String OAUTH_VERIFIER = "oauth_verifier";
	private static final String OAUTH_SECRET = "oauth_secret";
	private static final String AUTH_PROCESS = "auth_process";

	@Before
	static void interceptor() {
		TwitterAuthenticationHandler callback;
		
		try {
			callback = findCallback();
		} catch(Exception e) {
			Logger.error(e, e.getMessage());
			error(e.getMessage());
			return;
		}
		
		if(inProcess()) {
			endAuth(params.get(OAUTH_VERIFIER), callback);
		} else {
			beginAuth(callback);
		}
	}
	
	static void beginAuth(TwitterAuthenticationHandler callback) {
		String callbackUrl = Router.getFullUrl(request.action);
		RequestToken requestToken;
		
		try {
			requestToken = TwitterService.getRequestToken(callbackUrl);
		} catch(TwitterException e) {
			callback.authenticationFail(e);
			return;
		}
		
		flash.put(AUTH_PROCESS, 1);
		flash.put(OAUTH_TOKEN, requestToken.getToken());
		flash.put(OAUTH_SECRET, requestToken.getTokenSecret());
		
		redirect(requestToken.getAuthenticationURL());
	}
	
	static void endAuth(String verifier, TwitterAuthenticationHandler callback) {
		Twitter twitter = TwitterService.factory();
		RequestToken requestToken = new RequestToken(flash.get(OAUTH_TOKEN), flash.get(OAUTH_SECRET));
		AccessToken accessToken;
		
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
		} catch(TwitterException e) {
			callback.authenticationFail(e);
			return;
		}
		
		callback.authenticationSuccess(accessToken);
	}
	
	static TwitterAuthenticationHandler findCallback() throws InstantiationException, IllegalAccessException {
		Class controller = 
			Play.classloader.getAssignableClasses(TwitterAuthenticationHandler.class).get(0);

		return (TwitterAuthenticationHandler)controller.newInstance();
	}
	
	static Boolean inProcess() {
		return flash.contains(AUTH_PROCESS);
	}
	
	public static void index() {
		// No implementation; the auth flow is handled by the interceptor
	}
}
