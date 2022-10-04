public interface CardGroup {
    int getSize();
    void addCard(Card card);
    void removeCard(Card card);
    Card getCard(int index);
    Card[] getCardsWithValue(int value);
    Card[] getCardsWithSuit(char suit);
    void shuffleCards();
    int getMaxSize();
}
