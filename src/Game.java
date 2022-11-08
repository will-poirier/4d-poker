import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {
    private Deck deck;
    private Player[] players;
    private int pot;
    private int startingPlayer;
    private List<LimboGame> limbo;

    public Game(int playerCount, int startingCash, String humanName) {
        this(new Deck(), new Player[playerCount]);
        // Setup default players: 1 human and the rest bots
        players[0] = new CLIHumanPlayer(startingCash, humanName);
        for (int i = 1; i < players.length; i++) {
            players[i] = new RandomPlayer(startingCash);
        }
    }

    public Game(Deck deck, Player[] players) {
        this.deck = deck;
        this.players = players;
        pot = 0;
        startingPlayer = 0;
        this.limbo = new ArrayList<>();
    }

    public void shuffleDeck() {
        deck.shuffleCards();
    }

    public void startRound(int ante) {
        pot = 0;
        // 2 loops because order matters
        for (int i = startingPlayer; i < players.length; i++) {
            Player player = players[i];
            if (!player.isBankrupt() && !player.hasFolded()) {
                int in = player.ante(ante);
                if (in > 0) {
                    pot += in;
                    for (int j = 0; j < 5; j++) {
                        player.drawCard(deck.drawCard());
                    }
                }
            }
        }
        for (int i = 0; i < startingPlayer; i++) {
            Player player = players[i];
            if (!player.isBankrupt() && !player.hasFolded()) {
                int in = player.ante(ante);
                if (in > 0) {
                    pot += in;
                    for (int j = 0; j < 5; j++) {
                        player.drawCard(deck.drawCard());
                    }
                }
            }
        }
    }

    public void bettingPhase() {
        int currentBet = 0;
        int oldBet = -1;
        while (currentBet > oldBet) {
            oldBet = currentBet;
            for (int i = startingPlayer; i < players.length; i++) {
                Player player = players[i];
                if (!player.isBankrupt() && !player.hasFolded()) {
                    int newBet = player.bet(currentBet);
                    if (newBet > currentBet) {
                        currentBet = newBet;
                    }
                    pot += newBet;
                }
            }
            for (int i = 0; i < startingPlayer; i++) {
                Player player = players[i];
                if (!player.isBankrupt() && !player.hasFolded()) {
                    int newBet = player.bet(currentBet);
                    if (newBet > currentBet) {
                        currentBet = newBet;
                    }
                    pot += newBet;
                }
            }
        }
    }

    public void SwappingPhase() {
        List<Card> discard = new LinkedList<>();
        for (int i = startingPlayer; i < players.length; i++) {
            Player player = players[i];
            if (!player.isBankrupt() && !player.hasFolded()) {
                List<Card> trash = player.swapCards();
                for (Card x : trash) {
                    Card card = deck.drawCard();
                    player.drawCard(card);
                    discard.add(x);
                }
            }
        }
        for (int i = 0; i < startingPlayer; i++) {
            Player player = players[i];
            if (!player.isBankrupt() && !player.hasFolded()) {
                List<Card> trash = player.swapCards();
                for (Card x : trash) {
                    Card card = deck.drawCard();
                    player.drawCard(card);
                    discard.add(x);
                }
            }
        }
        for (Card card : discard) {
            deck.addCard(card);
        }
        shuffleDeck();
    }

    private class LimboGame {
        private Map<Player, Hand> data;
        private int floatingPot;

        private LimboGame() {
            for (Player player : players) {
                Hand hand = new Hand(player.getHandSize());
                for (int i = 0; i < hand.getMaxSize(); i++) {
                    hand.addCard(player.getHandCard(i));
                }
                data.put(player, hand);
            }
            this.floatingPot = pot;
        }

        private boolean hasBlanks() {
            for (Player player : data.keySet()) {
                if (player.hasBlanks()) {
                    return true;
                }
            }
            return false;
        }

        private Player[] getPlayers() {
            Player[] playerArray = new Player[data.size()];
            int i = 0;
            for (Player player : data.keySet()) {
                playerArray[i] = new Player(0, data.get(player), player.getName()) {
                    @Override
                    public int ante(int anteAmount) { return 0; }
                    @Override
                    public int bet(int currentCall) { return 0; }
                    @Override
                    public List<Card> swapCards() { return null; }
                    @Override
                    public void winCash(int amount) {
                        for (Player realPlayer : players) {
                            if (this.equals(realPlayer)) {
                                realPlayer.winCash(amount);
                                break;
                            }
                        }
                    }
                };
            }
            return playerArray;
        }
    }

    public Player determineWinner() {
        return determineWinner(players);
    }

    public Player determineWinner(Player[] players) {
        long winningScore = 0;
        Player currentWinner = null;
        boolean inLimbo = false;
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            if (!player.isBankrupt() && !player.hasFolded()) {
                player.sortHand();
                if (!player.hasBlanks()) {
                    long score = player.handValue();
                    System.out.println(player);
                    if (Long.compareUnsigned(score, winningScore) > 0) { // this is the first time I've ever run up against int limits and signed long limits that's crazy
                        currentWinner = player;
                        winningScore = score;
                    }
                } else {
                    // we're gonna have to put this hand on hold until there are no blanks in it
                    // how the fuck we're gonna do that?  Good question.  Probably a data structure or inner class.
                    // inner class it is.  also yuck ew gross time travel is hard to do (who could've guessed?)
                    limbo.add(new LimboGame());
                    inLimbo = true;
                    break;
                }
            }
        }
        if (!inLimbo) {
            currentWinner.winCash(pot);
            startingPlayer++;
            if (startingPlayer >= players.length) {
                startingPlayer = 0;
            }
            return currentWinner;
        } else {
            // :(
            return null;
        }
    }

}
