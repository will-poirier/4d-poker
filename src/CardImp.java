public class CardImp implements Card {

    private int value;
    private char suit;

    public CardImp(int value, char suit) {
        this.value = value;
        this.suit = suit;
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
    public String toString() {
        return String.format("[%02d %c]", value, suit);
    }

    @Override
    public int compareTo(Card other) {
        if (!(value == other.getValue())) {
            return value - other.getValue();
        } else {
            return suit - other.getSuit();
        }
    }
    
}
