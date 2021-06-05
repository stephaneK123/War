package edu.vtc;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


import edu.vtc.Card.AceHighRankCompare;
import edu.vtc.Card.Suit;

public class Player {
	private int _gamesWon; //games won by player
	private String _name; //name of the player
	private List<Card> _playerDeck; //a player's unique starting deck
	private List<Card> _discardPile; //contains all cards won by a player
	private Card _currentCard; //the last card the player drew 
	private boolean _loser = true; //decides if player has won
	/**
	 * create a new player 
	 * @param name the name of the player
	 */
	public Player(String name) {
		_name = name;
		_gamesWon = 0;
		_playerDeck = new LinkedList<>();
		_discardPile = new LinkedList<>();
	}

	/**
	 * draw a card, drawing a card removes it from the player's deck, 
	 * the drawn card becomes the player's current card
	 * @return the card it drew 
	 */
	public Card Draw() {
		//check if 
		_currentCard = ((LinkedList<Card>) _playerDeck).pop();
		if(_playerDeck.isEmpty() || _playerDeck.size() < 5) {
			refill_playerDeck();
		}
		return _currentCard;		
	}

	/**
	 * get a player's name
	 * @return the player's name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * get a player's discard pile, 
	 * the discard pile is a deck that contains all cards won by a player 
	 * @return the player's discardPile( deck holding thier won cards)
	 */
	public List<Card> get_discardPile() {
		return _discardPile;
	}

	/**
	 * get a player's unique deck (the one started with initially)
	 * @return the player's dec
	 */
	public List<Card> get_playerDeck() {
		return _playerDeck;
	}

	/**
	 * get the player's current card
	 * @return the player's current card
	 */
	public Card getCurrentCard() {
		return _currentCard;
	}

	/**
	 * set a player's current card
	 * @param x the card to set
	 */
	public void setCurrentCard(Card x) {
		_currentCard.equals(x);
	}

	/**
	 * add a card to a player's deck
	 * @param x the card being added
	 */
	public void addToPlayerDeck(Card x) {
		_playerDeck.add(x);
	}

	/**
	 * add a List<Card> to a player's deck
	 * @param x the List<Card> being added
	 */
	public void addToPlayerDeck(List<Card> x) {
		_playerDeck.addAll(x);
	}

	/**
	 * add a card to a player's discard deck
	 * @param x the card being added
	 */
	public void addToPlayerDiscardDeck(Card x) {
		_discardPile.add(x);
	}

	/**
	 *  add a List<Card> to a player's discard deck
	 * @param x the List<Card> being added
	 */
	public void addToPlayerDiscardDeck(List<Card> x) {
		_discardPile.addAll(x);
	}

	/**
	 * checks if player has lost, 
	 * a player has lost if thier deck and discard pile is empty
	 * @return true if the player has lost, false otherwise
	 */
	public boolean hasPlayerLost() {
		if(_playerDeck.isEmpty() && _discardPile.isEmpty())
			return _loser;
		return false;
	}

	/**
	 * change the status of the private field _loser, the field controls if a player has lost, 
	 * true will make the player lose
	 * @param x the status of the player
	 */
	public void setWinner(boolean x) {
		_loser = x;
	}

	/**
	 * refill the player's deck using thier discardpile (shuffled before hand),
	 * a player's deck gets refilled if it becomes empty or has less than 4 cards and thier discard pile is not empty, 
	 * the discard pile is cleared and all cards go to the player's deck  
	 */
	public void refill_playerDeck() {
		if(_playerDeck.isEmpty() || _playerDeck.size() < 5 && !_discardPile.isEmpty()) {
			Collections.shuffle(_discardPile);
			_playerDeck.addAll(_discardPile);
			_discardPile.clear();
		}
	}

	/**
	 * increase a player's game won (gets added toward)
	 * @param number the number to increase by, always > 0 < 2
	 */
	void setPlayerGamesWon(int number) {
		_gamesWon += number;
	}
	/**
	 * get a player's gameswon stats
	 * @return the number of games won by a player always > -1
	 */
	int getPlayerGamesWon() {
		return _gamesWon;
	}

	private void RepOk() {
		assert _playerDeck.size() < 27;
	}
	/**
	 * toString representation of a player, a player is represented by name and games won,
	 * i.e John with 3 games won will be [Name= John, gamesWon= 3]
	 */
	@Override
	public String toString() {
		return "Player [Name= " + _name + ", gamesWon= " + _gamesWon + "]";
	}

}
