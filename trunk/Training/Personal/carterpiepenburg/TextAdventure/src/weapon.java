public class weapon {
	
	public int minDamage;
	public int maxDamage;
	public String weaponName;
	public int tier;
	
	weapon(int consMinDamage, int consMaxDamage, String consName, int consTier){

		this.minDamage = consMinDamage;
		this.maxDamage = consMaxDamage;
		this.weaponName = consName;
		this.tier = consTier;
		
	}
}