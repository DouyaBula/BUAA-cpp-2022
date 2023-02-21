public class ExpBottle extends Bottle {
    private double expRatio;

    public ExpBottle(int id, String name, long price, double capacity, double expRatio) {
        super(id, name, price, capacity);
        this.expRatio = expRatio;
    }

    @Override
    public void printInfo() {
        System.out.println(
                "The expBottle's id is " + super.getId() + ", name is "
                        + super.getName() + ", capacity is " + super.getCapacity()
                        + ", filled is " + super.isFilled()
                        + ", expRatio is " + this.expRatio + "."
        );
    }

    @Override
    public void usedBy(Adventurer adventurer) throws Exception {
        super.usedBy(adventurer);
        adventurer.setExp(adventurer.getExp() * this.expRatio);
        System.out.println(adventurer.getName() + "'s exp became "
                + adventurer.getExp() + "."
        );
    }
}
