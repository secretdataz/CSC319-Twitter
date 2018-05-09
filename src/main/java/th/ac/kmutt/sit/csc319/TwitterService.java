package th.ac.kmutt.sit.csc319;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TwitterService {

    public static final int TWEET_AMOUNT_HARD_LIMIT = 10000;

    private Twitter twitter;

    public TwitterService() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(false)
                .setOAuthConsumerKey("gFfA13zCv9QicycCclM5Gmgwv")
                .setOAuthConsumerSecret("NxzoAvCaKDZeuWjZGzdqd5IwfZPWW6zCLoEdFszQ3ZK2O93PUN")
                .setOAuthAccessToken("979380875635732480-X2AJFyf8SP2FY6zieZ3j19T7VoL4Osa")
                .setOAuthAccessTokenSecret("Opjrbu0S28Zt34hodj9j4ZUQGQxBPxuE6zpSgLvrmZk55")
                .setTweetModeExtended(true)
                ;

        TwitterFactory tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();
    }

    public TwitterService(Twitter twitter) {
        this.twitter = twitter;
    }

    public Trends getThailandTrends() {
        try {
            Trends trends = twitter.getPlaceTrends(23424960);
            return trends;
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Tweet> getTweetsAbout(String queryString, int maxAmount, boolean allowRT, boolean allowQuote, int minRT, int minFav) throws TwitterException {
        maxAmount = Math.min(maxAmount, TWEET_AMOUNT_HARD_LIMIT);
        List<Status> tweets = new ArrayList<>();
        Query query = new Query(queryString);
        query.setCount(Math.min(maxAmount, 100));
        QueryResult result;
        do {
            result = twitter.search(query);
            if(result.getTweets() != null)
                tweets.addAll(result.getTweets().stream()
                        .filter(t -> (((allowRT && t.getRetweetCount() >= minRT) || !t.isRetweet()) &&
                                ((allowQuote || t.getQuotedStatus() != null)) &&
                                t.getFavoriteCount() >= minFav))
                        .collect(Collectors.toList()));

            if(result.hasNext() && query != null)
                query = result.nextQuery();
        } while(result.hasNext() && tweets.size() < maxAmount);

        return tweets.stream()
                .map(t -> new Tweet(t.getUser().getScreenName(), t.getUser().getName(), t.getCreatedAt(), t.getText(), t.isRetweet(), t.isFavorited(), t.getRetweetCount(), t.getFavoriteCount(), t.isTruncated()))
                .collect(Collectors.toList());
    }
}
