import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class models a Big Two card game.
 *
 * @author Hu Qiyun
 */
public class BigTwo implements CardGame {
	 
	 private Deck deck; // a deck of cards.
	 private ArrayList<CardGamePlayer> playerList; // a list of players.
	 private ArrayList<Hand> handsOnTable; // a list of hands played on table.
	 private int currentIdx; // an integer specifying the index of the current player.
	 private BigTwoTable table; // a BigTwoConsole object for providing the user interface.
	 private boolean firstMove; // whether the game is in the first move
	 private int lastPlayedPlayerId = -1; // the last player who have out a hand instead of a pass
	 private boolean isEnd = false; // whether a game is ended
	 
	 /**
	  * Constructor of BigTwo, it creates 4 players and add them to the list of players,
	  * and create a Big Two table which builds the GUI for the game and handles user actions.
	  */
	 public BigTwo() {	 
		 this.playerList = new ArrayList<CardGamePlayer>();
		 this.handsOnTable = new ArrayList<Hand>();
		 this.firstMove = true;		 
		 // create 4 players
		 for (int i=0; i<4; i++) {
			 this.playerList.add(new CardGamePlayer());
		 }
		 table = new BigTwoTable(this);   
	 }
	 
	 /**
	  * Returns the number of players in this card game.
	  * 
	  * @return the number of players in this card game.
	  * @see CardGame#getNumOfPlayers()
	  */
	 public int getNumOfPlayers() {
		 return this.playerList.size();
	 }

	/**
	 * Returns the deck of cards being used in this card game.
	 * 
	 * @return the deck of cards being used in this card game
	 * @see CardGame#getDeck()
	 */
	public Deck getDeck() {
		return this.deck;
	}

	/**
	 * Returns the list of players in this card game.
	 * 
	 * @return the list of players in this card game.
	 * @see CardGame#getPlayerList()
	 */
	public ArrayList<CardGamePlayer> getPlayerList() {
		return this.playerList;
	}


	/**
	 * Returns the list of hands played on the table.
	 * 
	 * @return the list of hands played on the table.
	 * @see CardGame#getHandsOnTable()
	 */
	public ArrayList<Hand> getHandsOnTable() {
		return handsOnTable;
	}

	/**
	 * Returns the index of the current player.
	 * 
	 * @return the index of the current player
	 * @see CardGame#getCurrentIdx()
	 */
	public int getCurrentIdx() {
		return this.currentIdx;
	}
	
	/**
	 * Returns the true if the game should end.
	 * 
	 * @return Returns the true if the game should end, false if not.
	 */
	public boolean getIsEnd() {
		return this.isEnd;
	}
	
   
	/**
	 * This method distributes a shuffled deck of cards to the four players 
	 * and choose the player who is holding Diamond 3 should start the game.
	 * 
	 * @param deck A shuffled deck of cards which will be used in the game, we will distribute cards to the four players.
	 */
	public void distributeCards(Deck deck) {
		for (int i=0; i<4; i++) {
			playerList.get(i).removeAllCards();
		}
		handsOnTable = new ArrayList<Hand>();
		for (int i=0; i<4; i++) {
			for (int j=i*13; j<(i+1)*13; j++) {
				playerList.get(i).getCardsInHand().addCard(deck.getCard(j));
		
				if (deck.getCard(j).getRank() == 2 && deck.getCard(j).getSuit() == 0) {
					currentIdx = i; // the player holding the Diamond 3 starts first
					table.setActivePlayer(i);
					table.printMsg("Player " + currentIdx + " 's turn:\n");
				}
			}
			playerList.get(i).getCardsInHand().sort();
		}
		deck.removeAllCards();
	}
	
	/**
	 * This method starts/restarts the game with a given shuffled deck of cards,
	 * it removes all cards from the players and table, distribute the cards to
	 * the players, also identify the player who should make the first move.
	 * 
	 * @param deck The deck of (shuffled) cards to be used in this game.
	 * @see CardGame#start()
	 */
	public void start(Deck deck) {
		isEnd = false;
		firstMove = true;
		distributeCards(deck);
		table.repaint();
	}
	 
