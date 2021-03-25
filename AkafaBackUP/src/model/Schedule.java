package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
	private int id;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<Period> periods;
	private ScheduleType scheduleType;
	
	public Schedule() {
		periods = new ArrayList<>();
	}
	public Schedule(LocalDate startDate, LocalDate endDate, ScheduleType scheduleType) {
		this();
		this.startDate = startDate;
		this.endDate = endDate;
		this.scheduleType = scheduleType;
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
	public List<Period> getPeriods() {
		return periods;
	}
	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}
	public boolean addPeriod(Period period) {
		return periods.add(period);
	}
	public boolean removePeriod(Period period) {
		return periods.remove(period);
	}
	public ScheduleType getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(ScheduleType scheduleType) {
		this.scheduleType = scheduleType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
