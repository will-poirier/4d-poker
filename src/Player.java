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
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player other = (Player)(obj);
            return other.hand.equals(this.hand);
        } else {
            return false;
        }
    }

    public abstract int ante(int anteAmount);
    public abstract int firstBettingRound(int currentCall);
    public abstract int secondBettingRound(int currentCall);
    public abstract List<Card> swapCards();

    public Score handValue() {
        // now that I can sort my hand, some of this should be much easier
        // I'll assume that I will only call this for non-blank-containing hands
        // christ wtf
        hand.sortCards();
        Score score = new Score();
        int highCard = hand.getCard(hand.getMaxSize() - 1).getValue().getValue(); // oh thats not good
        Card firstCard = hand.getCard(0); // for initializing purposes
        boolean flush = true;
        char flushSuit = firstCard.getSuit().getSymbol().charAt(0);
        boolean straight = true;
        int prevValue = firstCard.getValue().getValue() - 1; // to force the first straight check to pass
        int streak = 0;
        int streakValue = firstCard.getValue().getValue();
        int longestStreak = 0;
        int highestStreakValue = 0;
        int lowerStreakValue = 0;
        int numStreaks = 0;
        for (Card card : hand) {
            if (!(flushSuit == card.getSuit().getSymbol().charAt(0))) {
                flush = false;
            }
            if (!(prevValue + 1 == card.getValue().getValue())) {
                straight = false;
            }
            if (streakValue == card.getValue().getValue()) {
                streak++;
            } else {
                if (streakValue > highestStreakValue) {
                    highestStreakValue = streakValue;
                } else {
                    lowerStreakValue = streakValue;
                }
                streakValue = card.getValue().getValue();
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
