import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The BigTwoTable class implements the CardGameTable interface,
 * it is used to build a GUI for the Big Two card game and handle all user actions.
 * 
 * @author Hu Qiyun
 *
 */
public class BigTwoTable implements CardGameTable{
	
	public static final boolean DEBUG = false;
	private CardGame game; // a card game associates with this table.
	private boolean[] selected; // a boolean array indicating which cards are being selected.
	private int activePlayer; // an integer specifying the index of the active player.
	private int localPlayer; // an integer specifying the index of the player who is local and is using this gui.
	private JFrame frame; // the main window of the application.
	private JPanel bigTwoPanel; // a panel for showing the cards of each player and the cards played on the table.
	private JButton playButton; // a “Play” button for the active player to play the selected cards.
	private JButton passButton; // a “Pass” button for the active player to pass his/her turn to the next player.
	private JTextArea msgArea; // a text area for showing the current game status as well as end of game messages.
	private JTextField chatInput; // A text field for chat user input
	private JTextArea chatContent; // A text area for displaying chat content
	private JLabel msgLabel;
	private Image[][] cardImages; // a 2D array storing the images for the faces of the cards.
	private Image cardBackImage; // an image for the backs of the cards.
	private Image[] avatars; // array storing the images for the avatars.
	private Image background; // An image for the background
	private JMenuBar menuBar;// A menu bar for quit and restart
	private JMenu menuGame; // A menu to contain two items
	private JMenuItem quitMenu; // The quit menu item
	private JMenuItem connectMenuItem; // The connect menu item
	private JMenu menuMessage; // A menu to contain two items
	private JMenuItem clearInfoMenuItem; // The quit menu item
	private JMenuItem clearChatMenuItem; // The connect menu item
	private Image star; // A star image for active player 
	private String prompt; // a prompt for the active player indicates his type of selected hand.

	
	/**
	 * Constructor of BigTwoTable, the big two table would have a message area 
	 * showing the information of each turn, a big two panel containing four players
	 * and their cards and the last hands on table, also containing a button panel 
	 * holding a play button and a pass button.
	 * 
	 * @param gamea A reference to a card game associates with this table.
	 */
	BigTwoTable(CardGame game){

		this.game = game;
		selected = new boolean[13];
		prompt = "Your Selection: null";

		cardImages = new Image[13][4];
		for (int i=0; i<13; i++) {
			for (int j=0; j<4; j++) {
				String imagePath = "cardImages/" + String.valueOf(i) + String.valueOf(j) + ".jpg";
				cardImages[i][j] = new ImageIcon(imagePath).getImage();
			}
		}
		
		cardBackImage = new ImageIcon("cardImages/back_portrait.gif").getImage();
		
		avatars = new Image[4];
		avatars[0] = new ImageIcon("Avatars/batman.png").getImage();
		avatars[1] = new ImageIcon("Avatars/flash.png").getImage();
		avatars[2] = new ImageIcon("Avatars/green_lantern.png").getImage();
		avatars[3] = new ImageIcon("Avatars/superman.png").getImage();	
		
		star = new ImageIcon("star.jpg").getImage();
		background = new ImageIcon("background.jpg").getImage();
		
		// create a frame to contain all the widget
		frame = new JFrame("Big Two");
		frame.setSize(1200, 800);
		frame.setMinimumSize(new Dimension(1150, 700));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		
		// big two panel on the left
		bigTwoPanel = new BigTwoPanel();
		bigTwoPanel.setLayout(new BorderLayout());
		bigTwoPanel.setBackground(new Color(34, 139, 34));
		bigTwoPanel.addMouseListener(new BigTwoPanel());
		frame.add(bigTwoPanel, BorderLayout.CENTER);		
		
		// two buttons: play and pass
		playButton = new JButton("Play");
		playButton.addActionListener(new PlayButtonListener());
		passButton = new JButton("Pass");
		passButton.addActionListener(new PassButtonListener());
		JPanel buttonPanel = new JPanel();
		
		// chatting input field
		chatInput = new JTextField(25);
		chatInput.setFont(new Font("Verdana", 0, 13));
		chatInput.addActionListener(new ChatInputListener());
		msgLabel = new JLabel("Messages: ");
		msgLabel.setFont(new Font("Verdana", 1, 12));
		msgLabel.setHorizontalAlignment(0);
		buttonPanel.setLayout(new GridBagLayout());
		
	    GridBagConstraints c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 1; // default value
	    c.gridheight = 1; // default value
	    c.weightx = 0.0; // default value
	    c.weighty = 0.0; // default value
	    c.anchor = GridBagConstraints.CENTER; // default value
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.insets = new Insets(0, 0, 0, 0); // default value
	    c.ipadx = 0; // default value
	    c.ipady = 0; // default value
	    
	    c.insets = new Insets(0, 250, 0, 0);
		buttonPanel.add(playButton, c);
		c.gridx = 1;
		c.insets = new Insets(0, 0, 0, 300);
		buttonPanel.add(passButton, c);
		c.gridx = 2;
		c.insets = new Insets(0, 0, 0, 0);
		buttonPanel.add(msgLabel, c);
		c.gridx = 3;
		buttonPanel.add(chatInput, c);
		
		
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		// a message display area on the right with a scroller
		msgArea = new JTextArea(10, 30);
		msgArea.setFont(new Font ("Arial", 0, 14));
		msgArea.setEditable(false);
		msgArea.setLineWrap(true);
		JScrollPane scroller = new JScrollPane(msgArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		// a chat display area on the right with a scroller
		chatContent = new JTextArea(10, 30);
		chatContent.setFont(new Font("Arial", 0, 14));
		chatContent.setEditable(false);
		chatContent.append("This is the chat area!\n\n");
		JScrollPane chatScroller = new JScrollPane(chatContent);
		chatContent.setLineWrap(true);
		chatScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		// add the message display and the chat display area to the msgDisplayPanel(locate east)
		JPanel msgDisplayPanel = new JPanel();
		msgDisplayPanel.setLayout(new BoxLayout(msgDisplayPanel, BoxLayout.Y_AXIS));
		msgDisplayPanel.add(scroller);
		msgDisplayPanel.add(chatScroller);
		frame.add(msgDisplayPanel, BorderLayout.EAST);
		
		
		// a menu bar on the top with Game MI and Message MI.
			// Game MI
		menuBar = new JMenuBar();
		menuGame = new JMenu("Game");
		menuGame.setFont(new Font("Verdana", 1, 13));
		quitMenu = new JMenuItem("Quit");
		quitMenu.setFont(new Font("Verdana", 1, 13));
		quitMenu.addActionListener(new QuitMenuItemListener());	  
		connectMenuItem = new JMenuItem("Connect");
		connectMenuItem.setFont(new Font("Verdana", 1, 13));
		connectMenuItem.addActionListener(new ConnectMenuItemListener());
		menuGame.add(quitMenu);
		menuGame.add(connectMenuItem);
			// Message MI
		menuMessage = new JMenu("Message");
		menuMessage.setFont(new Font("Verdana", 1, 13));
		clearInfoMenuItem = new JMenuItem("Clear Info Board");
		clearInfoMenuItem.setFont(new Font("Verdana", 1, 13));
		clearInfoMenuItem.addActionListener(new ClearInfoBoardListener());	  
		clearChatMenuItem = new JMenuItem("Clear Chat Board");
		clearChatMenuItem.setFont(new Font("Verdana", 1, 13));
		clearChatMenuItem.addActionListener(new ClearChatBoardListener());
		menuMessage.add(clearInfoMenuItem);
		menuMessage.add(clearChatMenuItem);
		
		menuBar.add(menuGame);
		menuBar.add(menuMessage);
		frame.add(menuBar, BorderLayout.NORTH);
				
		frame.setVisible(true);		
	}

	/**
	 * Sets the index of the local player.
	 * 
	 * @param localPlayer An integer value representing the index of the local player, returns -1 if the argument is invalid.       
	 */
	public void setLocalPlayer(int localPlayer) {
		if (localPlayer<0 || localPlayer>game.getNumOfPlayers()-1) {
			this.localPlayer = -1;
		} else {
			this.localPlayer = localPlayer;
		}
	}
	
	/**
	 * Get the index of the local player.
	 * 
	 * @return Get the index of the local player
	 */
	public int getLocalPlayer() {
		return this.localPlayer;
	}
	
	
	/**
	 * Sets the index of the active player (i.e., the current player).
	 * 
	 * @param activePlayer An integer value representing the index of the active player, returns -1 if the argument is invalid.       
	 * @see CardGameTable#setActivePlayer(int activePlayer)
	 */
	public void setActivePlayer(int activePlayer) {
		if (activePlayer<0 || activePlayer>game.getNumOfPlayers()-1) {
			this.activePlayer = -1;
		} else {
			this.activePlayer = activePlayer;
		}
	}
	
	/**
	 * Get the index of the active player (i.e., the current player).
	 * 
	 * @return Get the index of the active player (i.e., the current player).
	 *  
	 */
	public int getActivePlayer() {
		return this.activePlayer;
	}
	
	

	/**
	 * Returns an array of indices of the cards selected.
	 * 
	 * @return An array of indices of the cards selected
	 * @see CardGameTable#getSelected()
	 */
	public int[] getSelected() {
		
		int numOfCardsSelected = 0;
		int index = 0;
		for (int i=0; i<selected.length; i++) 
			if (selected[i]) 
				numOfCardsSelected++;

		if (numOfCardsSelected==0) {
			return null;
		} else {
			int[] selectedIndices = new int[numOfCardsSelected];
			for (int i=0; i<selected.length; i++) {
				if (selected[i]) {
					selectedIndices[index] = i;
					index++;
				}
			}
			return selectedIndices;
		}
	}

	/**
	 * Resets the list of selected cards to an empty list.
	 * 
	 * @see CardGameTable#resetSelected()
	 */
	public void resetSelected() {
		for (int i=0; i<selected.length; i++) 
			selected[i] = false;
	}

	/**
	 * Repaints the GUI.
	 * 
	 * @see CardGameTable#repaint()
	 */
	public void repaint() {
		frame.repaint();
	}

	/**
	 * Prints the specified string to the message area of the card game table.
	 * 
	 * @param msg a String object to be printed to the message area of the card game table
	 *
	 * @see CardGameTable#printMsg(String msg)
	 */
	public void printMsg(String msg) {
		msgArea.append(msg);
	}
	
	
	/**
	 * Prints the specified string to the chat area of the card game table.
	 * 
	 * @param msg a String object to be printed to the chat area of the card game table
	 *
	 */
	public void printChatMsg(String msg) {
		chatContent.append(msg);
	}

	/**
	 * Clears the message area of the card game table.
	 * 
	 * @see CardGameTable#clearMsgArea()
	 */
	public void clearMsgArea() {
		msgArea.setText("");
	}

	/**
	 * Clears the chat area of the card game table.
	 * 
	 */
	public void clearChatArea() {
		chatContent.setText("");
	}
	
	/**
	 * Resets the GUI.
	 * 
	 * @see CardGameTable#reset()
	 */
	public void reset() {
		resetSelected();
		clearMsgArea();
		enable();
	}

	/**
	 * Enables user interactions.
	 * 
	 * @see CardGameTable#enable()
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
	}

	/**
	 * Disables user interactions.
	 * 
	 * @see CardGameTable#disable()
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);
		activePlayer = -1;
	}

	
	/**
	 * This class makes a draw panel, and overrides the paintComponent method. 
	 * And it also handles the mouse click event of BigTwoPanel.
	 * 
	 * @author Hu Qiyun
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener {
		
		
		/**
		 * Constructor of BigTwoPanel.
		 */
		public void BigTwoPanel() {
			
		}
		/**
		 * This method handles everything that should be painted on the panel, this method will be
		 * called automatically when repaint method is called to repaint everything every time 
		 * there is repaint.
		 * 
		 * @param g A Graphics object for drawing the panel.
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */		
		@Override
		public void paintComponent(Graphics g) {
	        super.paintComponent(g);
			g.setColor(Color.WHITE);			
			g.drawImage(background, 0, 0, bigTwoPanel.getWidth(), bigTwoPanel.getHeight(), this);
			
			for (int i=0; i<4; i++) {
				
				// cutting line
				g.drawLine(0, getHeight()*(i+1)/5, 2000, getHeight()*(i+1)/5);
				
				// player name and image
				if (game.getPlayerList().get(i).getName() != "") {
					String name;
					if (i == localPlayer) {
						name = "You";
					} else {
						name = game.getPlayerList().get(i).getName();
					}
					// player name
					g.drawString(name, 20, getHeight()*i/5+20);	
					// avatars image 
					g.drawImage(avatars[i], 10, getHeight()*i/5 + 30, getHeight()/7, getHeight()/7, this);
					
				}
			}
			
			// if game starts, draw cards, star, and prompt
			if (
				game.getPlayerList().get(0).getName() != "" && 
				game.getPlayerList().get(1).getName() != "" &&
				game.getPlayerList().get(2).getName() != "" &&
				game.getPlayerList().get(3).getName() != "" 
			) {
				
				if (localPlayer != activePlayer) {
					playButton.setEnabled(false);
					passButton.setEnabled(false);
				} else {
					playButton.setEnabled(true);
					passButton.setEnabled(true);
				}
				
				for (int i=0; i<4; i++) {
					
					// draw cards
					if (((BigTwoClient)game).endOfGame()) { // if game ends 
						for (int j=0; j<game.getPlayerList().get(i).getNumOfCards(); j++) {
							int rank = game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank();
							int suit = game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit();
							g.drawImage(cardImages[rank][suit], (j+5) * getWidth() / 25, getHeight() * i / 5 + 30, getHeight() / 9, getHeight() / 7, this);						
						}
					} else { // game is still in progress
						for (int j=0; j<game.getPlayerList().get(i).getNumOfCards(); j++) {
							if (i == localPlayer) { // for local player
								int rank = game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank();
								int suit = game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit();
								g.drawString(prompt, 18 * getWidth() / 25 + getHeight() / 9, getHeight()*i/5+20);
								if (selected[j]) {
									g.drawImage(cardImages[rank][suit], (j+5) * getWidth() / 25, getHeight() * i / 5 + 10, getHeight() / 9, getHeight() / 7, this);
								} else {
									g.drawImage(cardImages[rank][suit], (j+5) * getWidth() / 25, getHeight() * i / 5 + 30, getHeight() / 9, getHeight() / 7, this);
								}
							} else { // for others
								g.drawImage(cardBackImage, (j+5) * getWidth() / 25, getHeight() * i / 5 + 30, getHeight() / 9, getHeight() / 7, this);
							}
						}	
					}
					
					// draw star
					if (i == activePlayer) {
						g.drawImage(star, 100, getHeight() * i / 5 + 5, getHeight() / 27, getHeight() / 27, this);
					}
				}
				
				// draw last hand on table
				String lastHandOwner = (game.getHandsOnTable().size() == 0)? "No hands on table for now" : "Played by " + game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer().getName();
				g.drawString(lastHandOwner, 20, getHeight() * 4/5+20);
				if (game.getHandsOnTable().size() > 0) {
					for (int j = 0; j < game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).size(); j++) {
						int rank = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getCard(j).getRank();
						int suit = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getCard(j).getSuit();
						g.drawImage(cardImages[rank][suit], (j + 1) * getWidth() / 25, getHeight() * 4 /5 + 30, getHeight() / 9, getHeight() / 7, this);
					}
				}
			}
		} // end of paintComponent()
		

