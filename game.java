package org.example;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.play();
    }
}

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

        Shop[] shop = new Shop[3];

        Inventory inventory = new Inventory();

        Item[] item = ItemFactory.createItem();

        Loot loot = new Loot();

        System.out.println
                (
                        "=== Player ===" +
                                "\n(1) Warrior (HP:550)(AD:30)" +
                                "\n(2) Mage (HP:200)(AD:60)" +
                                "\n(3) Archer (HP:350)(AD:20)\n" +
                                "=== SELECT ==="
                );

        plchoise = scanner.nextInt();
        plchoise--;
        System.out.println("You Selected " + player[plchoise].name);

        inventory.addItem(item[1]);

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
                                "=== KAMPF ===" +
                                        "\n\n------------------------------------" +
                                        "\nPlayer: " + player[plchoise].name +
                                        "\nHP: " + player[plchoise].health + "/" + player[plchoise].maxHealth +
                                        "\nCoins: " + player[plchoise].coins +
                                        "\nEP: " + player[plchoise].exp +
                                        "\n------------------------------------" +
                                        "\n\n\n------------------------------------" +
                                        "\nGegner: " + enemy[ran].name +
                                        "\nHP: " + enemy[ran].health + "/" + enemy[ran].maxHealth +
                                        "\n------------------------------------" +
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

                        {
                            IO.println("Ungültige Auswahl!");
                        }

                    break;
                    case 4:
                        break out;
                }

                if (enemy[ran].health == 0)
                {
                    player[plchoise].coins += enemy[ran].coins;
                    player[plchoise].exp += enemy[ran].exp;
                    System.out.println
                            (
                                    "Du hast ein " + enemy[ran].name + " besiegt und hast " + enemy[ran].coins + " Coins und " + enemy[ran].exp + " EP bekommen!"

                            );

                    loot.Chest(inventory, random);
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

    public int size() {
        return items.size();
    }
}

class Loot{



    public void Chest(Inventory inventory, Random random) throws InterruptedException {
        int key = 3;
        if (key == 3)
        {
            Item randomItem = ItemFactory.getRandom(random);
            inventory.addItem(randomItem);
            System.out.println("DU hast ein Chest gefunden, sehen wir nach was drinnen ist...");
            Thread.sleep(700);
            System.out.println("Du hast " + randomItem.name + " gefunden!");
        }
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
                new Item("OneShot Item", 1000, 0, 100, 80),
                new Item("Heal(100)", 0, 100, 20, 15),
                new Item("Test", 35, 25, 20, 15)
        };
    }

    public static Item getRandom(Random random){
        Item[] items = createItem();

        int ran = random.nextInt(items.length);

        return items[ran];
    }
}
