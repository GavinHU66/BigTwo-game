import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class models a Big Two card game.
 *
 * @author Hu Qiyun
 */
public class BigTwo {
	 
	 private Deck deck; // a deck of cards.
	 private ArrayList<CardGamePlayer> playerList; // a list of players.
	 private ArrayList<Hand> handsOnTable; // a list of hands played on table.
	 private int currentIdx; // an integer specifying the index of the current player.
	 private BigTwoConsole bigTwoConsole; // a BigTwoConsole object for providing the user interface.
	 
	 /**
	  * Constructor of BigTwo.
	  */
	 public BigTwo() {   	   
	   // initialize the playerList and handsOnTable ArrayList before adding element to it.
	   this.playerList = new ArrayList<CardGamePlayer>();
	   this.handsOnTable = new ArrayList<Hand>();
	   this.bigTwoConsole = new BigTwoConsole(this);
	   for (int i=0; i<4; i++) {
		   this.playerList.add(new CardGamePlayer());
	   }	   
	 }

	 /**
	  * Retrieve the deck of cards being used.
	  *
	  * @return The deck of cards being used.
	  */
	 public Deck getDeck() {
		 return deck;
	 }

	 /**
	  * Retrieve the list of players.
	  *
	  * @return The list of players.
	  */
	 public ArrayList<CardGamePlayer> getPlayerList() {
		 return playerList;
	 }


	 /**
	  * Retrieve the list of hands played on the table.
	  *
	  * @return The list of hands played on the table.
	  */
	 public ArrayList<Hand> getHandsOnTable() {
		 return handsOnTable;
	 }

	 /**
	  * Retrieve the index of the current player.
	  *
	  * @return The index of the current player.
	  */
	 public int getCurrentIdx() {
		 return this.currentIdx;
	 }
   
	 /**
	  * This method distributes a shuffled deck of cards to the four players 
	  * and choose the player who is holding Diamond 3 should start the game.
	  * 
	  * @param deck A shuffled deck of cards which will be used in the game, we will distribute cards to the four players.
	  */
	 public void distributeCards(BigTwoDeck deck) {
		 for (int i=0; i<4; i++) {
			 for (int j=i*13; j<(i+1)*13; j++) {
				 playerList.get(i).getCardsInHand().addCard(deck.getCard(j));
				 if (deck.getCard(j).getSuit() == 0 && deck.getCard(j).getRank() == 2) {
					 currentIdx = i; // the player holding the Diamond 3 starts first
				 }
			 }
			 playerList.get(i).getCardsInHand().sort();
		 }
	 }
	
   
	 /**
	  * This method implements the logic of four players taking turns to give out a hand until the game finishes. 
	  * 
	  * @return winner The winner of the game.
	  */
	 public CardGamePlayer loopOfPlayers() {
		 // start the iteration of the player taking turns to give out hand	   
		 boolean gameShouldEnd = false; // to denote if the game should end
		 boolean isFirstMove = true; // in case the handsOnTable is empty
		 boolean ableToPass = false; // whether a player are eligible to pass his turn 
		 CardGamePlayer lastPlayedPlayer = null; // the last player who gives a hand
		 CardGamePlayer currentPlayer = null; // the current player who will make a decision
		 CardGamePlayer winner = null; // the winner of the game
		 CardList cardsPlayed = null; // the cards selected by the current player
		 Hand validHand = null; // the hand selected by the current player with validity
		 boolean isLegalMove = true;
		 boolean sayPass;
		 
		 while (!gameShouldEnd) {
			 
			 int cardIdx[];
			 this.bigTwoConsole.setActivePlayer(currentIdx);
			 currentPlayer = playerList.get(currentIdx);
			 this.bigTwoConsole.repaint();

			 
			 do {
				 if (!isLegalMove) {
					 System.out.println("Not a legal move!!!");
				 }
				 isLegalMove = false;
				 cardIdx = this.bigTwoConsole.getSelected();
				 cardsPlayed = currentPlayer.play(cardIdx);
				 validHand = composeHand(currentPlayer, cardsPlayed);
				 sayPass = (cardIdx==null)? true : false;
				 ableToPass = ( isFirstMove || currentPlayer == lastPlayedPlayer)? false : true;

				 if (isFirstMove && validHand != null ) {
					 if (validHand.containDiamondThree()) {
						 isLegalMove = true;
					 }
				 } else if (lastPlayedPlayer == currentPlayer && validHand != null) {
					 isLegalMove = true; 
				 } else if (!isFirstMove && ableToPass && sayPass) {
					 isLegalMove = true;
				 } else if (!isFirstMove && !sayPass && validHand != null) {
					 if (validHand.beats(handsOnTable.get(handsOnTable.size()-1))) {
						 isLegalMove = true;
					 }	 
				 } else {
					 isLegalMove = false;
				 }
			 } while (!isLegalMove);

			 
			 if (sayPass) {
				 System.out.print("{Pass} "); 
			 } else {
				 lastPlayedPlayer = currentPlayer;
				 String type = validHand.getType();
				 System.out.print("{" + type + "} ");
				 validHand.print(true, false);
				 System.out.println();

				 handsOnTable.add(validHand);
				 lastPlayedPlayer = currentPlayer;
			   
				 // when removing a card form the cardList, the size also shrinks!!! Then when should care about it!!!
				 Arrays.sort(cardIdx); // now cardIdx is in ascending order
				 for (int i=cardIdx.length-1; i>=0; i--) { // move the later card can get rid of size problem
					 currentPlayer.getCardsInHand().removeCard(cardIdx[i]);
				 }
			 }			 
			 
			 currentIdx++;
			 currentIdx%=4;
			 isFirstMove = false;
			 
			 
			 for (int i=0; i<playerList.size(); i++) {
				 if (playerList.get(i).getCardsInHand().size() == 0){
					 winner = playerList.get(i);
					 gameShouldEnd = true;
					 break;
				 }
			 }
			 System.out.println();
		 }
		 return winner;
	 }
   