	    /**
	     * Invoked when the mouse button has been clicked (pressed and released) on a component.
	     * 
	     * @see java.awt.event.MouseListener#Your SelectionExited(java.awt.event.MouseEvent)
	     */
	    public void mouseClicked(MouseEvent e) {
	    	if (DEBUG) System.out.println("in mouseClicked(e) method");
	    	int selectedCardIdx = findClickingLoaction(e.getX(), e.getY());
	    	if (selectedCardIdx != -1) {
	    		selected[selectedCardIdx] = !selected[selectedCardIdx];
		    	updatePrompt();
		    	BigTwoTable.this.repaint();
	    	} else {
	    		return;
	    	}

	    }

	    /**
	     * This method will update the prompt information according to the cards selected by the user.
	     */
	    public void updatePrompt() {
	    	int[] cardIdx = getSelected();
	    	if (cardIdx == null) {
	    		prompt = "Your Selection: null";
	    	} else {
		    	CardGamePlayer localGuy = game.getPlayerList().get(localPlayer);
		    	CardList cardsPlayed = localGuy.play(cardIdx);
		    	Hand validHand = ((BigTwoClient)game).composeHand(localGuy, cardsPlayed);
		    	if (validHand == null) {
		    		prompt = "Your Selection: invalid";
		    	} else {
		    		prompt = "Your Selection: " + validHand.getType();
		    	}
	    	}
	    }
	    
