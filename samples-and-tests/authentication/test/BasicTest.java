import org.junit.Test;

import play.test.UnitTest;
import twitter.TwitterService;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;

public class BasicTest extends UnitTest {

    @Test
    public void getRequestToken() throws TwitterException {
        String callback = "http://test.com";
        assertNotNull(TwitterService.getRequestToken(callback));
    }
    
    @Test
    public void tryTwitterFactory() {
    	assertNotNull(TwitterService.factory());
    }

    /*
    @Test
    public void verifyCredentials() throws TwitterException {
    	User user = TwitterService.factory(getAccessToken()).verifyCredentials();
    	assertNotNull(user);
    	assertEquals(SCREEN_NAME, user.getScreenName());
    }
    */
}
