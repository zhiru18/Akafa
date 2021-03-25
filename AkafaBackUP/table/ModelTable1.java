package table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public abstract class ModelTable1<T> extends JTable {
	private List<T> data;
	private ModelTableModel modelTableModel;

	public ModelTable1() {
		modelTableModel = new ModelTableModel();
		super.setModel(modelTableModel);
	}

	private class ModelTableModel extends DefaultTableModel {

		@Override
		public int getColumnCount() {
			return doGetColumnCount();
		}

		@Override
		public String getColumnName(int column) {
			return doGetColumnName(column);
		}

		@Override
		public int getRowCount() {
			int res = 0;
			if (data != null) {
				res = data.size();
			}
			return res;
		}

		@Override
		public String getValueAt(int row, int column) {
			T value = data.get(row);
			String res = doGetValueOfColumn(value, column);
			return res;
		}

	}

	public abstract String doGetValueOfColumn(T value, int column);

	public abstract int doGetColumnCount();

	public abstract String doGetColumnName(int column);

	public void setTableData(Collection<T> data) {
		this.data = new ArrayList<>(data);
		modelTableModel.fireTableDataChanged();
	}

	public T getSelectedObject() {
		T res = null;
		int row = super.getSelectedRow();
		if (row > -1) {
			res = data.get(row);
		}
		return res;
	}
}