	    /**
	     * Invoked when a mouse button has been pressed on a component, not used for this class.
	     * 
	     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	     */
	    public void mousePressed(MouseEvent e){}

	    /**
	     * Invoked when a mouse button has been released on a component, not used for this class.
	     * 
	     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	     */
	    public void mouseReleased(MouseEvent e){}

	    /**
	     * Invoked when the mouse enters a component, not used for this class.
	     * 
	     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	     */
	    public void mouseEntered(MouseEvent e){}

	    /**
	     * Invoked when the mouse exits a component, not used for this class.
	     * 
	     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	     */
	    public void mouseExited(MouseEvent e){}
	    
	    
	    /**
	     * This method finds which card is clicked by the player given the x and y coordinates of 
	     * user clicking location, it returns the index of the card being clicked, if it is an invalid 
	     * user click, returns -1, indicating there is no card being clicked.
	     * 
	     * @param x The x coordinate of user clicking location
	     * @param y The x coordinate of user clicking location
	     * @return The index of the card being clicked, or returns -1 if it is an invalid user click.
	     */
	    public int findClickingLoaction(int x, int y) {
	    	
	    	if (DEBUG) {
	    		System.out.println("in findClickingLoaction() method.");
	    		System.out.println("activePlayer = "  + activePlayer);
	    	}
	    	
			if (activePlayer == -1) {
				return -1;
			}
			int selectedCardIdx = -1;
			int width = bigTwoPanel.getWidth();
			int height = bigTwoPanel.getHeight();
			int numOfCards = game.getPlayerList().get(localPlayer).getNumOfCards();
	    	
			// whether in horizontal(x) range
			for (int i=0; i<numOfCards; i++) { // 0-12
				if (x>=(5+i)*width/25 && x<(6+i)*width/25) { 
					selectedCardIdx = i;
					break;
				}
				
				if (i==numOfCards-1) {
					if (x>=(5+i)*width/25 && x<(5+i)*width/25+height/9) {
						selectedCardIdx = i;
					}
				}
			}
			
			// if in horizontal range, find exactly which card is being clicked.
			if (selectedCardIdx != -1) {
				if (selected[selectedCardIdx]) {
					if (y>=height*localPlayer/5+10 && y<=height*localPlayer/5+10+height/7) {
						return selectedCardIdx;
					}
					else if (y>=height*localPlayer/5+10+height/7 && y<height*localPlayer/5+30+height/7) {
						int lastUnselectedCardIdx = lastUnSelectedCardIdx(selectedCardIdx);
						if (lastUnselectedCardIdx!=-1) {
							if (x>=(5+lastUnselectedCardIdx)*width/25 && x<(5+lastUnselectedCardIdx)*width/25+height/9) {
								return lastUnselectedCardIdx;
							}
						}
					}
				} else {
					if (y>=height*localPlayer/5+30 && y<=height*localPlayer/5+30+height/7) {
						return selectedCardIdx;
					} else if (y>=height*localPlayer/5+10 && y<=height*localPlayer/5+30) {
						int lastSelectedCardIdx = lastSelectedCardIdx(selectedCardIdx);
						if (lastSelectedCardIdx!=-1) {
							if (x>=(5+lastSelectedCardIdx)*width/25 && x<(5+lastSelectedCardIdx)*width/25+height/9) {
								return lastSelectedCardIdx;
							}
						}		
					}
				}
			}
			return -1;
	    }
	    
	    
	    /**
	     * This methods handles the case when player clicks the lower part of a selected card,
	     * which means there is possible that another unselected card being clicked.
	     * 
	     * @param selectedCardIdx The index of the a selected card where player clicks the lower part of it.
	     * @return The index of the last unselected card, or returns -1 if it is an invalid user click.
	     */
	    public int lastUnSelectedCardIdx(int selectedCardIdx) {
	    	if (selectedCardIdx>-1) {
	    		for (int i=selectedCardIdx-1; i>-1; i--) {
	    			if (selected[i] == false) {
	    				return i;
	    			}
	    		}
	    	}
	    	return -1;
	    }
	    
