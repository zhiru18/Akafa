package guilib;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public abstract class ModelTable<T> extends JTable {
	
	private List<T> data;
	private ModelTableModel modelTableModel;
	List<Color> rowColours = new ArrayList<>();

	public ModelTable() {
		modelTableModel = new ModelTableModel();
		super.setModel(modelTableModel);
	}
	

	public class ModelTableModel extends DefaultTableModel {
		//List<Color> rowColours = new ArrayList<>();
		public void setRowColour(int row, Color c) {
			rowColours.set(row, c);
	        fireTableRowsUpdated(row, row);
		}

		public Color getRowColour(int row) {
		        return rowColours.get(row);
		}
		
		public void setInitialColourOfRows() {
			for(int i = 0 ; i < getRowCount() ; i++)
				if(i % 2 == 0) {
					rowColours.add(Color.LIGHT_GRAY);
				}
				else {
					rowColours.add(Color.WHITE);
				}
		}
		
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
	
	public void setRowColour(int row, Color c) {
		rowColours.clear();
		for(int i = 0 ; i < getRowCount() ; i++) {
			if(i==row) {
				rowColours.add(c);
			}
			else {
				if(i % 2 == 0) {
					rowColours.add(Color.LIGHT_GRAY);
				}
				else {
					rowColours.add(Color.WHITE);
				}
			}
		}
   }

	public abstract String doGetValueOfColumn(T value, int column);

	public abstract int doGetColumnCount();

	public abstract String doGetColumnName(int column);

	public void setTableData(Collection<T> data) {
		this.data = new ArrayList<>(data);
		modelTableModel.fireTableDataChanged();
	}

	public void setColumnName(LocalDate date, int column) {
	  for (int i=0;i<doGetColumnCount()-column;i++) {	
	    doSetColumnName(date,column+i);
	  } 
	  modelTableModel.fireTableStructureChanged();
    }
	
	
	public T getSelectedObject() {
		T res = null;
		int row = super.getSelectedRow();
		if (row > -1) {
			res = data.get(row);
		}
		return res;
	}
   

	@Override
	public void setValueAt(Object value, int row, int column) {
		super.setValueAt(value, row, column);
	}

	public abstract void doSetColumnName(LocalDate date, int column);
		
}
