public class Game {
    
    private Deck deck;
    private Player[] players;
    private int pot;
    private int startingPlayer;

    public Game(int playerCount) {
        this(new Deck(), new Player[playerCount]);
        // Setup default players: 1 human and the rest bots
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
                pot += player.ante(ante);
            }
        }
        for (int i = 0; i < startingPlayer; i++) {
            Player player = players[i];
            if (!player.isBankrupt() && !player.hasFolded()) {
                pot += player.ante(ante);
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
        for (int i = startingPlayer; i < players.length; i++) {
            Player player = players[i];
            if (!player.isBankrupt() && !player.hasFolded()) {
                Card[] trash = player.swapCards();
                for (Card x : trash) {
                    Card card = deck.drawCard();
                    player.drawCard(card);
                    deck.addCard(x);
                }
            }
        }
        for (int i = 0; i < startingPlayer; i++) {
            Player player = players[i];
            if (!player.isBankrupt() && !player.hasFolded()) {
                Card[] trash = player.swapCards();
                for (Card x : trash) {
                    Card card = deck.drawCard();
                    player.drawCard(card);
                    deck.addCard(x);
                }
            }
        }
    }

    public Player determineWinner() {
        int winningScore = 0;
        Player currentWinner = null;
        for (int i = 0; i < players.length; i++) {
            int score = players[i].handValue();
            if (score > winningScore) {
                currentWinner = players[i];
                winningScore = score;
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
