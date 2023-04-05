package src.main.java.timeTravelPoker;

public class EntangledCard extends Card{
    private CardGroup originalHand;

    public EntangledCard(Card original, CardGroup originalHand) {
        super(original);
        this.originalHand = originalHand;
    }

    public void replaceInPast(Card replacement) {
        originalHand.removeCard(this);
        originalHand.addCard(replacement);
    }
}
