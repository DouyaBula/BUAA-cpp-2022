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
    public void usedBy(Adventurer adventurer) throws Exception {
        super.usedBy(adventurer);
        adventurer.setHealth(adventurer.getHealth() + super.getCapacity() * this.efficiency);
        System.out.println(adventurer.getName() + " recovered extra "
                + super.getCapacity() * this.efficiency + "."
        );
    }
}
