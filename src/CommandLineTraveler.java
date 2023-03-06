import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CommandLineTraveler extends TimeTraveler {
    private static final Scanner SCANNER = new Scanner(System.in);

    public CommandLineTraveler(String name, int startingCash, CardGroup hand, CardGroup pocket) {
        super(name, startingCash, hand, pocket);
    }

    @Override
    public Player copy() {
        return new CommandLineTraveler(name, money, new CardGroup(hand.getMaxSize()), pocket);
    }

    @Override
    public int ante(int anteAmount) {
        // copied from CommandLinePlayer :p
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
        return -1;
    }

    @Override
    public int firstBettingRound(int currentCall) {
        // copied from CommandLinePlayer :p
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
        // Since this is a human, the second round is the same as the first
        return firstBettingRound(currentCall);
    }

    @Override
    public List<Card> swapCards() {
        // here's the fun part
        // we're gonna do this in two stages.
        // The first is to send cards to the pocket
        // Then you send cards to the dealer
        // but you can't send empty cards to the dealer
        // that would be cringe

        // Sending cards to the pocket
        System.out.println("Current hand and money:");
        System.out.println(this);
        System.out.println("Which cards would you like to send to the pocket (indexing at 0)?");
        System.out.println("Ex: \">>>0 1 3\" would send the first, second, and fourth cards to the pocket. If you want to send none, just hit enter");
        System.out.print(">>>");
        String answer = SCANNER.nextLine();
        String[] indeces = answer.split(" ");
        Map<Card, Integer> cardsToSwap = new HashMap<>();
        for (String swapIndex : indeces) {
            try {
                int index = Integer.parseInt(swapIndex);
                if (index < hand.getSize()) {
                    Card currentCard = hand.getCard(index);
                    System.out.println("Which card in your pocket would you like to swap " + hand.getCard(index) + " with (indexing at 0)?");
                    System.out.println("If there's less than " + pocket.getMaxSize() + " in your pocket, you can send this card to the future by leaving the field blank and just pressing enter.");
                    System.out.print(">>>");
                    answer = SCANNER.nextLine();
                    try {
                        if (answer == null) {
                            
                            cardsToSwap.put(currentCard, -1);
                            continue;
                        }
                        int pocketIndex = Integer.parseInt(answer);
                        if (pocketIndex < pocket.getMaxSize()) {
                            cardsToSwap.put(currentCard, pocketIndex);
                        } else {
                            System.out.println("Index out of bounds: try smaller numbers (Did you accidentally put in multiple numbers?)");
                            return swapCards();
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("That's not a number, silly! Try it again from the top.");
                        return swapCards();
                    } catch (IndexOutOfBoundsException ioobe) {
                        System.out.println("You don't have any blank spaces available! Try again.");
                    }
                } else {
                    System.out.println("Index out of bounds: try smaller numbers (did you make sure to seperate the digits with spaces?)");
                    return swapCards();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("I couldn't quite get that. Try again.");
                return swapCards();
            }
        }
        for (Card card : cardsToSwap.keySet()) {
            int pocketIndex = cardsToSwap.get(card);
            if (pocketIndex == -1) {
                addCardToPocket(new EntangledCard(card, hand));
            } else {
                swapCardInPocket(card, pocketIndex);
            }
            hand.removeCard(card);
            hand.addCard(new BlankCard(card));
        }

        // Now the dealer; copied from CommandLinePlayer (with adjustments to deal with blanks)
        List<Card> cards = new LinkedList<>();
        System.out.println("Current hand and money:");
        System.out.println(this);
        System.out.println("Which cards would you like to swap with the dealer (indexing at 0)?");
        System.out.println("Ex: \">>>0 1 3\" would swap the first, second, and fourth cards in the hand. If you want to swap none, just hit enter.");
        System.out.print(">>>");
        answer = SCANNER.nextLine();
        indeces = answer.split(" ");
        for (String swapIndex : indeces) {
            try {
                int index = Integer.parseInt(swapIndex);
                Card currentCard = hand.getCard(index);
                if (currentCard instanceof BlankCard) {
                    System.out.println("You can't give the dealer cards from the future! That would out you as a cheater! We're going to keep this one in your hand");
                    continue;
                }
                if (index < hand.getSize()) {
                    cards.add(currentCard); // round handles the discard of these cards
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
