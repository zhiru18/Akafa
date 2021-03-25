package gui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import model.Employee;
import model.Post;
import model.Schedule;

public class ComboBoxRenderer extends BasicComboBoxRenderer {
    
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
 
        if (value instanceof Schedule)
        {
            Schedule schedule = (Schedule)value;
            setText("" + schedule.getId());
        }
        if (value instanceof Post)
        {
            Post post = (Post)value;
            setText(post.getName());
        }
        if (value instanceof Employee)
        {
            Employee employee = (Employee)value;
            String text = employee.getFirstName();
            if(employee.getMiddleName() != null) {
            	text += employee.getMiddleName();
            }
            setText(text);
        }
 
        return this;
    }
}
