package edu.vtc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Stephane.Katende
 *
 */
public class War {
	public List<Card> _gameDeck; // cards on table
	private Player _first;
	private Player _last; 
	private static int _compare; //



	/**
	 * create a war game
	 * @param a the first player 
	 * @param b the second player
	 */
	public War(Player a, Player b) {
		_first = a;
		_last = b;
		_gameDeck = new ArrayList<>();


	}

	/**
	 * deal a randomly shuffled Deck of card evenly at start of game, 
	 * each players recieve 26 cards to thier deck
	 */
	public void Deal() {
		Deck playingDeck = new Deck("War");
		playingDeck.size();
		playingDeck.shuffle(15);
		playingDeck.shuffle(10);
		//deal to first player the first 26 cards
		for(int i = 0; i < 26;i++) {
			_first.get_playerDeck().add(i, playingDeck.seeTopCard());
			playingDeck.drawTopCard();
		}
		for(int i = 0; i < 26;i++) {
			_last.get_playerDeck().add(i, playingDeck.seeTopCard());
			playingDeck.drawTopCard();
		}
	}

	/**
	 * play a round of the game, before a round starts each player checks if thier deck needs to be refilled,
	 *  a round starts by each player drawing a card, the cards are thrown in the game's deck
	 * a winner is decided, 
	 */
	public void rounds() {
		_first.refill_playerDeck();
		_last.refill_playerDeck();
		gameOver();
		//each players draws a card and put it in the game deck 
		_first.Draw();
		announcer(1);
		_last.Draw();
		announcer(2);
		_gameDeck.add(_first.getCurrentCard());
		_gameDeck.add(_last.getCurrentCard());
		//decides who wins
		isWinner();

	}

	/**
	 * check if a player has lost(empty deck and discard pile), if a player has lost it is announced,
	 * the winning player gets +1 to thier gameWon stats,the system terminates
	 */
	void gameOver() {
		//check to end the game
		if(_first.hasPlayerLost()) {
			_last.setPlayerGamesWon(1);    //increase games won
			announcer(7);
			System.exit(0); //terminate 
		}else if(_last.hasPlayerLost()) {
			_first.setPlayerGamesWon(1);
			announcer(6);
			System.exit(0); //terminate
		}
	}

	/**
	 *  check if a player has lost(having less cards in thier discard pile), if a player has lost it is announced,
	 *  if a tie it is announced, the system terminates
	 */
	void gameOverv2() {

		if(_first.get_discardPile().size() + _first.get_playerDeck().size() > _last.get_discardPile().size() + _last.get_playerDeck().size()) {
			announcer(11);
			_first.setWinner(true);
			System.exit(0); //terminate 
		}else if(_last.get_discardPile().size() + _last.get_playerDeck().size()  > _first.get_discardPile().size() + _first.get_playerDeck().size()) {
			announcer(12);
			_last.setWinner(true);
			System.exit(0); //terminate
		}else {
			System.out.println("No winner");
			System.exit(0);
		}
	}

	/**
	 * a war, before a war each player gets check to see if they can afford a war (have atleast 4 cards in deck),
	 *if a player can not afford a war they lose(if neither can the game ends), a war consists of each player drawing 4 times, a winner is decided
	 *based on the 4th card,   
	 * 
	 */
	public void isWar() {

		//each players draws 4 times
		for(int i = 0; i < 4; i++) {
			_first.Draw();
			announcer(1);
			_last.Draw();
			announcer(2);
			_gameDeck.add(_first.getCurrentCard());
			_gameDeck.add(_last.getCurrentCard());
		}
		//decides who win
		isWinner();

	}

	/**
	 * decides who wins the round, gives the winner all the cards, incase of a tie war is called,
	 * a player only enters war if they have enough cards to do so otherwise it is a lost,
	 *  resets the game deck and players current card
	 */
	public void isWinner() {
		_compare = Card.aceHighRankCompare.compare(_first.getCurrentCard(), _last.getCurrentCard());
		if(_compare > 0) {//player 1 won
			announcer(3);
			gameOver();
			_first.addToPlayerDiscardDeck(_gameDeck);
		}else if(_compare < 0){
			announcer(4);
			gameOver();
			_last.addToPlayerDiscardDeck(_gameDeck);
		}else{
			announcer(5);
			//can they afford war? if no you lose!
			if(_first.get_playerDeck().size() < 4 && _last.get_playerDeck().size() < 4){//neither player have enough cards
				announcer(10);
				gameOverv2();
				System.exit(0);
			}else if(_first.get_playerDeck().size() < 4) {//first player has less than 4 cards
				announcer(8);
				_first.setWinner(true);
				gameOverv2();
			}else if(_last.get_playerDeck().size() < 4) {//last player has less than 4 cards
				announcer(9);
				_last.setWinner(true);
				gameOverv2();
			}
			isWar();
		}
		//game deck and player's current card are reset
		_gameDeck.clear();
		_first.setCurrentCard(null);
		_last.setCurrentCard(null);
	}

	/**
	 * Start an automated game of War with players John and Alex playing, the game will play until
	 * a winner is decided  
	 */
	public static void play() {
		Player x = new Player("John");
		Player y = new Player("Alex");
		War a = new War(x,y);
		a.Deal();
		while(!x.hasPlayerLost()&&!y.hasPlayerLost())
		a.rounds();
		//testing purposes
		/*
		while(!x.hasPlayerLost()&&!y.hasPlayerLost()) {

			System.out.println(x+"'s deck zise "+x.get_playerDeck().size());
			System.out.println(x+"'s discardpile zise "+x.get_discardPile().size());
			System.out.println(y+"'s discardpile zise "+y.get_discardPile().size());
			System.out.println("Second player's deck zise "+y.get_playerDeck().size());
			a.rounds();
		}
		*/

	}