	 /**
	  * This method is called when the Big Two Card game ends, prints out the summary of each player.
	  * 
	  * @param winner The winner of the game.
	  */
	 public void endOfGame(CardGamePlayer winner) {
		 for (int i=0; i<playerList.size(); i++) {
			 if (playerList.get(i) == winner) {
				 System.out.println(playerList.get(i).getName() + " wins the game.");
			 } else {
				 System.out.println(playerList.get(i).getName() + " has " + playerList.get(i).getNumOfCards() + " cards in hand.");
			 }
		 } 
	 }

	 /**
	  * This method starts the game with a (shuffled) deck of cards supplied as the argument,
	  * it implements the Big Two game logics.
	  * 
	  * @param deck A shuffled deck of cards which will be used in the game, we will distribute cards to the four players.
	  */
	 public void start(BigTwoDeck deck) {
	   
		 // due to OO-approach and OO-concept, I create three methods by my self to support the start() method.
	   
		 System.out.println("Big two card game starts");
		 System.out.println();	   
	   
		 // allocate cards to the 4 players randomly.
		 distributeCards(deck);
	   
		 // four players start to take turns to give out a hand until the end of the game.
		 CardGamePlayer winner = loopOfPlayers();

		 // the Big Two Card game ends, prints out the summary of each player.
		 endOfGame(winner);
	 }


	 /**
	  * This method starts a Big Two card game.
	  *
	  * @param args not being used.
	  */
	 public static void main(String[] args) {

		 BigTwoDeck bigTwoDeck = new BigTwoDeck();

		 bigTwoDeck.initialize();
		 bigTwoDeck.shuffle();
	   
		 BigTwo bigTwo = new BigTwo();
		 bigTwo.start(bigTwoDeck);
	 }

	 /**
	  * This method returns a valid hand from the specified list of cards of the player or null.
	  *
	  * @param player The CardGamePlayer being inspected.
	  * @param cards The cards selected by the player CardGamePlayer.
	  * @return A valid Hand from the specified list of cards of the player, or null if there is no valid Hand.
	  */
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
}
