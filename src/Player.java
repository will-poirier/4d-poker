import java.util.List;

public abstract class Player {
    protected int money;
    protected CardGroup hand;

    public Player(int startingCash, CardGroup hand) {
        this.money = startingCash;
        this.hand = hand;
    }
    public Player(Player player) {
        this.money = player.money;
        this.hand = player.hand;
    }

    public int getMoney() {
        return money;
    }

    public void drawCard(Card card) {
        hand.addCard(card);
    }

    public void gainCards(Card[] cards) {
        hand.addCards(cards);
    }

    public void winCash(int amount) {
        money += amount;
    }

    public void spendCash(int amount) {
        money -= amount;
    }

    public boolean isBankrupt() {
        return money <= 0;
    }

    public void discardCard(Card card) {
        hand.removeCard(card);
    }

    @Override
    public String toString() {
        return ("[$" + money + " " + hand.toString() + "]");
    }

    public int getHandSize() {
        return hand.getMaxSize();
    }

    public Card getHandCard(int index) {
        return hand.getCard(index);
    }

    public void sortHand() {
        hand.sortCards();
    }

    @Override
    public int hashCode() {
        return hand.hashCode() + (money * hand.getMaxSize() * 52); // this way, not only are players unique, but their hash losslessly encodes all of the info about them
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player other = (Player)(obj);
            return other.hand.equals(this.hand);
        } else {
            return false;
        }
    }

    public abstract Player copy();

    public abstract int ante(int anteAmount);
    public abstract int firstBettingRound(int currentCall);
    public abstract int secondBettingRound(int currentCall);
    public abstract List<Card> swapCards();

    public Score handValue() {
        return Score.scoreHand(hand);
    }
}
