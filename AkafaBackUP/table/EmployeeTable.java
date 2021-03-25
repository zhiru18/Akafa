package table;


import model.Employee;

public class EmployeeTable extends ModelTable1<Employee>{
	public EmployeeTable() {
	}
	private static final String[] COL_NAMES = { "Fornavn", "Mellemnavn","EmpId"};
	@Override
	public String doGetValueOfColumn(Employee value, int column) {
		String res = null;
		switch (column) {
		case 0:
			 res = value.getFirstName();
			break;
		case 1:
			if(value.getMiddleName()!=null) {
			res = value.getMiddleName();
			}
			break;	
		case 2:
			res = "" + value.getId();
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

	

}
