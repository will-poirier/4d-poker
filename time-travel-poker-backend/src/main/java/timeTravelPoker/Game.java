package timeTravelPoker;

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
            System.out.println(currentRound.evaluate() + " Wins!"); // do something with that info maybe idk
        }
        // if that hand caused another limbo round to not have any blanks in it any more, then we should evaluate it
        for (Round round : limboRounds) {
            if (!round.inLimbo()) {
                System.out.println(round.evaluate() + " Wins (in the past :O)!"); // maybe do something with this as well
            }
        }

        // now replace every player with a copy that has the same money (and pocket, if applicable)
        List<Player> newPlayers = new LinkedList<>();
        for (int i = 0; i < players.size(); i++) {
            Player oldPlayer = players.get(i);
            Player newPlayer = oldPlayer.copy();
            newPlayers.add(newPlayer);
        }
        players = newPlayers; // reference types babee
        // This seems quite weird -- why not just reset the players' hands instead of making entirely new players?
        // It's because, since we're time-travelling, we need an unmodified record of any rounds in "limbo," including those players and their hands.
        // This does make giving the player in the future the winnings from the past more complicated...
        // TODO: rework winnings (or maybe TimeTraveler.copy()) to allow funds to go back from the past to the present

        deck = new Deck(); // reset the deck as well
    }
}