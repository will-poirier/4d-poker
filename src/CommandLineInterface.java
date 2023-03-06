import java.util.Scanner;

public class CommandLineInterface {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        Game game = new Game();
        game.setDeck(new Deck());
        System.out.println("Enter your name:");
        System.out.print(">>>");
        String name = SCANNER.nextLine();
        Player human = new CommandLineTraveler(name, 30, new CardGroup(5), new CardGroup(5));
        game.addPlayer(human);

        System.out.println("How many computer players?");
        System.out.print(">>>");
        String answer = SCANNER.nextLine();
        int randos = Integer.parseInt(answer);
        for (int i = 0; i < randos; i++) {
            game.addPlayer(new RandomPlayer("Random-" + i, 30, new CardGroup(5)));
        }

        while (true) {
            game.playRound(true);
            System.out.println(human);
            System.out.println("Another round (Y/N)?");
            System.out.print(">>>");
            answer = SCANNER.nextLine();
            if (answer.toUpperCase().equals("N")) {
                System.out.println("Fair enough. Toodles!");
                break;
            } else if (!answer.toUpperCase().equals("Y")) {
                System.out.println("That was a weird answer. I'll start another round (but you can always quit by using ctrl+C)");
            } else {
                System.out.println("Wonderful!");
            }
        }
    }
}
