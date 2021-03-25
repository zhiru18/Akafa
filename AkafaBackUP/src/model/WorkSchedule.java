package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**This class is a collection of all periods in a work week.
 * 
 * @author Group 1
 * @version 1
 */
public class WorkSchedule {
	private int id;
	private LocalDate startDate, endDate;
	private List<Period> periods;
	
	public WorkSchedule() {
		periods = new ArrayList<Period>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public boolean addPeriod(Period period) {
		return periods.add(period);
	}
	public boolean removePeriod(Period period) {
		return periods.remove(period);
	}
	public List<Period> findAllPeriods(){
		return periods;
	}
}