	/**
	 * see the game's deck (contains cards players are currently wagering during a round)
	 * @return the game deck, can be empty, can be null at times
	 */
	public List<Card> get_gameDeck() {
		return _gameDeck;
	}

	/**
	 * a switch containing all useful system out prints during a war game, 
	 * announcements are paired i.e 1 is for the first player 2 would be for the second 
	 * 3 & 4... and so on (except for 5 and any other noted below),
	 * 1 & 2 are for players drawing cards, 3 & 4 are for calling which player wins a round, 5 is for calling a tie (war),
	 * 6 & 7 are for calling which player wins the game, 8 & 9 are for calling player's not being able to afford a war,
	 *10 is for neither player being able to afford a war, 11 & 12 are for players winning by having more cards
	 * @param x the announcement to call, always > 0 
	 * 
	 */
	public void announcer(int x) {
		switch(x){
		case 1://drawing card for first player
			System.out.println(_first + " has played a " + _first.getCurrentCard());
			break;
		case 2://drawing card for second player
			System.out.println(_last + " has played a " + _last.getCurrentCard());
			break;
		case 3://first player winner
			System.out.println(_first+" Won the round\n");
			break;
		case 4://second player winner
			System.out.println(_last+" Won the round\n");
			break;
		case 5://tie will also announce war!
			System.out.println("TIE! War has begun");
			break;
		case 6://first player has won
			System.out.println(_first + " HAS WON.");
			break;
		case 7://second player has won
			System.out.println(_last + " HAS WON.");
			break;
		case 8://first player can not afford a war 
			System.out.println(_first+" does not have enough cards for a war");
			break;
		case 9://second player can not afford a war
			System.out.println(_last+" does not have enough cards for a war");
			break;
		case 10://neither player can afford a war
			System.out.println("Niether player can afford a war");
			break;
		case 11://first player winning by more cards
			System.out.println(_first + " HAS WON by having more cards.");
			break;
		case 12: //last player winning by more cards
			System.out.println(_last + " HAS WON by having more cards.");
			break;
		default:
			System.out.println("Do not call me");
			break;
		}

	}

	/**
	 * See the scoreboard, the score board will contains players and thier games won,
	 * only the top 5 players will be listed (highest wins)
	 */
	public void seeScoreBoard() {


	}

	/**
	 * add the winning player to score board or update it, terminate the system
	 * @param a the winning player
	 * @throws FileNotFoundException 
	 */
	void addToScoreBoard(Player a) throws FileNotFoundException {
		Scanner readFile = new Scanner(new File("scoreboardFile"));
		Boolean isPlayerOnFile = false; //to determine if player is already in file
		String currentPlayer = null;
		//reading the file
		while(readFile.hasNext()) {
			currentPlayer = readFile.next();
			if(a.getName().equals(currentPlayer))
				isPlayerOnFile = true;
		}
		//check if player is in score board
		if(isPlayerOnFile = true) {//player is already on leaderboard




		}else{//player is not on leader board




		}

	}
	/**
	 * adds a player's name and gamesWon attrubutes to file "gamescore.txt"
	 * @param a the player 
	 * @throws IOException 
	 */
	static void addplayerToFile(Player a) throws IOException {
		File scoreboardFile = new File("gamescore.txt");
		FileWriter ex = new FileWriter(scoreboardFile);
		PrintWriter x = new PrintWriter(ex);
		x.println("hello");
		x.print("heeeelo");
		x.close();


	}

	public static void main(String[] args) {
		play();
		/*Player john = new Player("john");
		Player alex = new Player("alexy");
		War test1 = new War(john,alex);
		List<Card> test = new ArrayList<>();
		test.add(new Card(Suit.Club,1));
		test.add(new Card(Suit.Diamond,2));
		test.add(new Card(Suit.Club,6));
		test.add(new Card(Suit.Club,3));
		//Collections.shuffle(test);
		john.addToPlayerDeck(test);
		//Collections.shuffle(test);
		alex.addToPlayerDeck(test);
		alex.addToPlayerDeck(new Card(Suit.Club,12));
		alex.addToPlayerDeck(new Card(Suit.Club,12));
		alex.addToPlayerDeck(new Card(Suit.Club,12));
		alex.addToPlayerDeck(new Card(Suit.Club,12));
		alex.addToPlayerDeck(new Card(Suit.Club,12));
		alex.addToPlayerDeck(new Card(Suit.Club,12));
		test1.rounds();*/



		/*
		System.out.println("First player's deck zise "+x.get_playerDeck().size());
		System.out.println("Second player's deck zise "+y.get_playerDeck().size());
		System.out.println("First player's discardpile zise "+x.get_discardPile().size());
		System.out.println("Second player's discardpile zise "+y.get_discardPile().size());
		while(!x.hasPlayerLost()&& !y.hasPlayerLost()) {
			test.rounds();
			System.out.println("First player's deck zise "+x.get_playerDeck().size());
			System.out.println("Second player's deck zise "+y.get_playerDeck().size());
			System.out.println("First player's discardpile zise "+x.get_discardPile().size());
			System.out.println("Second player's discardpile zise "+y.get_discardPile().size());
		}
		System.out.println("First player's deck zise "+x.get_playerDeck().size());
		System.out.println("Second player's deck zise "+y.get_playerDeck().size());
		System.out.println("First player's discardpile zise "+x.get_discardPile().size());
		System.out.println("Second player's discardpile zise "+y.get_discardPile().size()); */

		


	}








}
