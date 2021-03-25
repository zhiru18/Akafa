package model;

import java.time.LocalDate;
/**
 * The Period Class contain the information about Period and all the getters and setters for it
 * @author (group 1)
 * @version (1)
 */

public class Period {
	
	private int id;
	private LocalDate startDate;
	private LocalDate endDate;
	private Employee employee;
	private Post post;
	
	public Period() {
		
	}

	public Period(Employee employee, Post post, LocalDate startDate, ScheduleType scheduleType) {
		setEmployee(employee);
		setPost(post);
		setStartDate(startDate);
		setEndDate(startDate, scheduleType);
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

	public void setEndDate(LocalDate endDate, ScheduleType scheduleType) {
		if(scheduleType == null) {
			this.endDate = endDate;
		}else {
			switch(scheduleType) {
			case WEEK: 
				this.endDate = this.startDate.plusDays(4);
				break;
			case WEEKEND:
				this.endDate = this.startDate.plusDays(2);
				break;
			case VACATION:
				this.endDate = this.startDate.plusDays(7);
				break;
			default:
				this.endDate = endDate;
			}
		}
		
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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
		Period other = (Period) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
