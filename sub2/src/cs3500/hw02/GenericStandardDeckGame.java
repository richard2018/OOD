package cs3500.hw02;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * This is an implementation of GenericCardGameModel for a generic card game. It
 * is designed to work with class Card. It has methods to get a valid deck for
 * this game, start play with the provided deck and number of players, and get a
 * representation of the game state at any time. It also keeps track of the
 * current players' hands using a 2D ArrayList of cards
 */

public class GenericStandardDeckGame implements GenericCardGameModel<Card> {

  private ArrayList<List<Card>> players;

  public GenericStandardDeckGame() {
    this.players = new ArrayList<List<Card>>();
  }

  public GenericStandardDeckGame(int players) {
    this.startPlay(players, this.getDeck());
  }

  public GenericStandardDeckGame(int players, ArrayList<Card> deck) {
    this.startPlay(players, deck);
  }

  /**
   * Get a deck of all the valid playing cards for this game.This method will
   * return a deck of valid playing cards in no particular order.
   *
   * A deck is invalid if it does not contain 52 cards that have every standard
   * playing card (ie: 2-Ace of every suit).
   *
   * A deck is invalid if it does not have 52 cards, or if there are duplicate
   * cards, or if there are invalid cards (invalid suit or invalid number)
   *
   * @return a List of Cards
   */
  public List<Card> getDeck() {
    ArrayList<Card> deck = new ArrayList<Card>();
    String suits = "♣♦♥♠";
    for (char c : suits.toCharArray()) {
      for (int i = 2; i < 15; i++) {
        deck.add(new Card(i, c));
      }
    }
    return deck;
  }

  /**
   * Start playing with the given deck of cards and the given number of players.
   * This method distributes the deck AS IT IS GIVEN amongst the players, in
   * round-robin fashion e.g. if there are 3 players and 52 cards in the deck,
   * player 0 will get card nos 0,3,6,9,.. and player 1 will get card nos.
   * 1,4,7,10,... and so on.
   *
   * The deck is to be distributed in the exact sequence as it is provided.
   * Thus, NO SHUFFLING or REORDERING is allowed.
   *
   * If a deck is to be shuffled, it must be shuffled before calling this
   * method.
   *
   * @param numPlayers the number of players playing this game
   * @param deck       the deck of cards to be distributed among the players to
   *                   start the game
   */
  public void startPlay(int numPlayers, List<Card> deck) {
    if (numPlayers < 1 || numPlayers > 52) {
      throw new IllegalArgumentException("Invalid number of players");
    }
    if (deck.size() != 52) {
      throw new IllegalArgumentException("Deck must be 52 cards");
    }
    ArrayList<List<Card>> temp = new ArrayList<List<Card>>();
    int p = numPlayers;
    while (p > 0) {
      temp.add(new ArrayList<Card>());
      p--;
    }
    for (Card c : deck) {
      temp.get(p).add(c);
      p++;
      if (p == numPlayers) {
        p = 0;
      }
    }
    this.players = temp;
  }

  /**
   * This method will return a string that, when displayed, gives the current
   * state of the game.
   *
   * This string should be in the following format ({S} is space, {NL} is a
   * newline, text in italics are comments and do not appear in the string).
   * Replace placeholders with actual data as applicable:
   * <pre>
   * Number of players:{S}X{NL}
   * Player{S}1:{S}P11,{S}P12,{S}P13, ...{NL} <i>(Player 1 cards in sorted
   * order)</i>
   * Player{S}2:{S}P21,{S}P22,{S}P23, ...{NL} <i>(Player 2 cards in sorted
   * order)</i>
   * ...
   * Player{S}X:{S}PX1,{S}PX2,{S}PX3, ...{NL} <i>(Player X cards in sorted
   * order)</i>
   * </pre>
   *
   * @return a string that represents the current state of the game
   */
  public String getGameState() {
    String result = "Number of players: " + players.size();
    int player = 1;
    for (List<Card> a : players) {
      Collections.sort(a);
      boolean first = true;
      result += "\nPlayer " + player;
      for (Card c : a) {
        if (first) {
          result += ": " + c.toString();
          first = false;
        } else {
          result += ", " + c.toString();
        }
      }
      player++;
    }
    return result;
  }

  /**
   * Returns the list of each player's hand of cards
   *
   * @return a 2D ArrayList of Cards
   */
  public List<List<Card>> getPlayers() {
    return players;
  }
}
