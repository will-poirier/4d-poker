import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numPlayers = 0;
        while (numPlayers <= 1) {
            System.out.println("How many computer players?");
            System.out.print(">>");
            String response = scanner.nextLine();
            try {
                numPlayers = Integer.parseInt(response) + 1;
            } catch (NumberFormatException nfe) {
                System.out.println(response + " is not a positive integer!  Try again");
            }
        }
        Game game = new Game(numPlayers, 25);
        System.out.println("Shuffling the deck: ");
        game.shuffleDeck();
        System.out.println("Starting the game!");
        game.startRound(2);
        System.out.println("Beginning the first betting phase");
        game.bettingPhase();
        System.out.println("Beginning the swapping phase");
        game.SwappingPhase();
        System.out.println("Beginning the second betting phase");
        game.bettingPhase();
        System.out.println("Time to determine the winner!");
        Player winner = game.determineWinner();
        System.out.println(winner + " won!");
        scanner.close();
    }
}
