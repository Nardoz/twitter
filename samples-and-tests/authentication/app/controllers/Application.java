package controllers;

import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Controller;
import twitter.TwitterAuthenticationHandler;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public class Application extends Controller implements TwitterAuthenticationHandler {

	@Override
	public void authenticationFail(TwitterException e) {
		// TODO Auto-generated method stub
		error(e.getMessage());
	}

	@Override
	public void authenticationSuccess(AccessToken accessToken) {
		renderText("authenticationSuccess: u="+accessToken.getScreenName()+"; t="+accessToken.getToken()+"; s="+accessToken.getTokenSecret());
		Long userId = accessToken.getUserId();
		session.put("userId", userId);
		Cache.put("token-" + userId.toString(), accessToken);
	}
	
	@Before
	static void checkSession() {
    	if(session.contains("userId")) {
    		String cacheKey = "token-" + session.get("userId");
    		return Cache.get(cacheKey);
    	}
	}
	
    public static void index() {
        render();
    }
    
    public static void home() {
    	render();
    }
    
    public static void logout() {
    	session.clear();
    	index();
    }
}