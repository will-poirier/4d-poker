import java.util.List;

public class Round {
    private List<Player> players;
    private Deck deck;
    private int pot;
    private boolean debug;

    public Round(List<Player> players, Deck deck, boolean debug) {
        this.players = players;
        this.deck = deck;
        pot = 0;
        this.debug = debug;
        if (debug) {
            System.out.println("--players: " + players);
            System.out.println("--deck: " + deck);
        }
    }

    public Round(List<Player> players, Deck deck) {
        this(players, deck, false);
    }

    public int getPot() {
        return pot;
    }

    private void bettingRound(int bid) {
        int call = 0;
        int index = 0;
        Player highBidder = null;
        Player currentPlayer = null;
        do {
            currentPlayer = players.get(index);
            if (highBidder != null && currentPlayer.equals(highBidder)) {
                break;
            }
            call = currentPlayer.firstBettingRound(call);
            if (call < 0) {
                if (debug) {System.out.println("--fold: " + pot);}
                players.remove(currentPlayer); // player folded
                continue;
            }
            if (debug) {System.out.println("--call: " + call + ": " + pot);}
            pot += call;
            currentPlayer.spendCash(call);
            if (call > bid || highBidder == null) {
                highBidder = currentPlayer;
                bid = call;
            }
            index = (index + 1) % players.size(); // if everyone folds this throws a divide by 0 error
        } while (true); // everyone's favorite
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
            if (ante >= 0) { 
                // Deal out hands to players
                for (int i = 0; i < player.getHandSize(); i++) {
                    player.drawCard(deck.drawCard());
                }
            } else {
                players.remove(player); // Player folded (or i guess in this case decided to sit out or something)
            }
        }
        // First round of Betting -- continues until no one raises
        int bid = anteAmount; // assiming you have to start a bid with the same amount as the ante -- perhaps change this later
        bettingRound(bid);
        // Swap phase -- Players can choose to replace cards from their hands with ones from the deck
        for (Player player : players) {
            List<Card> cards = player.swapCards();
            if (debug) {System.out.println("--swap: " + cards);}
            for (Card card : cards) {
                player.discardCard(card);
                player.drawCard(deck.drawCard());
            }
        }
        // Second round of Betting -- works just like the first
        bettingRound(bid);
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
            // if the hands are equal, the player who was going first in the round gets the win. Should be rare enough to let me put off dealing with ties for now.
        }
        winner.winCash(pot);
        return winner;
    }
}
