public class Lexer {
    private String tweet;
    private Token token;
    private char[] tweetChars;
    private int pos = -1;

    public Lexer(String tweet) {
        this.tweet = tweet;
    }

    public void next() {
        tweetChars = tweet.toCharArray();
        pos++;
        if (tweetChars[pos] == '{') {
            token = new Token("{", "{");
        } else if (tweetChars[pos] == '}') {
            token = new Token("}", "}");
        } else if (tweetChars[pos] == '[') {
            token = new Token("[", "[");
        } else if (tweetChars[pos] == ']') {
            token = new Token("]", "]");
        } else if (tweetChars[pos] == '\"') {
            token = new Token("PlainString", readString());
        } else if (Character.isDigit(tweetChars[pos])) {
            token = new Token("Number", readNumber());
        } else if (tweetChars[pos] == 't') {
            pos += 3;
            token = new Token("Boolean", "true");
        } else if (tweetChars[pos] == 'f') {
            pos += 4;
            token = new Token("Boolean", "false");
        } else if (tweetChars[pos] == 'n') {
            pos += 3;
            token = new Token("null", "null");
        } else if (tweetChars[pos] == ':') {
            token = new Token(":", ":");
        } else if (tweetChars[pos] == ',') {
            token = new Token(",", ",");
        }
    }

    public String readString() {
        StringBuilder temp = new StringBuilder();
        pos++;
        while (tweetChars[pos] != '\"') {
            temp.append(tweetChars[pos]);
            pos++;
        }
        return temp.toString();
    }

    public String readNumber() {
        StringBuilder temp = new StringBuilder();
        while (Character.isDigit(tweetChars[pos])) {
            temp.append(tweetChars[pos]);
            pos++;
        }
        pos--;
        return temp.toString();
    }

    public Token getToken() {
        return token;
    }
}
