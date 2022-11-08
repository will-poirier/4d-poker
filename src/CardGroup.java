import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class CardGroup implements Iterable<Card> {
    private int size; // Also used as an index in cards (referencing the first unfilled value in cards)
    private int maxSize;
    private Card[] cards;
    private static Random random = new Random();

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
            int index = -1;
            for (int i = 0; i < cards.length; i++) {
                Card next = cards[i];
                if (next.equals(card)) {
                    index = i;
                    break;
                }
            }
            if (index > -1) {
                cards[index] = cards[size - 1];
                cards[size - 1] = null;
                size--;
            }
        }
    }

    public Card removeCard(int index) {
        Card card = getCard(index);
        removeCard(card);
        return card;
    }

    public Card getCard(int index) {
        return cards[index];
    }

    public List<Card> getCardsWithValue(int value) {
        List<Card> list = new LinkedList<>();
        for (Card card : cards) {
            if (card.getValue() == value) {
                list.add(card);
            }
        }
        return list;
    }

    public List<Card> getCardsWithSuit(char suit) {
        List<Card> list = new LinkedList<>();
        for (Card card : cards) {
            if (card.getSuit() == suit) {
                list.add(card);
            }
        }
        return list;
    }

    public void shuffleCards() {
        // A simple swap-shuffle method.  May not be perfectly shuffled, but should be at least random-ish.
        for (int i = 0; i < cards.length; i++) {
            int randIndex = random.nextInt(size);
            Card tempCard = cards[i];
            cards[i] = cards[randIndex];
            cards[randIndex] = tempCard;
        }
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

    public void sortCards() {
        // time to get weird
        // hell yeah O(n + nlogn) babee
        // thats not how big O works but oh well
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
