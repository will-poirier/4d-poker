import java.util.Iterator;

public abstract class TimeTraveler extends Player {
    private CardGroup pocket;
    
    public TimeTraveler(int startingCash, CardGroup hand, CardGroup pocket) {
        super(startingCash, hand);
        this.pocket = pocket;
    }

    public void addCardToPocket(Card card) {
        pocket.addCard(card);
    }

    public void takeCardFromPocket(Card card) {
        pocket.removeCard(card);
    }

    public void takeCardFromPocket(int index) {
        pocket.removeCard(index);
    }

    public void swapCardInPocket(Card cardIn, Card cardOut) {
        pocket.removeCard(cardOut);
        pocket.addCard(cardIn);
    }

    public void swapCardInPocket(Card cardIn, int indexOut) {
        pocket.removeCard(indexOut);
        pocket.addCard(cardIn);
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

    public void sortPocket() {
        pocket.sortCards();
    }

    @Override
    public String toString() {
        return super.toString() + "   Pocket: " + pocket.toString();
    }

    public boolean hasBlanks() {
        for (Card card : hand) {
            if (card instanceof EntangledCard) {
                return true;
            }
        }
        return false;
    }
}
