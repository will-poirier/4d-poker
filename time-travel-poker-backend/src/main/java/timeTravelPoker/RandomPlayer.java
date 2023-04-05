package timeTravelPoker;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class RandomPlayer extends Player {
    private static final Random RANDOM = new Random();

    public RandomPlayer(String name, int startingCash, CardGroup hand) {
        super(name, startingCash, hand);
    }

    @Override
    public Player copy() {
        return new RandomPlayer(name, money, new CardGroup(hand.getMaxSize()));
    }

    @Override
    public int ante(int anteAmount) {
        if (RANDOM.nextInt(4) == 0 || getMoney() < anteAmount) {
            // doesn't feel like ante-ing OR cannot afford to do so
            return 0;
        }
        return anteAmount;
    }

    @Override
    public int firstBettingRound(int currentCall) {
        int choice = RANDOM.nextInt(10);
        if (getMoney() < currentCall || choice == 0) {
            return 0; // Fold
        }
        if (choice < 3) {
            // Raise
            int raise = (int)(currentCall * (RANDOM.nextDouble() + 1));
            return raise < getMoney() ? raise : getMoney();
        }
        // Otherwise, Call
        return currentCall;
    }

    @Override
    public int secondBettingRound(int currentCall) {
        // Second round same as the first since this is pure random
        return firstBettingRound(currentCall);
    }

    @Override
    public List<Card> swapCards() {
        List<Card> swaps = new LinkedList<>();
        for (Card card : hand) {
            if (RANDOM.nextInt(3) == 0) {
                // swap this card
                swaps.add(card);
            }
        }
        return swaps;
    }
    
}
