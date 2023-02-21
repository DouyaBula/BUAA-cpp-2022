import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Map<Integer, Adventurer>> branches = new HashMap<>();
    private static String branchNow = "1"; //当前branch名称
    private static String branchName = "1"; //读入用

    private static Map<Integer, Adventurer> adventurers1 = new HashMap<>();    //第一个advs

    public static void main(String[] argv) {
        branches.put(branchNow, adventurers1);   //将第一个advs放入branches
        Map<Integer, Adventurer> adventurers = branches.get(branchNow);  //指针指向当前分支（第一个）
        int m;
        int advId = 0;
        int op;
        m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            op = scanner.nextInt();
            if (op < 11) {
                advId = scanner.nextInt();
            } else {
                branchName = scanner.next();
            }
            switch (op) {
                case 1:
                    adventurers.put(advId, new Adventurer(advId, scanner.next()));
                    break;
                case 2:
                    Equipment t = generateEquipment(scanner.nextInt());
                    adventurers.get(advId).addCommodity(t);
                    break;
                case 3:
                    adventurers.get(advId).delCommodity(scanner.nextInt());
                    break;
                case 4:
                    System.out.println(adventurers.get(advId).getPriceBig());
                    break;
                case 5:
                    System.out.println(adventurers.get(advId).getMaxPrice());
                    break;
                case 6:
                    System.out.println(adventurers.get(advId).getNumCommodity());
                    break;
                case 7:
                    adventurers.get(advId).getCommodities().get(scanner.nextInt()).printInfo();
                    break;
                case 8:
                    adventurers.get(advId).use(adventurers.get(advId));
                    break;
                case 9:
                    adventurers.get(advId).printInfo();
                    break;
                case 10:
                    adventurers.get(advId).addCommodity(adventurers.get(scanner.nextInt()));
                    break;
                case 11:
                    branches.put(branchName, copy(branches.get(branchNow)));    //深克隆到新分支
                    branchNow = branchName;
                    adventurers = branches.get(branchNow);  //切换到新分支
                    break;
                case 12:
                    branchNow = branchName;
                    adventurers = branches.get(branchNow);  //切换到新分支
                    break;
                default:
                    break;
            }
        }
    }

    static Map<Integer, Adventurer> copy(Map<Integer, Adventurer> adventurers) {
        Map<Integer, Adventurer> newAdventurers = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> employees = new HashMap<>();
        Adventurer temp;
        Commodity tempCommodity;
        for (Adventurer every :
                adventurers.values()) {
            temp = (Adventurer) every.clone();
            temp.setCommodities(new HashMap<>());
            ArrayList<Integer> tempEmployees = new ArrayList<>();
            for (Commodity everyCommodity :
                    every.getCommodities().values()) {
                if (everyCommodity instanceof Equipment) {
                    tempCommodity = (Equipment) ((Equipment) everyCommodity).clone();
                    temp.getCommodities().put(tempCommodity.getId(), tempCommodity);
                } else if (everyCommodity instanceof Adventurer) {
                    tempEmployees.add(everyCommodity.getId());
                }
            }
            employees.put(temp.getId(), tempEmployees);
            newAdventurers.put(temp.getId(), temp);
        }
        for (Adventurer every :
                newAdventurers.values()) {
            if (employees.get(every.getId()) != null) {
                for (Integer t :
                        employees.get(every.getId())) {
                    every.addCommodity(newAdventurers.get(t));
                }
            }
        }
        return newAdventurers;
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