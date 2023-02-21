import java.util.ArrayList;

public class Array extends Value {
    private String type;
    private ArrayList<Block> content;

    public Array(ArrayList<Block> content) {
        this.type = "Array";
        this.content = content;
    }

    public void addContent(Block block) {
        content.add(block);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public ArrayList<Block> getContent() {
        return content;
    }
}
