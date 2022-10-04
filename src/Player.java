public interface Player {
    int getCashRemaining();
    void drawCard(Card card);
    Card discardCard();
    int ante(int anteAmount);
    int bet(int currentCall);
    boolean hasFolded();
    Card[] swapCards();
    void gainCards(Card[] cards);
    void winCash(int amount);
    boolean isBankrupt();
}
