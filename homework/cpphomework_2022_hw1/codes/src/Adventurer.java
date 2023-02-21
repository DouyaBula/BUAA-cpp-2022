import java.math.BigInteger;
import java.util.ArrayList;

class Adventurer {
    private int id;
    private String name;
    private ArrayList<Bottle> bottles;

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        bottles = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Bottle findBottle(int botId) {
        Bottle result = null;
        for (Bottle t :
                bottles) {
            if (t.getId() == botId) {
                result = t;
                break;
            }
        }
        return result;
    }

    public void addBottle(Bottle bottle) {
        bottles.add(bottle);
    }

    public void delBottle(int botId) {
        bottles.remove(findBottle(botId));
    }

    public void changePrice(int botId, long price) {
        findBottle(botId).setPrice(price);
    }

    public void changeFilled(int bodId, boolean filled) {
        findBottle(bodId).setFilled(filled);
    }

    public BigInteger sumPrice() {
        BigInteger sum = BigInteger.ZERO;
        BigInteger t;
        for (Bottle n :
                bottles) {
            t = BigInteger.valueOf(n.getPrice());
            sum = sum.add(t);
        }
        return sum;
    }

    public long maxPrice() {
        long max = 0;
        for (Bottle n :
                bottles) {
            if (n.getPrice() > max) {
                max = n.getPrice();
            }
        }
        return max;
    }
}