import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIHumanPlayer extends Player {

    private static final Scanner scanner = new Scanner(System.in);

    public CLIHumanPlayer(int startingCash) {
        super(startingCash, new Hand(5));
    }

    @Override
    public int ante(int anteAmount) {
        System.out.println("Would you like to ante $" + anteAmount + "? (Y/N)");
        System.out.print(">>");
        String response = scanner.nextLine();
        response = response.toUpperCase();
        if (response.equals("Y")) {
            return spendCash(anteAmount);
        } else if (response.equals("N")) {
            fold();
            return 0;
        } else {
            fold();
            System.out.println("That was a weird answer.  I'll assume not."); // this is dumb but oh well
            return 0;
        }
    }

    @Override
    public int bet(int currentCall) {
        sortHand();
        System.out.println("Current hand:");
        System.out.println(this);
        System.out.println("Would you like to bet $" + currentCall + " or more? (Y/N)");
        System.out.print(">>");
        String response = scanner.nextLine();
        response = response.toUpperCase();
        if (response.equals("Y")) {
            System.out.println("How much would you like to bet?");
            System.out.print(">>");
            response = scanner.nextLine();
            int amount = Integer.parseInt(response);
            if (amount >= currentCall) {
                return spendCash(amount);
            } else {
                System.out.println("That's not enough!  I'll put in the minimum for you.");
                return spendCash(currentCall);
            }
        } else if (response.equals("N")) {
            fold();
            return 0;
        } else {
            fold();
            System.out.println("That was a weird answer.  I'll assume not."); // this is dumb but oh well
            return 0;
        }
    }

    @Override
    public List<Card> swapCards() {
        sortHand();
        System.out.println("Current hand:");
        System.out.println(this);
        System.out.println("Which cards would you like to swap (indexing at 0)?");
        System.out.println("(Ex: '>>0 1 3' to swap the 1st, 2nd, and 4th cards in your hand)");
        System.out.println("If none, just press enter.");
        System.out.print(">>");
        String response = scanner.nextLine();
        String[] responseArray = response.split(" ");
        List<Card> cards = new ArrayList<>(responseArray.length);
        for (int i = 0; i < responseArray.length; i++) {
            try {
                int index = Integer.parseInt(responseArray[i]);
                Card card = getHandCard(index);
                cards.add(card);
            } catch (NumberFormatException nfe) {
                System.out.println(responseArray[i] + " isn't a number; I'll just skip that one.");
            } catch (IndexOutOfBoundsException ioobe) {
                System.out.println("You don't have a " + responseArray[i] + "th card to swap; I'll just skip that one.");
            }
        }
        for (Card card : cards) {
            discardCard(card);
        }
        return cards;
    }
    
}
