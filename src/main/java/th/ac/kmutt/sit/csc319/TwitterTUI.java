package th.ac.kmutt.sit.csc319;

import twitter4j.TwitterException;

import java.util.List;
import java.util.Scanner;

public class TwitterTUI {

    private TwitterService twitterService;
    private Scanner sc = new Scanner(System.in);

    public TwitterTUI() {
        this.twitterService = new TwitterService();
    }

    public static void main(String[] args) {
        TwitterTUI self = new TwitterTUI();
        //while(true) {
            self.mainLoop();
        //}
    }

    private int promptInt(String prompt, int defaultValue) {
        System.out.println(prompt + " [" + defaultValue + "]");
        String ans = sc.nextLine();

        if(ans.isEmpty())
            return defaultValue;
        try {
            return Integer.parseInt(ans);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private boolean promptBoolean(String prompt, boolean defaultValue) {
        System.out.println(prompt + " (y/n) [" + (defaultValue ? "y" : "n") + "]");
        String ans = sc.nextLine();
        if(ans.isEmpty())
            return defaultValue;
        else if(ans.equals("n") || ans.equals("no"))
            return false;
        else
            return true;
    }

    private void mainLoop() {
        final boolean allowRT;
        final boolean allowQuote;
        final int minRT;
        final int minFav;
        int amount;

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Enter query: ");
        String queryString = sc.nextLine();
        amount = promptInt("Enter maximum number of tweets to collect", 15);
        boolean addOptions = promptBoolean("Do you want to specify additional query options?", false);
        if(addOptions) {
            allowRT = promptBoolean("Include retweets?", true);
            allowQuote = promptBoolean("Include quotes?", true);
            if(allowRT)
                minRT = Math.max(0, promptInt("Include only tweets with how many RT?", 0));
            else
                minRT = 0;
            minFav = Math.max(0, promptInt("Include only tweets with how many favorites?", 0));
        } else {
            allowRT = true;
            allowQuote = true;
            minRT = 0;
            minFav = 0;
        }
        List<Tweet> statusList;
        try {
            System.out.println("Fetching tweets from Twitter API...");
            statusList = twitterService.getTweetsAbout(queryString, amount, allowRT, allowQuote, minRT, minFav);
        } catch (TwitterException e) {
            e.printStackTrace();
            System.out.println("Error retrieving tweets with query " + queryString);
            System.out.println();
            return;
        }
        if(statusList == null || statusList.isEmpty()) {
            System.out.println("Nobody recently tweeted about the keyword");
            System.out.println();
            return;
        }

        System.out.println("Displaying " + Utils.convertIntToShortString(statusList.size()) + " tweets that match the criteria...");
        statusList.stream().forEach(e -> System.out.println("@" + e.getCreatorHandle() +": " + e.getText()));
    }
}
