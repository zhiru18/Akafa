package model;

import java.util.ArrayList;
import java.util.List;
/**This class is a collection of all periods in a work week.
 * 
 * @author Group 1
 * @version 1
 */
public class VacationSchedule {
	private int id;
	private int starteYear;
	private int endYear;
	private List<Period> periods;
	
	public VacationSchedule() {
		periods = new ArrayList<Period>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStarteYear() {
		return starteYear;
	}

	public void setStarteYear(int starteYear) {
		this.starteYear = starteYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public List<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}
}
