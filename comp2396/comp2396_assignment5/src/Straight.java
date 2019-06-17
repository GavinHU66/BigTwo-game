/**
 * The class models a hand of Straight in Big Two game. Subclass of Hand.
 *
 * @author Hu Qiyun
 */
public class Straight extends Hand {
	 
	 /**
	  * Constructor of Straight Hand, based on the constructor of its super class Hand.
	  *
	  * @param player The player who plays this hand.
	  * @param cards The list of cards held by the player.
	  */
	 Straight(CardGamePlayer player, CardList cards){
		 super(player,cards);
	 }

	 /**
	  * Check the Straight hand is valid or not.
	  *
	  * @return Return true if the hand is valid, false if not.
	  * @see Hand#isValid()
	  */
	 @Override
	 public boolean isValid(){
		 if (size() == 5) {
			 sort();
			 for (int i = 0; i < 4; i++) {
				 if ((getCard(i).getRank() + 1) % 13 != getCard(i + 1).getRank()) {
					 return false;
				 }
			 }
			 if (getCard(0).getSuit() == getCard(1).getSuit() && getCard(0).getSuit() == getCard(2).getSuit() && getCard(0).getSuit() == getCard(3).getSuit() && getCard(0).getSuit() == getCard(4).getSuit()) {
				 return false;
			 }
			 return true;
		 } 
		 return false;
	 }

  
  	/**
	 * Get the type of this Straight Hand.
	 *
	 * @return A String value of this Hand of Straight, i.e., "Straight".
	 *
	 * @see Hand#getType()
	 */
	@Override
	public String getType(){
		return "Straight";
	}


 }
