import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineTraveler extends TimeTraveler {
    private static final Scanner scanner = new Scanner(System.in);
    
    public CommandLineTraveler(int startingCash, int handSize, String name, int pocketSize) {
        super(startingCash, new Hand(handSize), name, new Hand(pocketSize));
    }
    public CommandLineTraveler(int startingCash, int handSize, String name) {
        this(startingCash, handSize, name, handSize);
    }
    public CommandLineTraveler(int startingCash, String name) {
        this(startingCash, 5, name, 5);
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
        System.out.println("Which cards would you like to swap (indexing at 0), and where would you like to swap them to?");
        System.out.println("(Ex: '>>0p0 1d 3d' to swap the 1st card with the 1st card in the pocket dimension, and then the 2nd and 4th cards with the dealer)");
        System.out.println("If none, just press enter.");
        System.out.print(">> ");
        String response = scanner.nextLine();
        String[] responseArray = response.split(" ");
        List<Card> cards = new ArrayList<>(responseArray.length);
        for (int i = 0; i < responseArray.length; i++) {
            try {
                String current = responseArray[i];
                if (current.split("p").length > 1) {
                    // Pocket Dimension shenanigans
                    int indexToPocket = current.charAt(0);
                    int indexToHand = current.charAt(2);
                    Card cardToPocket = getHandCard(indexToPocket);
                    drawCard(swapCardInPocket(cardToPocket, indexToHand)); // TODO: this is gonna mess up my indexing and I need to like rewrite this whole system to allow both to happen at the same time because in the UI I want them to happen in a simultaneous fashion so I should work that out sooner rather than later
                } else {
                    int index = current.charAt(0) - '0'; // looking a little like C out here
                    Card card = getHandCard(index);
                    cards.add(card);
                }
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
