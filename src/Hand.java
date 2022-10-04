public class Hand extends CardGroup {

    public Hand(int size) {
        super(size, 2, 14, null);
    }

    public Hand(int size, int minValue, int maxValue, char[] suits) {
        super(size, minValue, maxValue, suits);
    }
}
