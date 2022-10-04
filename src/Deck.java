public class Deck extends CardGroup {
    
    private static final char[] DEFAULT_SUITS = {'S', 'D', 'C', 'H'};

    public Deck() {
        this(52, 2, 14, null);
    }

    public Deck(int size, int minValue, int maxValue, char[] suits) {
        super(size);
        if (suits == null) {
            suits = DEFAULT_SUITS;
        }
        int numSuits = suits.length;
        // if ((maxValue - minValue) * numSuits == size) {
            // easy; just loop through maxValue and suits
            for (int i = minValue; i <= maxValue; i++) {
                for (int j = 0; j < numSuits; j++) {
                    addCard(new CardImp(i, suits[j]));
                }
            }
        // } else {
        //     // yuck gross ew stop that
        //     System.out.println("Ew gross why stop it yuck");
        // }
    }
    
}
