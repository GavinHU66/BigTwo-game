/**
 * The class models a hand of Triple in Big Two game. Subclass of Hand.
 *
 * @author Hu Qiyun
 */
public class Triple extends Hand {
	 
	 /**
	  * Constructor of Triple Hand, based on the constructor of its super class Hand.
	  *
	  * @param player The player who plays this hand.
	  * @param cards The list of cards held by the player.
	  */
	 Triple(CardGamePlayer player, CardList cards){
		 super(player,cards);
	 }

	 /**
	  * Check the Triple hand is valid or not.
	  *
	  * @return Return true if the Hand of Triple is valid, false if not.
	  * @see Hand#isValid()
	  */
	 @Override
	 public boolean isValid(){
		 if (
				 size() == 3 &&
				 getCard(0).getRank() == getCard(1).getRank() &&
				 getCard(0).getRank() == getCard(2).getRank() &&
				 getCard(2).getRank() == getCard(2).getRank()
				 
			) { return true; } else {
				return false;
			}
	 }

	 /**
	  * Get the type of this Triple Hand.
	  *
	  * @return A String value of this Hand of Triple, i.e., "Triple".
	  * @see Hand#getType()
	  */
	 @Override
	 public String getType(){
		 return "Triple";
	 }
 }
