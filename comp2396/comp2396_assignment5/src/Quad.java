/**
 * The class models a hand of Quad in Big Two game. Subclass of Hand.
 *
 * @author Hu Qiyun
 */
public class Quad extends Hand {

	 /**
	  * Constructor of Quad Hand, based on the constructor of its super class Hand.
	  *
	  * @param player The player who plays this hand.
	  * @param cards The list of cards held by the player.
	  */
	 Quad(CardGamePlayer player, CardList cards){
		 super(player,cards);
	 }

	 /**
	  * This method checks if this Quad Hand beats a specified Hand.
	  *
	  * @param hand The other Hand to be compared.
	  * @return Return true if this hand beats its opponent, false if not.
	  * @see Hand#beats(Hand hand)
	  */
	 @Override
	 public boolean beats(Hand hand) {
	  
		 if (hand.getType()=="Straight" || hand.getType()=="Flush" || hand.getType()=="FullHouse") {
			 return true;
		 } else {
			 return super.beats(hand);
		 }
	 }

	 /**
	  * Retrieve the card on the top of this Quad Hand, the card in the quadruplet
	  * with the highest suit in a quad is referred to as the top card of this quad.
	  * 
	  * @return Return the top card of this Hand.
	  */
	 @Override
	 public Card getTopCard(){
	  
		 if (isValid()) {
			 sort();
			 Card topCard = new Card(0,0);
			 if (getCard(0).getRank() == getCard(1).getRank() && getCard(0).getRank() == getCard(2).getRank() && getCard(0).getRank() == getCard(3).getRank()){
				 for (int i=0; i<=3; i++){ // AAAAB
					 if (getCard(i).getSuit()>topCard.getSuit()){
						 topCard = getCard(i);
					 }
				 }	      
			 } else if (getCard(1).getRank() == getCard(2).getRank() && getCard(1).getRank() == getCard(3).getRank() && getCard(1).getRank() == getCard(4).getRank()){
				 for (int i=1; i<=4; i++){ // ABBBB
					 if (getCard(i).getSuit()>topCard.getSuit()){
						 topCard = getCard(i);
					 }
				 }		      
			 }
			 return topCard;
		 } else {
			 return null;
		 }
	 }


	 /**
	  * Check the Quad hand is valid or not.
	  *
	  * @return Return true if the Hand of Quad is valid, false if not.
	  * @see Hand#isValid()
	  */
	 @Override
	 public boolean isValid(){
		 
		 if (size() == 5) {
			 sort();
  				if (getCard(0).getRank() == getCard(1).getRank() && getCard(0).getRank() == getCard(2).getRank() && getCard(0).getRank() == getCard(3).getRank() && getCard(0).getRank() != getCard(4).getRank()){
  					return true;
  				}
  				if (getCard(1).getRank() == getCard(2).getRank() && getCard(1).getRank() == getCard(3).getRank() && getCard(1).getRank() == getCard(4).getRank() && getCard(1).getRank() != getCard(0).getRank()){
  					return true;
  				}
  				return false;
		 }
		 return false; 		
	 }

  	/**
	 * Get the type of this Quad Hand.
	 *
	 * @return A String value of this Hand of Quad, i.e., "Quad".
	 *
	 * @see Hand#getType()
	 */
  	@Override
  	public String getType(){
  		return "Quad";
  	}

 }
