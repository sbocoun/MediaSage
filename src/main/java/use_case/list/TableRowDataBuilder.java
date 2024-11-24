package use_case.list;

import java.util.ArrayList;
import java.util.List;

import entity.AbstractMedia;
import entity.Movie;
import entity.Television;

/**
 * Builder to build the data representation of an abstract media.
 */
public class TableRowDataBuilder {

    /**
     * Build the data representation of the abstract media.
     * @param media the abstract media to convert to date
     * @return the data representation of the abstract media
     * @throws UnsupportedOperationException if the conversion for the media type isn't implemented here yet
     */
    public List<Object> createTableRowData(AbstractMedia media) throws UnsupportedOperationException {
        final List<Object> result;
        if (media instanceof Movie movie) {
            result = createTableRowData(movie);
        }
        else if (media instanceof Television television) {
            result = createTableRowData(television);
        }
        else {
            throw new UnsupportedOperationException("Unknown media type.");
        }
        return result;
    }

    private List<Object> createTableRowData(Movie movie) {
        final List<Object> result = new ArrayList<>();
        result.add(movie.getName());
        result.add(movie.getMinuteRuntime());
        result.add(movie.getDescription());
        result.add(movie.getGenres());
        result.add(movie.getCastMembers());
        result.add(movie.getUserRating());
        result.add(movie.getExternalRating());
        return result;
    }

    private List<Object> createTableRowData(Television television) {
        final List<Object> result = new ArrayList<>();
        result.add(television.getName());
        result.add(television.getSeasons());
        result.add(television.getEpisodesTotal());
        result.add(television.getDescription());
        result.add(television.getGenres());
        result.add(television.getCastMembers());
        result.add(television.getUserRating());
        result.add(television.getExternalRating());
        return result;
    }
}
