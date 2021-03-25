package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.PeriodController;
import database.DataAccessException;
import model.Employee;
import model.Period;
import model.Post;
import model.Schedule;

public class EditPeriodGUIDropDown extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private PeriodController periodController;
	private JTextField textScheduleType;
	private int scheduleId;
	private Schedule currentSchedule;
	private List<Schedule> weekSchedules;
	private List<Employee> employees;
	private List<Post> posts;
	private Period editedPeriod;
	private JComboBox<Schedule> scheduleComboBox;
	private JComboBox<Employee> employeeComboBox;
	private JComboBox<Post> postComboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditPeriodGUIDropDown dialog = new EditPeriodGUIDropDown();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditPeriodGUIDropDown(List<Schedule> schedules) {
		setModal(true);
		setBounds(100, 100, 560, 386);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{136, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblTilføjerPeriod = new JLabel("Tilf\u00F8jer Period p\u00E5 vagtplanen ");
			lblTilføjerPeriod.setFont(new Font("Tahoma", Font.PLAIN, 14));
			GridBagConstraints gbc_lblTilføjerPeriod = new GridBagConstraints();
			gbc_lblTilføjerPeriod.insets = new Insets(0, 0, 5, 5);
			gbc_lblTilføjerPeriod.anchor = GridBagConstraints.NORTH;
			gbc_lblTilføjerPeriod.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblTilføjerPeriod.gridx = 0;
			gbc_lblTilføjerPeriod.gridy = 0;
			contentPanel.add(lblTilføjerPeriod, gbc_lblTilføjerPeriod);
		}
		{
			JLabel lblSceduleId = new JLabel("Skema:");
			GridBagConstraints gbc_lblSceduleId = new GridBagConstraints();
			gbc_lblSceduleId.insets = new Insets(0, 0, 5, 5);
			gbc_lblSceduleId.anchor = GridBagConstraints.EAST;
			gbc_lblSceduleId.gridx = 0;
			gbc_lblSceduleId.gridy = 1;
			contentPanel.add(lblSceduleId, gbc_lblSceduleId);
		}
		{
			scheduleComboBox = new JComboBox<>();
			GridBagConstraints gbc_scheduleComboBox = new GridBagConstraints();
			gbc_scheduleComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_scheduleComboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_scheduleComboBox.gridx = 1;
			gbc_scheduleComboBox.gridy = 1;
			contentPanel.add(scheduleComboBox, gbc_scheduleComboBox);
		}
		{
			JLabel lblScheduleType = new JLabel("Skema Type:");
			lblScheduleType.setVerticalAlignment(SwingConstants.TOP);
			GridBagConstraints gbc_lblScheduleType = new GridBagConstraints();
			gbc_lblScheduleType.insets = new Insets(0, 0, 5, 5);
			gbc_lblScheduleType.anchor = GridBagConstraints.EAST;
			gbc_lblScheduleType.gridx = 0;
			gbc_lblScheduleType.gridy = 2;
			contentPanel.add(lblScheduleType, gbc_lblScheduleType);
		}
		{
			textScheduleType = new JTextField();
			textScheduleType.setEditable(false);
			GridBagConstraints gbc_textScheduleType = new GridBagConstraints();
			gbc_textScheduleType.insets = new Insets(0, 0, 5, 0);
			gbc_textScheduleType.fill = GridBagConstraints.HORIZONTAL;
			gbc_textScheduleType.gridx = 1;
			gbc_textScheduleType.gridy = 2;
			contentPanel.add(textScheduleType, gbc_textScheduleType);
			textScheduleType.setColumns(10);
		}
		{
			JLabel lblMedarbejder = new JLabel("Medarbejder:");
			GridBagConstraints gbc_lblMedarbejder = new GridBagConstraints();
			gbc_lblMedarbejder.anchor = GridBagConstraints.EAST;
			gbc_lblMedarbejder.insets = new Insets(0, 0, 5, 5);
			gbc_lblMedarbejder.gridx = 0;
			gbc_lblMedarbejder.gridy = 5;
			contentPanel.add(lblMedarbejder, gbc_lblMedarbejder);
		}
		{
			employeeComboBox = new JComboBox<>();
			GridBagConstraints gbc_employeeComboBox = new GridBagConstraints();
			gbc_employeeComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_employeeComboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_employeeComboBox.gridx = 1;
			gbc_employeeComboBox.gridy = 5;
			contentPanel.add(employeeComboBox, gbc_employeeComboBox);
		}
		{
			JLabel lblArbejdspost = new JLabel("Arbejdspost:");
			GridBagConstraints gbc_lblArbejdspost = new GridBagConstraints();
			gbc_lblArbejdspost.anchor = GridBagConstraints.EAST;
			gbc_lblArbejdspost.insets = new Insets(0, 0, 5, 5);
			gbc_lblArbejdspost.gridx = 0;
			gbc_lblArbejdspost.gridy = 8;
			contentPanel.add(lblArbejdspost, gbc_lblArbejdspost);
		}
		{
			postComboBox = new JComboBox<>();
			GridBagConstraints gbc_postComboBox = new GridBagConstraints();
			gbc_postComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_postComboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_postComboBox.gridx = 1;
			gbc_postComboBox.gridy = 8;
			contentPanel.add(postComboBox, gbc_postComboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton buttonSave = new JButton("Gem \u00E6ndringer");
				buttonSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						buttonSaveClicked();
					}
				});
				buttonPane.add(buttonSave);
				getRootPane().setDefaultButton(buttonSave);
			}
			{
				JButton buttonCancel = new JButton("Annuller");
				buttonCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						buttonCancelClicked();
					}
				});
			
				buttonCancel.setActionCommand("Cancel");
				buttonPane.add(buttonCancel);
			}
		}
		init(schedules);
	}
	public EditPeriodGUIDropDown() {
		this(null);
	}
	public EditPeriodGUIDropDown(Period editPeriod, Schedule schedule) {
		this(null);
		this.editedPeriod = editPeriod;
		currentSchedule = schedule;
		fillComboBoxes(null);
	}
	
	private void init(List<Schedule> schedules) {
		try {
			periodController = new PeriodController();
			employees = periodController.findAllEmployees();
			posts = periodController.findAllPosts();
			weekSchedules = new ArrayList<>();

			fillComboBoxes(schedules);
						
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(null, "DataAccessException", "DataAccessException", JOptionPane.ERROR_MESSAGE);
		}
		    textScheduleType.setText("WEEK");
	}
	private void fillComboBoxes(List<Schedule> schedules) {
		DefaultComboBoxModel<Employee> employeeModel = new DefaultComboBoxModel<>();
		for(Employee employee : employees) {
			employeeModel.addElement(employee);
		}
		employeeComboBox.setModel(employeeModel);
		employeeComboBox.setRenderer(new ComboBoxRenderer());

		DefaultComboBoxModel<Post> postModel = new DefaultComboBoxModel<>();
		for(Post post : posts) {
			if(post.getPriority() < 5) {
				postModel.addElement(post);
			}
		}
		postComboBox.setModel(postModel);
		postComboBox.setRenderer(new ComboBoxRenderer());

		if(editedPeriod != null) {
			Employee employee = editedPeriod.getEmployee();
			employeeComboBox.setSelectedItem(employee);
			Post post = editedPeriod.getPost();
			postComboBox.setSelectedItem(post);
		}
		
		DefaultComboBoxModel<Schedule> scheduleModel = new DefaultComboBoxModel<>();
		if(schedules != null) {
			weekSchedules = schedules;
		}
		if(currentSchedule != null) {
				weekSchedules.add(currentSchedule);
		}
		for(Schedule schedule : weekSchedules) {
				scheduleModel.addElement(schedule);
				scheduleComboBox.setModel(scheduleModel);
				scheduleComboBox.setRenderer(new ComboBoxRenderer());
		}

		
	}

	public int showDialog() {
		this.setVisible(true);
		return scheduleId;
	}
	public Period showDialogEdit() {
		this.setVisible(true);
		return editedPeriod;
	}
	

	/**
	 * This method creates a new period. 
	 * @return scheduleId scheduleId is Id of the schedule that the added period belongs to. 
	 */
	private void buttonSaveClicked() {
		Post post = null;
		Employee employee = null;
		Schedule schedule = null;
		if(postComboBox.getSelectedItem() != null) {
			post = (Post)postComboBox.getSelectedItem();
		} else{        		
			JOptionPane.showMessageDialog(null, "Vælg post i listen", "ingen post valgt",
			JOptionPane.ERROR_MESSAGE);
	    }
		if(employeeComboBox.getSelectedItem() != null) {
			employee = (Employee)employeeComboBox.getSelectedItem();
		} else{    
	    	JOptionPane.showMessageDialog(null, "Vælg medarbejder i listen", "Ingen medarbejder valgt",
			JOptionPane.ERROR_MESSAGE);
	    }
		if(scheduleComboBox.getSelectedItem() != null) {
			schedule = (Schedule)scheduleComboBox.getSelectedItem();
			scheduleId = schedule.getId();
		} else{        		
			JOptionPane.showMessageDialog(null, "Vælg skema i listen", "intet skema valgt",
			JOptionPane.ERROR_MESSAGE);
		}
		if(editedPeriod != null){
			editedPeriod.setEmployee(employee);
			editedPeriod.setPost(post);
		}
		try {
			if(currentSchedule != null) {
				periodController.updatePeriod(editedPeriod.getId(), post.getId(), employee.getId());
			}else {
				periodController.createInsertPeriod(employee.getId(), post.getId(), schedule.getStartDate(), scheduleId, schedule.getScheduleType());
			}
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Fejl",
					JOptionPane.ERROR_MESSAGE);
		}
		
		buttonCancelClicked();
	}


	/**
	 * This method closes the AddPeriodGUI. 
	 */
	private void buttonCancelClicked() {
		this.setVisible(false);
		this.dispose();
	}
}


