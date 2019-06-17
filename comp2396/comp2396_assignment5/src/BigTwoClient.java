
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

/**
 * This class models a Big Two card client.
 *
 * @author Hu Qiyun
 */
public class BigTwoClient implements CardGame, NetworkGame {
	
	
	private int numOfPlayers; // an integer specifying the number of players.
	private Deck deck; // a deck of cards.
	private ArrayList<CardGamePlayer> playerList; // a list of players.
	private ArrayList<Hand> handsOnTable; // a list of hands played on table.
	private int playerID; // an integer specifying the playerID (i.e., index) of the local player 
	private String playerName; // a string specifying the name of the local player.
	private String serverIP; // a string specifying the IP address of the game server.
	private int serverPort; // an integer specifying the TCP port of the game server.
	private Socket sock; // a socket connection to the game server.
	private ObjectOutputStream oos; // an ObjectOutputStream for sending messages to the server.
	private ObjectInputStream ois; // an ObjectInputStream for receiving messages from the server.
	private int currentIdx; // an integer specifying the index of the current player.
	private BigTwoTable table; // a BigTwoConsole object for providing the user interface.
	private boolean firstMove; // whether the game is in the first move
	private int lastPlayedPlayerId = -1; // the last player who have out a hand instead of a pass
	private boolean isEnd = false; // whether a game is ended
	
	private static final boolean DEBUG = false;

	 
	 /**
	  * Constructor of BigTwo, it creates 4 players and add them to the list of players,
	  * and create a Big Two table which builds the GUI for the game and handles user actions.
	  */
	 public BigTwoClient() {
		 
		 // create 4 players and add them to the list of the players
		 this.playerList = new ArrayList<CardGamePlayer>();
		 for (int i=0; i<4; i++) {
			 this.playerList.add(new CardGamePlayer(""));
		 }
		 
		 // create a BigTwoTable which builds the GUI for the game and handles user actions
		 table = new BigTwoTable(this);   
		 
		 // make a connection to the game server by calling the makeConnection() method from the NetWorkCame interface.
		 makeConnection();
		 
		 this.handsOnTable = new ArrayList<Hand>();
		 this.firstMove = true;	
		 table.disable();
		
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
		return this.handsOnTable;
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
				}
			}
			playerList.get(i).getCardsInHand().sort();
		}
		
		table.setActivePlayer(playerID);
		deck.removeAllCards();
