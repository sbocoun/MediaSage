package interface_adapter.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A factory for creating ListTableModel depending on the type of AbstractMedia displayed.
 */
public class ListTableModelFactory {
    private final Map<String, List<String>> mediaColumns;
    private final String entityMovie = "entity.Movie";
    private final String entityTelevision = "entity.Television";

    /**
     * Creates a new ListTableModelFactory that creates the corresponding ListTableModel for the media type.
     */
    public ListTableModelFactory() {
        final List<String> movieColumns = new ArrayList<>(List.of(
                "name", "runtime", "description", "genres", "cast", "user-rating", "external-rating"));
        final List<String> televisionColumns = new ArrayList<>(List.of(
                "name", "seasons", "total episodes", "description", "genres", "cast",
                "user-rating", "external-rating"));
        this.mediaColumns = new HashMap<>();
        mediaColumns.put(entityMovie, movieColumns);
        mediaColumns.put(entityTelevision, televisionColumns);
    }

    /**
     * Create a ListTableModel for the relevant mediaType.
     *
     * @param mediaType the type of AbstractMedia (e.g. entity.Movie)
     * @param mediaData the table (list of list) of abstract media data
     * @return a ListTableModel used to display the list of AbstractMedia in a JTable
     * @throws UnsupportedOperationException if the AbstractMedia type is unknown
     */
    public ListTableModel createListTableModel(String mediaType,
                                               List<List<Object>> mediaData)
            throws UnsupportedOperationException {
        final ListTableModel listTableModel;
        if (entityMovie.equals(mediaType)) {
            listTableModel = new ListTableModel(mediaColumns.get(entityMovie), mediaData);
        }
        else if (entityTelevision.equals(mediaType)) {
            listTableModel = new ListTableModel(mediaColumns.get(entityTelevision), mediaData);
        }
        else {
            throw new UnsupportedOperationException("Unknown media type.");
        }
        return listTableModel;
    }
}
