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
	
	private CardGame game; // a card game associates with this table.
	private boolean[] selected; // a boolean array indicating which cards are being selected.
	private int activePlayer; // an integer specifying the index of the active player.
	private JFrame frame; // the main window of the application.
	private JPanel bigTwoPanel; // a panel for showing the cards of each player and the cards played on the table.
	private JButton playButton; // a “Play” button for the active player to play the selected cards.
	private JButton passButton; // a “Pass” button for the active player to pass his/her turn to the next player.
	private JTextArea msgArea; // a text area for showing the current game status as well as end of game messages.
	private Image[][] cardImages; // a 2D array storing the images for the faces of the cards.
	private Image cardBackImage; // an image for the backs of the cards.
	private Image[] avatars; // array storing the images for the avatars.
	
	private JMenuBar menuBar;// A menu bar for quit and restart
	private JMenu menu; // A menu to contain two items
	private JMenuItem quitMenu; // The quit menu item
	private JMenuItem restartMenu; // The restart menu item
	private JMenuItem changeName; // The change-name menu item
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
		prompt = "Prompt: null";
		
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
		
		// create a frame to contain all the widget
		frame = new JFrame("Big Two");
		frame.setSize(1200, 800);
		frame.setMinimumSize(new Dimension(950, 700));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		// big two panel on the left
		bigTwoPanel = new BigTwoPanel();
		bigTwoPanel.setLayout(new BorderLayout());
		bigTwoPanel.setBackground(new Color(34, 139, 34));
		bigTwoPanel.addMouseListener(new BigTwoPanel());
		frame.add(bigTwoPanel, BorderLayout.CENTER);
		
		// two buttons: play and pass
		playButton = new JButton("play");
		playButton.addActionListener(new PlayButtonListener());
		passButton = new JButton("pass");
		passButton.addActionListener(new PassButtonListener());
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(playButton);
		buttonPanel.add(passButton);
		bigTwoPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		// a message area on the right with a scroller
		msgArea = new JTextArea(10, 30);
		msgArea.setFont(new Font ("Arial", Font.PLAIN, 14));
		msgArea.setEditable(false);
		msgArea.setLineWrap(true);
		JScrollPane scroller = new JScrollPane(msgArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.add(scroller, BorderLayout.EAST);
			
		// a menu bar on the top
		menuBar = new JMenuBar();
		menu = new JMenu("Game");
		menu.setFont(new Font("Verdana", 1, 13));
		quitMenu = new JMenuItem("Quit");
		quitMenu.setFont(new Font("Verdana", 1, 13));
		quitMenu.addActionListener(new QuitMenuItemListener());	  
		restartMenu = new JMenuItem("Restart");
		restartMenu.setFont(new Font("Verdana", 1, 13));
		restartMenu.addActionListener(new RestartMenuItemListener());
		changeName = new JMenuItem("Change name");
		changeName.setFont(new Font("Verdana", 1, 13));
		changeName.addActionListener(new changeNameListener());
		menu.add(quitMenu);
		menu.add(restartMenu);
		menu.add(changeName);
		menuBar.add(menu);
		frame.add(menuBar, BorderLayout.NORTH);
				
		frame.setVisible(true);		
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
	 * Clears the message area of the card game table.
	 * 
	 * @see CardGameTable#clearMsgArea()
	 */
	public void clearMsgArea() {
		msgArea.setText("");
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
			g.setColor(Color.BLACK);
			
			int greenPanelWidth = getHeight() - 40;

			for (int i=0; i<4; i++) {

				if (i==activePlayer-1) {
					g.drawString(game.getPlayerList().get(i).getName(), 20, greenPanelWidth*i/5+20);	
					g.setColor(Color.WHITE);
					g.drawLine(0, greenPanelWidth*(i+1)/5, 2000, greenPanelWidth*(i+1)/5);
					g.setColor(Color.BLACK);
				} else if (i==activePlayer) {
					g.setColor(Color.WHITE);
					g.drawString(game.getPlayerList().get(i).getName(), 20, greenPanelWidth*i/5+20);
					g.drawString(prompt, 18 * getWidth() / 25 + getHeight() / 9, greenPanelWidth*i/5+20);
					g.drawLine(0, greenPanelWidth*(i+1)/5, 2000, greenPanelWidth*(i+1)/5);
					g.setColor(Color.BLACK);
				} else {
					g.drawString(game.getPlayerList().get(i).getName(), 20, greenPanelWidth*i/5+20);
					g.drawLine(0, greenPanelWidth*(i+1)/5, 2000, greenPanelWidth*(i+1)/5);	
				}
			}
			
			String lastHandOwner = (game.getHandsOnTable().size() == 0)? "No hands on table for now" : "Played by " + game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer().getName();
			g.drawString(lastHandOwner, 20, greenPanelWidth* 4/5+20);
			
			// draw top 4 rows of the big two panel
			int rank=0;
			int suit=0;
			for (int i=0; i<4; i++) {
				
				// draw avatars
				g.drawImage(avatars[i], 10, greenPanelWidth*i/5 + 30, getHeight()/7, getHeight()/7, this);
				
				// draw cards
				if (((BigTwo)game).endOfGame()) {
					for (int j=0; j<game.getPlayerList().get(i).getNumOfCards(); j++) {
						rank = game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank();
						suit = game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit();
						g.drawImage(cardImages[rank][suit], (j+5) * getWidth() / 25, greenPanelWidth * i / 5 + 30, getHeight() / 9, getHeight() / 7, this);						
					}
				} else {
					if (i==activePlayer) { // active player
						g.drawImage(star, 80, getHeight() * i / 5 + 5, getHeight() / 27, getHeight() / 27, this);
						for (int j=0; j<game.getPlayerList().get(i).getNumOfCards(); j++) {
							rank = game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank();
							suit = game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit();
							if (selected[j]) {
								g.drawImage(cardImages[rank][suit], (j+5) * getWidth() / 25, greenPanelWidth * i / 5 + 10, getHeight() / 9, getHeight() / 7, this);
							} else {
								g.drawImage(cardImages[rank][suit], (j+5) * getWidth() / 25, greenPanelWidth * i / 5 + 30, getHeight() / 9, getHeight() / 7, this);
							}
						}				
					} else { // non-active players
						for (int j=0; j<game.getPlayerList().get(i).getNumOfCards(); j++) {
							g.drawImage(cardBackImage, (j+5) * getWidth() / 25, greenPanelWidth * i / 5 + 30, getHeight() / 9, getHeight() / 7, this);
						}
					}
				}		
			}
					
			// draw last hands on table
			if (game.getHandsOnTable().size() > 0) {
				for (int j = 0; j < game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).size(); j++) {
					rank = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getCard(j).getRank();
					suit = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getCard(j).getSuit();
					g.drawImage(cardImages[rank][suit], (j + 1) * getWidth() / 25, greenPanelWidth* 4 /5 + 30, getHeight() / 9, getHeight() / 7, this);
				}
			}
		} // end of paintComponent()
		

	    /**
	     * Invoked when the mouse button has been clicked (pressed and released) on a component.
	     * 
	     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	     */
	    public void mouseClicked(MouseEvent e) {
	    	int selectedCardIdx = findClickingLoaction(e.getX(), e.getY());
	    	if (selectedCardIdx != -1) {
	    		selected[selectedCardIdx] = !selected[selectedCardIdx];
	    	}
	    	updatePrompt();
	    	BigTwoTable.this.repaint();
	    }

	    /**
	     * This method will update the prompt information according to the cards selected by the user.
	     */
	    public void updatePrompt() {
	    	int[] cardIdx = getSelected();
	    	if (cardIdx == null) {
	    		prompt = "Prompt: null";
	    	} else {
		    	CardGamePlayer currentPlayer = game.getPlayerList().get(activePlayer);
		    	CardList cardsPlayed = currentPlayer.play(cardIdx);
		    	Hand validHand = ((BigTwo)game).composeHand(currentPlayer, cardsPlayed);
		    	if (validHand == null) {
		    		prompt = "Prompt: not valid!";
		    	} else {
		    		prompt = "Prompt: " + validHand.getType();
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
	    	// still has bugs
			if (activePlayer == -1) {
				return -1;
			}
			int selectedCardIdx = -1;
			int width = bigTwoPanel.getWidth();
			int height = bigTwoPanel.getHeight();
			int greenPanelWidth = bigTwoPanel.getHeight() - 40;
			int numOfCards = game.getPlayerList().get(activePlayer).getNumOfCards();
	    	
			// whether in horizontal(x) range
			for (int i=0; i<numOfCards; i++) { // 0-12
				if (x>=(3+2+i)*width/25 && x<(4+2+i)*width/25) { 
					selectedCardIdx = i;
					break;
				}
				
				if (i==numOfCards-1) {
					if (x>=(3+2+i)*width/25 && x<(3+2+i)*width/25+height/9) {
						selectedCardIdx = i;
					}
				}
			}
			
			// if in horizontal range, find exactly which card is being clicked.
			if (selectedCardIdx != -1) {
				if (selected[selectedCardIdx]) {
					if (y>=greenPanelWidth*activePlayer/5+10 && y<=greenPanelWidth*activePlayer/5+10+height/7) {
						return selectedCardIdx;
					}
					else if (y>=greenPanelWidth*activePlayer/5+10+height/7 && y<greenPanelWidth*activePlayer/5+30+height/7) {
						int lastUnselectedCardIdx = lastUnSelectedCardIdx(selectedCardIdx);
						if (lastUnselectedCardIdx!=-1) {
							if (x>=(5+lastUnselectedCardIdx)*width/25 && x<(5+lastUnselectedCardIdx)*width/25+height/9) {
								return lastUnselectedCardIdx;
							}
						}
					}
				} else {
					if (y>=greenPanelWidth*activePlayer/5+30 && y<=greenPanelWidth*activePlayer/5+30+height/7) {
						return selectedCardIdx;
					} else if (y>=greenPanelWidth*activePlayer/5+10 && y<=greenPanelWidth*activePlayer/5+30) {
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
			prompt  = "Prompt: null";
			int[] cardIdx = getSelected();
			if (cardIdx == null) {
				printMsg("Not a legal Move!!!\n");
				return;
			} else {
				game.makeMove(activePlayer, getSelected());
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
			resetSelected();
			prompt  = "Prompt: null";
			game.makeMove(activePlayer, getSelected());
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
	class RestartMenuItemListener implements ActionListener {

		/**
	     * Invoked when an action occurs, restart the game.
	     *
	     * @param e The action event that the restart menu item is clicked.
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	     */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			reset();
			BigTwoDeck deck = new BigTwoDeck();
			deck.initialize();
			deck.shuffle();
			game.start(deck);
			
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
	 * An inner class that implements the ActionListener interface, and implements
	 * the actionPerformed() method from the ActionListener interface to handle 
	 * menu-item-click events for the “Change name” menu item.
	 * 
	 * @author Hu Qiyun
	 *
	 */
	class changeNameListener implements ActionListener {

		/**
	     * Invoked when an action occurs, click the change-name menu item the game.
	     *
	     * @param e The action event that the play button is clicked.
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	     */
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog(frame, "New name");
			if (name == null) {
				return;
			} else {
				game.getPlayerList().get(activePlayer).setName(name);
				repaint();
			}
		}
	}
	
	
}
