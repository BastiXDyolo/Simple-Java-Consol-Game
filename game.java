package org.example;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

// Spiel Start
public class Main {
    public static void main() throws InterruptedException {
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
    int level;
    boolean alive;
    int expToNextLevel = 100;

    public Player(String name, int health, int maxHealth, int attackDamage, int coins, int exp, boolean alive, int level) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.attackDamage = attackDamage;
        this.coins = coins;
        this.exp = exp;
        this.level = level;
        this.alive = alive;
    }

    public void attack(Enemy target) {
        target.health = Math.max(0, target.health - attackDamage);
    }

    public void heal() {
        health = Math.min(maxHealth, health + 25 );
    }

    public void revive(){
        health = maxHealth;
        coins = 0;
        exp = 0;
    }
}

// hier wird Enemy erstellt und Angriff gesteuert
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

// Item Logik, hier werden Items erstellt und verwendet
class Item{
    String name;
    int attackDamage;
    int heal;
    int price;
    int sellPrice;
    boolean onetimeuse;

    public Item(String name, int attackDamage, int heal, int price, int sellPrice, boolean onetimeuse){
        this.name = name;
        this.attackDamage = attackDamage;
        this.heal = heal;
        this.price = price;
        this.sellPrice = sellPrice;
        this.onetimeuse = onetimeuse;
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
        // hier sind alle Klassen und Variablen, die ich oft gebraucht habe/wichtigsten
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int plchoise;
        // ran = random
        int ran;
        int action;

        Player[] player = PlayerFactory.createPlayers();
        Enemy[] enemy = EnemyFactory.createEnemy();
        Item[] item = ItemFactory.createItem();
        Inventory inventory = new Inventory();
        Level level = new Level();
        Loot loot = new Loot();
        DevTools devTools = new DevTools();
        ShopCreater shopCreater = new ShopCreater();

        // TUI
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

        // Cheat
        //devTools.getallItems(item, inventory);

        // Kämpfen bis Player tot ist
        out: while(player[plchoise].health >= 0)
        {
            ran = random.nextInt(3);
            enemy[ran].health = enemy[ran].maxHealth;
            int healSleep;

            // Kämpfen bis Enemy tot ist
            fight: while (enemy[ran].health >= 0 || player[plchoise].health >= 0)
            {

                // Schauen ob Player noch lebt, wenn nicht = spiel beenden
                if (player[plchoise].health <= 0)
                {
                    player[plchoise].revive();
                    System.out.println
                            (
                                    "\nDu bist gestorben!" +
                                            "\nDas Spiel wird beendet!"
                            );
                    // break erste while-Schleife
                    break out;
                }

                // TUI
                System.out.println
                        (
                                "\n======== KAMPF ========" +
                                        "\nPlayer: " + player[plchoise].name +
                                        "\nLVL: " + player[plchoise].level +
                                        "\nHP: " + player[plchoise].health + "/" + player[plchoise].maxHealth +
                                        "\nCoins: " + player[plchoise].coins +
                                        "\nEP: " + player[plchoise].exp + "/" + player[plchoise].expToNextLevel +
                                        "\n-----------------------" +
                                        "\n\n\n-----------------------" +
                                        "\nGegner: " + enemy[ran].name +
                                        "\nHP: " + enemy[ran].health + "/" + enemy[ran].maxHealth +
                                        "\n=======================" +
                                        "\n\n(1) ANGREIFEN\n" + "(2) HEILEN\n" + "(3) INVENTAR\n" + "(4) FLIEHEN\n"
                        );

                // Auf die auswahl des Players reagieren
                action = scanner.nextInt();
                switch (action)
                {
                    case 1:
                        // Angriff Logik
                        player[plchoise].attack(enemy[ran]);

                        if (enemy[ran].health <= 0)
                        {
                            break;
                        }
                        else
                        {
                            enemy[ran].attack(player[plchoise]);
                        }
                        // TUI
                        System.out.println
                                (
                                        "Der Gegner hat noch " + enemy[ran].health + "/" + enemy[ran].maxHealth + " HP" +
                                                "\nDu hast " + enemy[ran].attackDamage + " schaden bekommen. Du hast noch " +
                                                player[plchoise].health + "/" + player[plchoise].maxHealth + " HP"
                                );
                        break;

                    case 2:
                        // Player heal Logik
                        player[plchoise].heal();

                        break;

                    case 3:
                        // Inventar Logik
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


                        if (inventory.getItem(actionInv).onetimeuse == true)
                        {
                            inventory.removeItem(selectedItem);
                        }
                        if (enemy[ran].health > 0)
                        {
                            enemy[ran].attack(player[plchoise]);
                        }

                    {
                        IO.println("Ungültige Auswahl!");
                    }

                    break;
                    case 4:
                        // fliehen / exit game
                        break out;
                }

                // wenn Enemy tot gibt es dem Player coins, exp und mit Glück chest und shop
                if (enemy[ran].health == 0)
                {
                    // TUI
                    System.out.println
                            (
                                    "Du hast ein " + enemy[ran].name + " besiegt und hast " + enemy[ran].coins + " Coins und " + enemy[ran].exp + " EP bekommen!" +
                                            "\nDruecke Taste ENTER um weiter zu spielen!"
                            );
                    loot.getEnemyDrop(player[plchoise], enemy[ran]);
                    level.LevelUp(player[plchoise]);
                    loot.Chest(inventory, random);

                    int ran1 = random.nextInt(2);
                    int key = 1;

                    if (key == ran1)
                    {
                        // TUI
                        System.out.println("Du hast einen Shop gefunden!");
                        System.out.println("Reingehen?");
                        System.out.println("(1) Ja");
                        System.out.println("(2) Nein");

                        int choice = scanner.nextInt();

                        if (choice == 1)
                        {
                            shopCreater.createShop(random, item, player[plchoise], inventory);
                        }
                    }

                    break fight;
                }

                // Consolen schrubber(Console bereinigen)
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            }
        }
    }
}

