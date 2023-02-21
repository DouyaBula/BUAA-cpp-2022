import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static String tweet;
    private static String query;
    private static String regexDate = "(?<Start>\\d{4}-\\d{2}-\\d{2})~(?<End>\\d{4}-\\d{2}-\\d{2})";
    private static Pattern datePattern = Pattern.compile(regexDate);
    private static Matcher dateMatcher;
    private static ArrayList<Block> jsons = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Parser parser;

    public static void main(String[] argv) throws ParseException {
        //test();
        while (true) {
            tweet = scanner.nextLine();
            if (tweet.equals("END_OF_MESSAGE")) {
                break;
            } else {
                parser = new Parser(tweet);
                jsons.add(parser.parse());
            }
        }

        while (scanner.hasNext()) {
            query = scanner.next();
            switch (query) {
                case "Qdate":
                    qdate();
                    break;
                case "Qemoji":
                    qemoji();
                    break;
                case "Qcount":
                    qcount();
                    break;
                case "Qtext":
                    qtext();
                    break;
                case "Qsensitive":
                    qsensitive();
                    break;
                case "Qlang":
                    qlang();
                    break;
                default:
                    break;
            }
        }
    }

    public static void qdate() throws ParseException {
        String userId = scanner.next();
        String datePeriod = scanner.next();
        dateMatcher = datePattern.matcher(datePeriod);
        dateMatcher.find();
        String dateStart = dateMatcher.group("Start");
        String dateEnd = dateMatcher.group("End");
        int numberOfTweets = 0;
        int numberOfRetweets = 0;
        int numberOfFavorites = 0;
        int numberOfReplies = 0;
        for (Block json :
                jsons) {
            Block rawValue = (Block) json.getContent().get("raw_value");
            if (rawValue.getContent().get("user_id").getContent().equals(userId) &&
                    dateInPeriod((String) rawValue.getContent().get("created_at").getContent(),
                            dateStart, dateEnd)) {
                numberOfTweets += 1;
                numberOfRetweets += Integer.parseInt((String)
                        rawValue.getContent().get("retweet_count").getContent());
                numberOfFavorites += Integer.parseInt((String)
                        rawValue.getContent().get("favorite_count").getContent());
                numberOfReplies += Integer.parseInt((String)
                        rawValue.getContent().get("reply_count").getContent());
            }
        }
        System.out.printf("%d %d %d %d\n",
                numberOfTweets, numberOfRetweets, numberOfFavorites, numberOfReplies);
    }

    public static boolean dateInPeriod(
            String date, String start, String end) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.ENGLISH);
        String startPrecise = start + " 00:00:00";
        String endPrecise = end + " 23:59:59";
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNow = format1.parse(date);
        Date dateStart = format2.parse(startPrecise);
        Date dateEnd = format2.parse(endPrecise);
        return (dateNow.after(dateStart) && dateNow.before(dateEnd));
    }

    public static void qemoji() {
        String id = scanner.next();
        for (Block json :
                jsons) {
            Block rawValue = (Block) json.getContent().get("raw_value");
            if (rawValue.getContent().get("id").getContent().equals(id)) {
                Array emojis = (Array) rawValue.getContent().get("emojis");
                if (emojis.getContent().size() == 0) {
                    System.out.println("None");
                } else {
                    Collections.sort(emojis.getContent());
                    for (Block emoji :
                            emojis.getContent()) {
                        System.out.print(emoji.getContent().get("name").getContent() + " ");
                    }
                    System.out.println();
                }
                break;
            }
        }
    }

    public static void qcount() throws ParseException {
        String datePeriod = scanner.next();
        dateMatcher = datePattern.matcher(datePeriod);
        dateMatcher.find();
        String dateStart = dateMatcher.group("Start");
        String dateEnd = dateMatcher.group("End");
        int downloadNumber = 0;
        for (Block json :
                jsons) {
            String downloadTime = (String) json.getContent().get("download_datetime").getContent();
            if (downloadDateInPeriod(downloadTime, dateStart, dateEnd)) {
                downloadNumber++;
            }
        }
        System.out.println(downloadNumber);
    }

    public static boolean downloadDateInPeriod(
            String date, String start, String end) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        String startPrecise = start + " 00:00:00";
        String endPrecise = end + " 23:59:59";
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNow = format1.parse(date);
        Date dateStart = format2.parse(startPrecise);
        Date dateEnd = format2.parse(endPrecise);
        return (dateNow.after(dateStart) && dateNow.before(dateEnd));
    }

    public static void qtext() {
        String id = scanner.next();
        for (Block json :
                jsons) {
            Block rawValue = (Block) json.getContent().get("raw_value");
            if (rawValue.getContent().get("id").getContent().equals(id)) {
                String fullText = (String) rawValue.getContent().get("full_text").getContent();
                if (fullText.equals("null")) {
                    System.out.println("None");
                } else if (!fullText.equals("")) {
                    System.out.println(fullText);
                }
                break;
            }
        }
    }

    public static void qsensitive() {
        int sensitiveCount = 0;
        String userId = scanner.next();
        for (Block json :
                jsons) {
            Block rawValue = (Block) json.getContent().get("raw_value");
            if (rawValue.getContent().get("user_id").getContent().equals(userId) &&
                    rawValue.getContent().get("possibly_sensitive_editable")
                            .getContent().equals("true")) {
                sensitiveCount++;
            }
        }
        System.out.println(sensitiveCount);
    }

    public static void qlang() {
        String id = scanner.next();
        for (Block json :
                jsons) {
            Block rawValue = (Block) json.getContent().get("raw_value");
            if (rawValue.getContent().get("id").getContent().equals(id)) {
                System.out.println(rawValue.getContent().get("lang").getContent());
                break;
            }
        }
    }

    // TODO: DEBUG用，记得删
    public static void test() throws ParseException {
        System.out.println(dateInPeriod("Sat May 05 17:16:55 2022", "2022-01-01", "2022-06-06"));
        System.out.println(dateInPeriod("Sat May 05 17:16:55 2022", "2022-05-05", "2022-06-06"));
        System.out.println(dateInPeriod("Sat Jun 06 17:16:55 2022", "2022-05-05", "2022-06-06"));
        System.out.println(dateInPeriod("Sat Nov 05 17:16:55 2022", "2022-05-05", "2022-06-06"));
    }
}
