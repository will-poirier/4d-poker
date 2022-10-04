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
    
}
