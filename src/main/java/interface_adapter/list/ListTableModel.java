package interface_adapter.list;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.jetbrains.annotations.Nls;

/**
 * A TableModel used to display the list of media in the JTable in List View.
 */
public class ListTableModel implements TableModel {
    private List<String> columnNames;
    private List<List<Object>> table;

    public ListTableModel(List<String> columnNames, List<List<Object>> table) {
        this.columnNames = columnNames;
        this.table = table;
    }

    @Override
    public int getRowCount() {
        return table.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Nls
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames.get(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // for now, assume all columns are strings
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return table.get(rowIndex).get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        table.get(rowIndex).set(columnIndex, aValue);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Equivalent to reinitializing the TableModel.
     * @param newColumnNames the names of columns of the table
     * @param newTable the contents of the table
     */
    public void setTable(List<String> newColumnNames, List<List<Object>> newTable) {
        this.columnNames = newColumnNames;
        this.table = newTable;
    }
}
