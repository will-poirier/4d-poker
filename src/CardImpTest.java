import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class CardImpTest {
    @Test
    public void testCreateCardAndGetters() {
        Card card = new CardImp(4, 'H');
        assertEquals(card.getSuit(), 'H');
        assertEquals(card.getValue(), 4);
    }
}
