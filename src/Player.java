public abstract class Player {

    private int cash;
    private Hand hand;
    private boolean folded;

    public Player(int startingCash, Hand hand) {
        this.cash = startingCash;
        this.hand = hand;
        this.folded = false;
    }

    public int getCashRemaining() {
        return cash;
    }

    public Card discardCard() {
        return null;
    }

    public void drawCard(Card card) {
        hand.addCard(card);
    }

    public void gainCards(Card[] cards) {
        hand.addCards(cards);
    }

    public void winCash(int amount) {
        cash += amount;
    }

    public boolean isBankrupt() {
        return cash <= 0;
    }

    public boolean hasFolded() {
        return folded;
    }
    
    public int handValue() {
        // oh boy
        // TODO: handValue() because ew yuck gross
        return 0;
    }

    public void fold() {
        folded = true;
    }

    public int getHandSize() {
        return hand.getMaxSize();
    }

    public abstract int ante(int anteAmount);
    public abstract int bet(int currentCall);
    public abstract Card[] swapCards();
}
