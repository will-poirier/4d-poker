import java.util.Scanner;

public class CLIHuman extends Player {

    private static final Scanner scanner = new Scanner(System.in);

    public CLIHuman(int startingCash) {
        super(startingCash, new Hand(5));
    }

    @Override
    public int ante(int anteAmount) {
        System.out.println("Would you like to ante $" + anteAmount + "? (Y/N)");
        System.out.print(">>");
        String response = scanner.nextLine();
        response = response.toLowerCase();
        if (response == "y") {
            return anteAmount;
        } else if (response == "n") {
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
        System.out.println("Would you like to bet $" + currentCall + " or more? (Y/N)");
        System.out.print(">>");
        String response = scanner.nextLine();
        response = response.toLowerCase();
        if (response == "y") {
            System.out.println("How much would you like to bet?");
            System.out.print(">>");
            response = scanner.nextLine();
            int amount = Integer.parseInt(response);
            if (amount >= currentCall) {
                return amount;
            } else {
                System.out.println("That's not enough!  I'll put in the minimum for you.");
                return currentCall;
            }
        } else if (response == "n") {
            fold();
            return 0;
        } else {
            fold();
            System.out.println("That was a weird answer.  I'll assume not."); // this is dumb but oh well
            return 0;
        }
    }

    @Override
    public Card[] swapCards() {
        System.out.println("How many cards would you like to swap out (0 is a valid option)?");
        System.out.print(">>");
        String response = scanner.nextLine();
        int amount = Integer.parseInt(response);
        if (amount < 0 || amount > getHandSize()) {
            System.out.println("That's a weird answer.  I'll assume you're fine with what you have.");
            return null;
        } else {
            // TODO: Pick cards to remove
            return null;
        }
    }
    
}
