import java.math.BigInteger;

interface Commodity extends Comparable<Commodity> {
    public void usedBy(Adventurer adventurer) throws Exception;

    public void printInfo();

    public long getPrice();

    public BigInteger getPriceBig();

    public int getId();

    @Override
    default int compareTo(Commodity other) {
        if (this.getPriceBig().compareTo(other.getPriceBig()) < 0) {
            return 1;
        } else if (this.getPriceBig().compareTo(other.getPriceBig()) > 0) {
            return -1;
        }

        if (this.getId() < other.getId()) {
            return 1;
        } else if (this.getId() > other.getId()) {
            return -1;
        }

        return 0;
    }
}
