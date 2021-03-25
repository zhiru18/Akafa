package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import controller.WeekScheduleController;
import guilib.ModelTable;
import jdk.nashorn.internal.runtime.FindProperty;
import model.Period;
import model.Post;
import model.Schedule;

public class ScheduleTable extends ModelTable<Period> {
	private static String[] COL_NAMES = { "Medarbejder", "","","","","" };
	
	@Override
	public String doGetValueOfColumn(Period value, int column) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String startDate=  value.getStartDate().format(formatter);
		String endDate=  value.getEndDate().format(formatter);
		String res = null;
		switch (column) {
		case 0:
			if(value.getEmployee().getMiddleName()!=null) {
			  res = value.getEmployee().getFirstName()+ "" + value.getEmployee().getMiddleName();
			}
			else {
			 res =value.getEmployee().getFirstName();
			}
			break;
		case 1:
			if (startDate==doGetColumnName(column)) {
			   res = value.getPost().getName();
			}
			break;	
		case 2:
			if ((startDate==doGetColumnName(2)||value.getStartDate().plusDays(1).format(formatter)==doGetColumnName(1))
					&&endDate!=doGetColumnName(1)) {
				   res = value.getPost().getName();
				}
			break;	
		case 3:
			if ((startDate==doGetColumnName(3)||value.getStartDate().plusDays(1).
			format(formatter)==doGetColumnName(3)||value.getStartDate().plusDays(2).
	           format(formatter)==doGetColumnName(3))
			&& endDate!=doGetColumnName(2)) {
				   res = value.getPost().getName();
				}
			break;	
		case 4:
			if ((startDate==doGetColumnName(4)||value.getStartDate().plusDays(1).
			format(formatter)==doGetColumnName(4)||value.getStartDate().plusDays(2).
	           format(formatter)==doGetColumnName(4)||
	           value.getStartDate().plusDays(3).
	           format(formatter)==doGetColumnName(4))
			&& endDate!=doGetColumnName(3)) {
			res = value.getPost().getName();
			}
			break;			
		case 5:
			if ((startDate==doGetColumnName(5)||value.getStartDate().plusDays(1).
			format(formatter)==doGetColumnName(5)||value.getStartDate().plusDays(2).
	           format(formatter)==doGetColumnName(5)||
	           value.getStartDate().plusDays(5).
	           format(formatter)==doGetColumnName(5)||
	           value.getStartDate().plusDays(5).
	    	   format(formatter)==doGetColumnName(5))
			&& endDate!=doGetColumnName(4)) {
			res = value.getPost().getName();
			}
			break;		
		}
		return res;
	}

	@Override
	public int doGetColumnCount() {
		return COL_NAMES.length;
	}

	@Override
	public String doGetColumnName(int column) {
		return COL_NAMES[column];
	}

	@Override
	public void doSetColumnName(LocalDate date, int column) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = null;
		
		switch (column) {
		case 1:
			formattedDate = date.format(formatter);
			COL_NAMES[column]=(formattedDate);
			break;
		case 2:
			formattedDate = date.plusDays(1).format(formatter);
			COL_NAMES[column]=(formattedDate);
			break;
		case 3:
			formattedDate = date.plusDays(2).format(formatter);
			COL_NAMES[column]=(formattedDate);
			break;
		case 4:
			formattedDate = date.plusDays(3).format(formatter);
			COL_NAMES[column]=(formattedDate);
			break;
		case 5:
			formattedDate = date.plusDays(4).format(formatter);
			COL_NAMES[column]=(formattedDate);
			break;
		}
	}			
}
