import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class Adventurer implements Commodity, Cloneable {
    private int id;
    private String name;
    private Map<Integer, Commodity> commodities;
    private double health;
    private double exp;
    private double money;

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        commodities = new HashMap<>();
        health = 100.0f;
        exp = 0.0f;
        money = 0.0f;
    }

    @Override
    public int getId() {
        return id;
    }

    public void addCommodity(Commodity commodity) {
        commodities.put(commodity.getId(), commodity);
    }

    public void delCommodity(int commodityId) {
        commodities.remove(commodityId);
    }

    @Override
    public BigInteger getPriceBig() {
        BigInteger sum = BigInteger.ZERO;
        BigInteger t;
        for (Integer i :
                commodities.keySet()) {
            t = commodities.get(i).getPriceBig();
            sum = sum.add(t);
        }
        return sum;
    }

    public BigInteger getMaxPrice() {
        BigInteger max = BigInteger.ZERO;
        BigInteger temp;
        for (Integer n :
                commodities.keySet()) {
            temp = commodities.get(n).getPriceBig();
            if (max.compareTo(temp) < 0) {
                max = temp;
            }
        }
        return max;
    }

    public long getNumCommodity() {
        return commodities.size();
    }

    public double getHealth() {
        return health;
    }

    public double getExp() {
        return exp;
    }

    public double getMoney() {
        return money;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setCommodities(Map<Integer, Commodity> commodities) {
        this.commodities = commodities;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Commodity> getCommodities() {
        return commodities;
    }

    @Override
    public void printInfo() {
        System.out.println(
                "The adventurer's id is " + this.id + ", name is "
                        + this.name + ", health is " + this.health
                        + ", exp is " + this.exp + ", money is " + this.money + "."
        );
    }

    public void use(Adventurer father) {
        ArrayList<Commodity> list = new ArrayList<>(this.commodities.values());
        Collections.sort(list);
        try {
            for (Commodity temp :
                    list) {
                temp.usedBy(father);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Finish.
    @Override
    public void usedBy(Adventurer father) throws Exception {
        use(father);
    }

    //用getPriceBig吧，一个个改BigInteger要狗命了，tnnd不早说。
    @Override
    public long getPrice() {
        return 0L;
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