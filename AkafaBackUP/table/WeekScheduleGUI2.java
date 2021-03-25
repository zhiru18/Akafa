package table;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.PeriodController;
import controller.WeekScheduleController;
import database.DataAccessException;
import model.Employee;
import model.MatrixNotSquareException;
import model.Period;
import model.Post;
import model.Schedule;
import model.ScheduleType;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class WeekScheduleGUI2 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WeekScheduleController weekScheduleController;
	private PeriodController periodController; 
	private EmployeeTable scheduleTableOne;
	private EmployeeTable scheduleTableTwo; 
	private EmployeeTable scheduleTableThree;

	private JPanel contentPane;
	private JTextField textScheduleIDOne;
	private JTextField textScheduleIDTwo;
	private JTextField textScheduleIDThree;
	private JTextField textStartDate;
	private JTextField textFieldPostName;
	private JTextField textFieldEmployeeName;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WeekScheduleGUI2 dialog = new WeekScheduleGUI2();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public WeekScheduleGUI2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1062, 716);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel weekSchedulePanel = new JPanel();
		tabbedPane.addTab("Vagtplan", null, weekSchedulePanel, null);
		GridBagLayout gbl_weekSchedulePanel = new GridBagLayout();
		gbl_weekSchedulePanel.columnWidths = new int[]{387, -38, 193, 0, 202, 0, 176, 0, 0, 0, 0, 0, 0, 0};
		gbl_weekSchedulePanel.rowHeights = new int[]{0, 0, 0, 232, 0};
		gbl_weekSchedulePanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_weekSchedulePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		weekSchedulePanel.setLayout(gbl_weekSchedulePanel);
		
		JButton buttonCreateWeekSchedule = new JButton("Opret vagtplan");
		buttonCreateWeekSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createWeekScheduleClicked();
			}
		});
		
		textStartDate = new JTextField();
		textStartDate.addFocusListener(new FocusAdapter() {
			@Override 
			public void focusGained(FocusEvent arg0) {
				textStartDate.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textStartDate.getText() == null || textStartDate.getText().equals(""))
					textStartDate.setText("Start dato: dd-MM-YYYY");
			}
		});
		textStartDate.setText("Start dato: dd-MM-YYYY");
		GridBagConstraints gbc_textStartDate = new GridBagConstraints();
		gbc_textStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_textStartDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textStartDate.gridx = 0;
		gbc_textStartDate.gridy = 0;
		weekSchedulePanel.add(textStartDate, gbc_textStartDate);
		textStartDate.setColumns(10);
		GridBagConstraints gbc_buttonCreateWeekSchedule = new GridBagConstraints();
		gbc_buttonCreateWeekSchedule.anchor = GridBagConstraints.EAST;
		gbc_buttonCreateWeekSchedule.insets = new Insets(0, 0, 5, 5);
		gbc_buttonCreateWeekSchedule.gridx = 2;
		gbc_buttonCreateWeekSchedule.gridy = 0;
		weekSchedulePanel.add(buttonCreateWeekSchedule, gbc_buttonCreateWeekSchedule);
		
		
		JButton buttonEditWeekSchedule = new JButton("Rediger");
		buttonEditWeekSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editWeekScheduleClicked();
			}
		});
		GridBagConstraints gbc_buttonEditWeekSchedule = new GridBagConstraints();
		gbc_buttonEditWeekSchedule.anchor = GridBagConstraints.WEST;
		gbc_buttonEditWeekSchedule.insets = new Insets(0, 0, 5, 5);
		gbc_buttonEditWeekSchedule.gridx = 4;
		gbc_buttonEditWeekSchedule.gridy = 0;
		weekSchedulePanel.add(buttonEditWeekSchedule, gbc_buttonEditWeekSchedule);
		
	    textFieldEmployeeName = new JTextField();
	    textFieldEmployeeName.addFocusListener(new FocusAdapter() {
			@Override 
			public void focusGained(FocusEvent arg0) {
				textFieldEmployeeName.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textFieldEmployeeName.getText() == null || textStartDate.getText().equals(""))
					textFieldEmployeeName.setText("EmployeeName");
			}
		});
	    //textFieldEmployeeName.setVisible(false);
		textFieldEmployeeName.setText("EmployeeName");
		textFieldEmployeeName.setColumns(10);
		GridBagConstraints gbc_textFieldEmployeeName = new GridBagConstraints();
		gbc_textFieldEmployeeName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmployeeName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmployeeName.gridx = 6;
		gbc_textFieldEmployeeName.gridy = 0;
		weekSchedulePanel.add(textFieldEmployeeName, gbc_textFieldEmployeeName);
		
		textFieldPostName = new JTextField();
		textFieldPostName.addFocusListener(new FocusAdapter() {
			@Override 
			public void focusGained(FocusEvent arg0) {
				textFieldPostName.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textFieldPostName.getText() == null || textStartDate.getText().equals(""))
					textFieldPostName.setText("PostName");
			}
		});
		//textFieldPostName.setVisible(false);
		textFieldPostName.setText("PostName");
		textFieldPostName.setColumns(10);
		GridBagConstraints gbc_textFieldPostName = new GridBagConstraints();
		gbc_textFieldPostName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPostName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPostName.gridx = 6;
		gbc_textFieldPostName.gridy = 1;
		weekSchedulePanel.add(textFieldPostName, gbc_textFieldPostName);
		
		JButton btnPrintWeekSchedule = new JButton("Udskriv vagtplan");
		GridBagConstraints gbc_btnPrintWeekSchedule = new GridBagConstraints();
		gbc_btnPrintWeekSchedule.insets = new Insets(0, 0, 5, 0);
		gbc_btnPrintWeekSchedule.gridx = 12;
		gbc_btnPrintWeekSchedule.gridy = 1;
		weekSchedulePanel.add(btnPrintWeekSchedule, gbc_btnPrintWeekSchedule);
		
		JLabel lblPeriode = new JLabel("Periode: ");
		GridBagConstraints gbc_lblPeriode = new GridBagConstraints();
		gbc_lblPeriode.anchor = GridBagConstraints.EAST;
		gbc_lblPeriode.insets = new Insets(0, 0, 5, 5);
		gbc_lblPeriode.gridx = 0;
		gbc_lblPeriode.gridy = 2;
		weekSchedulePanel.add(lblPeriode, gbc_lblPeriode);
		
		textScheduleIDOne = new JTextField();
		GridBagConstraints gbc_textScheduleIDOne = new GridBagConstraints();
		gbc_textScheduleIDOne.insets = new Insets(0, 0, 5, 5);
		gbc_textScheduleIDOne.fill = GridBagConstraints.HORIZONTAL;
		gbc_textScheduleIDOne.gridx = 1;
		gbc_textScheduleIDOne.gridy = 2;
		weekSchedulePanel.add(textScheduleIDOne, gbc_textScheduleIDOne);
		textScheduleIDOne.setColumns(10);
		
		JLabel lblPeriode_1 = new JLabel("Periode:");
		GridBagConstraints gbc_lblPeriode_1 = new GridBagConstraints();
		gbc_lblPeriode_1.anchor = GridBagConstraints.EAST;
		gbc_lblPeriode_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPeriode_1.gridx = 2;
		gbc_lblPeriode_1.gridy = 2;
		weekSchedulePanel.add(lblPeriode_1, gbc_lblPeriode_1);
		
		textScheduleIDTwo = new JTextField();
		GridBagConstraints gbc_textScheduleIDTwo = new GridBagConstraints();
		gbc_textScheduleIDTwo.insets = new Insets(0, 0, 5, 5);
		gbc_textScheduleIDTwo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textScheduleIDTwo.gridx = 3;
		gbc_textScheduleIDTwo.gridy = 2;
		weekSchedulePanel.add(textScheduleIDTwo, gbc_textScheduleIDTwo);
		textScheduleIDTwo.setColumns(10);
		
		JLabel lblPeriode_2 = new JLabel("Periode:");
		GridBagConstraints gbc_lblPeriode_2 = new GridBagConstraints();
		gbc_lblPeriode_2.anchor = GridBagConstraints.EAST;
		gbc_lblPeriode_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblPeriode_2.gridx = 4;
		gbc_lblPeriode_2.gridy = 2;
		weekSchedulePanel.add(lblPeriode_2, gbc_lblPeriode_2);
		
		textScheduleIDThree = new JTextField();
		GridBagConstraints gbc_textScheduleIDThree = new GridBagConstraints();
		gbc_textScheduleIDThree.insets = new Insets(0, 0, 5, 5);
		gbc_textScheduleIDThree.fill = GridBagConstraints.HORIZONTAL;
		gbc_textScheduleIDThree.gridx = 5;
		gbc_textScheduleIDThree.gridy = 2;
		weekSchedulePanel.add(textScheduleIDThree, gbc_textScheduleIDThree);
		textScheduleIDThree.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		weekSchedulePanel.add(scrollPane, gbc_scrollPane);
		
	
		scheduleTableOne = new EmployeeTable();
		//scheduleTableOne.getTableHeader();
		scrollPane.setViewportView(scheduleTableOne);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 3;
		weekSchedulePanel.add(scrollPane_1, gbc_scrollPane_1);
		
		scheduleTableTwo = new EmployeeTable();
		scrollPane_1.setViewportView(scheduleTableTwo);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 4;
		gbc_scrollPane_2.gridy = 3;
		weekSchedulePanel.add(scrollPane_2, gbc_scrollPane_2);
		
		scheduleTableThree = new EmployeeTable();
		scrollPane_2.setViewportView(scheduleTableThree);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 6;
		gbc_scrollPane_3.gridy = 3;
		weekSchedulePanel.add(scrollPane_3, gbc_scrollPane_3);
		
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		
		init();
	}
	

	private void init() {
		try {
			weekScheduleController = new WeekScheduleController();
			
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(null, "DataAccessException", "DataAccessException", JOptionPane.ERROR_MESSAGE);
		}
		/*String IdOne = textScheduleIDOne.getText();
		String IdTwo = textScheduleIDTwo.getText();
		String IdThree = textScheduleIDThree.getText();
		try {
			weekScheduleController = new WeekScheduleController();
			scheduleTableOne.setTableData(weekScheduleController.findPeriodsByScheduleId(Integer.parseInt(IdOne), true));
			scheduleTableTwo.setTableData(weekScheduleController.findPeriodsByScheduleId(Integer.parseInt(IdTwo), true));
			scheduleTableThree.setTableData(weekScheduleController.findPeriodsByScheduleId(Integer.parseInt(IdThree), true));
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(null, "DataAccessException", "DataAccessException", JOptionPane.ERROR_MESSAGE);
		}	*/
	}
	
	private void createWeekScheduleClicked() {
		String dateOne = "12-05-2019";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate startDate = LocalDate.parse(dateOne, formatter);
		Employee e1= new Employee();
		e1.setFirstName("anne");
		Employee e2= new Employee();
		e2.setFirstName("lisa");
		
		Post p1= new Post();
		Post p2= new Post();
		p1.setName("B1");
		p2.setName("K1");
		
		Period period1=new Period(e1,p1, startDate, ScheduleType.WEEK);
		Period period2=new Period(e2,p2, startDate, ScheduleType.WEEK);
		
		
		ArrayList<Period> periods= new ArrayList<>();
		periods.add(period1);
		periods.add(period2);
		period1.setStartDate(startDate.plusDays(2));
		period1.setEndDate(startDate.plusDays(4), null);
		String formatDateTime = startDate.format(formatter);
		scheduleTableOne.setTableData(periods);
		
		scheduleTableOne.setColumnName(startDate, 1);
		scheduleTableTwo.setColumnName(startDate.plusDays(7), 1);
		scheduleTableThree.setColumnName(startDate.plusDays(14), 1);
		scheduleTableOne.setColumnName(startDate, 1);
		if (periods.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No firm found with given name", "No firm found",
					JOptionPane.ERROR_MESSAGE);
		} else {

			 System.out.println(formatDateTime);
			 System.out.println(scheduleTableOne.doGetColumnName(1));
			 System.out.println(scheduleTableOne.doGetColumnName(2));
			
		 
		}
		
		
	}

	
	private void editWeekScheduleClicked() {
		 //textFieldEmployeeName.setVisible(true);
		 //textFieldPostName.setVisible(true);
		 Period currPeriod = getSelectedPeriodFromWeekScheduleTableOne();
		currPeriod.getEmployee().setFirstName(textFieldEmployeeName.getText());
		currPeriod.getPost().setName(textFieldPostName.getText());
		String employeeName=currPeriod.getEmployee().getFirstName();
		String postName=currPeriod.getPost().getName();
		
		String employeeName1=textFieldEmployeeName.getText();
		String postName1=textFieldPostName.getText();

		int row=scheduleTableOne.getSelectedRow();
		int column=scheduleTableOne.getSelectedColumn();
		if(row>0) {
			try {
				if (column==0) {
					scheduleTableOne.setValueAt(employeeName1, row, column);
				}
			    else {
			    	scheduleTableOne.setValueAt(postName1, row, column);
			    }
			}catch(ArrayIndexOutOfBoundsException ee) {
				ee.printStackTrace();
			}
		}
		
		
	}
	
	private Period getSelectedPeriodFromWeekScheduleTableOne() {
		return this.scheduleTableOne.getSelectedObject();
		
	}
	
}
