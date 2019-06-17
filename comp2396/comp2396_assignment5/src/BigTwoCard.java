/**
 * This class is a subclass of the Card class, and is used to model a card used in a Big Two card game.
 *
 * @author Hu Qiyun
 */
public class BigTwoCard extends Card {
	

	private static final long serialVersionUID = -5822018182891811204L;


	/**
	 * Constructor for building a card with the specified suit and rank.
	 * 
	 * @param suit The suit of the BigTwoCard 0, 1, 2, 3.
	 * @param rank The rank of the BigTwoCard 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A, 2.
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
	}

	
	/**
	 * A method for comparing the order of this card with the specified card by the rule of Big Two card game.
	 * 
	 * @param card Another card which is to be compared to.
	 * @return Return 1 if this is larger than card, 0 if the same, -1 if smaller.
	 * @see Card#compareTo(Card)
	 */
	@Override
	public int compareTo(Card card) {
		
		// CardA is this card, CardB is the another card to be compared.
		int rankOfCardA = (getRank() == 0 || getRank() == 1)? getRank()+13 : getRank();
		int rankOfCardX = (card.getRank() == 0 || card.getRank() == 1)? card.getRank()+13 : card.getRank();
		
		int suitOfCardA = getSuit();
		int suitOfCardX = card.getSuit();
		
		if (rankOfCardA>rankOfCardX) {
			return 1;
		} else if (rankOfCardA<rankOfCardX) {
			return -1;
		} else if (suitOfCardA>suitOfCardX) {
			return 1;
		} else if (suitOfCardA<suitOfCardX) {
			return -1;
		} else {
			return 0;
		}
		
	}
}
