package twitter;

import play.Play;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService {
	private final static String CONSUMER_KEY = 
		Play.configuration.getProperty("twitter.consumerKey");
	private final static String CONSUMER_SECRET = 
		Play.configuration.getProperty("twitter.consumerSecret");

	private final static Configuration configuration =
		new ConfigurationBuilder()
				.setOAuthConsumerKey(CONSUMER_KEY)
				.setOAuthConsumerSecret(CONSUMER_SECRET)
				.build();

	public static Twitter factory() {
		return new TwitterFactory(configuration).getInstance();
	}
	
	public static Twitter factory(AccessToken accessToken) {
		return new TwitterFactory(configuration).getInstance(accessToken);
	}
	
	public static TwitterStream streamFactory(AccessToken accessToken) {
		return new TwitterStreamFactory(configuration).getInstance(accessToken);
	}
	
	public static RequestToken getRequestToken(String callback) throws TwitterException {
		return factory().getOAuthRequestToken(callback);
	}
}
