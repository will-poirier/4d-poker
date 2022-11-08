import java.util.Iterator;

public abstract class TimeTraveler extends Player {
    private CardGroup pocket;

    /*
    gee whiz this is more complicated than I expected
    because I have to keep rounds in limbo with blank cards
    So I need:
    D - Blank Cards to fill the pocket with at base
    D - A game that can keep hands in limbo until they have no blank cards left
    - A way to get cards into those limbo hands
    I kind of need to do a lot of refactoring ... :(
    - Probably remake scores to not run into signed-long and just use like an array or a small inner class or something
    */
    
    public TimeTraveler(int startingCash, Hand hand, String name, CardGroup pocket) {
        super(startingCash, hand, name);
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

    public void sortPocket() {
        pocket.sortCards();
    }

    @Override
    public String toString() {
        return super.toString() + "   Pocket: " + pocket.toString();
    }
}
