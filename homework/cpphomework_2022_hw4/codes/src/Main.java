import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static String regex =
            "(?<date>(?<year>\\d{1,4})\\/(?<month>\\d{1,2})\\/(?<day>\\d{1,2}))-" +
                    "(?<sender>[A-Za-z0-9]+)(?<receiver>@[A-Za-z0-9]+ )?:" +
                    "\"(?<message>[A-Za-z0-9 ?!,\\.@]+)\"";
    private static String regexRec = "@[A-Za-z0-9]+ ";
    private static Pattern pattern = Pattern.compile(regex);
    private static Pattern patternRec = Pattern.compile(regexRec);
    private static Matcher matcher;
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<String> records = new ArrayList<>();
    private static String inputLine;
    private static String[] inputLines;
    private static String op;
    private static String arg;

    public static void main(String[] args) {

        while (true) {
            inputLine = scanner.nextLine();
            if (inputLine.equals("END_OF_MESSAGE")) {
                break;
            }
            inputLines = inputLine.split(";");
            for (String temp :
                    inputLines) {
                temp = temp.trim();
                records.add(temp);
            }
        }
        while (scanner.hasNext()) {
            op = scanner.next();
            arg = scanner.next();
            switch (op) {
                case "qdate":
                    qdate();
                    break;
                case "qsend":
                    qsend();
                    break;
                case "qrecv":
                    arg = arg.substring(1, arg.length() - 1);
                    qrecv();
                    break;
                default:
                    break;
            }
        }
    }

    public static void qdate() {
        String[] yearMonthDay = arg.split("\\/");
        for (String temp : records) {
            matcher = pattern.matcher(temp);
            if (matcher.find()) {
                if (
                        Integer.valueOf(matcher.group("year")).intValue()
                                == Integer.valueOf(yearMonthDay[0]).intValue() &&
                                Integer.valueOf(matcher.group("month")).intValue()
                                        == Integer.valueOf(yearMonthDay[1]).intValue() &&
                                Integer.valueOf(matcher.group("day")).intValue()
                                        == Integer.valueOf(yearMonthDay[2]).intValue()
                ) {
                    System.out.println(temp + ";");
                }
            }
        }
    }

    public static void qsend() {
        for (String temp : records) {
            matcher = pattern.matcher(temp);
            if (matcher.find()) {
                if (("\"" + matcher.group("sender") + "\"").equals(arg)) {
                    System.out.println(temp + ";");
                }
            }
        }
    }

    public static void qrecv() {
        for (String temp : records) {
            matcher = pattern.matcher(temp);
            if (matcher.find()) {
                if (matcher.group("receiver") != null) {
                    if (matcher.group("receiver").equals("@" + arg + " ")) {
                        System.out.println(temp + ";");
                    }
                } else if (matcher.group("message").indexOf('@') != -1) {
                    Matcher tempMatcher = patternRec.matcher(matcher.group("message"));
                    if (tempMatcher.find()) {
                        if (tempMatcher.group().equals("@" + arg + " ")) {
                            System.out.println(temp + ";");
                        }
                    }
                }
            }
        }
    }
}

