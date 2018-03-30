package th.ac.kmutt.sit.csc319;

public class Utils {
    public static String convertIntToShortString(int input) {
        if(input < 1000) {
            return input + "";
        } else if(input >= 1000 && input < 10000) {
            return input / 1000 + "," + String.format("%03d",input % 1000);
        } else if(input >= 10000 && input < 1000000) {
            int remainder = input % 10000;
            return input/10000 + (remainder == 0 ? "" : "." + remainder % 1000 + "K");
        } else {
            int remainder = input % 1000000;
            return input/1000000 + (remainder == 0 ? "" : "." + remainder % 100000 + "K");
        }
    }
}
