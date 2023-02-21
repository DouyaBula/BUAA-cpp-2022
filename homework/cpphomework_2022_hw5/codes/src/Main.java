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
    private static String parameter;
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
            switch (op) {
                case "qdate":
                    arg = scanner.next();
                    qdate();
                    break;
                case "qsend":
                    readInstruction();
                    qsend();
                    break;
                case "qrecv":
                    readInstruction();
                    qrecv();
                    break;
                default:
                    break;
            }
        }
    }

    public static void readInstruction() {
        parameter = scanner.next();
        if (parameter.equals("-v")) {
            arg = scanner.next();
        } else {
            arg = parameter;
            parameter = "none";
        }
        arg = arg.substring(1, arg.length() - 1);
    }

    public static void qdate() {
        String[] yearMonthDay = arg.split("\\/", 3);
        boolean dateMatched;
        for (String temp : records) {
            dateMatched = true;
            matcher = pattern.matcher(temp);
            if (matcher.find()) {
                String[] dateInRecord = {
                        matcher.group("year"),
                        matcher.group("month"),
                        matcher.group("day")
                };
                for (int i = 0; i < 3; i++) {
                    if (yearMonthDay[i].equals("")) {
                        continue;
                    } else if (
                            !Integer.valueOf(dateInRecord[i]).
                                    equals(Integer.valueOf(yearMonthDay[i]))) {
                        dateMatched = false;
                        break;
                    }
                }
                if (dateMatched) {
                    System.out.println(temp + ";");
                }
            }
        }
    }

    public static void qsend() {
        for (String temp : records) {
            matcher = pattern.matcher(temp);
            if (matcher.find()) {
                switch (parameter) {
                    case "none":
                        if (matcher.group("sender").equals(arg)) {
                            System.out.println(temp + ";");
                        }
                        break;
                    default:
                        if (matcher.group("sender").contains(arg)) {
                            System.out.println(temp + ";");
                        }
                        break;
                }
            }
        }
    }

    public static void qrecv() {
        for (String temp : records) {
            matcher = pattern.matcher(temp);
            if (matcher.find()) {
                // 1. 在冒号前寻找
                if (matcher.group("receiver") != null) {
                    switch (parameter) {
                        case "none":
                            if (matcher.group("receiver").equals("@" + arg + " ")) {
                                System.out.println(temp + ";");
                            }
                            break;
                        default:
                            if (matcher.group("receiver").contains(arg)) {
                                System.out.println(temp + ";");
                            }
                            break;
                    }
                    // 2. 在冒号后（正文里）寻找
                } else if (matcher.group("message").indexOf('@') != -1) {
                    Matcher tempMatcher = patternRec.matcher(matcher.group("message"));
                    if (tempMatcher.find()) {
                        switch (parameter) {
                            case "none":
                                if (tempMatcher.group().equals("@" + arg + " ")) {
                                    System.out.println(temp + ";");
                                }
                                break;
                            default:
                                if (tempMatcher.group().contains(arg)) {
                                    System.out.println(temp + ";");
                                }
                                break;
                        }
                    }
                }
            }
        }
    }
}

