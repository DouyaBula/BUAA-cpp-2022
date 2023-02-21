import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static String regex =
            "(?<date>(?<year>\\d{1,4})/(?<month>\\d{1,2})/(?<day>\\d{1,2}))-" +
                    "(?<sender>[A-Za-z0-9]+)" +
                    "(@(?<receiver>[A-Za-z0-9]+) )?:" +
                    "\"(?<message>[A-Za-z0-9 ?!,.@]+)\"";
    private static Pattern patternRecord = Pattern.compile(regex);
    private static String regexRec = "@(?<receiver>[A-Za-z0-9]+) ";
    private static Pattern patternRec = Pattern.compile(regexRec);
    private static String regexCommandTypeQdate =
            "(?<date>(?<year>\\d{0,4})/(?<month>\\d{0,2})/(?<day>\\d{0,2}))" +
                    " *(-c)? *(\"(?<toChange>.*?)\")?";
    private static Pattern patternQdate = Pattern.compile(regexCommandTypeQdate);
    private static String regexQQ = "(?<v>-v)? *(-(?<op>ssq|ssr|pre|pos))? *" +
            "(\"(?<toSearch>.*?)\")? *(-c)? *" +
            "(\"(?<toChange>.*?)\")?";
    private static Pattern patternQQ = Pattern.compile(regexQQ);
    private static Matcher matcherRecord;
    private static Matcher matcherCommand;
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
            arg = scanner.nextLine();
            arg = arg.trim();
            switch (op) {
                case "qdate":
                    qdate();
                    break;
                case "qsend":
                    qsend();
                    break;
                case "qrecv":
                    qrecv();
                    break;
                default:
                    break;
            }
        }
    }

    public static void qdate() {
        boolean dateMatched;
        matcherCommand = patternQdate.matcher(arg);
        matcherCommand.find();
        if (qdateIllegal()) {
            System.out.println("Command Error!: Wrong Date Format! " +
                    "\"" + "qdate " + arg + "\"");//!Trim了，可能少空格
            return;
        }
        String[] yearMonthDay = {matcherCommand.group("year"),
                matcherCommand.group("month"),
                matcherCommand.group("day")};
        for (String temp : records) {
            dateMatched = true;
            matcherRecord = patternRecord.matcher(temp);
            if (matcherRecord.find()) {
                String[] dateInRecord = {
                        matcherRecord.group("year"),
                        matcherRecord.group("month"),
                        matcherRecord.group("day")
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
                    if (matcherCommand.group("toChange") != null) {
                        String tempMessage = clean(matcherCommand.group("toChange"));
                        temp = temp.replace(matcherRecord.group("message"), tempMessage);
                    }
                    System.out.println(temp + ";");
                }
            }
        }
    }

    public static boolean qdateIllegal() {
        boolean illegalQdate = false;
        int tempYear = 1;
        int tempMonth = 1;
        int tempDay = 1;
        if (!matcherCommand.group("month").equals("")) {
            tempMonth = Integer.parseInt(matcherCommand.group("month"));
            if (!(tempMonth >= 1 && tempMonth <= 12)) {
                illegalQdate = true;
                return illegalQdate;
            }
        }
        if (!matcherCommand.group("day").equals("")) {
            tempDay = Integer.parseInt(matcherCommand.group("day"));
            if (!matcherCommand.group("month").equals("")) {
                if (tempMonth == 1 || tempMonth == 3 || tempMonth == 5 ||
                        tempMonth == 7 || tempMonth == 8 || tempMonth == 10 ||
                        tempMonth == 12) {
                    if (!(tempDay >= 1 && tempDay <= 31)) {
                        illegalQdate = true;
                        return illegalQdate;
                    }
                } else if (tempMonth == 2) {
                    if (!matcherCommand.group("year").equals("")) {
                        tempYear = Integer.parseInt(matcherCommand.group("year"));
                        boolean leapYear = (
                                (tempYear % 4 == 0) && (tempYear % 100 != 0))
                                || (tempYear % 400 == 0);
                        if ((leapYear && !(tempDay >= 1 && tempDay <= 29)) ||
                                (!leapYear && !(tempDay >= 1 && tempDay <= 28))) {
                            illegalQdate = true;
                            return illegalQdate;
                        }
                    } else if (!(tempDay >= 1 && tempDay <= 29)) {
                        illegalQdate = true;
                        return illegalQdate;
                    }
                } else {
                    if (!(tempDay >= 1 && tempDay <= 30)) {
                        illegalQdate = true;
                        return illegalQdate;
                    }
                }
            } else {
                if (!(tempDay >= 1 && tempDay <= 31)) {
                    illegalQdate = true;
                    return illegalQdate;
                }
            }
        }
        return illegalQdate;
    }

    public static void qsend() {
        if (qqIllegal()) {
            System.out.println("Command Error!: Not Vague Query! " +
                    "\"" + "qsend " + arg + "\"");//!Trim了，可能少空格
            return;
        }
        matcherCommand = patternQQ.matcher(arg);
        matcherCommand.find();
        boolean dataMatched = false;
        boolean vague = false;
        String opQQ = "ssr";
        if (matcherCommand.group("v") != null) {
            vague = true;
            if (matcherCommand.group("op") != null) {
                opQQ = matcherCommand.group("op");
            }
        }
        for (String temp : records) {
            matcherRecord = patternRecord.matcher(temp);
            dataMatched = false;
            if (matcherRecord.find()) {
                if (vague) {
                    dataMatched = qsendVague(opQQ);
                } else {
                    if (matcherRecord.group("sender").equals(
                            matcherCommand.group("toSearch"))) {
                        dataMatched = true;
                    }
                }
            }
            if (dataMatched) {
                if (matcherCommand.group("toChange") != null) {
                    String tempMessage = clean(matcherCommand.group("toChange"));
                    temp = temp.replace(matcherRecord.group("message"), tempMessage);
                }
                System.out.println(temp + ";");
            }
        }
    }

    public static boolean qsendVague(String opQQ) {
        boolean dataMatched = false;
        switch (opQQ) {
            case "ssq":
                char[] senderArray = matcherRecord.group("sender").toCharArray();
                char[] toSearchArray = matcherCommand.group("toSearch").toCharArray();
                int cnt = 0;
                for (char tempChar :
                        senderArray) {
                    if (tempChar == toSearchArray[cnt]) {
                        cnt++;
                        if (cnt == matcherCommand.group("toSearch").length()) {
                            dataMatched = true;
                            break;
                        }
                    }
                }
                break;
            case "ssr":
                if (matcherRecord.group("sender").contains(
                        matcherCommand.group("toSearch"))) {
                    dataMatched = true;
                }
                break;
            case "pre":
                if (matcherRecord.group("sender").indexOf(
                        matcherCommand.group("toSearch")) == 0) {
                    dataMatched = true;
                }
                break;
            case "pos":
                if (matcherRecord.group("sender").contains(matcherCommand.group("toSearch")) &&
                        (matcherRecord.group("sender").lastIndexOf(
                                matcherCommand.group("toSearch")) ==
                                matcherRecord.group("sender").length() -
                                        matcherCommand.group("toSearch").length())) {
                    dataMatched = true;
                }
                break;
            default:
                break;
        }
        return dataMatched;
    }

    public static boolean qqIllegal() {
        boolean illegalQQ = false;
        Pattern pattern1 = Pattern.compile("-v");
        Pattern pattern2 = Pattern.compile("-ssq|-ssr|-pre|-pos");
        Matcher matcherOp1 = pattern1.matcher(arg);
        Matcher matcherOp2 = pattern2.matcher(arg);
        boolean find1 = matcherOp1.find();
        boolean find2 = matcherOp2.find();
        if (!find1 && find2) {
            illegalQQ = true;
            return illegalQQ;
        } else if (find1 && find2) {
            if (matcherOp1.start() > matcherOp2.start()) {
                illegalQQ = true;
                return illegalQQ;
            }
        }
        return illegalQQ;
    }

    public static void qrecv() {
        if (qqIllegal()) {
            System.out.println("Command Error!: Not Vague Query! " +
                    "\"" + "qrecv " + arg + "\"");//!Trim了，可能少空格
            return;
        }
        matcherCommand = patternQQ.matcher(arg);
        matcherCommand.find();
        boolean dataMatched = false;
        boolean vague = false;
        String opQQ = "ssr";
        if (matcherCommand.group("v") != null) {
            vague = true;
            if (matcherCommand.group("op") != null) {
                opQQ = matcherCommand.group("op");
            }
        }
        for (String temp : records) {
            matcherRecord = patternRecord.matcher(temp);
            dataMatched = false;
            if (matcherRecord.find()) {
                // 1. 在冒号前寻找
                if (matcherRecord.group("receiver") != null) {
                    if (vague) {
                        dataMatched = qrecvVague(matcherRecord.group("receiver"), opQQ);
                    } else {
                        if (matcherRecord.group("receiver").equals(
                                matcherCommand.group("toSearch"))) {
                            dataMatched = true;
                        }
                    }
                    // 2. 在冒号后（正文里）寻找
                } else if (matcherRecord.group("message").indexOf('@') != -1) {
                    Matcher tempMatcher = patternRec.matcher(matcherRecord.group("message"));
                    if (tempMatcher.find()) {
                        if (vague) {
                            dataMatched = qrecvVague(tempMatcher.group("receiver"), opQQ);
                        } else {
                            if (tempMatcher.group("receiver").equals(
                                    matcherCommand.group("toSearch"))) {
                                dataMatched = true;
                            }
                        }
                    }
                }
            }
            if (dataMatched) {
                if (matcherCommand.group("toChange") != null) {
                    String tempMessage = clean(matcherCommand.group("toChange"));
                    temp = temp.replace(matcherRecord.group("message"), tempMessage);
                }
                System.out.println(temp + ";");
            }
        }
    }

    public static boolean qrecvVague(String receiver, String opQQ) {
        boolean dataMatched = false;
        switch (opQQ) {
            case "ssq":
                char[] senderArray = receiver.toCharArray();
                char[] toSearchArray = matcherCommand.group("toSearch").toCharArray();
                int cnt = 0;
                for (char tempChar :
                        senderArray) {
                    if (tempChar == toSearchArray[cnt]) {
                        cnt++;
                        if (cnt == matcherCommand.group("toSearch").length()) {
                            dataMatched = true;
                            break;
                        }
                    }
                }
                break;
            case "ssr":
                if (receiver.contains(
                        matcherCommand.group("toSearch"))) {
                    dataMatched = true;
                }
                break;
            case "pre":
                if (receiver.indexOf(
                        matcherCommand.group("toSearch")) == 0) {
                    dataMatched = true;
                }
                break;
            case "pos":
                if (receiver.contains(matcherCommand.group("toSearch")) &&
                        (receiver.lastIndexOf(matcherCommand.group("toSearch")) ==
                                receiver.length() - matcherCommand.group("toSearch").length())) {
                    dataMatched = true;
                }
                break;
            default:
                break;
        }
        return dataMatched;
    }

    public static String clean(String toChange) {
        String content = matcherRecord.group("message");
        char[] stars = new char[toChange.length()];
        for (int i = 0; i < toChange.length(); i++) {
            stars[i] = '*';
        }
        String starsString = new String(stars);
        content = content.replace(toChange, starsString);
        return content;
    }
}

