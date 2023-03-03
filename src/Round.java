import java.util.List;

public class Round {
    private List<Player> players;
    private Deck deck;
    private int pot;

    public Round(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;
        pot = 0;
    }

    public int getPot() {
        return pot;
    }

    public void play(int anteAmount) {
        // Buy-in / Ante
        deck.shuffle();
        for (Player player : players) {
            int ante = player.ante(anteAmount);
            pot += ante;
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
        int call = 0;
        int oldCall;
        do {
            oldCall = call;
            for (Player player : players) {
                call = player.firstBettingRound(call);
                if (call < 0) {
                    players.remove(player); // player folded
                    continue;
                }
                pot += call; // uh oh that's probably not right
            }
        } while (call != oldCall);
        // Swap phase -- Players can choose to replace cards from their hands with ones from the deck
        for (Player player : players) {
            List<Card> cards = player.swapCards();
            for (Card card : cards) {
                player.discardCard(card);
                player.drawCard(deck.drawCard());
            }
        }
        // Second round of Betting -- works just like the first
        call = 0;
        do {
            oldCall = call;
            for (Player player : players) {
                call = player.firstBettingRound(call);
                if (call < 0) {
                    players.remove(player); // player folded
                    continue;
                }
                pot += call;
            }
        } while (call != oldCall);
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
                winner = player;
                continue;
            }
            // if the hands are equal, the player who was going first in the round gets the win. Should be rare enough to let me put off dealing with ties for now.
        }
        winner.winCash(pot);
        return winner;
    }
}
