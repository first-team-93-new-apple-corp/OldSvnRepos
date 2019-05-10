
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deck deck = new Deck();
		deck.shuffle();
		deck.shuffle();
		System.out.println(deck.cardCount);
		for (int i = 0; i <= 4; i++)
		{
			Card currentCard = deck.getBottomCard();
			System.out.println("Suit: " + currentCard.m_suitData + ", Value: " + currentCard.m_valueData);
		}
	}

}
