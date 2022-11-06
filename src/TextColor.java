/**
 * An enumeration that is used to keep track of ANSII codes used to change the
 * color of printed text. Works inside IntelliJ but not from a terminal.
 */
public enum TextColor {
    DEFAULT("\u001B[0m"), // ANSII reset; default color for the terminal
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    GRAY("\u001B[37m");

    /**
     * Used to store all possible colors.
     */
    private static final TextColor[] TEXT_COLORS = {
            DEFAULT, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, GRAY
    };

    /**
     * The ANSII code for this color of text.
     */
    private final String ansii;

    /**
     * Creates a new text color with the specified ANSII code.
     *
     * @param ansii The ANSII code for changing text color.
     */
    TextColor(String ansii) {
        this.ansii = ansii;
    }

    /**
     * Returns the ANSII code for one of the possible colors based on the
     * given number. Uses number % number of colors to determine which color
     * to return.
     *
     * @param number A positive number.
     * @return The ANSII code for the color as a string.
     */
    public static String getColorString(int number) {
        int index = number % TEXT_COLORS.length;
        return TEXT_COLORS[index].getANSII();
    }

    /**
     * Returns the specified string preceded by the ANSII color code and 
     * followed by the code to reset to the default terminal color.
     * 
     * @param number A positive number.
     * @param string The string to color.
     * @return The string preceded by the ANSII color code and followed by the
     * code to reset the text color.
     */
    public static String getColorString(int number, String string) {
        return getColorString(number) + string + DEFAULT.getANSII();
    }

    /**
     * Returns the ANSII code for this text color.
     *
     * @return The ANSII code for this text color.
     */
    public String getANSII() {
        return ansii;
    }

    @Override
    public String toString() {
        return getANSII();
    }
}