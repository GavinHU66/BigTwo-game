/**
 * This class is a subclass of the CardList class, and is used to model a hand of cards.
 *
 * @author Hu Qiyun
 */
public abstract class Hand extends CardList {

	
	/**
	 * Constructor for building a hand with the specified player and list of cards.
	 * 
	 * @param player The player who plays this hand.
	 * @param cards The list of Cards to compose the hand.
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player = player;
		for (int i=0; i<cards.size(); i++) {
			addCard(cards.getCard(i));
		}
	}

	private CardGamePlayer player; //– the player who plays this hand.

	/**
	 * Retrieve the player of this hand.
	 * 
	 * @return The player of this hand.
	 */
	public CardGamePlayer getPlayer() { //– a method for retrieving the player of this hand.
		return this.player;
	}
	
	/**
	 * Retrieve the top card of this hand.
	 * 
	 * @return The top card of this hand or returns null if the hand is empty.
	 */
	public Card getTopCard() { //– a method for retrieving the top card of this hand.
		sort();
		if (!isEmpty()) {
			return getCard(size() - 1);
		}
		return null;
	}
	
	/**
	 * This method checks if this hand beats a specified hand, and it is waiting for specification if needed.
	 * 
	 * @param hand The another specified hand which is to be compared. 
	 * @return Return true if the hands beats the given specified hand, false if not.
	 */
	public boolean beats(Hand hand) { // a method for checking if this hand beats a specified hand.
		if (getType() == hand.getType() && getTopCard().compareTo(hand.getTopCard())>0) {
			return true;
		}
		return false;
	}
		
	 /**
	  * This method tests if this hand contains the card Diamond Three. 
	  * 
	  * @return Return true if this hand contains Diamond Three, false if not.
	  */
	 boolean containDiamondThree() {
		 for (int i=0; i<size(); i++) {
			 if (getCard(i).getSuit() == 0 && getCard(i).getRank() == 2) {
				 return true;
			 }
		 }
		 return false;
	 }
	 
	/**
	 * Abstract method which will be implemented by the subclasses to indicate whether this type of hand is valid.
	 *
	 * @return Returns a boolean value true if this type of hand is valid, false if not.
	 */
	public abstract boolean isValid(); // a method for checking if this is a valid hand.
	
	/**
	 * Abstract method which will be implemented by the subclasses to a method to return a string specifying the type of this hand.
	 *
	 * @return A String object of the hand's name.			
	 */
	public abstract String getType(); // a method for returning a string specifying the type of this hand.
		
		
}
