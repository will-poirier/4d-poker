import java.util.Iterator;

public abstract class TimeTraveler extends Player {
    private CardGroup pocket;
    
    public TimeTraveler(int startingCash, Hand hand, Hand pocket) {
        super(startingCash, hand);
        this.pocket = pocket;
    }

    public void addCardToPocket(Card card) {
        pocket.addCard(card);
    }

    public void takeCardFromPocket(Card card) {
        pocket.removeCard(card);
    }

    public Card takeCardFromPocket(int index) {
        Card card = pocket.getCard(index);
        pocket.removeCard(card);
        return card;
    }

    public void swapCardInPocket(Card cardIn, Card cardOut) {
        pocket.removeCard(cardOut);
        pocket.addCard(cardIn);
    }

    public Card swapCardInPocket(Card cardIn, int indexOut) {
        Card card = takeCardFromPocket(indexOut);
        pocket.addCard(cardIn);
        return card;
    }

    public boolean isPocketFull() {
        return pocket.getMaxSize() == pocket.getSize() + 1; // since Size is actually an index lmao
    }

    public int getPocketSize() {
        return pocket.getSize();
    }

    public Card getCardInPocket(int index) {
        return pocket.getCard(index);
    }

    public Iterator<Card> getPocketIterator() {
        return pocket.iterator();
    }
}
