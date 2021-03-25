package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import controller.DBStatusController;
import controller.PeriodController;
import controller.WeekScheduleController;
import database.DataAccessException;
import guilib.MyTableCellRenderer;
import model.MatrixNotSquareException;
import model.Period;
import model.Schedule;
import model.SpecialNeeds;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop.Action;

import javax.swing.ListSelectionModel;

public class WeekScheduleGUI extends JFrame {
	private WeekScheduleController weekScheduleController;
	private ScheduleTable scheduleTableOne;
	private ScheduleTable scheduleTableTwo; 
	private ScheduleTable scheduleTableThree;
	private ScheduleTable scheduleTableFour;
	private ScheduleTable scheduleTableFive;
	private ScheduleTable scheduleTableSix;
	private ScheduleTable scheduleTableSeven;
	private JPanel contentPane;
	private JTextField textScheduleIDOne;
	private JTextField textScheduleIDTwo;
	private JTextField textScheduleIDThree;
	private JTextField textStartDate;
	private JTextField textScheduleIDFive;
	private JTextField textScheduleIDSix;
	private JTextField textScheduleIDSeven;
	private JTextField textScheduleIDFour;
	private JFrame frame;
	private List<Period> periodListOne;
	private List<Period> periodListTwo;
	private List<Period> periodListThree;
	private List<Period> periodListFour;
	private List<Period> periodListFive;
	private List<Period> periodListSix;
	private List<Period> periodListSeven;
	private JButton buttonCreateWeekSchedule;
	private JLabel lblStatus;
	private Period currentPeriodOne;
	private Period currentPeriodTwo;
	private Period currentPeriodThree;
	private Period currentPeriodFour;
	private Period currentPeriodFive;
	private Period currentPeriodSix;
	private Period currentPeriodSeven;
	private Schedule weekScheduleOne;
	private Schedule weekScheduleTwo;
	private Schedule weekScheduleThree;
	private Schedule weekScheduleFour;
	private Schedule weekScheduleFive;
	private Schedule weekScheduleSix;
	private Schedule weekScheduleSeven;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeekScheduleGUI frame = new WeekScheduleGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WeekScheduleGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1277, 771);
		
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
		gbl_weekSchedulePanel.columnWidths = new int[]{251, 178, 137, 233, 232, 0};
		gbl_weekSchedulePanel.rowHeights = new int[]{0, 0, 0, 0, 232, 0, 0, 0, 0, 0, 0};
		gbl_weekSchedulePanel.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_weekSchedulePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		weekSchedulePanel.setLayout(gbl_weekSchedulePanel);
		
		textStartDate = new JTextField();
		textStartDate.addFocusListener(new FocusAdapter() {
			@Override 
			public void focusGained(FocusEvent arg0) {
				textStartDate.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textStartDate.getText() == null || textStartDate.getText().equals(""))
					textStartDate.setText("Start dato");
			}
		});
		textStartDate.setText("Start dato: dd-MM-YYYY");
		GridBagConstraints gbc_textStartDate = new GridBagConstraints();
		gbc_textStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_textStartDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textStartDate.gridx = 0;
		gbc_textStartDate.gridy = 1;
		weekSchedulePanel.add(textStartDate, gbc_textStartDate);
		textStartDate.setColumns(10);
		
		buttonCreateWeekSchedule = new JButton("Opret vagtplan");
		buttonCreateWeekSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createWeekScheduleClicked();
			}
		});
		GridBagConstraints gbc_buttonCreateWeekSchedule = new GridBagConstraints();
		gbc_buttonCreateWeekSchedule.anchor = GridBagConstraints.WEST;
		gbc_buttonCreateWeekSchedule.insets = new Insets(0, 0, 5, 5);
		gbc_buttonCreateWeekSchedule.gridx = 1;
		gbc_buttonCreateWeekSchedule.gridy = 1;
		weekSchedulePanel.add(buttonCreateWeekSchedule, gbc_buttonCreateWeekSchedule);
		
		JButton buttonEditWeekSchedule = new JButton("Rediger");
		buttonEditWeekSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonEditWeekScheduleClicked();
			}
		});
		GridBagConstraints gbc_buttonEditWeekSchedule = new GridBagConstraints();
		gbc_buttonEditWeekSchedule.anchor = GridBagConstraints.WEST;
		gbc_buttonEditWeekSchedule.insets = new Insets(0, 0, 5, 5);
		gbc_buttonEditWeekSchedule.gridx = 2;
		gbc_buttonEditWeekSchedule.gridy = 1;
		weekSchedulePanel.add(buttonEditWeekSchedule, gbc_buttonEditWeekSchedule);
		
		JButton buttonPrint = new JButton("Udskriv");
		buttonPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonPrintClicked();
			}
		});
		
		JButton btnAddPeriod = new JButton("Tilf\u00F8j");
		btnAddPeriod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonAddPeriodClicked();
			}
		});
		GridBagConstraints gbc_btnAddPeriod = new GridBagConstraints();
		gbc_btnAddPeriod.anchor = GridBagConstraints.WEST;
		gbc_btnAddPeriod.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddPeriod.gridx = 3;
		gbc_btnAddPeriod.gridy = 1;
		weekSchedulePanel.add(btnAddPeriod, gbc_btnAddPeriod);
		GridBagConstraints gbc_buttonPrint = new GridBagConstraints();
		gbc_buttonPrint.fill = GridBagConstraints.VERTICAL;
		gbc_buttonPrint.insets = new Insets(0, 0, 5, 0);
		gbc_buttonPrint.gridx = 4;
		gbc_buttonPrint.gridy = 1;
		weekSchedulePanel.add(buttonPrint, gbc_buttonPrint);
		
		textScheduleIDOne = new JTextField();
		textScheduleIDOne.setEditable(false);
		GridBagConstraints gbc_textScheduleIDOne = new GridBagConstraints();
		gbc_textScheduleIDOne.insets = new Insets(0, 0, 5, 5);
		gbc_textScheduleIDOne.fill = GridBagConstraints.HORIZONTAL;
		gbc_textScheduleIDOne.gridx = 0;
		gbc_textScheduleIDOne.gridy = 3;
		weekSchedulePanel.add(textScheduleIDOne, gbc_textScheduleIDOne);
		textScheduleIDOne.setColumns(10);
		
		textScheduleIDTwo = new JTextField();
		textScheduleIDTwo.setEditable(false);
		GridBagConstraints gbc_textScheduleIDTwo = new GridBagConstraints();
		gbc_textScheduleIDTwo.gridwidth = 2;
		gbc_textScheduleIDTwo.insets = new Insets(0, 0, 5, 5);
		gbc_textScheduleIDTwo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textScheduleIDTwo.gridx = 1;
		gbc_textScheduleIDTwo.gridy = 3;
		weekSchedulePanel.add(textScheduleIDTwo, gbc_textScheduleIDTwo);
		textScheduleIDTwo.setColumns(10);
		
		textScheduleIDThree = new JTextField();
		textScheduleIDThree.setEditable(false);
		GridBagConstraints gbc_textScheduleIDThree = new GridBagConstraints();
		gbc_textScheduleIDThree.insets = new Insets(0, 0, 5, 5);
		gbc_textScheduleIDThree.fill = GridBagConstraints.HORIZONTAL;
		gbc_textScheduleIDThree.gridx = 3;
		gbc_textScheduleIDThree.gridy = 3;
		weekSchedulePanel.add(textScheduleIDThree, gbc_textScheduleIDThree);
		textScheduleIDThree.setColumns(10);
		
		textScheduleIDFour = new JTextField();
		textScheduleIDFour.setEditable(false);
		GridBagConstraints gbc_textScheduleIDFour = new GridBagConstraints();
		gbc_textScheduleIDFour.insets = new Insets(0, 0, 5, 0);
		gbc_textScheduleIDFour.fill = GridBagConstraints.HORIZONTAL;
		gbc_textScheduleIDFour.gridx = 4;
		gbc_textScheduleIDFour.gridy = 3;
		weekSchedulePanel.add(textScheduleIDFour, gbc_textScheduleIDFour);
		textScheduleIDFour.setColumns(10);
		
		JScrollPane scrollPaneOne = new JScrollPane();
		GridBagConstraints gbc_scrollPaneOne = new GridBagConstraints();
		gbc_scrollPaneOne.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneOne.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneOne.gridx = 0;
		gbc_scrollPaneOne.gridy = 4;
		weekSchedulePanel.add(scrollPaneOne, gbc_scrollPaneOne);
		
		scheduleTableOne = new ScheduleTable();
		scheduleTableOne.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scheduleTableOne.setDefaultRenderer(Object.class, new MyTableCellRenderer());
		scrollPaneOne.setViewportView(scheduleTableOne);
		
		JScrollPane scrollPaneTwo = new JScrollPane();
		GridBagConstraints gbc_scrollPaneTwo = new GridBagConstraints();
		gbc_scrollPaneTwo.gridwidth = 2;
		gbc_scrollPaneTwo.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneTwo.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTwo.gridx = 1;
		gbc_scrollPaneTwo.gridy = 4;
		weekSchedulePanel.add(scrollPaneTwo, gbc_scrollPaneTwo);
		
		scheduleTableTwo = new ScheduleTable();
		scheduleTableTwo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scheduleTableTwo.setDefaultRenderer(Object.class, new MyTableCellRenderer());
		scrollPaneTwo.setViewportView(scheduleTableTwo);
		
		JScrollPane scrollPaneThree = new JScrollPane();
		GridBagConstraints gbc_scrollPaneThree = new GridBagConstraints();
		gbc_scrollPaneThree.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneThree.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneThree.gridx = 3;
		gbc_scrollPaneThree.gridy = 4;
		weekSchedulePanel.add(scrollPaneThree, gbc_scrollPaneThree);
		
	    scheduleTableThree = new ScheduleTable();
	    scheduleTableThree.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    scheduleTableThree.setDefaultRenderer(Object.class, new MyTableCellRenderer());
	    scrollPaneThree.setViewportView(scheduleTableThree);
		
		JScrollPane scrollPaneFour = new JScrollPane();
		GridBagConstraints gbc_scrollPaneFour = new GridBagConstraints();
		gbc_scrollPaneFour.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPaneFour.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneFour.gridx = 4;
		gbc_scrollPaneFour.gridy = 4;
		weekSchedulePanel.add(scrollPaneFour, gbc_scrollPaneFour);
		
		scheduleTableFour = new ScheduleTable();
		scheduleTableFour.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scheduleTableFour.setDefaultRenderer(Object.class, new MyTableCellRenderer());
		scrollPaneFour.setViewportView(scheduleTableFour);
		
		textScheduleIDFive = new JTextField();
		textScheduleIDFive.setEditable(false);
		GridBagConstraints gbc_textScheduleIDFive = new GridBagConstraints();
		gbc_textScheduleIDFive.insets = new Insets(0, 0, 5, 5);
		gbc_textScheduleIDFive.fill = GridBagConstraints.HORIZONTAL;
		gbc_textScheduleIDFive.gridx = 0;
		gbc_textScheduleIDFive.gridy = 6;
		weekSchedulePanel.add(textScheduleIDFive, gbc_textScheduleIDFive);
		textScheduleIDFive.setColumns(10);
		
		textScheduleIDSix = new JTextField();
		textScheduleIDSix.setEditable(false);
		GridBagConstraints gbc_textScheduleIDSix = new GridBagConstraints();
		gbc_textScheduleIDSix.gridwidth = 2;
		gbc_textScheduleIDSix.insets = new Insets(0, 0, 5, 5);
		gbc_textScheduleIDSix.fill = GridBagConstraints.HORIZONTAL;
		gbc_textScheduleIDSix.gridx = 1;
		gbc_textScheduleIDSix.gridy = 6;
		weekSchedulePanel.add(textScheduleIDSix, gbc_textScheduleIDSix);
		textScheduleIDSix.setColumns(10);
		
		textScheduleIDSeven = new JTextField();
		textScheduleIDSeven.setEditable(false);
		GridBagConstraints gbc_textScheduleIDSeven = new GridBagConstraints();
		gbc_textScheduleIDSeven.insets = new Insets(0, 0, 5, 5);
		gbc_textScheduleIDSeven.fill = GridBagConstraints.HORIZONTAL;
		gbc_textScheduleIDSeven.gridx = 3;
		gbc_textScheduleIDSeven.gridy = 6;
		weekSchedulePanel.add(textScheduleIDSeven, gbc_textScheduleIDSeven);
		textScheduleIDSeven.setColumns(10);
		
		JScrollPane scrollPaneFive = new JScrollPane();
		GridBagConstraints gbc_scrollPaneFive = new GridBagConstraints();
		gbc_scrollPaneFive.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneFive.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneFive.gridx = 0;
		gbc_scrollPaneFive.gridy = 7;
		weekSchedulePanel.add(scrollPaneFive, gbc_scrollPaneFive);
		
		scheduleTableFive = new ScheduleTable();
		scheduleTableFive.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scheduleTableFive.setDefaultRenderer(Object.class, new MyTableCellRenderer());
		scrollPaneFive.setViewportView(scheduleTableFive);
		
		JScrollPane scrollPaneSix = new JScrollPane();
		GridBagConstraints gbc_scrollPaneSix = new GridBagConstraints();
		gbc_scrollPaneSix.gridwidth = 2;
		gbc_scrollPaneSix.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneSix.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneSix.gridx = 1;
		gbc_scrollPaneSix.gridy = 7;
		weekSchedulePanel.add(scrollPaneSix, gbc_scrollPaneSix);
		
		scheduleTableSix = new ScheduleTable();
		scheduleTableSix.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scheduleTableSix.setDefaultRenderer(Object.class, new MyTableCellRenderer());
		scrollPaneSix.setViewportView(scheduleTableSix);
		
		JScrollPane scrollPaneSeven = new JScrollPane();
		GridBagConstraints gbc_scrollPaneSeven = new GridBagConstraints();
		gbc_scrollPaneSeven.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneSeven.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneSeven.gridx = 3;
		gbc_scrollPaneSeven.gridy = 7;
		weekSchedulePanel.add(scrollPaneSeven, gbc_scrollPaneSeven);
		
		scheduleTableSeven = new ScheduleTable();
		scheduleTableSeven.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scheduleTableSeven.setDefaultRenderer(Object.class, new MyTableCellRenderer());
		scrollPaneSeven.setViewportView(scheduleTableSeven);
		
		lblStatus = new JLabel("status");
		lblStatus.setOpaque(true);
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStatus.insets = new Insets(0, 0, 0, 5);
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 9;
		weekSchedulePanel.add(lblStatus, gbc_lblStatus);
		
	    // shortcut key for buttonEditWeekSchedule Alt + o
		buttonCreateWeekSchedule.setMnemonic(KeyEvent.VK_O);
		
		// shortcut key for buttonEditWeekSchedule Alt + r 
		buttonEditWeekSchedule.setMnemonic(KeyEvent.VK_R);
		
		// shortcut key for buttonAddWeekSchedule Alt + t 
		btnAddPeriod.setMnemonic(KeyEvent.VK_T);
		 
	    // shortcut key for buttonPrint Alt + u 
		buttonPrint.setMnemonic(KeyEvent.VK_U);
		 
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		
		init();
	}
	
	/**
	 * This method prints the content pane with the tables.
	 */
	private void buttonPrintClicked() {
		PrinterJob pjob = PrinterJob.getPrinterJob();
		PageFormat preformat = pjob.defaultPage();
		preformat.setOrientation(PageFormat.LANDSCAPE);
		PageFormat postformat = pjob.pageDialog(preformat);
		//If user does not hit cancel then print.
		if (preformat != postformat) {
		    //Set print component
		    pjob.setPrintable(new Printer(contentPane), postformat);
		    if (pjob.printDialog()) {
		        try {
					pjob.print();
				} catch (PrinterException e) {
					e.printStackTrace();
				}
		    }
		}
		
	}

	private void init() {
		Thread thread = new Thread(() -> DBStatusController.checkStatus(this::setStatus));
		thread.start();
		try {
			weekScheduleController = new WeekScheduleController();
			periodListOne = new ArrayList<>();
			periodListTwo = new ArrayList<>();
			periodListThree = new ArrayList<>();
			periodListFour = new ArrayList<>();
			periodListFive = new ArrayList<>();
			periodListSix = new ArrayList<>();
			periodListSeven = new ArrayList<>(); 
			
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(null, "DataAccessException", "DataAccessException", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setStatus(boolean connected) {
		EventQueue.invokeLater(() -> {
			if(connected) {
				lblStatus.setText("Connected");
				lblStatus.setBackground(Color.GREEN);
			}else {
				lblStatus.setText("Disconnected");
				lblStatus.setBackground(Color.RED);
			}
		});
	}
	
	/**
	 * This method edits a period in a table. On a selected table updateTable og setTableData is used. 
	 */
	private void buttonEditWeekScheduleClicked() {
		List<Period> currentPeriods=getSelectedPeriodList();
		//If a period is selected
		if(isPeriodsSelected(currentPeriods)) {
			for(int i = 0; i < currentPeriods.size() ; i++) {
				if (currentPeriods.get(i)!=null) {
					switch(i+1) {
					case 1:
						updateTable(currentPeriodOne, periodListOne, weekScheduleOne.getId());
						scheduleTableOne.setTableData(periodListOne);
						break;
					case 2:
						updateTable(currentPeriodTwo, periodListTwo, weekScheduleTwo.getId());
						scheduleTableTwo.setTableData(periodListTwo);
						break;
					case 3:
						updateTable(currentPeriodThree, periodListThree, weekScheduleThree.getId());
						scheduleTableThree.setTableData(periodListThree);
						break;
					case 4:
						updateTable(currentPeriodFour, periodListFour, weekScheduleFour.getId());
						scheduleTableFour.setTableData(periodListFour);
						break;
					case 5:	
						updateTable(currentPeriodFive, periodListFive, weekScheduleFive.getId());
						scheduleTableFive.setTableData(periodListFive);
						break;
					case 6:
						updateTable(currentPeriodSix, periodListSix, weekScheduleSix.getId());
						scheduleTableSix.setTableData(periodListSix);
						break;
					case 7:	
						updateTable(currentPeriodSeven, periodListSeven, weekScheduleSeven.getId());
						scheduleTableSeven.setTableData(periodListSeven);
						break;
					}
				}
			} 
		}else {
			 JOptionPane.showMessageDialog(null, "Vælg én række at redigere i.", "Fejl: ingen eller for mange rækker valgt.", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
    /**
	 * This method gets a list of the selected scheduleTable objects, from all the scheduleTables. 
	 * @return currentPeriods currentPeriods is a list of periods. 
	 */
	 private List<Period> getSelectedPeriodList(){
		List<Period> currentPeriods = new ArrayList<>();
		currentPeriodOne = getSelectedPeriodFromWeekScheduleTables(scheduleTableOne);
		currentPeriodTwo = getSelectedPeriodFromWeekScheduleTables(scheduleTableTwo);
		currentPeriodThree = getSelectedPeriodFromWeekScheduleTables(scheduleTableThree);
		currentPeriodFour = getSelectedPeriodFromWeekScheduleTables(scheduleTableFour);
		currentPeriodFive = getSelectedPeriodFromWeekScheduleTables(scheduleTableFive);
		currentPeriodSix = getSelectedPeriodFromWeekScheduleTables(scheduleTableSix);
		currentPeriodSeven = getSelectedPeriodFromWeekScheduleTables(scheduleTableSeven);
		
		currentPeriods.add(currentPeriodOne);
		currentPeriods.add(currentPeriodTwo);
		currentPeriods.add(currentPeriodThree);
		currentPeriods.add(currentPeriodFour);
		currentPeriods.add(currentPeriodFive);
		currentPeriods.add(currentPeriodSix);
		currentPeriods.add(currentPeriodSeven);
		
		return currentPeriods;	
	}
	 /**
		 * This method gets a list of the scheduleTable IDs, from all the scheduleTables. 
		 * @return scheduleIds scheduleIds is a list of all the schedule IDs. 
		 */
	private List<Integer> getScheduleIdList(){
		 List<Integer> scheduleIds= new ArrayList<>();
		 scheduleIds.add(weekScheduleOne.getId());
		 scheduleIds.add(weekScheduleTwo.getId());
		 scheduleIds.add(weekScheduleThree.getId());
		 scheduleIds.add(weekScheduleFour.getId());
		 scheduleIds.add(weekScheduleFive.getId());
		 scheduleIds.add(weekScheduleSix.getId());
		 scheduleIds.add(weekScheduleSeven.getId());
		 return scheduleIds;
	 }
	/**
	 *  This method adds a period On a selected table., after the buttonSaveClicked method has run. 
	 */
	private void buttonAddPeriodClicked() {
		AddPeriodGUI addPeriodGUI = new AddPeriodGUI();
		int id =  addPeriodGUI.showDialog();
		List<Period> periods = new ArrayList<>();
		List<Integer> scheduleIdList = getScheduleIdList();
		try {
			periods = weekScheduleController.findPeriodsByScheduleId(id,true);
			if (isSchedulesSelected(scheduleIdList,id)) {
			    for(int i = 0; i < scheduleIdList.size() ; i++) {
				   if (scheduleIdList.get(i) == id) {
					  switch(i+1) {
					   case 1:
						   scheduleTableOne.setTableData(periods);
						    break;
					   case 2:
							scheduleTableTwo.setTableData(periods);
							break;
					   case 3:
							scheduleTableThree.setTableData(periods);
							break;
					   case 4:
							scheduleTableFour.setTableData(periods);
							break;
					   case 5:	
							scheduleTableFive.setTableData(periods);
							break;
					   case 6:
							scheduleTableSix.setTableData(periods);
							break;
					   case 7:	
							scheduleTableSeven.setTableData(periods);
							break;
						}
				     }
			      } 
			} else {
				 JOptionPane.showMessageDialog(null, "Vælg table er ikke eksist.", "Fejl: ingen table valgt.", JOptionPane.ERROR_MESSAGE);
			}
		  } catch (DataAccessException e) {
			JOptionPane.showMessageDialog(null, "DataAccessException", "DataAccessException", JOptionPane.ERROR_MESSAGE);
		  }
	 }
	
	/**
	 * This method updates a period in a table, after the buttonSaveClicked method has run. 
	 * @param currentPeriod currentPeriod is the selected period in the table.
	 * @param periods periods is a list of periods. 
	 */
	private void updateTable(Period currentPeriod, List<Period> periods, int sceduleId) {
		EditPeriodGUI editPeriodGUI = new EditPeriodGUI(currentPeriod,sceduleId);
		Period editedPeriod = editPeriodGUI.showDialog();
		for(Period period:periods) {
			if (period.equals(currentPeriod )) {
				period.setEmployee(editedPeriod.getEmployee());
				period.setPost(editedPeriod.getPost());
			}
		}
    }
	/**
	 * This method updates the rows color with SpecialNeeds.REDUCED_TIME employee in a table, after the setRowColour method has run. 
	 * @param sceduleTable sceduleTable is the selected table.
	 * @param periods periods is a list of periods. 
	 */
	private void updateTableColour( List<Period> periods,ScheduleTable sceduleTable) {
		for(int i = 0 ; i<periods.size(); i++) {
			if (periods.get(i).getEmployee().getSpecialNeeds()==SpecialNeeds.REDUCED_TIME) {
				sceduleTable.setRowColour(i, Color.YELLOW);
			}
				
		}
	}
	/**
	 * This method gets a selected scheduleTable object, from one scheduleTable. 
	 * @param scheduleTable scheduleTable is a ScheduleTable. 
	 */
	private Period getSelectedPeriodFromWeekScheduleTables(ScheduleTable scheduleTable) {
		return scheduleTable.getSelectedObject();
	 }

	/**
	 * This method starts when the button 'createWeekSchedule' has been clicked. It puts periods into the seven table by using setTableData. 
	 */
	private void createWeekScheduleClicked() {
		String dateOne = textStartDate.getText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		try {
		LocalDate startDate = LocalDate.parse(dateOne, formatter);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			weekScheduleOne = weekScheduleController.createWeekSchedule(startDate);
			if(weekScheduleOne == null) {
				JOptionPane.showMessageDialog(null, "Vagtskema for startdato er allerede genereret", "Fejl: Skema findes allerede i databasen", JOptionPane.ERROR_MESSAGE);
			}else {
				weekScheduleTwo = weekScheduleController.createWeekSchedule(startDate.plusDays(7));
				weekScheduleThree = weekScheduleController.createWeekSchedule(startDate.plusDays(14));
				weekScheduleFour = weekScheduleController.createWeekSchedule(startDate.plusDays(21));
				weekScheduleFive = weekScheduleController.createWeekSchedule(startDate.plusDays(28));
				weekScheduleSix = weekScheduleController.createWeekSchedule(startDate.plusDays(35));
				weekScheduleSeven = weekScheduleController.createWeekSchedule(startDate.plusDays(42));
					
				//Table one
				textScheduleIDOne.setText("Skema nr: " + weekScheduleOne.getId());
				periodListOne = weekScheduleController.findPeriodsByScheduleId(weekScheduleOne.getId(), true);
				scheduleTableOne.setTableData(periodListOne);
				scheduleTableOne.setColumnName(startDate, 1);
				updateTableColour(periodListOne,scheduleTableOne);
				//Table two
				textScheduleIDTwo.setText("Skema nr: " + weekScheduleTwo.getId());
				periodListTwo = weekScheduleController.findPeriodsByScheduleId(weekScheduleTwo.getId(), true);
				scheduleTableTwo.setTableData(periodListTwo);
				scheduleTableTwo.setColumnName(startDate.plusDays(7), 1);
				 updateTableColour(periodListTwo,scheduleTableTwo);
				//Table three
				textScheduleIDThree.setText("Skema nr: " + weekScheduleThree.getId());
				periodListThree = weekScheduleController.findPeriodsByScheduleId(weekScheduleThree.getId(), true);
				scheduleTableThree.setTableData(periodListThree);
				scheduleTableThree.setColumnName(startDate.plusDays(14), 1);
				updateTableColour(periodListThree,scheduleTableThree);
				//Table four
				textScheduleIDFour.setText("Skema nr: " + weekScheduleFour.getId());
				periodListFour = weekScheduleController.findPeriodsByScheduleId(weekScheduleFour.getId(), true);
				scheduleTableFour.setTableData(periodListFour); 
				scheduleTableFour.setColumnName(startDate.plusDays(21), 1);
				updateTableColour(periodListFour,scheduleTableFour);
				//Table five 
				textScheduleIDFive.setText("Skema nr: " + weekScheduleFive.getId());
				periodListFive = weekScheduleController.findPeriodsByScheduleId(weekScheduleFive.getId(), true);
				scheduleTableFive.setTableData(periodListFive);
				scheduleTableFive.setColumnName(startDate.plusDays(28), 1);
				updateTableColour(periodListFive,scheduleTableFive);
				//Table six
				textScheduleIDSix.setText("Skema nr: " + weekScheduleSix.getId());
				periodListSix = weekScheduleController.findPeriodsByScheduleId(weekScheduleSix.getId(), true);
				scheduleTableSix.setTableData(periodListSix);
				scheduleTableSix.setColumnName(startDate.plusDays(35), 1);
				updateTableColour(periodListSix,scheduleTableSix);
				//Table seven
				textScheduleIDSeven.setText("Skema nr: " + weekScheduleSeven.getId());
				periodListSeven = weekScheduleController.findPeriodsByScheduleId(weekScheduleSeven.getId(), true);
				scheduleTableSeven.setTableData(periodListSeven); 
				scheduleTableSeven.setColumnName(startDate.plusDays(42), 1);
				updateTableColour(periodListSeven,scheduleTableSeven);
			}
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(null, "DataAccessException", "DataAccessException", JOptionPane.ERROR_MESSAGE);
		} catch (MatrixNotSquareException e) {
			JOptionPane.showMessageDialog(null, "The matrix is not square", "MatrixNotSquareException", JOptionPane.ERROR_MESSAGE);
		} catch (DateTimeParseException dtpe) {
			JOptionPane.showMessageDialog(null, "Date input must be dd-MM-yyyy", "Date incorrect", JOptionPane.ERROR_MESSAGE);
		}
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
	}

	/**
	 * This method checks if any periods has been selected or not. 
	 * @param periods periods is a list of periods. 
	 * @return found found is a boolean and returns true is a period is selected. 
	 */
	private boolean isPeriodsSelected(List<Period> periods) {
		boolean found=false;
		for(int i = 0; i < periods.size() ; i++) {
			if(periods.get(i)!= null) {
				found = true;
			}
		}
		return found;
	}
	
	/**
	 * This method checks if any schedule has been selected or not. 
	 * @param  scheduleIds  scheduleIds is a list of schedule IDs. 
	 * @return found found is a boolean and returns true is a schedule is selected. 
	 */
	private boolean isSchedulesSelected(List<Integer> scheduleIds, int scheduleId) {
		boolean found=false;
		for(int i = 0; i < scheduleIds.size() ; i++) {
			if(scheduleIds.get(i) == scheduleId) {
				found = true;
			}
		}
		return found;
	}
}
