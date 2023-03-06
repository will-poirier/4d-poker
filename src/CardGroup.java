import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CardGroup implements Iterable<Card> {
    protected int size; // Also used as an index in cards (referencing the first unfilled value in cards)
    protected int maxSize;
    protected Card[] cards;
    protected static Random random = new Random();

    public CardGroup(int size) {
        this.size = 0; // confusing but it makes sense to the caller (i think :p)
        this.maxSize = size;
        this.cards = new Card[maxSize];
    }

    public void addCard(Card card) {
        if (size < maxSize) {
            cards[size] = card;
            size++;
        }
    }

    public void addCards(Card[] newCards) {
        for (Card card : newCards) {
            addCard(card);
        }
    }

    public void removeCard(Card card) {
        if (size > 0) {
            for (int i = 0; i < cards.length; i++) {
                Card next = cards[i];
                if (next.equals(card)) {
                    removeCard(i);
                    return;
                }
            }
        }
    }

    public void removeCard(int index) {
        cards[index] = cards[size - 1];
        cards[size - 1] = null;
        size--;
    }

    public Card getCard(int index) {
        return cards[index];
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        String result = "";
        for (Card card : cards) {
            if (card == null) {continue;}
            result += card.toString() + " ";
        }
        return result;
    }

    @Override
    public Iterator<Card> iterator() {
        return new Iterator<Card>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }
            @Override
            public Card next() {
                return cards[index++];
            }
        };
    }

    @Override
    public int hashCode() {
        int code = 0;
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            if (card == null) { continue; } // implicit code += 0
            code += card.hashCode() + (i * 52);
        }
        return code;
    }

    public void sortCards() {
        List<Card> cardList = new ArrayList<>();
        for (Card card : cards) {
            cardList.add(card);
        }
        Collections.sort(cardList);
        for (int i = 0; i < cards.length; i++) {
            cards[i] = cardList.get(i);
        }
    }
}
