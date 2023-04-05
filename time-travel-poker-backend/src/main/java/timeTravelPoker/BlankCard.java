package src.main.java.timeTravelPoker;

public class BlankCard extends Card {
    public BlankCard(Card original) {
        super(original);
    }

    @Override
    public String toString() {
        return "[__  _]";
    }
}
