package org.example;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

// Spiel Start
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.play();
    }
}

// Was kann Player und was hat Player für Werte
class Player {
    String name;
    int health;
    int maxHealth;
    int attackDamage;
    int coins;
    int exp;
    boolean alive;

    public Player(String name, int health, int maxHealth, int attackDamage, int coins, int exp, boolean alive) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.attackDamage = attackDamage;
        this.coins = coins;
        this.exp = exp;
        this.alive = alive;
    }

    public void attack(Enemy target) {
        target.health = Math.max(0, target.health - attackDamage);
    }

    public void heal() {
        health = Math.min(maxHealth, health + 35 );
    }

    public void revive(){
        health = maxHealth;
        coins = 0;
        exp = 0;
    }
    public void spell(){

    }
}

// Was kann Enemy und was hat Enemy für Werte
class Enemy {
    String name;
    int health;
    int maxHealth;
    int attackDamage;
    int coins;
    int exp;
    boolean alive;

    public Enemy(String name, int health, int maxHealth, int attackDamage, int coins, int exp, boolean alive) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.attackDamage = attackDamage;
        this.coins = coins;
        this.exp = exp;
        this.alive = alive;
    }

    public void attack(Player target) {
        target.health = Math.max(0, target.health - attackDamage);
    }
}

class Shop {
    String name;
    int attackDamage;
    int heal;
    int price;

    public Shop(String name, int attackDamage, int heal, int price){
        this.name = name;
        this.attackDamage = attackDamage;
        this.heal = heal;
        this.price = price;
    }
}

class Item{
    String name;
    int attackDamage;
    int heal;
    int price;
    int sellPrice;

    public Item(String name, int attackDamage, int heal, int price, int sellPrice){
        this.name = name;
        this.attackDamage = attackDamage;
        this.heal = heal;
        this.price = price;
        this.sellPrice = sellPrice;
    }

    public void use(Player player, Enemy enemy)
    {
        if(this.heal > 0 && player.health <= player.maxHealth)
        {
            if (player.health <= Math.max(player.maxHealth, player.maxHealth - 100))
            {
                player.health = Math.min(player.maxHealth, player.health + this.heal);
            }
        }

        if (this.attackDamage > 0)
        {
            enemy.health = Math.max(0, enemy.health - attackDamage);
        }
    }
}

class Game{

    public void play() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int plchoise;
        int ran = random.nextInt(3);
        int action;

        Player[] player = PlayerFactory.createPlayers();

        Enemy[] enemy = EnemyFactory.createEnemy();

        Inventory inventory = new Inventory();

        Item[] item = ItemFactory.createItem();

        Loot loot = new Loot();

        System.out.println
                (
                        "=== Player ===" +
                                "\n(1) Warrior (HP 550 | AD 30)" +
                                "\n(2) Mage (HP 200 | AD 60)" +
                                "\n(3) Archer (HP 350 | AD 20)\n" +
                                "=== SELECT ==="
                );

        plchoise = scanner.nextInt();
        plchoise--;
        System.out.println("You Selected " + player[plchoise].name);

        out: while(player[plchoise].health >= 0)
        {
            ran = random.nextInt(3);
            enemy[ran].health = enemy[ran].maxHealth;

            // Kämpfen bis
            fight: while (enemy[ran].health >= 0 || player[plchoise].health >= 0)
            {
                if (player[plchoise].health <= 0)
                {
                    player[plchoise].revive();
                    System.out.println
                            (
                                    "\nDu bist gestorben!" +
                                            "\nDas Spiel wird beendet!"
                            );
                    break out;
                }

                System.out.println
                        (
                                "\n======== KAMPF ========" +
                                        "\nPlayer: " + player[plchoise].name +
                                        "\nHP: " + player[plchoise].health + "/" + player[plchoise].maxHealth +
                                        "\nCoins: " + player[plchoise].coins +
                                        "\nEP: " + player[plchoise].exp +
                                        "\n-----------------------" +
                                        "\n\n\n-----------------------" +
                                        "\nGegner: " + enemy[ran].name +
                                        "\nHP: " + enemy[ran].health + "/" + enemy[ran].maxHealth +
                                        "\n=======================" +
                                        "\n\n(1) ANGREIFEN\n" + "(2) HEILEN\n" + "(3) INVENTAR\n" + "(4) FLIEHEN\n"
                        );

                action = scanner.nextInt();
                switch (action)
                {
                    case 1:
                        player[plchoise].attack(enemy[ran]);

                        if (enemy[ran].health <= 0)
                        {
                            break;
                        }
                        else
                        {
                            enemy[ran].attack(player[plchoise]);
                        }

                        System.out.println
                                (
                                        "Der Gegner hat noch " + enemy[ran].health + "/" + enemy[ran].maxHealth + " HP" +
                                                "\nDu hast " + enemy[ran].attackDamage + " schaden bekommen. Du hast noch " +
                                                player[plchoise].health + "/" + player[plchoise].maxHealth + " HP"
                                );
                        break;

                    case 2:
                        player[plchoise].heal();
                        break;

                    case 3:
                        int z = 1;
                        for(int i = 0; i < inventory.size(); i++)
                        {
                            Item UpdatetItem = inventory.getItem(i);
                            System.out.println("(" + z + ")" + UpdatetItem.name);
                            z++;
                        }

                        System.out.println("(" + z + ")" + "Exit");

                        int actionInv = scanner.nextInt();

                        if (actionInv == z)
                        {
                            break;
                        }

                        actionInv--;
                        Item selectedItem = inventory.getItem(actionInv);
                        selectedItem.use(player[plchoise], enemy[ran]);
                        inventory.removeItem(selectedItem);

                    {
                        IO.println("Ungültige Auswahl!");
                    }

                    break;
                    case 4:
                        break out;
                }

                if (enemy[ran].health == 0)
                {
                    System.out.println
                            (
                                    "Du hast ein " + enemy[ran].name + " besiegt und hast " + enemy[ran].coins + " Coins und " + enemy[ran].exp + " EP bekommen!" +
                                            "\nDruecke Taste ENTER um weiter zu spielen!"
                            );
                    loot.getMobDrop(player[plchoise], enemy[ran]);
                    loot.Chest(inventory, random);

                    int ran1 = random.nextInt(2);
                    int key = 1;

                    if (key == ran1)
                    {
                        System.out.println("Du hast einen Shop gefunden!");
                        System.out.println("Rein gehen?");
                        System.out.println("(1) Ja");
                        System.out.println("(2) Nein");

                        int choice = scanner.nextInt();

                        if (choice == 1)
                        {
                            ShopCreater.createShop(random, inventory, player[plchoise]);
                        }
                    }

                    break fight;
                }

                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            }
        }
    }
}

