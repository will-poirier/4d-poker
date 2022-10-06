import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CLIRandomPlayer extends Player {
    private static final Random random = new Random();

    public CLIRandomPlayer(int startingCash) {
        super(startingCash, new Hand(5));
    }

    @Override
    public int ante(int anteAmount) {
        if (random.nextInt(2) % 2 == 0 && getCashRemaining() >= anteAmount) { // 50/50
            return anteAmount;
        } else {
            fold();
            return 0;
        }
    }

    @Override
    public int bet(int currentCall) {
        if (random.nextInt(2) % 2 == 0 && getCashRemaining() >= currentCall) { // 50/50
            return currentCall + random.nextInt(10 < getCashRemaining() ? 10 : getCashRemaining());
        } else {
            fold();
            return 0;
        }
    }

    @Override
    public Card[] swapCards() {
        List<Card> list = new LinkedList<>();
        for (int i = 0; i < getHandSize(); i++) {
            Card card = getHandCard(i);
            if (random.nextInt(2) % 2 == 0) {
                list.add(card);
                discardCard(card);
            }
        }
        return (Card[])list.toArray();
    }
    
}
