
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deck deck = new Deck();
		
		deck.addCard(new Card("heart",1));
		deck.addCard(new Card("heart",2));
		deck.addCard(new Card("heart",3));
		deck.addCard(new Card("heart",4));
		deck.addCard(new Card("heart",5));
		deck.addCard(new Card("heart",6));
		deck.addCard(new Card("heart",7));
		deck.addCard(new Card("heart",8));
		deck.addCard(new Card("heart",9));
		deck.addCard(new Card("heart",10));
		deck.addCard(new Card("heart",11));
		deck.addCard(new Card("heart",12));
		deck.addCard(new Card("heart",13));
		deck.addCard(new Card("spade",1));
		deck.addCard(new Card("spade",2));
		deck.addCard(new Card("spade",3));
		deck.addCard(new Card("spade",4));
		deck.addCard(new Card("spade",5));
		deck.addCard(new Card("spade",6));
		deck.addCard(new Card("spade",7));
		deck.addCard(new Card("spade",8));
		deck.addCard(new Card("spade",9));
		deck.addCard(new Card("spade",10));
		deck.addCard(new Card("spade",11));
		deck.addCard(new Card("spade",12));
		deck.addCard(new Card("spade",13));
		deck.addCard(new Card("club",1));
		deck.addCard(new Card("club",2));
		deck.addCard(new Card("club",3));
		deck.addCard(new Card("club",4));
		deck.addCard(new Card("club",5));
		deck.addCard(new Card("club",6));
		deck.addCard(new Card("club",7));
		deck.addCard(new Card("club",8));
		deck.addCard(new Card("club",9));
		deck.addCard(new Card("club",10));
		deck.addCard(new Card("club",11));
		deck.addCard(new Card("club",12));
		deck.addCard(new Card("club",13));
		deck.addCard(new Card("diamond",1));
		deck.addCard(new Card("diamond",2));
		deck.addCard(new Card("diamond",3));
		deck.addCard(new Card("diamond",4));
		deck.addCard(new Card("diamond",5));
		deck.addCard(new Card("diamond",6));
		deck.addCard(new Card("diamond",7));
		deck.addCard(new Card("diamond",8));
		deck.addCard(new Card("diamond",9));
		deck.addCard(new Card("diamond",10));
		deck.addCard(new Card("diamond",11));
		deck.addCard(new Card("diamond",12));
		deck.addCard(new Card("diamond",13));
		
		deck.shuffle();
		
		//for (int ii = 0; ii < 5; ii++)
		//{
		//	deck.drawTopCard().print();
		//}
		
		deck.sort();
		
		for (int ii = 0; ii < 5; ii++)
		{
			deck.drawTopCard().print();
		}
	}
}
