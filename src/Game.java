import java.util.LinkedList;
import java.util.List;

public class Game {
    
    private Deck deck;
    private Player[] players;
    private int pot;
    private int startingPlayer;

    public Game(int playerCount, int startingCash) {
        this(new Deck(), new Player[playerCount]);
        // Setup default players: 1 human and the rest bots
        players[0] = new CLIHumanPlayer(startingCash);
        for (int i = 1; i < players.length; i++) {
            players[i] = new CLIRandomPlayer(startingCash);
        }
    }

    public Game(Deck deck, Player[] players) {
        this.deck = deck;
        this.players = players;
        pot = 0;
        startingPlayer = 0;
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
                pot += in;
                if (in > 0) {
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
                pot += in;
                if (in > 0) {
                    for (int j = 0; j < 5; j++) {
                        player.drawCard(deck.drawCard());
                    }
                }
            }
        }
    }

    public void bettingPhase() {
        int currentBet = 0;
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

    public Player determineWinner() {
        long winningScore = 0;
        Player currentWinner = null;
        for (int i = 0; i < players.length; i++) {
            if (!players[i].isBankrupt() && !players[i].hasFolded()) {
                players[i].sortHand();
                long score = players[i].handValue();
                System.out.println(players[i]);
                if (Long.compareUnsigned(score, winningScore) > 0) { // this is the first time I've ever run up against int limits and signed long limits that's crazy
                    currentWinner = players[i];
                    winningScore = score;
                }
            }
        }
        currentWinner.winCash(pot);
        startingPlayer++;
        if (startingPlayer >= players.length) {
            startingPlayer = 0;
        }
        return currentWinner;
    }

}