	    /**
	     * This methods handles the case when player clicks the upper part of an unselected card,
	     * which means there is possible that another selected card being clicked.
	     * 
	     * @param unSelectedCardIdx The index of the an unselected card where player clicks the upper part of it.
	     * @return The index of the last unselected card, or returns -1 if it is an invalid user click.
	     */
	    public int lastSelectedCardIdx(int unSelectedCardIdx) {
	    	if (unSelectedCardIdx>-1) {
	    		for (int i=unSelectedCardIdx-1; i>-1; i--) {
	    			if (selected[i] == true) {
	    				return i;
	    			}
	    		}
	    	}
	    	return -1;
	    }
	    
	}
		
	/**
	 * This class implements the ActionListener interface, and implements the actionPerformed methods,
	 * it handles the button-click events for the “Play” button.
	 * 
	 * @author Hu Qiyun
	 *
	 */
	class PlayButtonListener implements ActionListener {
		/**
		 * This method handle the action event, when the play button is clicked, if player does not select any card,
		 * it will prompt "Not a legal Move", otherwise it just call the player to make move.
		 * 
		 * @param e The action event that the play button is clicked.
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			prompt  = "Your Selection: null";
			if (activePlayer != game.getCurrentIdx()) {
				resetSelected();
				printMsg("This is not your turn!!!\n");
				printMsg("Player " +  game.getCurrentIdx() + " " + game.getPlayerList().get(game.getCurrentIdx()).getName() + " 's turn\n");
				repaint();
			} else {
				int[] cardIdx = getSelected();
				if (cardIdx == null) {
					printMsg("Not a legal move!!!\n");
					printMsg("Player " +  game.getCurrentIdx() + " " + game.getPlayerList().get(game.getCurrentIdx()).getName() + " 's turn\n");
					resetSelected();
					repaint();
					return;
				} else {
					game.makeMove(activePlayer, getSelected());
				}
			}			
		}
	}
	

	/**
	 * This class implements the ActionListener interface, and implements the actionPerformed() methods,
	 * it handles the button-click events for the “Pass” button.
	 * 
	 * @author Hu Qiyun
	 *
	 */
	class PassButtonListener implements ActionListener {
		/**
		 * This method handle the action event, when the pass button is clicked, it just call the 
		 * player to make move.
		 * 
		 * @param e The action event that the play button is clicked.
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (activePlayer != game.getCurrentIdx()) {
				resetSelected();
				printMsg("This is not your turn!!!\n");
				printMsg("Player " +  game.getCurrentIdx() + " " + game.getPlayerList().get(game.getCurrentIdx()).getName() + " 's turn\n");
				repaint();
			} else {
				resetSelected();
				prompt  = "Your Selection: null";
				game.makeMove(activePlayer, getSelected());
			}
		}
	}

	/**
	 * An inner class that implements the ActionListener interface, it implements 
	 * the actionPerformed() method from the ActionListener interface to handle 
	 * menu-item-click events for the “Restart” menu item.
	 * 
	 * @author Hu Qiyun
	 *
	 */
	class ConnectMenuItemListener implements ActionListener {

		/**
		 * This method is invoked when the Connect item in menu is clicked, it tries to connect to the server.
		 * 
		 * @param e Action event that the the restart item in menu is clicked.
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		
		public void actionPerformed(ActionEvent e) {
			if (localPlayer == -1) {
				((BigTwoClient) game).makeConnection();
			} else if (localPlayer >= 0 && localPlayer <= 3) {
				printMsg("You are already connected to the server!\n");
			}
		}
	}

	
	/**
	 * An inner class that implements the ActionListener interface, and implements
	 * the actionPerformed() method from the ActionListener interface to handle 
	 * menu-item-click events for the “Quit” menu item.
	 * 
	 * @author Hu Qiyun
	 *
	 */
	class QuitMenuItemListener implements ActionListener {

		/**
	     * Invoked when an action occurs, exit the game.
	     *
	     * @param e The action event that the quit menu item is clicked.
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	     */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);			
		}
	}

	
	/**
	 * This class handles the chat input field event when the enter key is hit, then sends the chat message to the server.
	 * 
	 * @author Hu Qiyun
	 *
	 */
	class ChatInputListener implements ActionListener {

		/**
		 * This method handles the event when the enter key is hit in text field, to send the message out to the server.
		 * 
		 * @param e Action event when user hits the enter
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String content = chatInput.getText();
			if (content != "") {
				chatInput.setText("");
				((BigTwoClient) game).sendMessage(new CardGameMessage(CardGameMessage.MSG, -1, content));
			}
		}
	}
	
	/**
	 * An inner class that implements the ActionListener interface, and implements
	 * the actionPerformed() method from the ActionListener interface to handle 
	 * menu-item-click events for the “Clear Info Board” menu item.
	 * 
	 * @author Hu Qiyun
	 *
	 */
	class ClearInfoBoardListener implements ActionListener{
		/**
		 * Handle menu-Item click events for "Clear Info Board" menu item
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			clearMsgArea();
		}
	}
	
	/**
	 * An inner class that implements the ActionListener interface, and implements
	 * the actionPerformed() method from the ActionListener interface to handle 
	 * menu-item-click events for the “Clear Chat Board” menu item.
	 * 
	 * @author Hu Qiyun
	 *
	 */
	class ClearChatBoardListener implements ActionListener{
		/**
		 * Handle menu-item click events for "Clear Chat Board" menu item, just empty the chat area
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			clearChatArea();
		}
	}
	
	
}
