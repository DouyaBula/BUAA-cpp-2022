public class RareSword extends Sword {
    private double extraExpBonus;

    public RareSword(int id, String name, long price, double sharpness, double extraExpBonus) {
        super(id, name, price, sharpness);
        this.extraExpBonus = extraExpBonus;
    }

    @Override
    public void printInfo() {
        System.out.println(
                "The rareSword's id is " + super.getId() + ", name is " + super.getName()
                        + ", sharpness is " + super.getSharpness() + ", extraExpBonus is "
                        + this.extraExpBonus + "."
        );
    }

    @Override
    public void usedBy(Adventurer adventurer) throws Exception {
        super.usedBy(adventurer);
        adventurer.setExp(adventurer.getExp() + this.extraExpBonus);
        System.out.println(adventurer.getName() + " got extra exp "
                + this.extraExpBonus + "."
        );
    }
}
