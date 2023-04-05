package src.main.java.timeTravelPoker;

public enum Suit {
    CLUB(TextColor.BLUE, "C"),
    DIAMOND(TextColor.YELLOW, "D"),
    HEART(TextColor.RED, "H"),
    SPADE(TextColor.GREEN, "S");

    private final TextColor color;
    private final String symbol;

    private Suit(TextColor color, String symbol){
        this.color = color;
        this.symbol = symbol;
    }

    public TextColor getColor() {
        return color;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return color + symbol + TextColor.DEFAULT;
    }
}
