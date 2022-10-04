import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class CardGroup {
    private int size; // Also used as an index in cards (referencing the first unfilled value in cards)
    private int maxSize;
    private Card[] cards;
    private static Random random = new Random();
    private static final char[] DEFAULT_SUITS = {'S', 'D', 'C', 'H'};

    public CardGroup(int size, int minValue, int maxValue, char[] suits) {
        this.size = 0; // confusing but it makes sense to the caller (i think :p)
        this.maxSize = size;
        this.cards = new Card[maxSize];
        if (suits == null) {
            suits = DEFAULT_SUITS;
        }
        int numSuits = suits.length;
        if ((maxValue - minValue) * numSuits == size) {
            // easy; just loop through maxValue and suits
            for (int i = minValue; i <= maxValue; i++) {
                for (int j = 0; j < numSuits; j++) {
                    int index = (numSuits * (i - minValue)) + j;
                    this.cards[index] = new CardImp(i, suits[j]);
                }
            }
        } else {
            // yuck gross ew stop that
            System.out.println("Ew gross why stop it yuck");
        }
    }

    public void addCard(Card card) {
        if (size < maxSize) {
            cards[size] = card;
            size++;
        }
    }

    public void removeCard(Card card) {
        if (size > 0) {
            cards[size - 1] = null;
            size--;
        }
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
}
