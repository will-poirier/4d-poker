public class Deck extends CardGroup {
    
    public Deck() {
        super(52, 2, 14, null);
    }

    public Deck(int size, int minValue, int maxValue, char[] suits) {
        super(size, minValue, maxValue, suits);
    }
    
}
