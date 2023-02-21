public class StringForm extends Value {
    private String type;
    private String content;

    public StringForm(String type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getContent() {
        return content;
    }
}
