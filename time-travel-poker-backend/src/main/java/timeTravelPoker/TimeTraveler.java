package src.main.java.timeTravelPoker;

import java.util.Iterator;

public abstract class TimeTraveler extends Player {
    protected CardGroup pocket;
    
    public TimeTraveler(String name, int startingCash, CardGroup hand, CardGroup pocket) {
        super(name, startingCash, hand);
        this.pocket = pocket;
    }

    public void addCardToPocket(Card card) {
        if (!isPocketFull()) {
            pocket.addCard(card);
            hand.removeCard(card);
            hand.addCard(new BlankCard(card));
        } else {
            throw new IndexOutOfBoundsException("The pocket was already full -- you have to swap a card");
        }
    }

    public void swapCardInPocket(Card cardIn, EntangledCard cardOut) {
        pocket.removeCard(cardOut);
        cardOut.replaceInPast(cardIn);
        hand.removeCard(cardIn);
        hand.addCard(cardOut);
    }

    public void swapCardInPocket(Card cardIn, int indexOut) {
        EntangledCard cardOut = (EntangledCard)(pocket.getCard(indexOut));
        swapCardInPocket(cardIn, cardOut);
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

    @Override
    public int hashCode() {
        return super.hashCode() + (pocket.hashCode() * (hand.getMaxSize() * 52)); // keeping the theme of hash codes encoding all relevant information about a player
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
