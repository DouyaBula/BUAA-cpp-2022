import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

class Adventurer {
    private int id;
    private String name;
    private Map<Integer, Equipment> equipments;
    private double health;
    private double exp;
    private double money;

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        equipments = new HashMap<>();
        health = 100.0f;
        exp = 0.0f;
        money = 0.0f;
    }

    public int getId() {
        return id;
    }

    public void addEquipment(Equipment equipment) {
        equipments.put(equipment.getId(), equipment);
    }

    public void delEquipment(int equipmentId) {
        equipments.remove(equipmentId);
    }

    public BigInteger getSumPrice() {
        BigInteger sum = BigInteger.ZERO;
        BigInteger t;
        for (Integer i :
                equipments.keySet()) {
            t = BigInteger.valueOf(equipments.get(i).getPrice());
            sum = sum.add(t);
        }
        return sum;
    }

    public long getMaxPrice() {
        long max = 0;
        for (Integer n :
                equipments.keySet()) {
            if (equipments.get(n).getPrice() > max) {
                max = equipments.get(n).getPrice();
            }
        }
        return max;
    }

    public long getNumEquipment() {
        return equipments.size();
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

    public String getName() {
        return name;
    }

    public Map<Integer, Equipment> getEquipments() {
        return equipments;
    }

    public void printInfo() {
        System.out.println(
                "The adventurer's id is " + this.id + ", name is "
                        + this.name + ", health is " + this.health
                        + ", exp is " + this.exp + ", money is " + this.money + "."
        );
    }

    public void use(Equipment equipment) {
        try {
            equipment.usedBy(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}