class Inventory{
    private ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public void removeItem(Item item){
        items.remove(item);
    }

    public int size() {
        return items.size();
    }
}

class Loot{
    Scanner scanner = new Scanner(System.in);

    public void Chest(Inventory inventory, Random random) {
        int key = random.nextInt(6);
        if (key == 3)
        {
            Item randomItem = ItemFactory.getRandom(random);
            inventory.addItem(randomItem);
            System.out.println("DU hast ein Chest gefunden, sehen wir nach was drinnen ist...");
            System.out.println(
                    "Du hast " + randomItem.name + " gefunden!" +
                    "\nDruecke Taste ENTER um weiter zu spielen!"
            );
            scanner.nextLine();
        }
    }

    public void getMobDrop(Player player, Enemy enemy){
        player.coins += enemy.coins;
        player.exp += enemy.exp;
        scanner.nextLine();
    }
}


class PlayerFactory {

    public static Player[] createPlayers() {
        return new Player[] {
                new Player("Warrior", 550, 550, 30, 0, 0, true),
                new Player("Mage", 200, 200, 60, 0, 0, true),
                new Player("Archer", 350, 350, 20, 0, 0, true)
        };
    }
}

class EnemyFactory {

    public static Enemy[] createEnemy() {
        return new Enemy[] {
                new Enemy("Zombie", 150, 150, 10, 2, 15, true),
                new Enemy("Goblin", 100, 100, 15, 1, 10, true),
                new Enemy("Dragon", 350, 350, 20, 3, 25, true),
        };
    }
}

class ItemFactory {

    public static Item[] createItem() {
        return new Item[] {
                new Item("OneShot Item (1000 AD)", 1000, 0, 100, 80),
                new Item("Heal Potion (+100 HP)", 0, 100, 20, 15),
                new Item("Heavy Sword (120 AD | -10 HP)", 120, -10, 30, 20),
                new Item("Test", 35, 25, 20, 15)
                // new Item("CleanItem", 1000, 1000, 1000, 1000),
        };
    }

    public static Item getRandom(Random random){
        Item[] items = createItem();

        int ran = random.nextInt(items.length);

        return items[ran];
    }
}

class ShopCreater{

    public static Item[] createShop(Random random, Inventory inventory, Player player) {
        Scanner scanner = new Scanner(System.in);
        Item[] shopItems = new Item[3];

        money: while (true) {

            for (int i = 0; i < 3; i++) {
                System.out.println();
            }

            int z = 1;

            System.out.println("Coins: " + player.coins);
            System.out.println("=== Shopify ===");

            for (int i = 0; i < 3; i++) {
                shopItems[i] = ItemFactory.getRandom(random);
                System.out.println("(" + z + ") " + shopItems[i].name + " (" + shopItems[i].attackDamage + "AD | " + shopItems[i].heal + " HP)" + "   Coins: " + shopItems[i].price);
                z++;
            }
            System.out.println("(" + z + ") Exit");
            System.out.println("===============");

            int choice = scanner.nextInt();

            if (z == choice) {
                return shopItems;
            }

            choice--;

            if (player.coins >= shopItems[choice].price) {
                player.coins -= shopItems[choice].price;
            } else {
                System.out.println("Nicht genuegend Geld!");
                continue money;
            }

            inventory.addItem(shopItems[choice]);
            System.out.println("\nThank You :)");
            break money;

        }
        return shopItems;
    }
}
