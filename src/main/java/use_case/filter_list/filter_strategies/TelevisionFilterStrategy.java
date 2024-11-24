package use_case.filter_list.filter_strategies;

import entity.Television;

import java.util.*;

/**
 * Filter strategy for television shows.
 */
public class TelevisionFilterStrategy implements FilterStrategy {

    @Override
    public <T> boolean meetsCriteria(T media, Map<String, List<String>> filterCriteria) {
        boolean result = true;
        final Television show = (Television) media;
        // First compare keywords and ensure that all the keywords in
        // the filter are present in the movie description.
        // To ensure fair comparisons, the contents of all sets in the function are converted to lowercase.
        final Set<String> movieKeywords = new HashSet<>(toLowercase(getKeywords(show.getDescription())));
        Set<String> keywordCriteria = new HashSet<>();
        if (filterCriteria.get("keywords") != null) {
            keywordCriteria = new HashSet<>(toLowercase(filterCriteria.get("keywords")));
        }
        if (!trulyEmpty(keywordCriteria) && !movieKeywords.containsAll(keywordCriteria)) {
            result = false;
        }

        // Next, compare the genre and ensure that the movie genres match
        // the filter genres.
        final Set<String> movieGenres = new HashSet<>(toLowercase(show.getGenres()));
        Set<String> genreCriteria = new HashSet<>();
        if (filterCriteria.get("genres") != null) {
            genreCriteria = new HashSet<>(toLowercase(filterCriteria.get("genres")));
        }
        if (!trulyEmpty(genreCriteria) && !movieGenres.containsAll(genreCriteria)) {
            result = false;
        }

        // Finally, compare the actors and ensure that the movie actors match
        // the filter actors.
        final Set<String> movieActors = new HashSet<>(toLowercase(show.getCastMembers()));
        Set<String> actorCriteria = new HashSet<>();
        if (filterCriteria.get("actors") != null) {
            actorCriteria = new HashSet<>(toLowercase(filterCriteria.get("actors")));
        }
        if (!trulyEmpty(actorCriteria) && !movieActors.containsAll(actorCriteria)) {
            result = false;
        }
        return result;
    }

    private List<String> getKeywords(String description) {
        final String[] keywordArray = description.replaceAll("[\\p{Punct}]", "").split("\\s+");
        return new ArrayList<>(Arrays.stream(keywordArray).toList());
    }

    private List<String> toLowercase(List<String> strings) {
        return strings.stream().map(String::toLowerCase).toList();
    }

    private boolean trulyEmpty(Set s) {
        return s == null || s.isEmpty() || (s.size() == 1 && s.contains(""));
    }
}
