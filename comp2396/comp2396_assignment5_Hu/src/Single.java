/**
 * The class models a hand of Single in Big Two game. Subclass of Hand.
 *
 * @author Hu Qiyun
 */
public class Single extends Hand {
	 
	 /**
	  * Constructor of Single Hand, based on the constructor of its super class Hand.
	  *
	  * @param player The player who plays this hand.
	  * @param cards The list of cards held by the player.
	  */
	 Single(CardGamePlayer player, CardList cards){
		 super(player,cards);
	 }

	 /**
	  * Check the Single hand is valid or not.
	  *
	  * @return Return true if the Single of Quad is valid, false if not.
	  * @see Hand#isValid()
	  */
	 @Override
	 public boolean isValid(){
		 return (size() == 1)? true : false;
	 }

	 /**
	  * Get the type of this Hand.
	  *
	  * @return A String value of this Hand of Single, i.e., "Single".
	  * @see Hand#getType()
	  */
	 @Override
	 public String getType(){
		 return "Single";
	 }
 }
