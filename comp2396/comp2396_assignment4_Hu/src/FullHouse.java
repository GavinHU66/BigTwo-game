/**
 * The class models a hand of FullHouse in Big Two game. Subclass of Hand.
 *
 * @author Hu Qiyun
 *
 */
public class FullHouse extends Hand {
	
	/**
	 * Constructor of FullHouse Hand, based on the constructor of its super class Hand.
	 *
	 * @param player The player who play this hand.
	 * @param cards The list of cards held by the player.
	 */
	FullHouse(CardGamePlayer player, CardList cards){
		super(player,cards);
	}

	/**
	 * This method checks if this FullHouse Hand beats a specified Hand.
	 *
	 * @param hand The other Hand to be compared.
	 * @return Return true if this hand beats its opponent, false if not.
	 * @see Hand#beats(Hand hand)
	 */
	@Override
	public boolean beats(Hand hand) {	  
	  
		if (hand.getType()=="Straight" || hand.getType()=="Flush") {
			return true;
		} else {
			return super.beats(hand);
		}
  }

	/**
	 * Retrieve the card on the top of this FullHouse Hand, the card in the triplet 
	 * with the highest suit in a full house is referred to as the top card of this FullHouse.
	 *	 
	 * @return Return the top card of this Hand.
	 */
	@Override
	public Card getTopCard(){
		
		// the card in the triplet with the highest suit is referred to as the top card.
		sort();
		Card topCard = new Card(0,0);
		if (getCard(0).getRank() == getCard(1).getRank() && getCard(0).getRank() == getCard(2).getRank() && getCard(3).getRank() == getCard(4).getRank()){
			for (int i=0; i<=2; i++){ // AAABB
				if (getCard(i).getSuit()>topCard.getSuit()){
					topCard = getCard(i);
				}
			}
		} else if (getCard(0).getRank() == getCard(1).getRank() && getCard(2).getRank() == getCard(3).getRank() && getCard(2).getRank() == getCard(4).getRank()){
			for (int i=2; i<=4; i++){ // AABBB
				if (getCard(i).getSuit()>topCard.getSuit()){
					topCard = getCard(i);
				}
			}
		}
		return topCard;
  }


	/**
	 * Check the FullHouse hand is valid or not.
	 *
	 * @return Return true if the Hand of FullHouse is valid, false if not.
	 * @see Hand#isValid()
	 */
	@Override
	public boolean isValid(){
		if (size() == 5){
			sort();
			if (getCard(0).getRank() == getCard(1).getRank() && getCard(0).getRank() == getCard(2).getRank() && getCard(3).getRank() == getCard(4).getRank()){
				return true;
			} else if (getCard(0).getRank() == getCard(1).getRank() && getCard(2).getRank() == getCard(3).getRank() && getCard(2).getRank() == getCard(4).getRank()){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Get the type of this FullHouse Hand.
	 *
	 * @return A String value of this Hand of FullHouse, i.e., "FullHouse".
	 *
	 * @see Hand#getType()
	 */
	@Override
	public String getType(){
		return "FullHouse";
	}
 }
