public class CommandLineInterface {
    public static void main(String[] args) {
        Game game = new Game();
        game.setDeck(new Deck());
        Player human = new CommandLinePlayer(30, new CardGroup(5));
        game.addPlayer(human);

        game.playRound();
        System.out.println(human);
    }
}
