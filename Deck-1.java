package edu.vtc;
//can not find the best implementation but settled on tree set :)
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.vtc.Card.Suit;

/**
 * 
 * @author Stephane.Katende
 *
 */
public class Deck{
	private String _name;
	private List<Card> _deck; // the deck of card
	private int _amount = 0;
	private Card[] _currentCard; //cards in hand, not more than 7, may be null, may be empty
	/**
	 * create a standard deck of cards
	 * @param name the name of deck for toString purposes, not more than 15 characters  
	 */
	public Deck(String name) {
		_name = name;
		_deck = new ArrayList<>(); 
		//filling deck 

		for ( int rank = 1; rank <= 13; rank++ )
			for(Suit x : Card.Suit.values())
				_deck.add(new Card(x,rank));

		RepOK();
	}
	/**
	 * represenation of the deck by ranks with suits i.e [Two of Ace] 
	 */
	public String toString() {
		String print = "";
		int i = 0;
		for(Card x : _deck) {
			i++;
			print += "|" + x;
			if(x.getSuit().equals(Suit.Spade))
				print += "|" + "\n";
		}
	return "Name of Deck : " + _name + "\n" + print;
	}
	/**
	 * shuffle a deck of card
	 * @param amount the number of times deck will be shuffled, always > 0 <= 15 
	 */
	public void shuffle(int amount) {
		_amount = amount;
		//swaping two random cards as amount times
		for(int i = 0; i <= amount; i++) {	
			int randomCard = (int) (52 * Math.random());
			int randomCardv2 = (int) (52 * Math.random());


			Card a = _deck.get(randomCard);
			Card b = _deck.get(randomCardv2);

			_deck.set(randomCard, b);
			_deck.set(randomCardv2, a);

		}

	}
	/**
	 * checks if deck of card is empty 
	 * @return true if deck is empty, false otherwise
	 */
	public boolean isDeckEmpty() {
		return _deck.isEmpty();

	}
	/**
	 * finding the top card of deck
	 * @return the top card of the deck
	 */
	public Card seeTopCard() {
		return _deck.get(0);
	}
	/**
	 * draws top card removing from deck
	 */
	public void drawTopCard() {
		_deck.remove(0);
	}

	/**
	 * adds a card to the top of the deck and removes bottom card
	 * @param x the card being added to top,
	 * never empty, never null
	 */
	public void addTopCard(Card x) {
		_deck.add(0,x);
		_deck.remove(_deck.size() - 1);
	}
	/**
	 * check for duplicate cards in deck
	 * @return true if duplicates found false otherwise 
	 */
	public boolean isDuplicate() {
	
		for (int i = 0; i <= _deck.size(); i++) {
			for (int j = i + 1 ; j <= _deck.size(); j++) {
				if (_deck.get(i).getRank() == _deck.get(j).getRank() && _deck.get(i).getSuit() == _deck.get(j).getSuit()) {
					return true;
				}
			}
		}
		return false;

	}

	private void RepOK() {
		assert isDuplicate() == false;//check for duplicates
		assert _name.length() >= 15;
		assert _deck.size() <= 52; //the deck must always be 52 or less
		assert _deck.isEmpty() != true; // the deck must not be empty
		assert _amount <= 15 && _amount >= 0; //you can not shuffle more than 15 times or less than 0 times
	}
	public int size() {
		// TODO Auto-generated method stub
		return _deck.size();
	}


}
