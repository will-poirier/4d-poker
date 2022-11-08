public class CardImp implements Card {
    private int value;
    private char suit;
    private boolean blank;

    public CardImp(int value, char suit, boolean blank) {
        this.value = value;
        this.suit = suit;
        this.blank = blank;
    }
    /**
     * Creates a new blank card, with garbage values for its Value and Suit
     */
    public CardImp() {
        this(0, ' ', true);
    }
    public CardImp(int value, char suit) {
        this(value, suit, false);
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public char getSuit() {
        return suit;
    }

    @Override
    public boolean isBlank() {
        return blank;
    }

    @Override
    public String toString() {
        if (blank) {
            return "[XX X]";
        }
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
        if (blank || other.isBlank()) {
            return 0; // blank cards, in theory, should not care about what their value or suit is at all
        }
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
            return ((value == other.getValue()) && (suit == other.getSuit())) || blank == other.isBlank();
        } else {
            return false;
        }
    }
    
}
