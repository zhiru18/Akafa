package controller;

import java.util.List;

import database.WorkScheduleDB;
import database.WorkScheduleDBIF;
import model.WorkSchedule;

/**This class manages WorkSchedules.
 * 
 * @author Group 1
 * @version 1
 */
public class WorkScheduleController {
	private WorkSchedule currentWorkSchedule;
	private WorkScheduleDBIF workScheduleDB;
	
	public WorkScheduleController() {
		currentWorkSchedule = new WorkSchedule();
		workScheduleDB = new WorkScheduleDB();
	}
	public WorkSchedule createWorkSchedule(){
		//TODO
		return null;
	}
	public WorkSchedule updateWorkSchedule() {
		//TODO
		return null;
	}
	public List<WorkSchedule> findAllWorkSchedules(){
		//TODO
		return null;
	}
	public WorkSchedule findScheduleById(int id) {
		//TODO
		return null;
	}
	public List<WorkSchedule> findScheduleByDate(){
		//TODO
		return null;
	}
}
