public class enemy {
	
	public String enemyName;
	public int enemyHealth;
	public int enemyMinDamage;
	public int enemyMaxDamage;
	public int enemyMinGold;
	public int enemyMaxGold;
	public int enemyMinXP;
	public int enemyMaxXP;
	public int tier;
	
	enemy(String consName, int consHealth, int consMinDamage, int consMaxDamage, int consMinGold, int consMaxGold, int consMinXP, int consMaxXP, int consTier){
		
		this.enemyName = consName;
		this.enemyHealth = consHealth;
		this.enemyMinDamage = consMinDamage;
		this.enemyMaxDamage = consMaxDamage;
		this.enemyMinGold = consMinGold;
		this.enemyMaxGold = consMaxGold;
		this.enemyMinXP = consMinXP;
		this.enemyMaxXP = consMaxXP;
		this.tier = consTier;
		
	}
	
}
