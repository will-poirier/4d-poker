package timeTravelPoker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestCard {
    @Test
    public void testBasics() {
        // tests Constructor and getters
        Card card = new Card(Value.THREE, Suit.CLUB);
        assertEquals(card.getValue(), Value.THREE);
        assertEquals(card.getSuit(), Suit.CLUB);
    }

    @Test
    public void testToString() {
        Card card = new Card(Value.SEVEN, Suit.HEART);
        String expected = "[" + Value.SEVEN.toString() + " " + Suit.HEART.toString() + "]";
        assertEquals(card.toString(), expected);
    }

    @Test
    public void testEqualsFails() {
        Card card1 = new Card(Value.SEVEN, Suit.HEART);
        Card card2 = new Card(Value.THREE, Suit.DIAMOND);
        Card card3 = new Card(Value.THREE, Suit.HEART);
        Card card4 = new Card(Value.SEVEN, Suit.DIAMOND);

        assertFalse(card1.equals(card2));
        assertFalse(card2.equals(card3));
        assertFalse(card3.equals(card4));
        assertFalse(card4.equals(card1));
        assertFalse(card2.equals(card4));
        assertFalse(card1.equals(card3));
        assertFalse(card1.equals("String"));
    }

    @Test
    public void testEqualsSucceeds() {
        Card card1 = new Card(Value.FIVE, Suit.SPADE);
        Card card2 = new Card(Value.FIVE, Suit.SPADE);

        assertTrue(card1.equals(card2));
    }

    @Test
    public void testCopyConstructor() {
        Card card1 = new Card(Value.ACE, Suit.HEART);
        Card card2 = new Card(card1);

        assertEquals(card2.getSuit(), card1.getSuit());
        assertEquals(card2.getValue(), card1.getValue());
    }

    @Test
    public void testCompare() {
        Card card1 = new Card(Value.TWO, Suit.CLUB);
        Card card2 = new Card(Value.ACE, Suit.DIAMOND);
        Card card3 = new Card(Value.JACK, Suit.SPADE);
        Card card4 = new Card(Value.JACK, Suit.HEART);

        assertTrue(card3.compareTo(card1) > 0);
        assertTrue(card3.compareTo(card2) < 0);
        // assertTrue(card3.compareTo(card4) == 0);
    }

    @Test
    public void testHashCode() {
        Card card = new Card(Value.KING, Suit.HEART);
        int hash = card.hashCode();
        Card card2 = new Card(Value.FOUR, Suit.SPADE);
        int hash2 = card2.hashCode();
        int hashAgain = card.hashCode();

        assertEquals(hash, hashAgain);
        assertNotEquals(hash2, hash);
        assertNotEquals(hash2, hashAgain);
    }
}
