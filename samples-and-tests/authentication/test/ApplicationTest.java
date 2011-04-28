import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {
	private final static String AUTH_URL = "http://api.twitter.com/oauth";
	
    @Test
    public void beginTwitterAuthentication() {
        Response response = GET("/twitter/connect");
        assertStatus(302, response);
        assertTrue(response.getHeader("Location"), 
        		response.getHeader("Location").startsWith(AUTH_URL));
    }
}