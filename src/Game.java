import java.util.LinkedList;
import java.util.List;

public class Game {
    private List<Player> players;
    private Deck deck;
    private List<Round> limboRounds;

    public Game() {
        players = new LinkedList<>();
        limboRounds = new LinkedList<>();
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void playRound() {
        playRound(false);
    }

    public void playRound(boolean debug) {
        Round currentRound = new Round(players, deck, debug);
        currentRound.play(5); // 5 is a placeholder idk how you're supposed to 'calc' ante
        if (currentRound.inLimbo()) {
            limboRounds.add(currentRound);
        } else {
            currentRound.evaluate(); // do something with that info maybe idk
        }
        // if that hand caused another limbo round to not have any blanks in it any more, then we should evaluate it
        for (Round round : limboRounds) {
            if (!round.inLimbo()) {
                round.evaluate(); // maybe do something with this as well
            }
        }
    }
}