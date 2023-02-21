public class EpicSword extends Sword {
    private double evolveRatio;

    public EpicSword(int id, String name, long price, double sharpness, double evolveRatio) {
        super(id, name, price, sharpness);
        this.evolveRatio = evolveRatio;
    }

    @Override
    public void printInfo() {
        System.out.println(
                "The epicSword's id is " + super.getId() + ", name is " + super.getName()
                        + ", sharpness is " + super.getSharpness() + ", evolveRatio is "
                        + this.evolveRatio + "."
        );
    }

    @Override
    public void usedBy(Adventurer adventurer) throws Exception {
        super.usedBy(adventurer);
        super.setSharpness(super.getSharpness() * evolveRatio);
        System.out.println(super.getName() + "'s sharpness became " + super.getSharpness() + ".");
    }
}
