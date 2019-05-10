//This game is inspired and based around Codecourse's fantastic video https://www.youtube.com/watch?v=EpB9u4ItOYU
package tutorials;

import java.util.Scanner;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		
		//System Objects
		Scanner in = new Scanner(System.in);
		Random rand = new Random();
		
		//Game Variables
		String enemies = ("Greninja, Crobat, Dusknoir, Scizor");
		int maxEnemyHealth = 300;
		int enemyAttackDamage = 75;
		
		//player variables
		int Health = 250;
		int attackDamage = 50;
		int numHealthPotions = 3;
		int healthPotionHealAmount = 50;
		
		boolean running = true;
		
		System.out.println("Welcome to the unoffical Pokemon Stadium 3!");
		
		GAME:
		while(running) {
			System.out.println("-----------------------------------------------");
			
			int enemyHealth = rand.nextInt(maxEnemyHealth);
			String enemy = enemies(rand.nextInt(enemies.length()));
			System.out.println("\t#" + enemy + "Woah! A new Pokemon has shown up! #\n" );
			
			while(enemyHealth > 0) {
				System.out.println("\tYour HP" + Health);
				System.out.println("\t" + enemy + "'s HP" + enemyHealth);
				System.out.println("\n\tChoose Your Option!");
				System.out.println("\t1. Attack!");
				System.out.println("\t2. Drink Health Potion");
				System.out.println("\t3. R U N!");
				System.out.println("\t4. Find a health potion!");
				
				String input = in.nextLine();
				if(input.equals("1")) {
					int damageDealt = rand.nextInt(attackDamage);
					int damageTaken = rand.nextInt(enemyAttackDamage);
					
					enemyHealth -= damageDealt;
					Health -= damageTaken;
					
					System.out.println(("\t> You Attack the" + enemy + "for" + damageDealt + "Damage"));
					System.out.println("\t> You recieve " + damageTaken + "In recoil damage");
					
					if(Health < 1) {
						System.out.println("You ran out of HP! You blacked out!");
						System.exit();
						
					}
				}
				else if(input.equals("2")) {
					if(numHealthPotions > 0);
					health += healthPotionHealAmount;
					numHealthPotion--;
					System.out.println("You just drank a potion! You gained " + healthPotionHealAmount +" in health! "
							+ " You have " + numHealthPotions = "Left!");
				}
				else {
					System.out.println("You have no more potions :(");
					
				}
					
				}
				else if(input.equals("3")) {
					System.out.println("You ran from the fight!");
					System.exit();
				}
				else if(input.equals("4") {
					System.out.println("You tried to look for a health Potion!");
					double randNumber = Math.random();
					d = randNumber * 100;

					int randomInt = (int)d + 1;
					
					if(int >= 25) {
						numHealthPotions++;
						System.out.println("You found a health potion! You have " + numHealthPotions + "potions!")
						else if(int <= 25) {
							System.out.println("You found no health Potions :(");
						}
					}
				else {
					System.out.println("That is not an option!")
				}
			}
		}
		
	}

	private static String enemies(int nextInt) {
		// TODO Auto-generated method stub
		return null;
	}
}
