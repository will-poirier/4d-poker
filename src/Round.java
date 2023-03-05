import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Round {
    private List<Player> players;
    private Map<Player, Integer> bets;
    private Deck deck;
    private int pot;
    private boolean debug;
    private List<Player> out;

    public Round(List<Player> players, Deck deck, boolean debug) {
        this.players = players;
        this.deck = deck;
        pot = 0;
        this.debug = debug;
        if (debug) {
            System.out.println("--players: " + players);
            System.out.println("--deck: " + deck);
        }
        out = new LinkedList<>();
        bets = new HashMap<>();
        for (Player player : players) {
            bets.put(player, 0);
        }
    }

    public Round(List<Player> players, Deck deck) {
        this(players, deck, false);
    }

    public int getPot() {
        return pot;
    }

    private int bettingRound(int bid) {
        int call = bid;
        int index = 0;
        Player highBidder = null;
        Player currentPlayer = null;
        do {
            currentPlayer = players.get(index);
            if (!out.contains(currentPlayer)) {
                if (highBidder != null && currentPlayer.equals(highBidder)) {
                    break;
                }
                call = currentPlayer.firstBettingRound(call);
                if (call <= 0) {
                    if (debug) {System.out.println("--fold: " + pot);}
                    out.add(currentPlayer);
                } else {
                    if (debug) {System.out.println("--call: " + call + ": " + pot);}
                    pot += call - bets.get(currentPlayer);
                    currentPlayer.spendCash(call - bets.get(currentPlayer));
                    bets.put(currentPlayer, call);
                    if (call > bid || highBidder == null) {
                        highBidder = currentPlayer;
                        bid = call;
                    }
                }
            }
            index = (index + 1) % players.size();
        } while (true); // everyone's favorite
        return bid;
    }

    public void play(int anteAmount) {
        // Buy-in / Ante
        deck.shuffle();
        if (debug) {System.out.println("--shuffle: " + deck);}
        for (Player player : players) {
            int ante = player.ante(anteAmount);
            if (debug) {System.out.println("--ante: " + ante + ": " + pot);}
            pot += ante;
            player.spendCash(ante);
            if (ante > 0) { 
                // Deal out hands to players
                for (int i = 0; i < player.getHandSize(); i++) {
                    player.drawCard(deck.drawCard());
                }
            } else {
                out.add(player); // Player folded (or i guess in this case decided to sit out or something)
            }
        }
        // First round of Betting -- continues until no one raises
        int bid = anteAmount; // assiming you have to start a bid with the same amount as the ante -- perhaps change this later
        bid = bettingRound(bid);
        // Swap phase -- Players can choose to replace cards from their hands with ones from the deck
        for (Player player : players) {
            if (!out.contains(player)){
                List<Card> cards = player.swapCards();
                if (debug) {System.out.println("--swap: " + cards);}
                for (Card card : cards) {
                    player.discardCard(card);
                    player.drawCard(deck.drawCard());
                }
            }
        }
        // Second round of Betting -- works just like the first
        bid = bettingRound(bid);
    }

    public boolean inLimbo() {
        for (Player player : players) {
            if (player instanceof TimeTraveler) {
                TimeTraveler traveler = (TimeTraveler)(player);
                if (traveler.hasBlanks()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Player evaluate() {
        // Evaluate player hands and find the winner
        Player winner = null;
        for (Player player : players) {
            if (winner == null) {
                winner = player;
                continue;
            }
            if (player.handValue().compareTo(winner.handValue()) > 0) {
                if (debug) {System.out.println("--compare: " + player.handValue() + " > " + winner.handValue());}
                winner = player;
                continue;
            }
            if (debug) { System.out.println("--currentWinner: " + winner); }
            // if the hands are equal, the player who was going first in the round gets the win. Should be rare enough to let me put off dealing with ties for now.
        }
        winner.winCash(pot);
        return winner;
    }
}
