package interface_adapter.list;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * A TableModel used to display the list of media in the JTable in List View.
 */
public class ListTableModel extends AbstractTableModel {
    private List<String> columnNames;
    private transient List<List<Object>> table;

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

    @Override
    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return table.get(rowIndex).get(columnIndex);
    }

    /**
     * Replace the current data table and columns with the ones from newListTableModel.
     * @param newListTableModel new list table model
     */
    public void replaceTable(ListTableModel newListTableModel) {
        this.table = newListTableModel.getTable();
        this.columnNames = newListTableModel.getColumnNames();
        this.fireTableDataChanged();
    }

    /**
     * Return the data table stored in this TableModel.
     * @return the data table
     */
    public List<List<Object>> getTable() {
        return table;
    }

    /**
     * Return the list of column names stored in this TableModel.
     * @return the list of column names
     */
    public List<String> getColumnNames() {
        return columnNames;
    }
}
