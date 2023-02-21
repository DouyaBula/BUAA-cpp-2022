import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] argv) {
        Map<Integer, Adventurer> adventurers = new HashMap<>();
        int m;
        int advId;
        int op;
        m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            op = scanner.nextInt();
            advId = scanner.nextInt();
            switch (op) {
                case 1:
                    adventurers.put(advId, new Adventurer(advId, scanner.next()));
                    break;
                case 2:
                    Equipment t = generateEquipment(scanner.nextInt());
                    adventurers.get(advId).addEquipment(t);
                    break;
                case 3:
                    adventurers.get(advId).delEquipment(scanner.nextInt());
                    break;
                case 4:
                    System.out.println(adventurers.get(advId).getSumPrice());
                    break;
                case 5:
                    System.out.println(adventurers.get(advId).getMaxPrice());
                    break;
                case 6:
                    System.out.println(adventurers.get(advId).getNumEquipment());
                    break;
                case 7:
                    adventurers.get(advId).getEquipments().get(scanner.nextInt()).printInfo();
                    break;
                case 8:
                    adventurers.get(advId).use(
                            adventurers.get(advId).getEquipments().get(scanner.nextInt())
                    );
                    break;
                case 9:
                    adventurers.get(advId).printInfo();
                    break;
                default:
                    break;
            }
        }
    }

    static Equipment generateEquipment(int type) {
        Equipment equipment = null;
        int id = scanner.nextInt();
        String name = scanner.next();
        long price = scanner.nextLong();
        switch (type) {
            case 1:
                equipment = new Bottle(id, name, price, scanner.nextDouble());
                break;
            case 2:
                equipment = new HealingPotion(id, name, price,
                        scanner.nextDouble(), scanner.nextDouble());
                break;
            case 3:
                equipment = new ExpBottle(id, name, price, scanner.nextDouble(),
                        scanner.nextDouble());
                break;
            case 4:
                equipment = new Sword(id, name, price, scanner.nextDouble());
                break;
            case 5:
                equipment = new RareSword(id, name, price, scanner.nextDouble(),
                        scanner.nextDouble());
                break;
            case 6:
                equipment = new EpicSword(id, name, price, scanner.nextDouble(),
                        scanner.nextDouble());
                break;
            default:
                break;
        }
        return equipment;
    }
}