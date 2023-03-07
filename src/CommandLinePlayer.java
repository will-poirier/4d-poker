import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CommandLinePlayer extends Player {
    private static final Scanner SCANNER = new Scanner(System.in);

    public CommandLinePlayer(String name, int startingCash, CardGroup hand) {
        super(name, startingCash, hand);
    }

    @Override
    public Player copy() {
        return new CommandLinePlayer(name, money, new CardGroup(hand.getMaxSize()));
    }

    @Override
    public int ante(int anteAmount) {
        System.out.println("You have $" + getMoney() + ".");
        System.out.println("(Y/N) Would you like to ante? The current ante is " + anteAmount + ".");
        System.out.print(">>>");
        String answer = SCANNER.nextLine();
        if (answer.toUpperCase().equals("Y")) {
            return anteAmount;
        } else if (!answer.toUpperCase().equals("N")) {
            System.out.println("I don't understand that. Let me ask again:");
            return ante(anteAmount); // recursion is so cool
        }
        return 0;
    }

    @Override
    public int firstBettingRound(int currentCall) {
        System.out.println("Current hand and money:");
        System.out.println(this);
        System.out.println("Current call: $" + currentCall);
        System.out.println("Would you like to (F)old, (C)all, or (R)aise?");
        System.out.print(">>>");
        String answer = SCANNER.nextLine();
        answer = answer.toUpperCase();
        if (answer.equals("F")) {
            return -1; // ints < 0 are considered folds in a Round
        } else if (answer.equals("C")) {
            return currentCall;
        } else if (answer.equals("R")) {
            System.out.println("How much total would you like to add to the pot?");
            System.out.print(">>>");
            try {
                int total = Integer.parseInt(SCANNER.nextLine());
                if (total <= currentCall) {
                    System.out.println("You can't raise to a lower value than the current call! Let's go back a step.");
                    return firstBettingRound(currentCall);
                }
                return total;
            } catch (NumberFormatException nfe) {
                System.out.println("I didn't quite catch that. Let's try again from the start.");
                return firstBettingRound(currentCall);
            }

        } else {
            System.out.println("I didn't quite catch that. I'll ask again.");
            return firstBettingRound(currentCall);
        }
    }

    @Override
    public int secondBettingRound(int currentCall) {
        return firstBettingRound(currentCall); // because this is a human player, the two betting rounds are effectively identical
    }

    @Override
    public List<Card> swapCards() {
        List<Card> cards = new LinkedList<>();
        System.out.println("Current hand and money:");
        System.out.println(this);
        System.out.println("Which cards would you like to swap with the dealer (indexing at 0)?");
        System.out.println("Ex: \">>>0 1 3\" would swap the first, second, and fourth cards in the hand. If you want to swap none, just hit enter.");
        System.out.print(">>>");
        String answer = SCANNER.nextLine();
        if (answer.equals("")) {
            return cards;
        }
        String[] indeces = answer.split(" ");
        for (String swapIndex : indeces) {
            try {
                int index = Integer.parseInt(swapIndex);
                if (index < hand.getSize()) {
                    cards.add(hand.getCard(index)); // round handles the discard of these cards
                } else {
                    System.out.println("Index out of bounds: try smaller numbers (did you make sure to seperate the digits with spaces?)");
                    return swapCards();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("I couldn't quite get that. Try again.");
                return swapCards();
            }
        }
        return cards;
    }
    
}
