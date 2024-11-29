package view.list.update;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import interface_adapter.list.ListTableModel;
import view.ListView;

/**
 * A listener for rating column updates in the table.
 */
public class UserRatingUpdateListener implements TableModelListener {
    private final ListView listView;

    public UserRatingUpdateListener(ListView listView) {
        this.listView = listView;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (listView.getMediaListTable().getModel() instanceof ListTableModel listTableModel) {
            if (listTableModel.getColumnNames().indexOf("user-rating") == e.getColumn() && listView.getIsUserAction()) {
                // assumption: only 1 user rating is updated at a time, which is consistent with the default editor
                final int row = e.getFirstRow();
                final int ratingCol = e.getColumn();
                if (validateRating(row, ratingCol)) {
                    listView.getListUpdateController().executeUserRatingUpdate(
                            (String) listView.getMediaCollectionSelector().getSelectedItem(),
                            (String) listView.getMediaListTable().getModel().getValueAt(row, 0),
                            (int) listView.getMediaListTable().getModel().getValueAt(row, ratingCol));
                }
                else {
                    final int delay = 3000;
                    listView.setEphemeralLabel("Invalid input.", delay);
                }
            }
        }
    }

    /**
     * Check that the rating contained at row, col is a valid rating, and set it to -1 if not.
     *
     * @param row the row containing the updated rating
     * @param col the col containing the updated rating
     * @return if the rating is valid
     */
    private boolean validateRating(int row, int col) {
        final Object ratingRaw = listView.getMediaListTable().getModel().getValueAt(row, col);
        boolean result = false;
        // to make sure we're not accidentally overriding a previous value
        final boolean isUserActionPrev = listView.getIsUserAction();
        listView.setIsUserAction(false);
        try {
            final String ratingString = (String) ratingRaw;
            if (ratingString.isBlank()) {
                clearRating(row, col);
            }
            else {
                convertToInt(ratingString, row, col);
            }
            result = true;
        }
        catch (NumberFormatException | ClassCastException ex) {
            clearRating(row, col);
        }
        listView.setIsUserAction(isUserActionPrev);
        return result;
    }

    private void clearRating(int row, int col) {
        listView.getMediaListTable().getModel().setValueAt(-1, row, col);
    }

    private void convertToInt(String ratingString, int row, int col) {
        listView.getMediaListTable().getModel().setValueAt(Integer.parseInt(ratingString), row, col);
    }
}
