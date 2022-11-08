import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public abstract int ante(int anteAmount);
    public abstract int bet(int currentCall);
    public abstract List<Card> swapCards();



    public long handValue() {
        // Okay gonna do some base-14 tomfoolery
        // The first digit is (high_card - 1)
        // The other n digits are for the special things (pairs/full house/straight/etc.)
        // Where 0 means they don't have it, and anything else is they have it with the digit as the high card
        long score = 0;
        // High Card
        int highCard = 0;
        for (int i = 0; i < hand.getSize(); i++) {
            Card card = hand.getCard(i);
            if (card.getValue() > highCard) {
                highCard = card.getValue();
            }
        }
        score += (highCard - 1);
        // pair, 2 pair, and many-of-a-kind: acquiring candidates (2 or more in the hand)
        int[] pairs = new int[2];
        for (Card card1 : hand) {
            for (Card card2 : hand) {
                if (card1 != card2 && card1.getValue() == card2.getValue()) {
                    // make sure I'm not overcounting triples
                    if (card1.getValue() != pairs[0] && card1.getValue() != pairs[1]) {
                        if (pairs[0] != 0) {
                            pairs[1] = card1.getValue();
                            // both pairs found, no need to keep looping
                            break; // well really I should set a flag and double break but oh well
                        } else {
                            pairs[0] = card1.getValue();
                        }
                    }
                }
            }
        }
        // pair, 2 pair, and many-of-a-kind: valuing said candidates
        for (int i = 0; i < pairs.length; i++) {
            int pairValue = pairs[i];
            if (pairValue == 0) { continue; }
            int count = 0;
            for (Card card : hand) {
                if (card.getValue() == pairValue) {
                    count++;
                }
            }
            switch (count) {
                case 2:
                    // check for full-house
                    if (score > (14 * 14) - 1) { // (14 * 14 * 14) - 1 is the highest value a pair can be, which
                        // since the only things that were added to score so far are high-card and 3- or 4-of-a-kind, a score > 13 * 14 * 14 means a full house here
                        // full house is the 7th weakest hand, so it gets the 7th digit in the base-14 number
                        // BUT it also gets the 2nd digit as the pair; and it already has the 4th digit but we can keep that it won't cause any problems
                        // and actually having that 4th digit makes reverse-engineering the value easier
                        int fullHouseValue = pairs[(i == 0 ? 1 : 0)];
                        score += (int)((fullHouseValue - 1) * Math.pow(14, 6));
                    } else if (score > 13) {
                        // two pair
                        int otherPairValue = pairs[(i == 0 ? 1 : 0)];
                        if (pairValue > otherPairValue) {
                            score += (int)((pairValue - 1) * Math.pow(14, 2));
                            break;
                        } else {
                            score -= (int)((otherPairValue - 1) * Math.pow(14, 1));
                            score += (int)((otherPairValue - 1) * Math.pow(14, 2));
                        }
                    }
                    // a pair also gets points in here, regardless of what else is happening
                    score += (int)((pairValue - 1) * Math.pow(14, 1));
                    break;
                case 3:
                    // 3 of a kind is the 4th weakest hand, so it gets the 4th digit in the base-14 number
                    score += (int)((pairValue - 1) * Math.pow(14, 3));
                    break;
                case 4:
                    // 4 of a kind is the 8th weakest hand, so it gets the 8th digit in the base-14 number
                    score += ((pairValue - 1) * Math.pow(14, 7));
                    break;
                default:
                    // nothing special; do nothing
                    // in theory, should never actually happen anyways
                    break;
            }
        }
        // flush
        boolean flush = true;
        char suit = ' ';
        for (Card card : hand) {
            char currentSuit = card.getSuit();
            if (suit == ' ') {
                suit = currentSuit;
            }
            if (currentSuit != suit) {
                // no flush here
                flush = false;
                break;
            }
        }
        if (flush) {
            // A flush is the 6th weakest hand, so it gets the 6th digit in this base-14 number
            score += (long)((highCard - 1) * Math.pow(14, 5));
        }
        // straight
        List<Integer> valueList = new ArrayList<>(5);
        for (int i = 0; i < hand.getSize(); i++) {
            Card card = hand.getCard(i);
            valueList.add(card.getValue());
        }
        valueList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1; // this feels dumb.  anyways
            }
            
        });
        boolean straight = true;
        for (int i = 0; i < valueList.size() - 1; i++) {
            if (valueList.get(i) != valueList.get(i + 1) + 1) {
                straight = false;
                break;
            }
        }
        if (straight) {
            // A straight is the 5th weakest hand, so it gets the 5th digit in this base-14 number
            score += (long)((highCard - 1) * Math.pow(14, 4));
        }
        // Straight Flush and Royal Flush
        if (straight && flush) {
            if (highCard == 14) {
                // A royal flush is the 10th weakest hand (and the strongest), so it gets the 10th digit in the base-14 number
                // [TECHNICALLY a royal flush doesn't need a special hand since it'll beat all other straight flushes w/ highcard but like y'know]
                score += (long)((highCard - 1) * Math.pow(14, 9));
            } else {
                // A straight flush is the 9th weakest hand, so it gets the 9th digit in this base-14 number
                score += (long)((highCard - 1) * Math.pow(14, 8));
            }
        }
        // that SHOULD be every hand accounted for, in such a way that hands that beat each other will always beat each other
        // thanks to the power of alternative bases :)
        return score;
    }
}
