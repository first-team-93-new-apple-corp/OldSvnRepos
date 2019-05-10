
public class Card
{
	
	Card m_nextCard;
	Deck.suit m_suitData;
	Deck.value m_valueData;
	/**
	 * A class that represents a card from a 52-card deck and stores values for the suit and the deck
	 * @param suitData
	 * The data for the suit of the card
	 * @param valueData
	 * The data for the value of the card
	 */
	public Card(Deck.suit suitData, Deck.value valueData)
	{
		m_suitData = suitData;
		m_valueData = valueData;
	}
	/**
	 * Used to set the value of the card that this card is linked to, or under
	 * @param nextCard
	 * The pointer to the card that this card will point to
	 */
	public void setNextCard(Card nextCard)
	{
		m_nextCard = nextCard;
	}
	/**
	 * Places a card on the bottom of the deck
	 * @param cardOnBottom
	 * The card to be placed on the bottom
	 */
	public void putCardOnBottom(Card cardOnBottom)
	{
		if(m_nextCard != null)
		{
			m_nextCard.putCardOnBottom(cardOnBottom);
		}
		else
		{
			setNextCard(cardOnBottom);
		}
	}
	/**
	 * Places a card a set number down into the deck
	 * @param cardToPlace
	 * The Card being placed
	 * @param amountDown
	 * The number of cards into the deck that it places the card, 0 is top, 1 is one card down, etc
	 */
	public void putCardDownLevel(Card cardToPlace, int amountDown)
	{
		Card tempCard = cardToPlace;
		if(amountDown == 0)
		{
			tempCard.m_nextCard = m_nextCard;
			m_nextCard = tempCard;
		}
		else if (m_nextCard == null)
		{
			m_nextCard = tempCard;
			System.out.println("Not enough cards in the pile, placing on the bottom");
		}
		else
		{
			m_nextCard.putCardDownLevel(tempCard, amountDown - 1);
		}
	}
	public Card getCardFromLocation(int amountDown)
	{
		if(m_nextCard != null)
		{
			if(amountDown == 0)
			{
				Card drawnCard = m_nextCard;
				m_nextCard = m_nextCard.m_nextCard;
				return drawnCard;
			}
			else
			{
				return(m_nextCard.getCardFromLocation(amountDown - 1));
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
		if(m_nextCard.m_nextCard == null)
		{
			Card cardToReturn = m_nextCard;
			m_nextCard = null;
			return cardToReturn;
		}
		else
		{
			return m_nextCard.getBottomCard();
		}
	}
}
