package th.ac.kmutt.sit.csc319;

import java.util.Date;

public class Tweet {
    private String creatorHandle;
    private String creatorName;
    private Date createdAt;
    private String text;
    private boolean isRetweet;
    private boolean isQuote;
    private int retweetCount;
    private int favoriteCount;
    private boolean isTruncated;

    public Tweet(String creatorHandle, String creatorName, Date createdAt, String text, boolean isRetweet, boolean isQuote, int retweetCount, int favoriteCount, boolean isTruncated) {
        this.creatorHandle = creatorHandle;
        this.creatorName = creatorName;
        this.createdAt = createdAt;
        this.text = text;
        this.isRetweet = isRetweet;
        this.isQuote = isQuote;
        this.retweetCount = retweetCount;
        this.favoriteCount = favoriteCount;
        this.isTruncated = isTruncated;
    }

    public String getCreatorHandle() {
        return creatorHandle;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }

    public boolean isRetweet() {
        return isRetweet;
    }

    public boolean isQuote() {
        return isQuote;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public boolean isTruncated() {
        return isTruncated;
    }
}
