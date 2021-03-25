package guilib;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;






public class MyTableCellRenderer extends DefaultTableCellRenderer {
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        ModelTable<Object>.ModelTableModel model = (ModelTable<Object>.ModelTableModel) table.getModel();
		final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		model.setInitialColourOfRows();
		c.setBackground(model.getRowColour(row));
        return c;
    }
}
