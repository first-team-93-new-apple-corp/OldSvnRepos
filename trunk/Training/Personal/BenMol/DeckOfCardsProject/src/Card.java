
public class Card {
	String suit;
	int value;
	Card nextCard;
	public Card(String suit, int value) {
		this.suit = suit;
		this.value = value;
	}
	
	public void print() {
		System.out.print(value + " of ");
		System.out.println(suit + "s");
	}
}
