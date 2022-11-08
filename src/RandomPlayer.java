import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player {
    private static final Random random = new Random();

    public RandomPlayer(int startingCash, String name) {
        super(startingCash, new Hand(5), name);
    }
    public RandomPlayer(int startingCash) {
        super(startingCash, new Hand(5), "Bot");
    }

    @Override
    public int ante(int anteAmount) {
        if (getCashRemaining() >= anteAmount) {
            return spendCash(anteAmount);
        } else {
            fold();
            return 0;
        }
    }

    @Override
    public int bet(int currentCall) {
        if (getCashRemaining() > currentCall) {
            int addedBet = getCashRemaining() - currentCall;
            return spendCash(currentCall + random.nextInt(10 < addedBet ? 10 : addedBet));
        } else if (getCashRemaining() == currentCall){
            return spendCash(currentCall);
        } else {
            fold();
            return 0;
        }
    }

    @Override
    public List<Card> swapCards() {
        List<Card> list = new LinkedList<>();
        for (int i = 0; i < getHandSize(); i++) {
            Card card = getHandCard(i);
            if (random.nextInt(3) % 2 == 0) {
                list.add(card);
                discardCard(card);
            }
        }
        return list;
    }
    
}
