import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class PlayerTest {
    private Player constructMockPlayer() {
        return new Player(10, new Hand(5)) {
            @Override
            public int ante(int anteAmount) {
                return 0;
            }
            @Override
            public int bet(int currentCall) {
                return 0;
            }
            @Override
            public Card[] swapCards() {
                return null;
            }
            
        };
    }

    @Test
    public void testConstructPlayer() {
        Player player = constructMockPlayer();
        assertEquals(10, player.getCashRemaining());
        assertEquals(5, player.getHandSize());
    }

    @Test
    public void testDrawCard() {
        Player player = constructMockPlayer();
        Card card = new CardImp(3, 'C');
        player.drawCard(card);
        assertEquals(card, player.getHandCard(0));
    }

    @Test
    public void testGainCards() {
        Player player = constructMockPlayer();
        Card[] cards = {new CardImp(4, 'H'), 
        new CardImp(8, 'D'), new CardImp(13, 'D')};
        player.gainCards(cards);
        for (int i = 0; i < cards.length; i++) {
            assertEquals(cards[i], player.getHandCard(i));
        }
    }

    @Test
    public void testWinCash() {
        Player player = constructMockPlayer();
        player.winCash(25);
        assertEquals(35, player.getCashRemaining());
    }

    @Test
    public void testIsBankrupt() {
        Player player = constructMockPlayer();
        assertEquals(false, player.isBankrupt());
        // I don't have a way to make a player lose money w/o the other three methods
    }

    @Test
    public void testHasFolded() {
        Player player = constructMockPlayer();
        assertEquals(false, player.hasFolded());
        player.fold();
        assertEquals(true, player.hasFolded());
    }

    // The next many tests will be devoted to testing handValue(), because that's a really complicated method
    @Test
    public void testHandValueHighCardOnly() {
        Player player = constructMockPlayer();
        Card[] cards = {new CardImp(13, 'D'), 
                        new CardImp(3, 'C'),
                        new CardImp(9, 'C'),
                        new CardImp(7, 'H'),
                        new CardImp(10, 'S')
                        };
        player.gainCards(cards);
        long value = player.handValue();
        int expected = 13 - 1;
        assertEquals(expected, value);
    }

    @Test
    public void testHandValueOnePair() {
        Player player = constructMockPlayer();
        Card[] cards = {new CardImp(13, 'D'), 
                        new CardImp(3, 'C'),
                        new CardImp(9, 'C'),
                        new CardImp(9, 'H'),
                        new CardImp(10, 'S')
                        };
        player.gainCards(cards);
        long value = player.handValue();
        int expected = (8 * 14) + (12);
        assertEquals(expected, value);
    }

    @Test
    public void testHandValueTwoPair() {
        Player player = constructMockPlayer();
        Card[] cards = {new CardImp(3, 'D'), 
                        new CardImp(3, 'C'),
                        new CardImp(9, 'C'),
                        new CardImp(9, 'H'),
                        new CardImp(10, 'S')
                        };
        player.gainCards(cards);
        long value = player.handValue();
        int expected = (2 * 14) + (8 * 14 * 14) + (9);
        assertEquals(expected, value);
    }

    @Test
    public void testHandValueThreeOfAKind() {
        Player player = constructMockPlayer();
        Card[] cards = {new CardImp(13, 'D'), 
                        new CardImp(9, 'C'),
                        new CardImp(9, 'C'),
                        new CardImp(9, 'H'),
                        new CardImp(10, 'S')
                        };
        player.gainCards(cards);
        long value = player.handValue();
        int expected = (8 * 14 * 14 * 14) + (12);
        assertEquals(expected, value);
    }

    @Test
    public void testHandValuePlainStraight() {
        Player player = constructMockPlayer();
        Card[] cards = {new CardImp(6, 'D'), 
                        new CardImp(7, 'C'),
                        new CardImp(8, 'C'),
                        new CardImp(9, 'H'),
                        new CardImp(10, 'S')
                        };
        player.gainCards(cards);
        long value = player.handValue();
        int expected = (9 * 14 * 14 * 14 * 14) + (9);
        assertEquals(expected, value);
    }

    @Test
    public void testHandValuePlainFlush() {
        Player player = constructMockPlayer();
        Card[] cards = {new CardImp(13, 'C'), 
                        new CardImp(3, 'C'),
                        new CardImp(9, 'C'),
                        new CardImp(7, 'C'),
                        new CardImp(10, 'C')
                        };
        player.gainCards(cards);
        long value = player.handValue();
        int expected = (12 * 14 * 14 * 14 * 14 * 14) + (12);
        assertEquals(expected, value);
    }

    @Test
    public void testHandValueFullHouse() {
        Player player = constructMockPlayer();
        Card[] cards = {new CardImp(13, 'D'), 
                        new CardImp(13, 'C'),
                        new CardImp(9, 'C'),
                        new CardImp(9, 'H'),
                        new CardImp(13, 'S')
                        };
        player.gainCards(cards);
        long value = player.handValue();
        long expected = (12 * 14 * 14 * 14 * 14 * 14 * 14) + (12 * 14 * 14 * 14) + (8 * 14) + (12); // these numbers get kinda big dawg
        assertEquals(expected, value);
    }

    @Test
    public void testHandValueFourOfAKind() {
        Player player = constructMockPlayer();
        Card[] cards = {new CardImp(13, 'D'), 
                        new CardImp(13, 'C'),
                        new CardImp(9, 'C'),
                        new CardImp(13, 'H'),
                        new CardImp(13, 'S')
                        };
        player.gainCards(cards);
        long value = player.handValue();
        long expected = (12 * 14 * 14 * 14 * 14 * 14 * 14 * 14) + (12);
        assertEquals(expected, value);
    }

    @Test
    public void testHandValueStraightFlush() {
        Player player = constructMockPlayer();
        Card[] cards = {new CardImp(6, 'H'), 
                        new CardImp(8, 'H'),
                        new CardImp(7, 'H'),
                        new CardImp(9, 'H'),
                        new CardImp(10, 'H')
                        };
        player.gainCards(cards);
        long value = player.handValue();
        long expected = (long)(9 * Math.pow(14, 8)) + (long)(9 * Math.pow(14, 4)) + (long)(9 * Math.pow(14, 5)) + (9);
        assertEquals(expected, value);
    }

    @Test
    public void testHandValueRoyalFlush() {
        Player player = constructMockPlayer();
        Card[] cards = {new CardImp(13, 'S'), 
                        new CardImp(10, 'S'),
                        new CardImp(14, 'S'),
                        new CardImp(12, 'S'),
                        new CardImp(11, 'S')
                        };
        player.gainCards(cards);
        long value = player.handValue();
        long expected = (long)(13 * Math.pow(14, 9)) + (long)(13 * Math.pow(14, 4)) + (long)(13 * Math.pow(14, 5)) + (13);
        assertEquals(expected, value);
    }
}
