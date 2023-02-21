public class Sword extends Equipment {
    private double sharpness;

    public Sword(int id, String name, long price, double sharpness) {
        super(id, name, price);
        this.sharpness = sharpness;
    }

    public double getSharpness() {
        return sharpness;
    }

    public void setSharpness(double sharpness) {
        this.sharpness = sharpness;
    }

    @Override
    public void printInfo() {
        System.out.println(
                "The sword's id is " + super.getId() + ", name is " + super.getName()
                        + ", sharpness is " + this.sharpness + "."
        );
    }

    @Override
    public void usedBy(Adventurer adventurer) {
        adventurer.setHealth(adventurer.getHealth() - 10.0);
        adventurer.setExp(adventurer.getExp() + 10.0);
        adventurer.setMoney(adventurer.getMoney() + this.sharpness);
        System.out.println(adventurer.getName() + " used "
                + super.getName() + " and earned " + this.sharpness + "."
        );
    }
}
