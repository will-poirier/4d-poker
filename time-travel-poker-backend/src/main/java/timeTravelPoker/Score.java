package src.main.java.timeTravelPoker;

public class Score implements Comparable<Score>{
    private int specials;
    private int tieBreaker;

    public static Score scoreHand(CardGroup hand) {
        hand.sortCards();
        Score score = new Score();
        int highCard = hand.getCard(hand.getMaxSize() - 1).getValue().getValue(); // oh thats not good
        Card firstCard = hand.getCard(0); // for initializing purposes
        boolean flush = true;
        Suit flushSuit = firstCard.getSuit();
        boolean straight = true;
        int prevValue = firstCard.getValue().getValue() - 1; // to force the first straight check to pass
        int streak = 0;
        int streakValue = firstCard.getValue().getValue();
        int longestStreak = 0;
        int highestStreakValue = 0;
        int lowerStreakValue = 0;
        int numStreaks = 0;
        for (Card card : hand) {
            if (!(flushSuit == card.getSuit())) {
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

    public Score() {
        specials = 0b0;
        tieBreaker = 0;
    }

    public void setTieBreaker(int n) {
        tieBreaker = n;
    }

    public void addSpecial(int n) {
        specials += n;
    }
    
    @Override
    public int compareTo(Score o) {
        if (specials == o.specials) {
            return tieBreaker - o.tieBreaker;
        } else {
            return specials - o.specials;
        }
    }

    @Override
    public String toString() {
        return "[" + specials + ", " + tieBreaker + "]";
    }
}
