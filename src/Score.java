public class Score implements Comparable<Score>{
    private int specials;
    private int tieBreaker;

    public Score() {
        specials = 0b0;
        tieBreaker = 0;
    }

    public void setTieBreaker(int n) {
        tieBreaker = n;
    }

    public void addSpecial(int n) {
        specials += n;
    }
    
    @Override
    public int compareTo(Score o) {
        if (specials == o.specials) {
            return tieBreaker - o.tieBreaker;
        } else {
            return specials - o.specials;
        }
    }
}
