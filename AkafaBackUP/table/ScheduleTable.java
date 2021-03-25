package table;

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
	public ScheduleTable() {
	}
	private static String[] COL_NAMES = { "Medarbejder", "","","","","" };
	
	@Override
	public String doGetValueOfColumn(Period value, int column) {
		String res = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate startDate= LocalDate.parse(doGetColumnName(1), formatter);
		
		if (value.getEndDate().equals(value.getStartDate())) {	      
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
             	  if(value.getStartDate().equals(startDate)) {
 		    		res=value.getPost().getName();
             	  }
 		    	break;	
             case 2:
             	 if(value.getStartDate().equals(startDate.plusDays(1))) {
     		    		res=value.getPost().getName();
                   }   
 		    	break;	
 		    case 3:
 		    	if(value.getStartDate().equals(startDate.plusDays(2))) {
 		    		res=value.getPost().getName();
                 }   
 		    	break;	
 		    case 4:	
 		    	if(value.getStartDate().equals(startDate.plusDays(3))) {
 		    		res=value.getPost().getName();
                }   
 		    	break;
 		    case 5:
 		    	if(value.getStartDate().equals(startDate.plusDays(4))) {
 		    		res=value.getPost().getName();
               }   
 				break;		
            }
         }                  
   
		if (value.getEndDate().equals(value.getStartDate().plusDays(1))) {
			
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
             	  if(value.getStartDate().equals(startDate)) {
 		    		res=value.getPost().getName();
             	  }
 		    	break;	
             case 2:
             	 if(value.getStartDate().equals(startDate.plusDays(1))||value.getStartDate().equals(startDate)) {
     		    	res=value.getPost().getName();
                   }   
 		    	break;	
 		     case 3:
 		    	 if(value.getStartDate().equals(startDate.plusDays(2))||value.getStartDate().equals(startDate.plusDays(1))) {
 		    		 res=value.getPost().getName();
                 }   
 		    	 break;	
 		     case 4:	
 		    	 if(value.getStartDate().equals(startDate.plusDays(3))||value.getStartDate().equals(startDate.plusDays(2))) {
 		    		res=value.getPost().getName(); 
                 }   
 		    	 break;
 		    case 5:
 		    	if(value.getStartDate().equals(startDate.plusDays(3))) {
 		    	  res=value.getPost().getName();
                }   
 				break;		
            }
         }       
		
         if (value.getEndDate().equals(value.getStartDate().plusDays(2))) {
			
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
             	 if(value.getStartDate().equals(startDate)) {
 		    		res=value.getPost().getName();
             	 }
 		    	 break;	
             case 2:
             	 if(value.getStartDate().equals(startDate.plusDays(1))||value.getStartDate().equals(startDate)) {
     		    	res=value.getPost().getName();
                 }   
 		    	 break;	
 		    case 3:
 		    		res=value.getPost().getName();
 		    	break;	
 		    case 4:	
 		    	if(value.getStartDate().equals(startDate.plusDays(2))||value.getStartDate().equals(startDate.plusDays(1))) {
 		    		res=value.getPost().getName();
                }   
 		    	break;
 		    case 5:
 		    	if(value.getStartDate().equals(startDate.plusDays(2))) {
 		    		res=value.getPost().getName();
               }   
 			   break;		
 		    		
            }
         }   
         if (value.getEndDate().equals(value.getStartDate().plusDays(3))) {
	  
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
     	         if(value.getStartDate().equals(startDate)) {
	    		     res=value.getPost().getName();
     	         }
	    	     break;	
            case 2:
		    	 res=value.getPost().getName();
	    	     break;	
	        case 3:
	    		 res=value.getPost().getName();
	    	     break;	
	        case 4:		    	
	    		 res=value.getPost().getName();
	    	     break;
	        case 5:
	    	     if(value.getStartDate().equals(startDate.plusDays(1))) {
	    		    res=value.getPost().getName();
	    	     }
			     break;		
            }
         }        
         if (value.getStartDate().plusDays(4).equals(value.getEndDate())) {
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
