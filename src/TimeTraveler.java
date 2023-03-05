import java.util.Iterator;

public abstract class TimeTraveler extends Player {
    protected CardGroup pocket;
    
    public TimeTraveler(int startingCash, CardGroup hand, CardGroup pocket) {
        super(startingCash, hand);
        this.pocket = pocket;
    }

    public void addCardToPocket(Card card) {
        if (!isPocketFull()) {
            pocket.addCard(card);
        } else {
            throw new IndexOutOfBoundsException("The pocket was already full -- you have to swap a card");
        }
    }

    public void swapCardInPocket(Card cardIn, EntangledCard cardOut) {
        pocket.removeCard(cardOut);
        cardOut.replaceInPast(cardIn);
    }

    public void swapCardInPocket(Card cardIn, int indexOut) {
        EntangledCard cardOut = (EntangledCard)(pocket.getCard(indexOut));
        pocket.removeCard(cardOut);
        cardOut.replaceInPast(cardIn);
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
        return super.toString() + " {" + pocket.toString() + "}";
    }

    public boolean hasBlanks() {
        for (Card card : hand) {
            if (card instanceof BlankCard) {
                return true;
            }
        }
        return false;
    }
}
