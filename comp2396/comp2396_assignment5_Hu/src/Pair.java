/**
 * The class models a hand of Pair in Big Two game. Subclass of Hand.
 *
 * @author Hu Qiyun
 */
public class Pair extends Hand {

	 /**
	  * Constructor of Pair Hand, based on the constructor of its super class Hand.
	  *
	  * @param player The player who plays this hand.
	  * @param cards The list of cards held by the player.
	  */
	 Pair(CardGamePlayer player, CardList cards){
		 super(player,cards);
	 }

	 /**
	  * Check the Pair hand is valid or not.
	  *
	  * @return Return true if the Hand of Pair is valid, false if not. 
	  * @see Hand#isValid()
	  */
	 @Override
	 public boolean isValid(){
		 return ((size() == 2) && (getCard(0).getRank() == getCard(1).getRank())) ? true : false;
	 }

	 /**
	  * Get the type of this Hand.
	  *
	  * @return A String value of this Hand of Pair, i.e., "Pair".
	  * @see Hand#getType()
	  */
	 @Override
	 public String getType(){
		 return "Pair";
	 }

 }
