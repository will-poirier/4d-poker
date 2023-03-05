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
