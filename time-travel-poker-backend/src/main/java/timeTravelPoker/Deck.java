package timeTravelPoker;

public class Deck extends CardGroup {
    
    public Deck() {
        this(Value.values().length * Suit.values().length);
        for (Value value : Value.values()) {
            for (Suit suit : Suit.values()) {
                addCard(new Card(value, suit));
            }
        }
    }

    public Deck(int size) {
        super(size);
    }

    public Card drawCard() {
        Card card = this.getCard(this.getSize() - 1);
        this.removeCard(card);
        return card;
    }

    public void shuffle() {
        // A simple swap-shuffle method.  May not be perfectly shuffled, but should be at least random-ish.
        for (int i = 0; i < cards.length; i++) {
            int randIndex = random.nextInt(size);
            Card tempCard = cards[i];
            cards[i] = cards[randIndex];
            cards[randIndex] = tempCard;
        }
    }
    
}
