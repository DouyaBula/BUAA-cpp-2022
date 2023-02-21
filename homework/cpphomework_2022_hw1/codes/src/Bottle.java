class Bottle {
    private int id;
    private String name;
    private long price;
    private double capacity;
    private boolean filled;

    public Bottle(int id, String name, long price, double capacity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.filled = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public double getCapacity() {
        return capacity;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public void printInfo() {
        System.out.println(
                "The bottle's id is " + this.id + ", name is "
                        + this.name + ", capacity is " + this.capacity
                        + ", filled is " + this.filled + "."
        );
    }
}