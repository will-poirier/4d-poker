import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CardImpTest {
    @Test
    public void testCreateCardAndGetters() {
        Card card = new CardImp(4, 'H');
        assertEquals(card.getSuit(), 'H');
        assertEquals(card.getValue(), 4);
    }
}
