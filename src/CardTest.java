import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CardTest {
    @Test
    public void testCreateCardAndGetters() {
        Card card = new Card(4, 'H');
        assertEquals(card.getSuit(), 'H');
        assertEquals(card.getValue(), 4);
    }
}
