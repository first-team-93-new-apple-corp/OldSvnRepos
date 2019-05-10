
public class Main {

public static void main(String[] args) {
		
		//Creating empty card spaces
		deck myDeck = new deck();
	
		//Diamonds
		myDeck.addCard(new Card("Ace", "Diamonds"));
		myDeck.addCard(new Card("2", "Diamonds"));
		myDeck.addCard(new Card("3", "Diamonds"));
		myDeck.addCard(new Card("4", "Diamonds"));
		myDeck.addCard(new Card("5", "Diamonds"));
		myDeck.addCard(new Card("6", "Diamonds"));
		myDeck.addCard(new Card("7", "Diamonds"));
		myDeck.addCard(new Card("8", "Diamonds"));
		myDeck.addCard(new Card("9", "Diamonds"));
		myDeck.addCard(new Card("10", "Diamonds"));
		myDeck.addCard(new Card("Jack", "Diamonds"));
		myDeck.addCard(new Card("Queen", "Diamonds"));
		myDeck.addCard(new Card("King", "Diamonds"));
		
		//Hearts
		myDeck.addCard(new Card("Ace", "Hearts"));
		myDeck.addCard(new Card("2", "Hearts"));
		myDeck.addCard(new Card("3", "Hearts"));
		myDeck.addCard(new Card("4", "Hearts"));
		myDeck.addCard(new Card("5", "Hearts"));
		myDeck.addCard(new Card("6", "Hearts"));
		myDeck.addCard(new Card("7", "Hearts"));
		myDeck.addCard(new Card("8", "Hearts"));
		myDeck.addCard(new Card("9", "Hearts"));
		myDeck.addCard(new Card("10", "Hearts"));
		myDeck.addCard(new Card("Jack", "Hearts"));
		myDeck.addCard(new Card("Queen", "Hearts"));
		myDeck.addCard(new Card("King", "Hearts"));
		
		//Spades
		myDeck.addCard(new Card("Ace", "Spades"));
		myDeck.addCard(new Card("2", "Spades"));
		myDeck.addCard(new Card("3", "Spades"));
		myDeck.addCard(new Card("4", "Spades"));
		myDeck.addCard(new Card("5", "Spades"));
		myDeck.addCard(new Card("6", "Spades"));
		myDeck.addCard(new Card("7", "Spades"));
		myDeck.addCard(new Card("8", "Spades"));
		myDeck.addCard(new Card("9", "Spades"));
		myDeck.addCard(new Card("10", "Spades"));
		myDeck.addCard(new Card("Jack", "Spades"));
		myDeck.addCard(new Card("Queen", "Spades"));
		myDeck.addCard(new Card("King", "Spades"));
		
		//Clubs
		myDeck.addCard(new Card("Ace", "Clubs"));
		myDeck.addCard(new Card("2", "Clubs"));
		myDeck.addCard(new Card("3", "Clubs"));
		myDeck.addCard(new Card("4", "Clubs"));
		myDeck.addCard(new Card("5", "Clubs"));
		myDeck.addCard(new Card("6", "Clubs"));
		myDeck.addCard(new Card("7", "Clubs"));
		myDeck.addCard(new Card("8", "Clubs"));
		myDeck.addCard(new Card("9", "Clubs"));
		myDeck.addCard(new Card("10", "Clubs"));
		myDeck.addCard(new Card("Jack", "Clubs"));
		myDeck.addCard(new Card("Queen", "Clubs"));
		myDeck.addCard(new Card("King", "Clubs"));
		
		//Printing cards
		System.out.println("Cards in deck:");
		System.out.println(" ");
		
		Card nextCard;
		
		for(int i = 0; i < 52; i++) {
			
			nextCard = myDeck.drawCard();
			System.out.println(nextCard.number + " of " + nextCard.suit);
			
		}
		
	}	
	
}
