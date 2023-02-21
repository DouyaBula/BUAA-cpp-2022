import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] argv) {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        int m;
        int advId;
        int op;
        Scanner scanner = new Scanner(System.in);
        m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            op = scanner.nextInt();
            advId = scanner.nextInt();
            switch (op) {
                case 1:
                    adventurers.add(new Adventurer(advId, scanner.next()));
                    break;
                case 2:
                    findAdv(adventurers, advId).addBottle(new Bottle(scanner.nextInt(),
                            scanner.next(), scanner.nextLong(), scanner.nextDouble()));
                    break;
                case 3:
                    findAdv(adventurers, advId).delBottle(scanner.nextInt());
                    break;
                case 4:
                    findAdv(adventurers, advId).changePrice(scanner.nextInt(), scanner.nextLong());
                    break;
                case 5:
                    findAdv(adventurers, advId).changeFilled(
                            scanner.nextInt(), scanner.nextBoolean());
                    break;
                case 6:
                    System.out.println(findAdv(adventurers,advId).findBottle(
                            scanner.nextInt()).getName());
                    break;
                case 7:
                    System.out.println(findAdv(adventurers,advId).findBottle(
                            scanner.nextInt()).getPrice());
                    break;
                case 8:
                    System.out.println(findAdv(adventurers,advId).findBottle(
                            scanner.nextInt()).getCapacity());
                    break;
                case 9:
                    System.out.println(
                            findAdv(adventurers,advId).findBottle(scanner.nextInt()).isFilled()
                    );
                    break;
                case 10:
                    findAdv(adventurers, advId).findBottle(scanner.nextInt()).printInfo();
                    break;
                case 11:
                    System.out.println(findAdv(adventurers, advId).sumPrice());
                    break;
                case 12:
                    System.out.println(findAdv(adventurers, advId).maxPrice());
                    break;
                default:
                    break;
            }
        }
    }

    public static Adventurer findAdv(ArrayList<Adventurer> adventurers, int id) {
        Adventurer result = null;
        for (Adventurer t :
                adventurers) {
            if (t.getId() == id) {
                result = t;
            }
        }
        return result;
    }

}