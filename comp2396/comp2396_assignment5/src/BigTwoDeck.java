/**
 * This class is a subclass of the Deck class, and is used to model a deck of cards used in a Big Two card game.
 *
 * @author Hu Qiyun
 */
public class BigTwoDeck extends Deck {
	
	/**
	 * This method overrides the initialize method in Deck class. This method removes 
	 * all cards from the deck, creates 52 Big Two cards and add them to the deck.
	 * 
	 * @see Deck#initialize()
	 */
	@Override
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				addCard(card);
			}
		}
	}	
	
}
