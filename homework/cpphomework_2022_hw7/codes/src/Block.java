import java.util.HashMap;

public class Block extends Value implements Comparable<Block> {
    private String type;
    private HashMap<String, Value> content;

    public Block(HashMap<String, Value> content) {
        this.type = "Block";
        this.content = content;
    }

    public void addContent(String name, Value value) {
        content.put(name, value);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public HashMap<String, Value> getContent() {
        return content;
    }

    @Override
    public int compareTo(Block o) {
        int thisCount = Integer.parseInt((String) this.getContent().get("count").getContent());
        int thatCount = Integer.parseInt((String) o.getContent().get("count").getContent());
        String thisName = (String) this.getContent().get("name").getContent();
        String thatName = (String) o.getContent().get("name").getContent();
        if (thisCount == thatCount) {
            return thisName.compareTo(thatName);
        } else {
            return thatCount - thisCount;
        }
    }
}
