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
        Game game = new Game(numPlayers);
        scanner.close();
    }
}