// managed das inventar bzw. es lagert items
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

// Chest spawn Logik und Enemy Coin und exp Drops
class Loot{
    Scanner scanner = new Scanner(System.in);

    public void Chest(Inventory inventory, Random random) {
        int key = random.nextInt(6);
        if (key == 3)
        {
            // TUI
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

    public void getEnemyDrop(Player player, Enemy enemy){
        player.coins += enemy.coins;
        player.exp += enemy.exp;
        scanner.nextLine();
    }
}

// hier werden Player Charakter erstellt
class PlayerFactory {

    public static Player[] createPlayers() {
        return new Player[] {
                new Player("Warrior", 450, 450, 35, 0, 0, true, 1),
                new Player("Mage", 260, 260, 55, 0, 0, true, 1),
                new Player("Archer", 350, 350, 30, 0, 0, true, 1)
        };
    }
}

// hier werden Enemys erstellt
class EnemyFactory {

    public static Enemy[] createEnemy() {
        return new Enemy[] {
                new Enemy("Zombie", 100, 100, 25, 8, 20, true),
                new Enemy("Goblin", 140, 140, 27, 10, 25, true),
                new Enemy("Dragon", 300, 300, 32, 25, 60, true),
        };
    }
}

// hier werden alle items erstellt, hier mit Randomizer Logik
class ItemFactory {

    public static Item[] createItem() {
        return new Item[] {
                new Item("OneShot Item (1000 AD)", 1000, 0, 100, 80, true),
                new Item("Heal Potion (+100 HP)", 0, 100, 20, 15, true),
                new Item("Heavy Sword (120 AD | -10 HP)", 120, -10, 30, 20, false),
                new Item("Test", 35, 25, 20, 15, false)
                // new Item("CleanItem", 1000, 1000, 1000, 1000),
        };
    }

    public static Item getRandom(Random random){
        Item[] items = createItem();

        int ran = random.nextInt(items.length);

        return items[ran];
    }
}

// hiermit kann man sehr leicht den shop erstellen
class ShopCreater{

    public Item[] createShop(Random random, Item[] items, Player player, Inventory inventory) {
        Scanner scanner = new Scanner(System.in);
        Item[] shopItems = new Item[3];
        shopItems[0] = ItemFactory.getRandom(random);
        shopItems[1] = ItemFactory.getRandom(random);
        shopItems[2] = ItemFactory.getRandom(random);

        while (true) {
            for (int i = 0; i < 3; i++) {
                System.out.println();
            }

            System.out.println("Coins: " + player.coins);
            System.out.println("=== Shopify ===");
            System.out.println("(" + 1 + ") " + shopItems[0].name + " (" + shopItems[0].attackDamage + "AD | " + shopItems[0].heal + " HP)" + "   Coins: " + shopItems[0].price);
            System.out.println("(" + 2 + ") " + shopItems[1].name + " (" + shopItems[1].attackDamage + "AD | " + shopItems[1].heal + " HP)" + "   Coins: " + shopItems[1].price);
            System.out.println("(" + 3 + ") " + shopItems[2].name + " (" + shopItems[2].attackDamage + "AD | " + shopItems[2].heal + " HP)" + "   Coins: " + shopItems[2].price);
            System.out.println("(" + 4 + ") Exit");
            System.out.println("===============");

            int choice = scanner.nextInt();
            int choiceIndex = choice - 1;

            if (4 == choice) {
                return shopItems;
            }

            if (player.coins >= shopItems[choiceIndex].price) {
                player.coins -= shopItems[choiceIndex].price;
                inventory.addItem(shopItems[choiceIndex]);
                break;
            } else {
                System.out.println("Nicht genuegend Geld!");
            }
        }
        System.out.println("\nThank You :)");

        return shopItems;
    }
}

// hier wird exp zu lvl konvertiert
class Level{
     public void LevelUp(Player player){
         while (player.exp >= player.expToNextLevel) {

             player.exp -= player.expToNextLevel;
             player.level++;

             player.expToNextLevel += 50;

             player.coins += 20;

             System.out.println("LEVEL UP!");
             System.out.println("Dein Level: " + player.level);
             System.out.println("EXP fuer naechstes Level: " + player.expToNextLevel);
         }

     }
}

// Cheats ;)
class DevTools{
    public void getallItems(Item[] items, Inventory inventory){
        for (int i = 0; i < items.length; i++) {
            inventory.addItem(items[i]);
        }
    }
}
