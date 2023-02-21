class Bottle extends Equipment {
    private double capacity;
    private boolean filled;

    public Bottle(int id, String name, long price, double capacity) {
        super(id, name, price);
        this.capacity = capacity;
        this.filled = true;
    }

    public double getCapacity() {
        return capacity;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public void printInfo() {
        System.out.println(
                "The bottle's id is " + super.getId() + ", name is "
                        + super.getName() + ", capacity is " + this.capacity
                        + ", filled is " + this.filled + "."
        );
    }

    @Override
    public void usedBy(Adventurer adventurer) throws Exception {
        if (!isFilled()) {
            throw new Exception("Failed to use " + super.getName() + " because it is empty.");
        }
        adventurer.setHealth(adventurer.getHealth() + this.capacity / 10.0);
        this.filled = false;
        super.setPrice((long) Math.floor(super.getPrice() / 10.0));
        System.out.println(adventurer.getName() + " drank " + super.getName()
                + " and recovered " + this.capacity / 10.0 + "."
        );
    }
}