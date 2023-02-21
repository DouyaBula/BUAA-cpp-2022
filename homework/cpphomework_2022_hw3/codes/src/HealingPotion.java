public class HealingPotion extends Bottle {
    private double efficiency;

    public HealingPotion(int id, String name, long price, double capacity, double efficiency) {
        super(id, name, price, capacity);
        this.efficiency = efficiency;
    }

    @Override
    public void printInfo() {
        System.out.println(
                "The healingPotion's id is " + super.getId() + ", name is "
                        + super.getName() + ", capacity is " + super.getCapacity()
                        + ", filled is " + super.isFilled()
                        + ", efficiency is " + this.efficiency + "."
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

            adventurer.setHealth(adventurer.getHealth() + super.getCapacity() * this.efficiency);
            System.out.println(adventurer.getName() + " recovered extra "
                    + super.getCapacity() * this.efficiency + "."
            );
        }
    }
}
