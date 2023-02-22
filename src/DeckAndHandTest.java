import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class DeckAndHandTest {
    // Combined because they are quite similar, conceptually, and extend the same abstract class
    @Test
    public void testConstructionAndGetters() {
        Deck deck = new Deck();
        Hand hand = new Hand(5);
        assertEquals(deck.getMaxSize(), 52);
        assertEquals(hand.getMaxSize(), 5);
        assertEquals(deck.getSize(), 52);
        assertEquals(hand.getSize(), 0);
        System.out.println(deck);
    }

    @Test
    public void testAddCard() {
        // Assuming that if it works for Hand, it works for Deck, since they use the same method from CardGroup
        Hand hand = new Hand(5);
        Card card = new CardImp(6, 'D');
        hand.addCard(card);
        assertEquals(hand.getSize(), 1);
        assertEquals(hand.getCard(0), card);
    }

    @Test
    public void testRemoveCard() {
        Deck deck = new Deck();
        deck.removeCard(new CardImp(12, 'C'));
        assertEquals(deck.getSize(), 51);
    }

    @Test
    public void testGetCardsWithValue() {
        Deck deck = new Deck();
        List<Card> cards = deck.getCardsWithValue(11);
        assertEquals(cards.size(), 4); // assuming for now that these are the correct 4 cards
    }

    @Test
    public void testGetCardsWithSuit() {
        Deck deck = new Deck();
        List<Card> cards = deck.getCardsWithSuit('S');
        assertEquals(cards.size(), 13); // assuming for now that these are the correct 13 cards
    }

    @Test
    public void impreciseTestShuffle() {
        // Imprecise because no asserts, just a print to visually check if shuffle works well enough
        // A sneaky test of toStrings as well :D
        Deck deck = new Deck();
        System.out.println(deck);
        deck.shuffleCards();
        System.out.println(deck);
    }
}
