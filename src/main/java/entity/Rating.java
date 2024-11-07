package entity;

/**
 * The representation of a review score for media, normalized to 0-100.
 */
public class Rating {
    private int score;

    public Rating(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
