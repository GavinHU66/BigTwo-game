
public class TesterClass {
	   public static Hand composeHand(CardGamePlayer player, CardList cards) {
		   Hand hand;
		   if (player == null || cards == null) {
			   return null;
		   } else {
			   int x = cards.size();
			   switch (x) {
			   case 1:
				   hand = new Single(player, cards);
				   if (hand.isValid()) {
					   return hand;
				   }
				   break;
			   case 2:
				   hand = new Pair(player, cards);
				   if (hand.isValid()) {
					   return hand;
				   }
				   break;
			   case 3:
				   hand = new Triple(player, cards);
				   if (hand.isValid()) {
					   return hand;
				   }
				   break;
			   case 5:
				   hand = new Straight(player, cards);
				   if (hand.isValid()) {
					   return hand;
				   }
				   hand = new Flush(player, cards);
				   if (hand.isValid()) {
					   return hand;
				   }
				   hand = new FullHouse(player, cards);
				   if (hand.isValid()) {
					   return hand;
				   }
				   hand = new Quad(player, cards);
				   if (hand.isValid()) {
					   return hand;
				   }
				   hand = new StraightFlush(player, cards);
				   if (hand.isValid()) {
					   return hand;
				   }
				   break;

			   }
			   return null;
		   }
	   }
	public static void main(String[] args) {
		CardGamePlayer player1 = new CardGamePlayer();
		CardGamePlayer player2 = new CardGamePlayer();
		
		CardList cards1 = new CardList();
		cards1.addCard(new BigTwoCard(1,6));
		cards1.addCard(new BigTwoCard(2,7));
		cards1.addCard(new BigTwoCard(3,8));
		cards1.addCard(new BigTwoCard(0,9));
		cards1.addCard(new BigTwoCard(1,10));
		
		Hand hand1 = composeHand(player1, cards1);
		
		
		Card topCard = hand1.getTopCard();
		System.out.println(topCard.getSuit() + " " + topCard.getRank());
		
		
		
		
		CardList cards2 = new CardList();
		cards2.addCard(new BigTwoCard(3,8));
		cards2.addCard(new BigTwoCard(3,9));
		cards2.addCard(new BigTwoCard(3,10));
		cards2.addCard(new BigTwoCard(0,6));
		cards2.addCard(new BigTwoCard(3,7));
		
		Hand hand2 = composeHand(player2, cards2);

		if (hand1 == null ) {
			System.out.println("hand 1 is null");
		} else {
			System.out.println("hand 1: ");
			System.out.println(hand1.getType());
			hand1.print(true, true);
		}
		System.out.println();
		if (hand2 == null ) {
			System.out.println("hand 2 is null");
		} else {
			System.out.println("hand 2: ");
			System.out.println(hand2.getType());
			hand2.print(true, true);
		}


		System.out.println();
		System.out.println();

		if (hand1 != null && hand2 != null) {
			if (hand1.beats(hand2)) {
				System.out.println("hand 1 beats hand 2, " + hand1.getType() + " beats " + hand2.getType() + "!");
			} else {
				System.out.println("hand 1 cannot beat hand 2, " + hand1.getType() + " cannot beats " + hand2.getType() + "!");
			}
		}

							

	}

}