	/**
	 * This method makes a move by a player with the specified playerID using the cards 
	 * specified by the list of indices, this method should be called from the BigTwoTable 
	 * when the active player presses either the “Play” or “Pass” button, just call the checkMove() 
	 * method from the CardGame interface with the playerID and cardIdx as the arguments.
	 * 
	 * @param playerID the playerID of the player who makes the move
	 * @param cardIdx the list of the indices of the cards selected by the player
	 * @see CardGame#makeMove()
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		checkMove(playerID, cardIdx);
	}

	/**
	 * Checks the move made by the player, if the move is legal, we finish this move and print out
	 * the information to the message area, if not legal, we let the player move again until it is legal.
	 * 
	 * @param playerID the playerID of the player who makes the move
	 * @param cardIdx the list of the indices of the cards selected by the player
	 * CardGame#checkMove()
	 */
	public void checkMove(int playerID, int[] cardIdx) {
		
		String info = "";

		CardGamePlayer currentPlayer = this.getPlayerList().get(playerID);
		boolean ableToPass = false; // whether a player are eligible to pass his turn 	
		
		CardList cardsPlayed = null; // the cards selected by the current player
		Hand validHand = null; // the hand selected by the current player with validity
		boolean isLegalMove = true;
		boolean sayPass;	 

		isLegalMove = false;
		cardsPlayed = currentPlayer.play(cardIdx);
		validHand = composeHand(currentPlayer, cardsPlayed);
		sayPass = (cardIdx==null)? true : false;
		ableToPass = ( firstMove || playerID == lastPlayedPlayerId)? false : true;
		
		if (sayPass) {
			info += "{Pass}";
		} else {
			if (validHand != null) {
				info += "{" + validHand.getType() + "} ";
			}
			for (int i = 0; i < cardsPlayed.size(); i++) {
				info += "[" + cardsPlayed.getCard(i) + "] ";
			}

		}
		

		 if (firstMove && validHand != null ) {
			 if (validHand.containDiamondThree()) {
				 isLegalMove = true;
			 }
		 } else if (lastPlayedPlayerId == playerID && validHand != null) {
			 isLegalMove = true; 
		 } else if (!firstMove && ableToPass && sayPass) {
			 isLegalMove = true;
		 } else if (!firstMove && !sayPass && validHand != null) {
			 if (validHand.beats(handsOnTable.get(handsOnTable.size()-1))) {
				 isLegalMove = true;
			 }	 
		 } else {
			 isLegalMove = false;
		 }
			

		 if (!isLegalMove) {
			 info += " <== Not a legal move!!!\n";
			 info += "Player " +  playerID + " 's turn:\n";
			 table.printMsg(info);
			 table.resetSelected();
			 table.repaint();
			 return;
		 } else {
			 if (!sayPass) { // if not say pass, then add it to handsOnTable and remove the cards from the player 
				 handsOnTable.add(validHand);
				 lastPlayedPlayerId = playerID;   
				 // when removing a card form the cardList, the size also shrinks!!! Then when should care about it!!!
				Arrays.sort(cardIdx); // now cardIdx is in ascending order
				for (int i=cardIdx.length-1; i>=0; i--) { // move the later card can get rid of size problem
					currentPlayer.getCardsInHand().removeCard(cardIdx[i]);
				}
			}		 
			currentIdx++;
			currentIdx%=4;
			info += "\nPlayer " +  currentIdx + " 's turn:\n";
			table.printMsg(info);
			firstMove = false;
			table.setActivePlayer(currentIdx);
			table.resetSelected();
			table.repaint();
			
			if (endOfGame()) {
				String summary = "Game ends!\n";
				int winner = -1;
				for (int i = 0; i < 4; i++) {
					if (playerList.get(i).getNumOfCards() == 0) {
						winner = i;
						break;
					}
				}
				for (int i = 0; i < 4; i++) {
					summary += playerList.get(i).getName();
					if (i == winner) {
						summary += " wins the game!\n";
					} else {
						summary += (" has " + playerList.get(i).getNumOfCards() + " cards in hand.\n");
					}
				}
				table.printMsg(summary);
				table.repaint();
				table.disable();
			} 		 
		 }
	}

	/**
	 * Checks for end of game.
	 * 
	 * @return true if the game ends; false otherwise
	 * @see CardGame#endOfGame()
	 */
	public boolean endOfGame() {
		for (int i=0; i<playerList.size(); i++) {
			if (playerList.get(i).getCardsInHand().size() == 0) {
				isEnd = true;
				return true;
			}		
		}
		return false;
	}


	/**
	 * This main method starts a Big Two card game, it creates 
	 * a Big Two card game and starts the game with the deck of cards.
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
