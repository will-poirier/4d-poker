package timeTravelPoker;

public class Card implements Comparable<Card>{
    private Value value;
    private Suit suit;

    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    /**
     * Copy Constructor
     * @param other the Card to copy
     */
    public Card(Card other) {
        this.value = other.value;
        this.suit = other.suit;
    }

    public Value getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return "[" + value.toString() + " " + suit.toString() + "]";
    }

    @Override
    public int hashCode() {
        int code = value.getValue() - 2; // that way 2's are represented as 0's for minor efficiency's sake
        switch (suit) {
            case CLUB:
                code += 0;
                break;
            case DIAMOND:
                code += 13;
                break;
            case HEART:
                code += 13 * 2;
                break;
            case SPADE:
                code += 13 * 3;
                break;
        }
        return code;
    }

    @Override
    public int compareTo(Card other) {
        if (!(value == other.getValue())) {
            return value.compareValue(other.getValue());
        } else {
            return suit.compareTo(other.getSuit());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card other = (Card)(obj);
            return ((value == other.getValue()) && (suit == other.getSuit())); // this is leveraged for pocket swappage;
        } else {
            return false;
        }
    }
}
