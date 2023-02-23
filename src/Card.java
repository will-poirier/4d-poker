public class Card implements Comparable<Card>{
    private int value;
    private char suit;

    public Card(int value, char suit) {
        this.value = value;
        this.suit = suit;
    }
    /**
     * Creates a new blank card, with garbage values for its Value and Suit
     */
    public Card() {
        this(0, ' ');
    }

    public int getValue() {
        return value;
    }

    public char getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        TextColor color = TextColor.DEFAULT;
        switch (suit) {
            case 'C':
                color = TextColor.BLUE;
                break;
            case 'D':
                color = TextColor.PURPLE;
                break;
            case 'H':
                color = TextColor.RED;
                break;
            case 'S':
                color = TextColor.GREEN;
                break;
        }
        return String.format("[%02d " + color + "%c" + TextColor.DEFAULT + "]", value, suit);
    }

    @Override
    public int compareTo(Card other) {
        if (!(value == other.getValue())) {
            return value - other.getValue();
        } else {
            return suit - other.getSuit();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card other = (Card)(obj);
            return ((value == other.getValue()) && (suit == other.getSuit()));
        } else {
            return false;
        }
    }
    
}
