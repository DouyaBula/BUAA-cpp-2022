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
    public void usedBy(Adventurer adventurer) {
        if (!isFilled()) {
            System.out.println("Failed to use " + super.getName() + " because it is empty.");
        } else {
            adventurer.setHealth(adventurer.getHealth() + super.getCapacity() / 10.0);
            super.setFilled(false);
            super.setPrice((long) Math.floor(super.getPrice() / 10.0));
            System.out.println(adventurer.getName() + " drank " + super.getName()
                    + " and recovered " + super.getCapacity() / 10.0 + "."
            );

            adventurer.setExp(adventurer.getExp() * this.expRatio);
            System.out.println(adventurer.getName() + "'s exp became "
                    + adventurer.getExp() + "."
            );
        }
    }
}
