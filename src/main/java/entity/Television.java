package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The representation of a Movie.
 */
public class Television extends AbstractMedia {
    private String description;
    private List<String> castMembers;
    private final Map<Integer, Integer> seasonToEpisodeCount;

    public Television(String name,
                      List<String> genres,
                      Rating userRating,
                      Rating externalRating,
                      String description,
                      List<String> castMembers) {
        super(name, genres, userRating, externalRating);
        this.description = description;
        this.castMembers = castMembers;
        this.seasonToEpisodeCount = new HashMap<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCastMembers() {
        return castMembers;
    }

    public void setCastMembers(List<String> castMembers) {
        this.castMembers = castMembers;
    }

    /**
     * Returns the total episode count of the television show.
     * @return the total episode count of the television show
     */
    public int getEpisodesTotal() {
        int result = 0;
        for (Integer season : seasonToEpisodeCount.keySet()) {
            result += seasonToEpisodeCount.get(season);
        }
        return result;
    }

    /**
     * Returns the number of episodes for the given season in the show.
     * @param season the season number to get the episode count from
     * @return the number of episodes in the season
     */
    public int getEpisodes(int season) {
        int result = 0;
        if (seasonToEpisodeCount.containsKey(season)) {
            result = seasonToEpisodeCount.get(season);
        }
        return result;
    }

    /**
     * Sets the episode count for the given season.
     * @param season the season to set the episode count
     * @param episodes the number of episodes in the season
     */
    public void setEpisodes(int season, int episodes) {
        seasonToEpisodeCount.put(season, episodes);
    }

    /**
     * Get the total number of seasons in the show.
     * @return the total number of seasons the show has.
     */
    public int getSeasons() {
        return seasonToEpisodeCount.size();
    }
}
