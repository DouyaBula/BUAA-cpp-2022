import java.math.BigInteger;

class Equipment implements Commodity, Cloneable {
    private int id;
    private String name;
    private long price;

    public Equipment(int id, String name, long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public BigInteger getPriceBig() {
        return BigInteger.valueOf(this.price);
    }

    @Override
    public long getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public void printInfo() {
    }

    @Override
    public void usedBy(Adventurer adventurer) {

    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ignore) {
            System.out.println("Copy ignore");
        }
        return null;
    }
}
