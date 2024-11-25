package interface_adapter.filter_list;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import interface_adapter.list.ListViewModel;
import use_case.filter_list.FilterListOutputBoundary;
import use_case.filter_list.FilterListOutputData;

/**
 * Presenter for the filter list.
 */
public class FilterPresenter implements FilterListOutputBoundary {
    private final ListViewModel listViewModel;

    public FilterPresenter(ListViewModel listViewModel) {
        this.listViewModel = listViewModel;
    }

    /**
     * Prepares the success view.
     *
     * @param filterListOutputData The output data.
     */
    public void prepareSuccessView(FilterListOutputData filterListOutputData) {
        final JTable table = listViewModel.getState().getMediaTable();
        final TableRowSorter<?> sorter = (TableRowSorter<?>) table.getRowSorter();
        final RowFilter<TableModel, Integer> rf = new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                final String name = (String) entry.getValue(0);
                return filterListOutputData.getFilteredMediaNames().contains(name);
            }
        };
        sorter.setRowFilter(rf);
    }

    /**
     * Prepares the fail view.
     *
     * @param message The message to display.
     */
    public void prepareFailView(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
