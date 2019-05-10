
import java.util.Random;

public class Deck {
	Card topCard;
	int numCards;
	
	public Deck() {
		numCards = 0;
	}
	
	public void addCard(Card newCard) {
		if (numCards != 0)
			newCard.nextCard = topCard;
		topCard = newCard;
		numCards++;
	}
	
	public Card drawTopCard()
	{
		if (numCards != 0) {
			Card tempCard = topCard;
			topCard = topCard.nextCard;
			numCards--;
			tempCard.nextCard = null;
			return tempCard;
		}
		else
			return new Card("null",0);
	}
	
	public void insertCardAt(Card newCard, int index) {
		if(index > numCards)
		{
			insertCardAt(newCard,numCards);
			return;
		}
		else if (index == 0)
		{
			newCard.nextCard = topCard;
			topCard = newCard;
		}
		else {
			Card tempCard = topCard;
			for (int ii = 0; ii < index-1; ii++) {
				tempCard = tempCard.nextCard;
			}
			newCard.nextCard = newCard;
			tempCard.nextCard = newCard;
		}
		numCards++;
	}
	
	public Card removeCardAt(int index) {
		if(index > numCards)
		{
			return new Card("null",0);
		}
		else if (index == 0)
		{
			Card tempCard = topCard;
			topCard = topCard.nextCard;
			tempCard.nextCard = null;
			numCards--;
			return tempCard;
		}
		else {
			Card tempCard = topCard;
			for (int ii = 0; ii < index-1; ii++) {
				tempCard = tempCard.nextCard;
			}
			Card remCard = tempCard.nextCard;
			tempCard.nextCard = remCard.nextCard;
			remCard.nextCard = null;
			numCards--;
			return remCard;
		}
	}
	
	public void shuffle() {
		Card tempCard;
		int randIndex;
		Random rng = new Random();
		
		for (int placedCount = 0; placedCount < numCards; placedCount++)
		{
			randIndex = rng.nextInt(numCards-placedCount) + placedCount;
			tempCard = this.removeCardAt(randIndex);
			addCard(tempCard);
		}
	}
	
	public void sort() {
		if (numCards < 2)
		{
			return;
		}
		while (bubbleSortPass());
	}
	
	private boolean bubbleSortPass() {
		Card tempCard;
		boolean swappedSomething = false;
		boolean swap = compareCards(topCard, topCard.nextCard);
		if (swap)
		{
			swappedSomething = true;
			tempCard = topCard;
			topCard = topCard.nextCard;
			tempCard.nextCard = topCard.nextCard;
			topCard.nextCard = tempCard;
		}
		
		Card prevCard = topCard;
		tempCard = topCard.nextCard;
		while(tempCard.nextCard != null)
		{
			if (testAndSwapCards(prevCard, tempCard))
			{
				swappedSomething = true;
				tempCard = prevCard.nextCard;
			}
			else
			{
				prevCard = tempCard;
				tempCard = tempCard.nextCard;
			}
		}
		return swappedSomething;
	}
	
	private boolean testAndSwapCards(Card prevCard, Card cardToSwap) {
		if (cardToSwap.nextCard == null)
			return false;
		else
		{
			if (compareCards(cardToSwap, cardToSwap.nextCard))
			{
				prevCard.nextCard = cardToSwap.nextCard;
				cardToSwap.nextCard = prevCard.nextCard.nextCard;
				prevCard.nextCard.nextCard = cardToSwap;
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	// Returns true if cardTwo is "greater than" cardOne, false otherwise
	private boolean compareCards(Card cardOne, Card cardTwo) {
		int comparison = cardOne.suit.compareTo(cardTwo.suit);
		if (comparison > 0) 
		{
			return true;
		}
		else if (comparison < 0)
		{
			return false;
		}
		else
		{	
			if (cardOne.value > cardTwo.value) {
				return true;
			}
			else
			{
				return false;
			}
		}
	}
}
