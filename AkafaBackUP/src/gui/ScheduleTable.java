package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import guilib.ModelTable;
import model.Period;

public class ScheduleTable extends ModelTable<Period> {
	public ScheduleTable() {
	}
	private static String[] COL_NAMES = { "Medarbejder", "","","","","" };
	
	@Override
	public String doGetValueOfColumn(Period value, int column) {
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
			res = value.getPost().getName();
			break;	
		case 2:
			res = value.getPost().getName();
			break;	
		case 3:
			res = value.getPost().getName();
			break;	
		case 4:
			res = value.getPost().getName();
			break;			
		case 5:
			res = value.getPost().getName();
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
