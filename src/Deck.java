public class Deck implements CardGroup {

    private int size;
    private int maxSize;
    private Card[] cards;

    public Deck() {
        this(52, 2, 14, 4);
    }

    public Deck(int size, int minValue, int maxValue, int numSuits) {
        // TODO Create deck for n cards with parameters
    }

    @Override
    public int getSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void addCard(Card card) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeCard(Card card) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Card getCard(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Card[] getCardsWithValue(int value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Card[] getCardsWithSuit(char suit) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void shuffleCards() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getMaxSize() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
