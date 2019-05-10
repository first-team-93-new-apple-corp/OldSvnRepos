import java.util.Scanner;
import javafx.stage.Stage;
import javafx.application.Application;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class textadventure extends Application {
	
	//Creating Scanner
	public static Scanner sc = new Scanner(System.in);
    public static String userInput = null;
    
    //Creating Item/Enemy Lists
    public static weapon[] weaponList = new weapon[100];
    public static armor[] armorList = new armor[100];
    public static enemy[] enemyList = new enemy[100];
    public static ranged[] rangedList = new ranged[100];
    
    //Creating Variables
    
    //Item/Enemy Levels
    public static int weaponLevel = 0; //s
    public static int rangedLevel = 0; //s
    public static int armorLevel = 0; //s
    public static int enemyLevel = 0; //s
    
    //Gold
    public static int gold = 15; //s
    public static int goldGained;
    
    //Health
    public static int playerHealth; //s
    public static int enemyHealth; //s
    
    //Game States
    public static boolean encounteredEnemy = false; //s
    public static boolean shop = false; //s
    
    //Temporary Damage
    public static int tempEnemyDamage;
    public static int tempPlayerDamage;
    
    //Shop Prices
    public static int weaponUpgradeCost = 10; //s
    public static int armorUpgradeCost = 15; //s
    public static int rangedUpgradeCost = 10; //s
    
    //Tracks # of Max Potions Bought
    public static int numMaxPotionsBought = 0; //s
    
    //Arrows
    public static int arrowCost = 15;
    public static int numArrows = 5; //s
    
    //Potions
    public static int potionUpgradeCost = 20;
    public static int numHealthPotions = 2; //s
    public static int maxPotionUpgradeCost = 150; //s
    public static int numMaxPotions = 0; //s
    public static int potionHeal = 25; //s
    
    //Tracks # of Upgrades Bought
    public static int numArmorUpgrades; //s
    public static int numWeaponUpgrades; //s
    public static int numRangedUpgrades; //s
    
    public static int numEnemies;
    
    public static int enemyHitMiss;
    
    //Player Stats
    public static int playerLevel = 1; //s
    public static int playerXP = 0; //s
    public static int levelUpgrade = 25; //s
    public static int playerTempXP;
    
    //Tracks if CMD is Open/Closed
    public static boolean cmdOpen = true;
    
    //Tracks what bosses were defeated
    public static boolean dragonDefeated = false; //s
    public static boolean mysticSpiritDefeated = false; //s
    
    //Cheat Code
    public static String cheatCode = "1234567890";
    
    //Limits amount of max potions in shop
    public static int numMaxPotionsInShop = 5; //s
    
    //Limits the melee and ranged tier based off of armor level
    public static int allowedTier = 1; //s
    
    //Generates the save code
    public static void generateSaveCode() {
    	System.out.println("Copy code below:");
    	System.out.println(weaponLevel + "," + rangedLevel + "," + armorLevel + "," + gold + "," + playerHealth + "," + encounteredEnemy + "," + shop + "," + weaponUpgradeCost + "," + armorUpgradeCost + "," + 
    	rangedUpgradeCost + "," + numMaxPotionsBought + "," + numArrows + "," + numHealthPotions + "," + numMaxPotions + "," + numArmorUpgrades + "," + numWeaponUpgrades + "," + 
    	numRangedUpgrades + "," + playerLevel + "," + playerXP + "," + levelUpgrade + "," + dragonDefeated + "," + enemyLevel + "," + enemyHealth + "," + numMaxPotionsInShop + "," + allowedTier + "," + 
    	mysticSpiritDefeated + "," + arrowCost + "," + potionHeal + "," + potionUpgradeCost + "," + maxPotionUpgradeCost + ",");
    	System.out.println("");
    }
    
    //Processes the save code and loads it
    public static void loadSaveCode() {
    	System.out.println("Paste save code here:");
    	sc.useDelimiter(",");
    	weaponLevel = sc.nextInt();
    	rangedLevel = sc.nextInt();
    	armorLevel = sc.nextInt();
    	gold = sc.nextInt();
    	playerHealth = sc.nextInt();
    	encounteredEnemy = sc.nextBoolean();
    	shop = sc.nextBoolean();
    	weaponUpgradeCost = sc.nextInt();
    	armorUpgradeCost = sc.nextInt();
    	rangedUpgradeCost = sc.nextInt();
    	numMaxPotionsBought = sc.nextInt();
    	numArrows = sc.nextInt();
    	numHealthPotions = sc.nextInt();
    	numMaxPotions = sc.nextInt();
    	numArmorUpgrades = sc.nextInt();
    	numWeaponUpgrades = sc.nextInt();
    	numRangedUpgrades = sc.nextInt();
    	playerLevel = sc.nextInt();
    	playerXP = sc.nextInt();
    	levelUpgrade = sc.nextInt();
    	dragonDefeated = sc.nextBoolean();
    	enemyLevel = sc.nextInt();
    	enemyHealth = sc.nextInt();
    	numMaxPotionsInShop = sc.nextInt();
    	allowedTier = sc.nextInt();
    	mysticSpiritDefeated = sc.nextBoolean();
    	arrowCost = sc.nextInt();
    	potionHeal = sc.nextInt();
    	potionUpgradeCost = sc.nextInt();
    	maxPotionUpgradeCost = sc.nextInt();
		printStats();
		displayOptions();
    }
    
    //Prints all player stats
    public static void printStats() {
    	if(shop == true) {
			System.out.println("");
			System.out.println(">>>>>>SHOP>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			if(weaponLevel < numWeaponUpgrades && weaponList[weaponLevel+1].tier <= allowedTier) {
				System.out.println("Next weapon: " + weaponList[weaponLevel+1].weaponName + " lv." + (weaponLevel+2));
				System.out.println("Next weapon damage: " + weaponList[weaponLevel+1].minDamage + " - " + weaponList[weaponLevel+1].maxDamage);
				System.out.println("Weapon cost: " + weaponUpgradeCost + " gold");
				System.out.println("");
			}
			if(weaponLevel == numWeaponUpgrades || weaponList[weaponLevel+1].tier > allowedTier) {
				System.out.println("Weapon upgrade: Out of Stock");
				System.out.println("");
			}
			if(rangedLevel < numRangedUpgrades && rangedList[rangedLevel+1].tier <= allowedTier) {
				System.out.println("Next ranged weapon: " + rangedList[rangedLevel+1].weaponName + " lv." + (rangedLevel+2));
				System.out.println("Next ranged weapon damage: " + rangedList[rangedLevel+1].minDamage + " - " + rangedList[rangedLevel+1].maxDamage);
				System.out.println("Ranged weapon cost: " + rangedUpgradeCost + " gold");
			}
			if(rangedLevel == numRangedUpgrades || rangedList[rangedLevel+1].tier > allowedTier) {
				System.out.println("Ranged weapon upgrade: Out of Stock");
			}
			System.out.println("");
			System.out.println("Arrow cost: " + arrowCost + " gold");
			System.out.println("");
			if(armorLevel < numArmorUpgrades && armorList[armorLevel+1].tier <= allowedTier) {
				System.out.println("Next armor set: " + armorList[armorLevel+1].armorName + " lv." + (armorLevel+2));
				System.out.println("Next armor health: " + armorList[armorLevel+1].health);
				System.out.println("Armor cost: " + armorUpgradeCost + " gold");
				System.out.println("");
			}
			if(armorLevel == numArmorUpgrades || armorList[armorLevel+1].tier > allowedTier) {
				System.out.println("Armor upgrade: Out of Stock");
				System.out.println("");
			}
			System.out.println("Potion cost: " + potionUpgradeCost + " gold");
			System.out.println("Potion heal amount: " + potionHeal);
			System.out.println("");
			System.out.println("Max potion cost: " + maxPotionUpgradeCost + " gold");
			if(numMaxPotionsBought < 5) {
			System.out.println("Max potions in stock: " + (numMaxPotionsInShop - numMaxPotionsBought));
			}
			if(numMaxPotionsBought >= 5) {
				System.out.println("No more max potions in stock!");
				}
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("");
		}
    	System.out.println("<-----EQUIPMENT--------------------------------------------------->");
		System.out.println("Weapon equipped: " + weaponList[weaponLevel].weaponName + " lv." + (weaponLevel+1));
		System.out.println("Weapon damage: " + weaponList[weaponLevel].minDamage + " - " + weaponList[weaponLevel].maxDamage);
		System.out.println("");
		System.out.println("Ranged weapon equipped: " + rangedList[rangedLevel].weaponName + " lv." + (rangedLevel+1));
		System.out.println("Ranged weapon damage: " + rangedList[rangedLevel].minDamage + " - " + rangedList[rangedLevel].maxDamage);
		System.out.println("Arrows: " + numArrows);
		System.out.println("");
		System.out.println("Armor equipped: " + armorList[armorLevel].armorName + " lv." + (armorLevel+1));
		System.out.println("Health: " + playerHealth);
		System.out.println("");
		System.out.println("Potions: " + numHealthPotions);
		System.out.println("");
		System.out.println("Max potions: " + numMaxPotions);
		System.out.println("");
		System.out.println("Gold: " + gold);
		System.out.println("");
		System.out.println("Player level: " + playerLevel);
		System.out.println("XP to next level: " + (levelUpgrade - playerXP));
		System.out.println("<----------------------------------------------------------------->");
		System.out.println("");
    }
    
    //Displays player options
    public static void displayOptions() {
    	if(encounteredEnemy == true) {
        	System.out.println("You may:");
        	System.out.println("1. Melee Attack");
        	System.out.println("2. Ranged Attack");
        	System.out.println("3. Use Potion");
        	System.out.println("4. Use Max-potion");
        	System.out.println("5. View Equipment");
        	System.out.println("6. Run");
        	System.out.println("");
    	}
		if(shop == true) {
			System.out.println("You may:");
			System.out.println("1. Upgrade melee weapon");
			System.out.println("2. Upgrade ranged weapon");
			System.out.println("3. Upgrade armor");
			System.out.println("4. Buy potion");
			System.out.println("5. Buy max-potion");
			System.out.println("6. Buy arrow");
			System.out.println("7. Exit Shop");
			System.out.println("");
		}
    }
    
    //Changes game state to the shop
    public static void enterShop() {
    	shop = true;
    	encounteredEnemy = false;
    	printStats();
		displayOptions();
		
    }
    
    //Exits shop and changes game state to encountered enemy
    public static void exitShop() {
    	encounter();
    }
    
    //Encounters an enemy
    public static void encounter() {
    	shop = false;
    	encounteredEnemy = true;
    	if(armorLevel <= 2) {
    		if(armorLevel <= numEnemies) {
    			enemyLevel = ThreadLocalRandom.current().nextInt(0, armorLevel+1);
    		}
    		else if(armorLevel > numEnemies) {
    			enemyLevel = ThreadLocalRandom.current().nextInt(0, numEnemies+1);
    		}
    	}
    	if(armorLevel > 2) {
    		if(armorLevel <= numEnemies) {
    			enemyLevel = ThreadLocalRandom.current().nextInt(armorLevel - 1, armorLevel+1);
    		}
    		else if(armorLevel > numEnemies) {
    			enemyLevel = ThreadLocalRandom.current().nextInt(armorLevel - 1, numEnemies+1);
    		}
    	}
    	if(enemyList[enemyLevel].tier > allowedTier) {
    		while(enemyList[enemyLevel].tier > allowedTier) {
    			enemyLevel -= 1;
    		}
    	}
    	if(enemyList[enemyLevel].tier < allowedTier) {
    		while(enemyList[enemyLevel].tier < allowedTier) {
    			enemyLevel += 1;
    		}
    	}
    	if(dragonDefeated == false && armorLevel >= 9) {
    		enemyLevel = 50;
    	}
    	if(mysticSpiritDefeated == false && armorLevel >= 19 && dragonDefeated == true) {
    		enemyLevel = 51;
    	}
    	setEnemyHealth();
    	if(enemyLevel == 50) {
    		System.out.println("                      ^\\    ^                  \r\n" + 
    				"                      / \\\\  / \\                 \r\n" + 
    				"                     /.  \\\\/   \\      |\\___/|   \r\n" + 
    				"  *----*           / / |  \\\\    \\  __/  O  O\\   \r\n" + 
    				"  |   /          /  /  |   \\\\    \\_\\/  \\     \\     \r\n" + 
    				" / /\\/         /   /   |    \\\\   _\\/    '@___@      \r\n" + 
    				"/  /         /    /    |     \\\\ _\\/       |U\r\n" + 
    				"|  |       /     /     |      \\\\\\/        |\r\n" + 
    				"\\  |     /_     /      |       \\\\  )   \\ _|_\r\n" + 
    				"\\   \\       ~-./_ _    |    .- ; (  \\_ _ _,\\'\r\n" + 
    				"~    ~.           .-~-.|.-*      _        {-,\r\n" + 
    				" \\      ~-. _ .-~                 \\      /\\'\r\n" + 
    				"  \\                   }            {   .*\r\n" + 
    				"   ~.                 '-/        /.-~----.\r\n" + 
    				"     ~- _             /        >..----.\\\\\\\r\n" + 
    				"         ~ - - - - ^}_ _ _ _ _ _ _.-\\\\\\");
    		System.out.println("");
    	}
    	if(enemyLevel == 51 && dragonDefeated == true) {
    		System.out.println("     .-.\r\n" + 
    				"   .'   `.\r\n" + 
    				"   :o o   :\r\n" + 
    				"   : O    `.\r\n" + 
    				"  :         ``.\r\n" + 
    				" :             `.\r\n" + 
    				":  :         .   `.\r\n" + 
    				":   :          ` . `.\r\n" + 
    				" `.. :            `. ``;\r\n" + 
    				"    `:;             `:'\r\n" + 
    				"       :              `.\r\n" + 
    				"        `.              `.     .\r\n" + 
    				"          `'`'`'`---..,___`;.-'");
    		System.out.println("");
    	}
    	System.out.println("You encountered a(n) " + enemyList[enemyLevel].enemyName + "!");
    	System.out.println("Enemy health: " + enemyList[enemyLevel].enemyHealth);
    	System.out.println("Enemy damage: " + enemyList[enemyLevel].enemyMinDamage + " - " + enemyList[enemyLevel].enemyMaxDamage);
    	System.out.println("");
    	System.out.println("Your health is at " + playerHealth + ".");
    	System.out.println("");
		displayOptions();
    }
    
    //Player dies
    public static void death() {
    	if(playerHealth <= 0) {
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("                     .ed\"\"\"\" \"\"\"$$$$be.\r\n" + 
					"                   -\"           ^\"\"**$$$e.\r\n" + 
					"                 .\"                   '$$$c\r\n" + 
					"                /                      \"4$$b\r\n" + 
					"               d  3                      $$$$\r\n" + 
					"               $  *                   .$$$$$$\r\n" + 
					"              .$  ^c           $$$$$e$$$$$$$$.\r\n" + 
					"              d$L  4.         4$$$$$$$$$$$$$$b\r\n" + 
					"              $$$$b ^ceeeee.  4$$ECL.F*$$$$$$$\r\n" + 
					"  e$\"\"=.      $$$$P d$$$$F $ $$$$$$$$$- $$$$$$\r\n" + 
					" z$$b. ^c     3$$$F \"$$$$b   $\"$$$$$$$  $$$$*\"      .=\"\"$c\r\n" + 
					"4$$$$L        $$P\"  \"$$b   .$ $$$$$...e$$        .=  e$$$.\r\n" + 
					"^*$$$$$c  %..   *c    ..    $$ 3$$$$$$$$$$eF     zP  d$$$$$\r\n" + 
					"  \"**$$$ec   \"   %ce\"\"    $$$  $$$$$$$$$$*    .r\" =$$$$P\"\"\r\n" + 
					"        \"*$b.  \"c  *$e.    *** d$$$$$\"L$$    .d\"  e$$***\"\r\n" + 
					"          ^*$$c ^$c $$$      4J$$$$$% $$$ .e*\".eeP\"\r\n" + 
					"             \"$$$$$$\"'$=e....$*$$**$cz$$\" \"..d$*\"\r\n" + 
					"               \"*$$$  *=%4.$ L L$ P3$$$F $$$P\"\r\n" + 
					"                  \"$   \"%*ebJLzb$e$$$$$b $P\"\r\n" + 
					"                    %..      4$$$$$$$$$$ \"\r\n" + 
					"                     $$$e   z$$$$$$$$$$%\r\n" + 
					"                      \"*$c  \"$$$$$$$P\"\r\n" + 
					"                       .\"\"\"*$$$$$$$$bc\r\n" + 
					"                    .-\"    .$***$$$\"\"\"*e.\r\n" + 
					"                 .-\"    .e$\"     \"*$c  ^*b.\r\n" + 
					"          .=*\"\"\"\"    .e$*\"          \"*bc  \"*$e..\r\n" + 
					"        .$\"        .z*\"               ^*$e.   \"*****e.\r\n" + 
					"        $$ee$c   .d\"                     \"*$.        3.\r\n" + 
					"        ^*$E\")$..$\"                         *   .ee==d%\r\n" + 
					"           $.d$$$*                           *  J$$$e*\r\n" + 
					"            \"\"\"\"\"                              \"$$$\"\r\n" + 
					"");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("You have died.");
			System.exit(0);
		}
    }
    
    //Checks to see if cheat code was entered
    public static void cheatCodeEntered() {
    	weaponLevel = numWeaponUpgrades;
		armorLevel = numArmorUpgrades;
		rangedLevel = numRangedUpgrades;
		numHealthPotions = 99999;
		numMaxPotions = 99999;
		numArrows = 99999;
		gold = 99999;
		setHealth();
    }
    
    //Sets player health
    public static void setHealth() {
    	playerHealth = armorList[armorLevel].health;
    }
    
    //Sets enemy health
    public static void setEnemyHealth() {
    	enemyHealth = enemyList[enemyLevel].enemyHealth;
    }
    
    //Runs when first boss is defeated
    public static void firstBossDefeated() {
    	System.out.println("");
    	System.out.println("You have defeated the almighty Elder Dragon! As you return to the village, you feel a coldness around you. When you return, everything\n" +
    	"is different. All the people are gone, and the buildings look crumbled. You seem to have returned to a village that is not your own.");
    	System.out.println("");
    	numMaxPotionsBought = 0;
    	numMaxPotionsInShop = 10;
    	maxPotionUpgradeCost = 250;
    	potionUpgradeCost = 50;
    	potionHeal = 100;
    	allowedTier = 2;
    	arrowCost = 50;
    }
    
    //Runs when second boss is defeated
    public static void secondBossDefeated() {
    	System.out.println("");
    	System.out.println("You have defeated the mystic spirit! The cold feeling leaves as you as you walk back to your village. The people of the village/n" +
    	"are back, and the spirits have been vanquished.");
    	System.out.println("");
    }
	
	public static void main(String[] args) {
		
		//Title screen
		System.out.println(" ________         ___                             \r\n" + 
				"/_  __/ /  ___   / _ \\__ _____  ___ ____ ___  ___ \r\n" + 
				" / / / _ \\/ -_) / // / // / _ \\/ _ `/ -_) _ \\/ _ \\\r\n" + 
				"/_/ /_//_/\\__/ /____/\\_,_/_//_/\\_, /\\__/\\___/_//_/\r\n" + 
				"                              /___/               \r\n" + 
				"");
		
		System.out.println("");
		System.out.println("Press enter to continue..");
		System.out.println("");
		sc.nextLine();
		
		//Creating Melee Weapons
		weaponList[0] = new weapon(1, 3, "Fist", 1);	
		weaponList[1] = new weapon(4, 6, "Brass Knuckles", 1);
		weaponList[2] = new weapon(7, 11, "Rusty Iron Dagger", 1);
		weaponList[3] = new weapon(15, 20, "Iron Dagger", 1);
		weaponList[4] = new weapon(20, 25, "Sharpened Iron Dagger", 1);
		weaponList[5] = new weapon(25, 30, "Rusty Iron Shortsword", 1);
		weaponList[6] = new weapon(30, 35, "Iron Shortsword", 1);
		weaponList[7] = new weapon(37, 42, "Sharpened Iron Shortsword", 1);
		weaponList[8] = new weapon(47, 52, "Rusty Iron Greatsword", 1);
		weaponList[9] = new weapon(55, 60, "Iron Greatsword", 1);
		weaponList[10] = new weapon(65, 70, "Sharpened Iron Greatsword", 1);
		weaponList[11] = new weapon(75, 80, "Rusty Ghost Cleaver", 2);
		weaponList[12] = new weapon(80, 85, "Ghost Cleaver", 2);
		weaponList[13] = new weapon(90, 95, "Sharpened Ghost Cleaver", 2);
		weaponList[14] = new weapon(100, 105, "Broken Illuminate Knife", 2);
		weaponList[15] = new weapon(110, 115, "Illuminate Knife", 2);
		weaponList[16] = new weapon(120, 125, "Sharpened Illuminate Knife", 2);
		weaponList[17] = new weapon(130, 135, "Broken Illuminate Sword", 2);
		weaponList[18] = new weapon(140, 145, "Illuminate Sword", 2);
		weaponList[19] = new weapon(150, 155, "Enchanted Illuminate Sword", 2);
		numWeaponUpgrades = 19;
		
		//Creating Ranged Weapons
		rangedList[0] = new ranged(1, 4, "Flimsy Wooden Shortbow", 1);
		rangedList[1] = new ranged(6, 10, "Wooden Shortbow", 1);
		rangedList[2] = new ranged(12, 16, "Strong Wooden Shortbow", 1);
		rangedList[3] = new ranged(18, 22, "Flimsy Wooden Longbow", 1);
		rangedList[4] = new ranged(24, 28, "Wooden Longbow", 1);
		rangedList[5] = new ranged(32, 36, "Strong Wooden Longbow", 1);
		rangedList[6] = new ranged(36, 40, "Flimsy Ranger's Bow", 1);
		rangedList[7] = new ranged(44, 48, "Ranger's Bow", 1);
		rangedList[8] = new ranged(48, 52, "Strong Ranger's Bow", 1);
		rangedList[9] = new ranged(56, 60, "Weak Warrior's Bow", 1);
		rangedList[10] = new ranged(70, 80, "Warrior's Bow", 1);
		rangedList[11] = new ranged(80, 85, "Flimsy Ghost's Shortbow", 2);
		rangedList[12] = new ranged(90, 95, "Ghost's Shortbow", 2);
		rangedList[13] = new ranged(100, 105, "Strong Ghost's Shortbow", 2);
		rangedList[14] = new ranged(110, 115, "Flimsy Illuminate Longbow", 2);
		rangedList[15] = new ranged(120, 125, "Illuminate Longbow", 2);
		rangedList[16] = new ranged(130, 135, "Strong Illuminate Longbow", 2);
		rangedList[17] = new ranged(140, 145, "Flimsy Illuminate Crossbow", 2);
		rangedList[18] = new ranged(150, 155, "Illuminate Crossbow", 2);
		rangedList[19] = new ranged(160, 165, "Strong Illuminate Crossbow", 2);
		numRangedUpgrades = 19;
		
		//Creating Armor
		armorList[0] = new armor(50, "Ragged Clothing", 1);
		armorList[1] = new armor(100, "Broken Chainmail", 1);
		armorList[2] = new armor(150, "Rusty Iron Armor", 1);
		armorList[3] = new armor(200, "Iron Armor", 1);
		armorList[4] = new armor(250, "Polished Iron Armor", 1);
		armorList[5] = new armor(300, "Rusty Knights Armor", 1);
		armorList[6] = new armor(350, "Knights Armor", 1);
		armorList[7] = new armor(400, "Polished Knights Armor", 1);
		armorList[8] = new armor(500, "Rusty Warriors Gear", 1);
		armorList[9] = new armor(600, "Warriors Gear", 1);
		armorList[10] = new armor(800, "Broken Ghost Gear", 2);
		armorList[11] = new armor(1000, "Ghost Gear", 2);
		armorList[12] = new armor(1000, "Polished Ghost Gear", 2);
		armorList[13] = new armor(1200, "Broken Illuminate Chainmail", 2);
		armorList[14] = new armor(1400, "Illuminate Chainmail", 2);
		armorList[15] = new armor(1600, "Polished Illuminate Chainmail", 2);
		armorList[16] = new armor(1800, "Broken Illuminate Gear", 2);
		armorList[17] = new armor(2000, "Illuminate Gear", 2);
		armorList[18] = new armor(2200, "Polished Illuminate Gear", 2);
		armorList[19] = new armor(2500, "Spirit Gear", 2);
		numArmorUpgrades = 19;
		
		//Creating enemies
		enemyList[0] = new enemy("Slime", 20,  1, 3, 10, 20, 1, 10, 1);
		enemyList[1] = new enemy("Skeleton Knight", 60, 5, 7, 25, 35, 10, 20, 1);
		enemyList[2] = new enemy("Skeleton Warrior", 80, 10, 13, 40, 60, 20, 30, 1);
		enemyList[3] = new enemy("Goblin", 100, 15, 17, 65, 75, 30, 40, 1);
		enemyList[4] = new enemy("Orc", 120, 20, 22, 80, 110, 40, 50, 1);
		enemyList[5] = new enemy("Troll", 140, 25, 27, 115, 145, 50, 60, 1);
		enemyList[6] = new enemy("Armored Troll", 160, 30, 32, 150, 180, 60, 70, 1);
		enemyList[7] = new enemy("Baby Dragon", 180, 35, 37, 220, 235, 70, 80, 1);
		enemyList[8] = new enemy("Dragon", 200, 38, 40, 230, 250, 80, 90, 1);
		enemyList[9] = new enemy("Slime Spirit", 240, 40, 45, 250, 280, 90, 100, 2);
		enemyList[10] = new enemy("Skeleton Knight Spirit", 260, 45, 50, 300, 330, 100, 110, 2);
		enemyList[11] = new enemy("Skeleton Warrior Spirit", 280, 50, 55, 330, 350, 110, 120, 2);
		enemyList[12] = new enemy("Goblin Spirit", 300, 55, 60, 360, 380, 120, 130, 2);
		enemyList[13] = new enemy("Orc Spirit", 320, 60, 65, 380, 400, 130, 140, 2);
		enemyList[14] = new enemy("Troll Spirit", 340, 65, 70, 400, 430, 140, 150, 2);
		enemyList[15] = new enemy("Armored Troll Spirit", 360, 70, 75, 430, 450, 150, 160, 2);
		enemyList[16] = new enemy("Weakened Spirit", 380, 75, 80, 450, 480, 160, 170, 2);
		enemyList[17] = new enemy("Strengthened Spirit", 400, 80, 85, 480, 500, 170, 180, 2);
		numEnemies = 17;
		
		//Creating bosses
		enemyList[50] = new enemy("Dragon Elder", 750, 75, 100, 300, 400, 90, 100, 1);
		enemyList[51] = new enemy("Mystic Spirit", 4000, 150, 200, 600, 800, 200, 250, 2);
		
		//Starting the game
		System.out.println("You enter the dungeon. The walls are mossy and the floor is covered in a thick sheet of dust");
		System.out.println("");
		setHealth();
		encounter();
		
		
		//User Input 
		while(cmdOpen && (encounteredEnemy == true || shop == true)) {
			
			//Shop commands
			if(encounteredEnemy == false && shop == true) {
				userInput = null;
				userInput = sc.nextLine();
				userInput = userInput.toUpperCase();
				if((userInput .equals("UPGRADE MELEE WEAPON") || userInput .equals("1")) && (gold >= weaponUpgradeCost) && (weaponLevel < numWeaponUpgrades) && (weaponList[weaponLevel+1].tier == allowedTier) && (weaponLevel <= armorLevel)) {
					weaponLevel += 1;
					System.out.println("");
					System.out.println("You upgraded your weapon to the " + weaponList[weaponLevel].weaponName + "!");
					System.out.println("");
					gold -= weaponUpgradeCost;
					weaponUpgradeCost *= 1.75;
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				} 
				if((userInput .equals("UPGRADE RANGED WEAPON") || userInput .equals("2")) && (gold >= rangedUpgradeCost) && (rangedLevel < numRangedUpgrades) && (rangedList[rangedLevel+1].tier == allowedTier) && (rangedLevel <= armorLevel)) {
					rangedLevel += 1;
					System.out.println("");
					System.out.println("You upgraded your ranged weapon to the " + rangedList[rangedLevel].weaponName + "!");
					System.out.println("");
					gold -= rangedUpgradeCost;
					rangedUpgradeCost *= 1.7;
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				} 
				else if((userInput .equals("UPGRADE ARMOR") || userInput .equals("3")) && (gold >= armorUpgradeCost) && (armorLevel < numArmorUpgrades) && (armorList[armorLevel+1].tier == allowedTier)) {
					armorLevel += 1;
					System.out.println("");
					System.out.println("You upgraded your armor to the " + armorList[armorLevel].armorName + " set!");
					System.out.println("");
					gold -= armorUpgradeCost;
					armorUpgradeCost *= 1.7;
					setHealth();
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				} 
				else if((userInput .equals("BUY POTION") || userInput .equals("4")) && gold >= potionUpgradeCost) {
					numHealthPotions += 1;
					System.out.println("");
					System.out.println("You bought a potion!");
					System.out.println("");
					gold -= potionUpgradeCost;
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if((userInput .equals("BUY MAX-POTION") || userInput .equals("5")) && gold >= maxPotionUpgradeCost && numMaxPotionsBought < numMaxPotionsInShop){
					numMaxPotions += 1;
					System.out.println("");
					System.out.println("You bought a max-potion!");
					System.out.println("");
					gold -= maxPotionUpgradeCost;
					numMaxPotionsBought += 1;
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if((userInput .equals("BUY ARROW") || userInput .equals("6")) && gold >= arrowCost){
					numArrows += 1;
					System.out.println("");
					System.out.println("You bought an arrow!");
					System.out.println("");
					gold -= arrowCost;
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if((userInput .equals("EXIT SHOP")) || userInput .equals("7")) {
					System.out.println("");
					exitShop();
				}
				else if((userInput .equals("UPGRADE ARMOR") || userInput .equals("3")) && gold < armorUpgradeCost && armorLevel < numArmorUpgrades){
					System.out.println("You do not have enough gold for this armor!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if((userInput .equals("UPGRADE MELEE WEAPON") || userInput .equals("1")) && gold < weaponUpgradeCost && weaponLevel < numWeaponUpgrades){
					System.out.println("You do not have enough gold for this melee weapon!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if((userInput .equals("UPGRADE RANGED WEAPON") || userInput .equals("2")) && gold < rangedUpgradeCost && rangedLevel < numRangedUpgrades){
					System.out.println("You do not have enough gold for this ranged weapon!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if((userInput .equals("BUY POTION") || userInput .equals("4")) && gold < potionUpgradeCost){
					System.out.println("You do not have enough gold for potions!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if((userInput .equals("BUY MAX-POTION") || userInput .equals("5")) && gold < maxPotionUpgradeCost){
					System.out.println("You do not have enough gold for max-potions!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if((userInput .equals("BUY ARROW") || userInput .equals("6")) && gold < arrowCost){
					System.out.println("You do not have enough gold for arrows!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if((userInput .equals("BUY MAX-POTION") || userInput .equals("5")) && numMaxPotionsBought >= numMaxPotionsInShop){
					System.out.println("You have already bought all max potions from the shop!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if((userInput .equals("UPGRADE MELEE WEAPON") || userInput .equals("1")) && (weaponLevel == numWeaponUpgrades || weaponList[weaponLevel+1].tier > allowedTier)){
					System.out.println("There are no more melee weapons for sale!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				} 
				else if((userInput .equals("UPGRADE RANGED WEAPON") || userInput .equals("2")) && (rangedLevel == numRangedUpgrades || rangedList[rangedLevel+1].tier > allowedTier)){
					System.out.println("There are no more ranged weapons for sale!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				} 
				else if((userInput .equals("UPGRADE ARMOR") || userInput .equals("3")) && (armorLevel == numArmorUpgrades || armorList[armorLevel+1].tier > allowedTier)){
					System.out.println("There are no more armor sets for sale!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				} 
				else if((userInput .equals("UPGRADE MELEE WEAPON") || userInput .equals("1")) && (weaponLevel > armorLevel)){
					System.out.println("You must upgrade your armor first!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if((userInput .equals("UPGRADE RANGED WEAPON") || userInput .equals("2")) && (rangedLevel > armorLevel)){
					System.out.println("You must upgrade your armor first!");
					System.out.println("");
					System.out.println("Press enter to continue..");
					System.out.println("");
					userInput = sc.nextLine();
					enterShop();
				}
				else if(userInput.equals("STOP")) {
					System.exit(0);
				}
				else if(userInput.equals("SAVE")) {
					generateSaveCode();
				}
				else if(userInput.equals("LOAD")) {
					loadSaveCode();
				}
				else if(userInput.equals(cheatCode)) {
					cheatCodeEntered();
				} 
			}
	    
			
			//Encountered enemy commands
			
			//Melee code
			if(encounteredEnemy == true) {
				userInput = null;
				userInput = sc.nextLine();
				userInput = userInput.toUpperCase();
				if(userInput .equals("MELEE ATTACK") || userInput .equals("1")){
					tempPlayerDamage = ThreadLocalRandom.current().nextInt(weaponList[weaponLevel].minDamage, weaponList[weaponLevel].maxDamage + 1);
					System.out.println("");
					System.out.println("You attack the " + enemyList[enemyLevel].enemyName + " for " + tempPlayerDamage + " hitpoints.");
					enemyHealth -= tempPlayerDamage;
					if(enemyHealth > 0) {
						System.out.println("The " + enemyList[enemyLevel].enemyName + " is now at " + enemyHealth + " health.");
						System.out.println("");
					} 
					else if(enemyHealth <= 0) {
						System.out.println("The " + enemyList[enemyLevel].enemyName + " is now dead.");
						System.out.println("");
					}
					if(enemyHealth > 0) {
						enemyHitMiss = ThreadLocalRandom.current().nextInt(0, 5);
						
						if(enemyHitMiss >= 0 && enemyHitMiss <=3) {
						tempEnemyDamage = ThreadLocalRandom.current().nextInt(enemyList[enemyLevel].enemyMinDamage, enemyList[enemyLevel].enemyMaxDamage + 1);
						System.out.println("The " + enemyList[enemyLevel].enemyName + " hits you for " + tempEnemyDamage + " hitpoints.");
					
						playerHealth -= tempEnemyDamage;
						} else {
							System.out.println("The enemy missed!");
						}
						
						if(playerHealth > 0) {
							System.out.println("Your health is now " + playerHealth + " hitpoints.");
							System.out.println("");
							displayOptions();
						}
						if(playerHealth <= 0) {
							encounteredEnemy = false;
							death();
						}
					}
					else if(enemyHealth <= 0 && playerHealth > 0) {
						System.out.println( 
								" \\ \\    / (_)    | |                 | |\r\n" + 
								"  \\ \\  / / _  ___| |_ ___  _ __ _   _| |\r\n" + 
								"   \\ \\/ / | |/ __| __/ _ \\| '__| | | | |\r\n" + 
								"    \\  /  | | (__| || (_) | |  | |_| |_|\r\n" + 
								"     \\/   |_|\\___|\\__\\___/|_|   \\__, (_)\r\n" + 
								"                                 __/ |  \r\n" + 
								"                                |___/   \r\n");
						System.out.println(enemyList[enemyLevel].enemyName + " has been defeated!");
						goldGained = ThreadLocalRandom.current().nextInt(enemyList[enemyLevel].enemyMinGold, enemyList[enemyLevel].enemyMaxGold + 1);
						System.out.println("You gained " + goldGained + " gold!");
						System.out.println("");
						gold += goldGained;
						playerTempXP = ThreadLocalRandom.current().nextInt(enemyList[enemyLevel].enemyMinXP, enemyList[enemyLevel].enemyMaxXP + 1);
						System.out.print("You gained " + playerTempXP + " XP!");
						System.out.print("");
						playerXP += playerTempXP;
						
						while(playerXP >= levelUpgrade) {
							playerXP -= levelUpgrade;
							levelUpgrade *= 2;
							playerLevel += 1;
							System.out.println("");
							System.out.println("You leveled up to level " + playerLevel + "!");
							System.out.println("You gained " + (100*(playerLevel-1)) + " gold from leveling up!");
							System.out.println("");
							gold += (100*(playerLevel-1));
						}
						
						if(enemyLevel == 50) {
				    		dragonDefeated = true;
				    		firstBossDefeated();
				    	}
						
						if(enemyLevel == 51) {
				    		mysticSpiritDefeated = true;
				    		secondBossDefeated();
				    	}
						
						System.out.println("");
						System.out.println("Press enter to continue..");
						System.out.println("");
						sc.nextLine();
						
						System.out.println("");
						System.out.println("You head out of the dungeon to the shop.");
						System.out.println("");
						enterShop();
						
					}
				}
				
				//Ranged code
				else if(userInput .equals("RANGED ATTACK") || userInput .equals("2") && numArrows > 0){
					tempPlayerDamage = ThreadLocalRandom.current().nextInt(rangedList[rangedLevel].minDamage, rangedList[rangedLevel].maxDamage + 1);
					System.out.println("");
					System.out.println("You attack the " + enemyList[enemyLevel].enemyName + " for " + tempPlayerDamage + " hitpoints.");
					enemyHealth -= tempPlayerDamage;
					numArrows -= 1;
					if(enemyHealth > 0) {
						System.out.println("The " + enemyList[enemyLevel].enemyName + " is now at " + enemyHealth + " health.");
						System.out.println("");
					} 
					else if(enemyHealth <= 0) {
						System.out.println("The " + enemyList[enemyLevel].enemyName + " is now dead.");
						System.out.println("");
					}
					if(enemyHealth > 0) {
						tempEnemyDamage = ThreadLocalRandom.current().nextInt(enemyList[enemyLevel].enemyMinDamage, enemyList[enemyLevel].enemyMaxDamage + 1);
						System.out.println("The " + enemyList[enemyLevel].enemyName + " hits you for " + tempEnemyDamage + " hitpoints.");
					
						playerHealth -= tempEnemyDamage;
						if(playerHealth > 0) {
							System.out.println("Your health is now " + playerHealth + " hitpoints.");
							System.out.println("");
							displayOptions();
						}
						if(playerHealth <= 0) {
							encounteredEnemy = false;
							death();
						}
					}
					else if(enemyHealth <= 0 && playerHealth > 0) {
						System.out.println( 
								" \\ \\    / (_)    | |                 | |\r\n" + 
								"  \\ \\  / / _  ___| |_ ___  _ __ _   _| |\r\n" + 
								"   \\ \\/ / | |/ __| __/ _ \\| '__| | | | |\r\n" + 
								"    \\  /  | | (__| || (_) | |  | |_| |_|\r\n" + 
								"     \\/   |_|\\___|\\__\\___/|_|   \\__, (_)\r\n" + 
								"                                 __/ |  \r\n" + 
								"                                |___/   \r\n");
						System.out.println(enemyList[enemyLevel].enemyName + " has been defeated!");
						goldGained = ThreadLocalRandom.current().nextInt(enemyList[enemyLevel].enemyMinGold, enemyList[enemyLevel].enemyMaxGold + 1);
						System.out.println("You gained " + goldGained + " gold!");
						System.out.println("");
						gold += goldGained;
						playerTempXP = ThreadLocalRandom.current().nextInt(enemyList[enemyLevel].enemyMinXP, enemyList[enemyLevel].enemyMaxXP + 1);
						System.out.print("You gained " + playerTempXP + " XP!");
						System.out.print("");
						playerXP += playerTempXP;
						
						while(playerXP >= levelUpgrade) {
							playerXP -= levelUpgrade;
							levelUpgrade *= 2;
							playerLevel += 1;
							System.out.println("");
							System.out.println("You leveled up to level " + playerLevel + "!");
							System.out.println("You gained " + (100*(playerLevel-1)) + " gold for leveling up!");
							System.out.println("");
							gold += (100*(playerLevel-1));
						}
						
						if(enemyLevel == 50) {
				    		dragonDefeated = true;
				    		firstBossDefeated();
				    	}
						
						if(enemyLevel == 51) {
				    		mysticSpiritDefeated = true;
				    		secondBossDefeated();
				    	}
						
						System.out.println("");
						System.out.println("Press enter to continue..");
						System.out.println("");
						sc.nextLine();
						
						System.out.println("");
						System.out.println("You head out of the dungeon to the shop.");
						System.out.println("");
						enterShop();
						
					}
				}
				else if (userInput .equals("RANGED ATTACK") || userInput .equals("2") && numArrows <= 0) {
					System.out.println("You don't have any arrows left!");
					System.out.println("");
					displayOptions();
				}
				else if((userInput .equals("USE POTION") || userInput .equals("3")) && numHealthPotions > 0) {
					numHealthPotions -= 1;
					playerHealth += potionHeal;
					if(playerHealth > armorList[armorLevel].health) {
						playerHealth = armorList[armorLevel].health;
					}
					System.out.println("");
					System.out.println("Your health is now at " + playerHealth + ".");
					System.out.println("");
					displayOptions();
				}
				else if((userInput .equals("USE POTION") || userInput .equals("3")) && numHealthPotions <= 0) {
					System.out.println("You do not have any potions! Return to the shop to buy more.");
					System.out.println("");
					displayOptions();
				}
				else if((userInput .equals("USE MAX-POTION") || userInput .equals("4")) && numMaxPotions > 0) {
					numMaxPotions -= 1;
					playerHealth = armorList[armorLevel].health;
					if(playerHealth > armorList[armorLevel].health) {
						playerHealth = armorList[armorLevel].health;
					}
					System.out.println("");
					System.out.println("Your health is now at " + playerHealth + ".");
					System.out.println("");
					displayOptions();
				}
				else if((userInput .equals("USE MAX-POTION") || userInput .equals("4")) && numMaxPotions <= 0) {
					System.out.println("You do not have any max-potions! Return to the shop to buy more.");
					System.out.println("");
					displayOptions();
				}
				else if(userInput .equals("VIEW EQUIPMENT") || userInput .equals("5")) {
					System.out.println("");
					printStats();
					displayOptions();
				}
				else if(userInput .equals("RUN") || userInput .equals("6")) {
					if(ThreadLocalRandom.current().nextInt(0, 2) == 1) {
						System.out.println("You ran away from the battle!");
						System.out.println("");
						System.out.println("");
						System.out.println("Press enter to continue..");
						System.out.println("");
						sc.nextLine();
						
						
						System.out.println("");
						System.out.println("You head out of the dungeon to the shop.");
						System.out.println("");
						enterShop();
						
						
					} else {
						System.out.println("You attempted to run, but the " + enemyList[enemyLevel].enemyName + " caught up with you!");
						
						tempEnemyDamage = ThreadLocalRandom.current().nextInt(enemyList[enemyLevel].enemyMinDamage, enemyList[enemyLevel].enemyMaxDamage + 1);
						System.out.println("The " + enemyList[enemyLevel].enemyName + " hits you for " + tempEnemyDamage + " hitpoints.");
						playerHealth -= tempEnemyDamage;
						if(playerHealth <= 0) {
							encounteredEnemy = false;
							death();
						}
						System.out.println("Your health is now " + playerHealth + " hitpoints.");
						System.out.println("");
						displayOptions();
						
					}
				}
				else if(userInput.equals("STOP")) {
					System.exit(0);
				}
				else if(userInput.equals("SAVE")) {
					generateSaveCode();
				}
				else if(userInput.equals("LOAD")) {
					loadSaveCode();
				}
				else if(userInput.equals(cheatCode)) {
					cheatCodeEntered();
				} 
			} 
		} 
		
		//Senses if cmd is closed
		try {
			sc.hasNext();
			if(sc.ioException() != null)
			{
				cmdOpen = false;
			}
		}catch(Exception e) {
			cmdOpen = false;
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
}