public class EntangledCard extends Card{
    private CardGroup originalHand;

    public EntangledCard(Card original, CardGroup originalHand) {
        super(original);
        this.originalHand = originalHand;
    }
}
