import java.util.Scanner;

public class CommandLineInterface {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        Game game = new Game();
        game.setDeck(new Deck());
        Player human = new CommandLinePlayer(30, new CardGroup(5));
        game.addPlayer(human);

        System.out.println("How many computer players?");
        System.out.print(">>>");
        String answer = SCANNER.nextLine();
        int randos = Integer.parseInt(answer);
        for (int i = 0; i < randos; i++) {
            game.addPlayer(new RandomPlayer(30, new CardGroup(5)));
        }

        game.playRound(true);
        System.out.println(human);
    }
}
