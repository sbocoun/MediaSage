package interface_adapter.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A factory for creating ListTableModel depending on the type of AbstractMedia displayed.
 */
public class ListTableModelFactory {

    /**
     * Create a ListTableModel for the relevant mediaType.
     *
     * @param mediaType the type of AbstractMedia (e.g. entity.Movie)
     * @param mediaList the list of AbstractMedia
     * @return a ListTableModel used to display the list of AbstractMedia in a JTable
     * @throws UnsupportedOperationException if the AbstractMedia type is unknown
     */
    public ListTableModel createListTableModel(String mediaType,
                                               List<Map<String, Object>> mediaList)
            throws UnsupportedOperationException {
        // remove this final keyword when adding more media types
        final ListTableModel listTableModel;
        if ("entity.Movie".equals(mediaType)) {
            final List<String> columnNames = new ArrayList<>(List.of(
                    "name", "runtime", "description", "genres", "cast", "rating (user)", "rating (external)"));
            final List<List<Object>> table = new ArrayList<>();
            for (final Map<String, Object> media : mediaList) {
                final List<Object> row = new ArrayList<>();
                row.add(media.get("name"));
                row.add(media.get("runtime"));
                row.add(media.get("description"));
                row.add(media.get("genres"));
                row.add(media.get("cast"));
                row.add(media.get("user-rating"));
                row.add(media.get("external-rating"));
                table.add(row);
            }
            listTableModel = new ListTableModel(columnNames, table);
        }
        else {
            throw new UnsupportedOperationException("Unknown media type.");
        }
        return listTableModel;
    }
}
