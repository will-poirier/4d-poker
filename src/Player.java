import java.util.List;

public abstract class Player {
    private String name;
    private int cash;
    private Hand hand;
    private boolean folded;

    public Player(int startingCash, Hand hand, String name) {
        this.cash = startingCash;
        this.hand = hand;
        this.folded = false;
        this.name = name;
    }
    public Player(Player player) {
        this.cash = player.cash;
        this.hand = player.hand;
        this.folded = player.folded;
        this.name = player.name;
    }

    public String getName() {
        return name;
    }

    public int getCashRemaining() {
        return cash;
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

    public int spendCash(int amount) {
        cash -= amount;
        return amount;
    }

    public boolean isBankrupt() {
        return cash <= 0;
    }

    public boolean hasFolded() {
        return folded;
    }

    public void discardCard(Card card) {
        hand.removeCard(card);
    }

    @Override
    public String toString() {
        return (cash + " " + hand.toString());
    }

    public void fold() {
        folded = true;
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
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player other = (Player)(obj);
            return name.equals(other.name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public abstract int ante(int anteAmount);
    public abstract int bet(int currentCall);
    public abstract List<Card> swapCards();

    public Score handValue() {
        // now that I can sort my hand, some of this should be much easier
        // I'll assume that I will only call this for non-blank-containing hands
        hand.sortCards();
        Score score = new Score();
        int highCard = hand.getCard(hand.getMaxSize() - 1).getValue();
        Card firstCard = hand.getCard(0); // for initializing purposes
        boolean flush = true;
        char flushSuit = firstCard.getSuit();
        boolean straight = true;
        int prevValue = firstCard.getValue() - 1; // to force the first straight check to pass
        int streak = 0;
        int streakValue = firstCard.getValue();
        int longestStreak = 0;
        int highestStreakValue = 0;
        int lowerStreakValue = 0;
        int numStreaks = 0;
        for (Card card : hand) {
            if (!(flushSuit == card.getSuit())) {
                flush = false;
            }
            if (!(prevValue + 1 == card.getValue())) {
                straight = false;
            }
            if (streakValue == card.getValue()) {
                streak++;
            } else {
                if (streakValue > highestStreakValue) {
                    highestStreakValue = streakValue;
                } else {
                    lowerStreakValue = streakValue;
                }
                streakValue = card.getValue();
                if (streak > 1) {
                    numStreaks++;
                    if (streak > longestStreak) {
                        longestStreak = streak;
                    }
                }
                streak = 1;
            }
        }
        if (flush && straight && highCard == 14) {
            score.addSpecial(0b100000000);
            score.setTieBreaker(highCard);
        } else if (flush && straight) {
            score.addSpecial(0b010000000);
            score.setTieBreaker(highCard);
        } else if (flush) {
            score.addSpecial(0b000010000);
            score.setTieBreaker(highCard);
        } else if (straight) {
            score.addSpecial(0b000001000);
            score.setTieBreaker(highCard);
        }
        if (numStreaks > 0) {
            // pair, two-pair, three-of-a-kind, full house, and four-of-a-kind
            // this is where tieBreaker is gonna have to encode some goofy information
            // now it's base 15 since I'm not needing to be optimal about storage, since I'm only gonna need 15^3 instead of 14^10 or 15^10
            if (longestStreak >= 4) {
                // four-of-a-kind
                score.addSpecial(0b001000000);
                score.setTieBreaker(highCard + 15 * highestStreakValue);
            } else if (longestStreak == 3) {
                if (numStreaks == 2) {
                    // full house
                    score.addSpecial(0b000100000);
                    score.setTieBreaker(highCard + 15 * lowerStreakValue + 15 * 15 * highestStreakValue);
                } else {
                    // three-of-a-kind
                    score.addSpecial(0b000000100);
                    score.setTieBreaker(highCard + 15 * highestStreakValue);
                }
            } else {
                if (numStreaks == 2) {
                    //two-pair
                    score.addSpecial(0b000000010);
                    score.setTieBreaker(highCard + 15 * lowerStreakValue + 15 * 15 * highestStreakValue);
                } else {
                    // pair
                    score.addSpecial(0b000000001);
                    score.setTieBreaker(highCard + 15 * highestStreakValue);
                }
            }
        }
        return score;
    }
}
