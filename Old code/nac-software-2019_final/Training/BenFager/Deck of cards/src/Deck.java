import java.util.ArrayList;
import java.util.Random;

public class Deck 
{
	public enum suit
	{
		CLUBS, HEARTS, SPADES, DIAMONDS
	}
	public enum value
	{
		ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
	}
	
	suit[] suitList = {suit.CLUBS, suit.HEARTS, suit.SPADES, suit.DIAMONDS};
	value[] valueList = {value.ACE, value.TWO, value.THREE, value.FOUR, value.FIVE, value.SIX, value.SEVEN, value.EIGHT, value.NINE, value.TEN, value.JACK, value.QUEEN, value.KING};
	
	Card topCard;
	int cardCount = 0;
	public Deck()
	{
		deckReset();
	}
	/**
	 * The method used to reset the deck and put 52 cards, perfectly sorted, into the deck.
	 */
	public void deckReset()
	{
		cardCount = 0;
		topCard = null;
		for (int suits = 0; suits <= 3; suits++)
		{
			for (int values = 0; values <= 12; values++)
			{
				putCardOnBottom(new Card(suitList[suits], valueList[values]));
			}
		}
	}
	/**
	 * Method used to put a card on the bottom of the deck
	 * @param bottomCard
	 * The card class that is put on the bottom
	 */
	public void putCardOnBottom(Card bottomCard)
	{
		cardCount += 1;
		if(topCard != null)
		{
			topCard.putCardOnBottom(bottomCard);
		}
		else
		{
			topCard = bottomCard;
		}
	}
	/**
	 * Takes the top card off the deck and returns it
	 * @return
	 * The card that was on the top
	 */
	public Card drawTopCard()
	{
		
		Card returnCard = topCard;
		if(returnCard != null)
		{
			topCard = topCard.m_nextCard;
			cardCount--;
			returnCard.m_nextCard = null;
			return returnCard;
		}
		else
		{
			return null;
		}
	}
	/**
	 * Gets the number of cards in the deck
	 * @return
	 * The number of cards in the deck
	 */
	public int getCardCount()
	{
		return cardCount;
	}
	/**
	 * Places a card a set number down into the deck
	 * @param cardToPlace
	 * The Card being placed
	 * @param amountDown
	 * The number of cards into the deck that it places the card, 0 is top, 1 is one card down, etc
	 */
	public void placeCardAtSetLocation(Card cardToPlace, int amountDown)
	{
		cardCount++;
		if(amountDown == 0)
		{
			cardToPlace.m_nextCard = topCard;
			topCard = cardToPlace;
		}
		else
		{
			if(topCard != null)
			{
				topCard.putCardDownLevel(cardToPlace, (amountDown - 1));
			}
			else
			{
				System.out.println("Not enough cards in the pile, placing on the bottom");
				topCard = cardToPlace;
			}
		}
	}
	/**
	 * Gets a card from a set position in the deck, removes it, and returns it
	 * @param amountDown
	 * The number of cards into the deck that it gets the card, 0 is top, 1 is one card down, etc
	 * @return
	 * returns the card
	 */
	public Card getCardFromLocation(int amountDown)
	{
		if(topCard != null)
		{
			cardCount--;
			if(amountDown == 0)
			{
				Card drawnCard = topCard;
				topCard = topCard.m_nextCard;
				return drawnCard;
			}
			else
			{
				return(topCard.getCardFromLocation(amountDown - 1));
			}
		}
		else
		{
			System.out.println("There are not enough cards in the deck, returning 'null'");
			return null;
		}
	}
	public Card getBottomCard()
	{
		if(topCard == null)
		{
			return null;
		}
		else if(topCard.m_nextCard == null)
		{
			cardCount--;
			Card drawnCard = topCard;
			topCard = null;
			return drawnCard;
		}
		else
		{
			cardCount--;
			return(topCard.getBottomCard());
		}
	}
	public void shuffleOne()
	{
		ArrayList<Card> cardList = new ArrayList<Card>();
		Boolean isFinished = false;
		while(!isFinished)
		{
			Card cardDrawn = getBottomCard();
			if(cardDrawn == null)
			{
				isFinished = true;
			}
			else
			{
				cardList.add(cardDrawn);
			}
		}
		Random ran = new Random();
		isFinished = false;
		while(!isFinished)
		{
			if(cardList.size() > 1)
			{
			int x = ran.nextInt(cardList.size() - 1);
			
			Card bottomCard = (cardList.remove(x));
			putCardOnBottom(bottomCard);
			}
			else
			{
				isFinished = true;
			}
		}
		Card bottomCard = (cardList.remove(0));
		putCardOnBottom(bottomCard);
	}
	/**
	 * shuffles twice for redundancy and because it is more random then
	 */
	public void shuffle()
	{
		shuffleOne();
		shuffleOne();
	}
}