//		if (table.getActivePlayer() == playerID) {
//			table.printMsg("Your turn:\n");
//		} else {
//			table.printMsg("Player " + playerList.get(currentIdx).getName() + " 's turn:\n");
//		}
		table.printMsg("Player " + playerList.get(currentIdx).getName() + " 's turn:\n");
		
	}
	
	/**
	 * This method starts/restarts the game with a given shuffled deck of cards,
	 * it removes all cards from the players and table, distribute the cards to
	 * the players, also identify the player who should make the first move.
	 * 
	 * @param deck The deck of (shuffled) cards to be used in this game.
	 * @see CardGame#start(Deck deck)
	 */
	public void start(Deck deck) {
		
		// remove all the cards from the players as well as from the table;
		// distribute the cards to the players; 
		// identify the player who holds the 3 of Diamonds; 
		// set the currentIdx of the BigTwoClient instance to the playerID 	
		// (i.e., index) of the player who holds the 3 of Diamonds; 
		distributeCards(deck);
		
		// set the activePlayer of the BigTwoTable instance to the playerID (i.e., index) 
		// of the local player (i.e., only shows the cards of the local player and the local 
		// player can only select cards from his/her own hand).
		table.setActivePlayer(playerID);
		
		isEnd = false;
		firstMove = true;
		table.repaint();
	}
	 
	/**
	 * This method makes a move by a player with the specified playerID using the cards 
	 * specified by the list of indices, this method should be called from the BigTwoTable 
	 * when the active player presses either the “Play” or “Pass” button.
	 * 
	 * @param playerID the playerID of the player who makes the move
	 * @param cardIdx the list of the indices of the cards selected by the player
	 * @see CardGame#makeMove(int playerID, int[] cardIdx)
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		sendMessage(new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx));
	}

	/**
	 * Checks the move made by the player, This method should be called from the parseMessage() 
	 * method when a message of the type MOVE is received from the game server, if the move is legal, 
	 * we finish this move and print out the information to the message area, if not legal, 
	 * we let the player move again until it is legal.
	 * 
	 * @param playerID the playerID of the player who makes the move
	 * @param cardIdx the list of the indices of the cards selected by the player
	 * CardGame#checkMove(int playerID, int[] cardIdx)
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
		
		info += "\n";
		
		if (firstMove) {
			table.enable();
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
			 if (table.getActivePlayer() == playerID) {
				 info += " <== Not a legal move!!!\n";
				 info += "Your turn:\n";
				 table.printMsg(info);
				 table.resetSelected();
				 table.repaint();
			 }
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
			
			if (table.getActivePlayer() == playerID) {
				info += "Your turn:\n";
			} else {
				info += "Player " +  playerList.get(currentIdx).getName() + "'s turn:\n";
			}
			
			table.printMsg(info);
			firstMove = false;
			if (table.getActivePlayer() == playerID) {
				table.resetSelected();
			}
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
				
				JOptionPane.showMessageDialog(null, summary);
				table.disable();
				sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
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
	 * This method creates an instance of BigTwoClient.
	 *
	 * @param args not being used.
	 */
	public static void main(String[] args) {
		BigTwoClient game = new BigTwoClient();
	}

	/**
	 * This method returns a valid hand from the specified list of cards of the player or null.
	 *
	 * @param player The CardGamePlayer being inspected.
	 * @param cards The cards selected by the player CardGamePlayer.
	 * @return A valid Hand from the specified list of cards of the player, or null if there is no valid Hand.
	 */
	public Hand composeHand(CardGamePlayer player, CardList cards) {
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

	
	/**
	 * Returns the playerID (index) of the local player.
	 * 
	 * @return the playerID (index) of the local player
	 * @see NetworkGame#getPlayerID()
	 */
	@Override
	public int getPlayerID() {
		return this.playerID;
	}

	/**
	 * Sets the playerID (index) of the local player.
	 * 
	 * @param playerID the playerID (index) of the local player.
	 * @see NetworkGame#setPlayerID(int playerID)
	 */
	@Override
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
		
	}

	
	/**
	 * Returns the name of the local player.
	 * 
	 * @return the name of the local player
	 * @see NetworkGame#getPlayerName()
	 */
	@Override
	public String getPlayerName() {	
		return this.playerName;
	}

	/**
	 * Sets the name of the local player.
	 * 
	 * @param playerName the name of the local player
	 * @see NetworkGame#setPlayerName(String playerName)
	 */
	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * Returns the IP address of the server.
	 * 
	 * @return the IP address of the server
	 * @see NetworkGame#getServerIP()
	 */
	@Override
	public String getServerIP() {
		return this.serverIP;
	}

	/**
	 * Sets the IP address of the server.
	 * 
	 * @param serverIP the IP address of the server
	 * @see NetworkGame#setServerIP(String serverIP)
	 */
	@Override
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;		
	}

	/**
	 * Returns the TCP port of the server.
	 * 
	 * @return the TCP port of the server
	 * @see NetworkGame#getServerPort()
	 */
	@Override
	public int getServerPort() {
		return this.serverPort;
	}

	/**
	 * Sets the TCP port of the server
	 * 
	 * @param serverPort the TCP port of the server
	 * @see NetworkGame#setServerPort(int serverPort)
	 */
	@Override
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * Makes a network connection to the server.
	 *
	 * @see NetworkGame#makeConnection()
	 */
	@Override
	public synchronized void makeConnection() {
		
		playerName = JOptionPane.showInputDialog("Please enter your name: ", "Default_name");

		if (playerName != null) {
			try {
				serverIP = JOptionPane.showInputDialog("Please enter your IP address: ", "127.0.0.1");
				serverPort = Integer.parseInt(JOptionPane.showInputDialog("Please enter your Port number: ", "2396"));
				sock = new Socket(serverIP, serverPort);
				
				oos = new ObjectOutputStream(sock.getOutputStream());
				
				// create a thread for receiving messages from the server
				Thread receivingThread = new Thread(new ServerHandler());
				receivingThread.start();
				
				if(DEBUG) {
					System.out.println("playerName = " + playerName);
				}
				
				// send a message of the type JOIN to the game server, with playerID -1 and data being a reference to a string 
				// representing the name of the local player
				sendMessage(new CardGameMessage(CardGameMessage.JOIN, -1, playerName));
				
				// send a message of the type READY to the game server, with playerID and data being -1 and null.
				sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			table.repaint();
		}	
	
	}

	/**
	 * Parses the specified message received from the server.
	 * 
	 * @param message the specified message received from the server
	 * @see NetworkGame#parseMessage(GameMessage message)
	 */
	@Override
	public synchronized void parseMessage(GameMessage message) {
		
		int msgType = message.getType();
		int msgPlayerID = message.getPlayerID();
		Object msgData = message.getData();
		
		switch(msgType) {
		
		  case CardGameMessage.PLAYER_LIST:
			  if (DEBUG) System.out.println("Client receive CardGameMessage.PLAYER_LIST");
			  playerID = msgPlayerID; // set the local player id
//			  table.setActivePlayer(playerID);
			  for (int i=0; i<playerList.size(); i++) {
				  if ( ((String[])msgData)[i]!=null ) {
					  playerList.get(i).setName( ((String[])message.getData())[i]); 
				  } 
			  }
			  break;
			  
		  case CardGameMessage.JOIN:
			  if (DEBUG) System.out.println("Client receive CardGameMessage.JOIN");
			  // add a new player to the player list by updating his/her name
			  playerList.get(msgPlayerID).setName(((String)msgData));
			  table.printMsg("Player " + playerList.get(msgPlayerID).getName() + " joined the game!\n");
			  break;
			  
		  case CardGameMessage.FULL:
			  if (DEBUG) System.out.println("Client receive CardGameMessage.FULL");
			  playerID = -1;
			  table.printMsg("The game is now full, Please try later!\n");
			  break;
			  
		  case CardGameMessage.QUIT:
			  // remove a player from the game by setting his/her name to an empty string
			  if (DEBUG) System.out.println("Client receive CardGameMessage.QUIT");
			  table.printMsg("Player " + msgPlayerID + " " + playerList.get(msgPlayerID).getName() + " left the game.\n");
			  playerList.get(msgPlayerID).setName("");
			  if (!endOfGame()) {
				  table.disable();
				  sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
				  for (int i = 0; i < 4; i++) {
					  playerList.get(i).removeAllCards();
				  }
			  }
			  table.disable();
			  break;
			  
		  case CardGameMessage.READY:
			  if (DEBUG) System.out.println("Client receive CardGameMessage.READY");
			  table.printMsg("Player " + playerList.get(msgPlayerID).getName() + " is ready!\n");
			  break;
			  
		  case CardGameMessage.START:
			  if (DEBUG) System.out.println("Client receive CardGameMessage.START");
			  table.printMsg("Game is started!\n\n");
			  start((Deck)msgData);
			  table.enable();
			  break;
			  
		  case CardGameMessage.MOVE:
			  if (DEBUG) System.out.println("Client receive CardGameMessage.MOVE");
			  checkMove(message.getPlayerID(), (int[])message.getData());
			  break;
			  
		  case CardGameMessage.MSG:
			  if (DEBUG) System.out.println("Client receive CardGameMessage.MSG");
			  table.printChatMsg((String)msgData + "\n");
			  break;
			  
		  default: // invalid message
			  if (DEBUG) System.out.println("Client receive invalid message");
			  table.printMsg("Wrong message type: " + message.getType());
			  break;
		}
		table.repaint();
		
	}

	/**
	 * Sends the specified message to the server.
	 * 
	 * @param message the specified message to be sent the server
	 * @see NetworkGame#sendMessage(GameMessage message)
	 */
	@Override
	public synchronized void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * This class reads incoming messages from the server
	 * 
	 * @author Hu Qiyun
	 *
	 */
	public class ServerHandler implements Runnable {
		
		/**
		 * This method which will be called when the thread starts. and read the message from the server.
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			try {
				ois = new ObjectInputStream(sock.getInputStream());
				CardGameMessage message = null;
				while ((message = (CardGameMessage) ois.readObject()) != null) {
					parseMessage(message);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	 
}
