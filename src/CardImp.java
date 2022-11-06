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
    
}
