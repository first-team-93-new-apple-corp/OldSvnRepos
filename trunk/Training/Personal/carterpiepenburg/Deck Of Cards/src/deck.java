public class deck {
		
	Card topCard;
	
	public void addCard(Card card) {
		
		card.nextCard = topCard;
		topCard = card;
		
	}
	
	public Card drawCard(){
		
		Card tempCard = topCard;
		topCard = topCard.nextCard;
		
		return tempCard;
		
	}
}
