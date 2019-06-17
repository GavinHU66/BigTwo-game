/**
 * The class models a hand of Flush in Big Two game. Subclass of Hand.
 *
 * @author Hu Qiyun
 */
public class Flush extends Hand {
	 
	 /**
	  * Constructor of Flush Hand, based on the constructor of its super class Hand.
	  *
	  * @param player The player who plays this hand.
	  * @param cards The list of cards held by the player.
	  */
	 Flush(CardGamePlayer player, CardList cards){
		 super(player,cards);
	 }

	 /**
	  * Check the Flush hand is valid or not.
	  *
	  * @return Return true if the Hand of Flush is valid, false if not.
	  * @see Hand#isValid()
	  */
	 @Override
	 public boolean isValid(){
		 sort();

		 if (size() == 5){
			 for (int i=0; i<size()-1; i++){
				 if (getCard(i).getSuit() != getCard(i+1).getSuit()){
					 return false;
				 }
			 }
			 for (int i = 0; i < 4; i++) {
				 if ((getCard(i).getRank() + 1) % 13 != getCard(i + 1).getRank() % 13) {
					 return true;
				 }
			 }
			 return false;
		 }
		 return false;
	 }

	 /**
	  * Get the type of this Flush Hand.
	  *
	  * @return A String value of this Hand of Flush, i.e., "Flush".
	  * @see Hand#getType()
	  */
	 @Override
	 public String getType(){
		 return "Flush";
	 }
  
	 /**
	  * This method checks if this Flush Hand beats a specified Hand,
	  * A flush with a higher suit beats a flush with a lower suit, 
	  * for flushes with the same suit, the one having a top card with a 
	  * higher rank beats the one having a top card with a lower rank.
	  *
	  * @param hand The other Hand to be compared.
	  * @return Return true if this hand beats its opponent, false if not. 
	  * @see Hand#beats(Hand hand)
	  */
	 @Override
	 public boolean beats(Hand hand) {
	  
		 if (hand.getType() == "Straight") {
			 return true;
		 } else if (hand.getType() == "Flush") {
			 if (getCard(0).getSuit() > hand.getCard(0).getSuit()) {
				 return true;
			 } else if (getCard(0).getSuit() < hand.getCard(0).getSuit()) {
				 return false;
			 } else {
				 return super.beats(hand);
			 }
		 }
		 return false;
	 }
  
  
	 /**
	  * Retrieve the card on the top of this Flush Hand, 
	  * the card with the highest rank in a flush is referred to as the top card of this flush.
	  *
	  * @return Return the top card of this Hand.
	  */
	 @Override
	 public Card getTopCard(){
		 if (isValid()) {
			 sort();
			 return getCard(4);	  
		 } else {
			 return null;
		 }
	 }

 }
