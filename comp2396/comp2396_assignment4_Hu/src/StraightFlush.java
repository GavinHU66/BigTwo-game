/**
 * The class models a hand of StraightFlush in Big Two game. Subclass of Hand.
 *
 * @author Hu Qiyun
 */
public class StraightFlush extends Hand {

	 /**
	  * Constructor of StraightFlush Hand, based on the constructor of its super class Hand.
	  *
	  * @param player The player who plays this hand.
	  * @param cards The list of cards held by the player.
	  */
	 StraightFlush(CardGamePlayer player, CardList cards){
		 super(player,cards);
	 }

	 /**
	  * This method checks if this StraightFlush Hand beats a specified Hand.
	  *
	  * @param hand The other Hand to be compared.
	  * @return Return true if this hand beats its opponent, false if not.
	  * @see Hand#beats(Hand hand)
	  */
	 @Override
	 public boolean beats(Hand hand) {
	  
		 if (hand.getType()=="Straight" || hand.getType()=="Flush" || hand.getType()=="FullHouse" || hand.getType()=="Quad") {
			 return true;
		 } else {
			 return super.beats(hand);
		 }
	 }

	 /**
	  * Retrieve the card on the top of this StraightFlush Hand, the card with the 
	  * highest rank in a straight flush is referred to as the top card of this straight flush.
	  * 
	  * @return Return the top card of this Hand.
	  */
	 @Override
	 public Card getTopCard(){
		 // the card with the highest rank is referred to as the top card.
		 if (isValid()) {
			 sort();
			 return getCard(4);
		 } else {
			 return null;
		 }
	 }
	 
	 /**
	  * Check the StraightFlush hand is valid or not.
	  *
	  * @return Return true if the Hand of StraightFlush is valid, false if not.
	  * @see Hand#isValid()
	  */
	 @Override
	 public boolean isValid(){
		 if (size() == 5) {
			 sort();
			 if (getCard(0).getSuit() == getCard(1).getSuit() && getCard(0).getSuit() == getCard(2).getSuit() && getCard(0).getSuit() == getCard(3).getSuit() && getCard(0).getSuit() == getCard(4).getSuit()) {
				 for (int i = 0; i < 4; i++) {
					 if ((getCard(i).getRank() + 1) % 13 != getCard(i + 1).getRank() % 13) {
						 return false;
					 }
				 }
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }


	 /**
	  * Get the type of this StraigtFlush Hand.
	  *
	  * @return A String value of this Hand of StraightFlush, i.e., "StraightFlush".
	  * @see Hand#getType()
	  */
	 @Override
	 public String getType(){
		 return "StraightFlush";
	 }

 }
