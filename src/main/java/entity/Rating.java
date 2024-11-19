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

    /**
     * Sets the rating score for the media, constrained to 0-100.
     * @param score will be restricted to 0-100, then stored as the score
     */
    public void setScore(int score) {
        final int maxScore = 100;
        if (score < 0) {
            this.score = 0;
        }
        else {
            this.score = Math.min(score, maxScore);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(score);
    }
}
