package table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.PeriodController;
import database.DataAccessException;
import model.Employee;
import model.Period;
import model.Post;
import model.ScheduleType;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class AddPeriodGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textEmployeeFirstName;
	private JTextField textPost;
	private Period addedPeriod;
	private JTextField textEmployeeMiddleName;
	private PeriodController periodController;
	private JTextField textScheduleId;
	private JTextField textPeriodStartDate;
	private JTextField textScheduleType;
	private JTextField textEndDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddPeriodGUI dialog = new AddPeriodGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddPeriodGUI(String scheduleId) {
		setModal(true);
		setBounds(100, 100, 560, 386);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{136, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{14, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
			JLabel lblSceduleId = new JLabel("Scedule ID:");
			GridBagConstraints gbc_lblSceduleId = new GridBagConstraints();
			gbc_lblSceduleId.insets = new Insets(0, 0, 5, 5);
			gbc_lblSceduleId.anchor = GridBagConstraints.EAST;
			gbc_lblSceduleId.gridx = 0;
			gbc_lblSceduleId.gridy = 1;
			contentPanel.add(lblSceduleId, gbc_lblSceduleId);
		}
		{
			textScheduleId = new JTextField();
			GridBagConstraints gbc_textScheduleId = new GridBagConstraints();
			gbc_textScheduleId.insets = new Insets(0, 0, 5, 0);
			gbc_textScheduleId.fill = GridBagConstraints.HORIZONTAL;
			gbc_textScheduleId.gridx = 1;
			gbc_textScheduleId.gridy = 1;
			contentPanel.add(textScheduleId, gbc_textScheduleId);
			textScheduleId.setColumns(10);
		}
		{
			JLabel lblMedarbejder = new JLabel("Medarbejder:");
			GridBagConstraints gbc_lblMedarbejder = new GridBagConstraints();
			gbc_lblMedarbejder.anchor = GridBagConstraints.EAST;
			gbc_lblMedarbejder.insets = new Insets(0, 0, 5, 5);
			gbc_lblMedarbejder.gridx = 0;
			gbc_lblMedarbejder.gridy = 2;
			contentPanel.add(lblMedarbejder, gbc_lblMedarbejder);
		}
		{
			JLabel lblFornavn = new JLabel("Fornavn:");
			GridBagConstraints gbc_lblFornavn = new GridBagConstraints();
			gbc_lblFornavn.insets = new Insets(0, 0, 5, 5);
			gbc_lblFornavn.anchor = GridBagConstraints.EAST;
			gbc_lblFornavn.gridx = 0;
			gbc_lblFornavn.gridy = 3;
			contentPanel.add(lblFornavn, gbc_lblFornavn);
		}
		{
			textEmployeeFirstName = new JTextField();
			GridBagConstraints gbc_textEmployeeFirstName = new GridBagConstraints();
			gbc_textEmployeeFirstName.insets = new Insets(0, 0, 5, 0);
			gbc_textEmployeeFirstName.fill = GridBagConstraints.HORIZONTAL;
			gbc_textEmployeeFirstName.gridx = 1;
			gbc_textEmployeeFirstName.gridy = 3;
			contentPanel.add(textEmployeeFirstName, gbc_textEmployeeFirstName);
			textEmployeeFirstName.setColumns(10);
		}
		{
			JLabel lblMellemnavn = new JLabel("Mellemnavn:");
			GridBagConstraints gbc_lblMellemnavn = new GridBagConstraints();
			gbc_lblMellemnavn.insets = new Insets(0, 0, 5, 5);
			gbc_lblMellemnavn.anchor = GridBagConstraints.EAST;
			gbc_lblMellemnavn.gridx = 0;
			gbc_lblMellemnavn.gridy = 4;
			contentPanel.add(lblMellemnavn, gbc_lblMellemnavn);
		}
		{
			textEmployeeMiddleName = new JTextField();
			GridBagConstraints gbc_textEmployeeMiddleName = new GridBagConstraints();
			gbc_textEmployeeMiddleName.insets = new Insets(0, 0, 5, 0);
			gbc_textEmployeeMiddleName.fill = GridBagConstraints.HORIZONTAL;
			gbc_textEmployeeMiddleName.gridx = 1;
			gbc_textEmployeeMiddleName.gridy = 4;
			contentPanel.add(textEmployeeMiddleName, gbc_textEmployeeMiddleName);
			textEmployeeMiddleName.setColumns(10);
		}
		{
			JLabel lblArbejdspost = new JLabel("Arbejdspost:");
			GridBagConstraints gbc_lblArbejdspost = new GridBagConstraints();
			gbc_lblArbejdspost.anchor = GridBagConstraints.EAST;
			gbc_lblArbejdspost.insets = new Insets(0, 0, 5, 5);
			gbc_lblArbejdspost.gridx = 0;
			gbc_lblArbejdspost.gridy = 5;
			contentPanel.add(lblArbejdspost, gbc_lblArbejdspost);
		}
		{
			textPost = new JTextField();
			GridBagConstraints gbc_textPost = new GridBagConstraints();
			gbc_textPost.insets = new Insets(0, 0, 5, 0);
			gbc_textPost.fill = GridBagConstraints.HORIZONTAL;
			gbc_textPost.gridx = 1;
			gbc_textPost.gridy = 5;
			contentPanel.add(textPost, gbc_textPost);
			textPost.setColumns(10);
		}
		{
			JLabel lblPeriodStartDate = new JLabel("Period StarteDato: ");
			GridBagConstraints gbc_lblPeriodStartDate = new GridBagConstraints();
			gbc_lblPeriodStartDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblPeriodStartDate.anchor = GridBagConstraints.EAST;
			gbc_lblPeriodStartDate.gridx = 0;
			gbc_lblPeriodStartDate.gridy = 6;
			contentPanel.add(lblPeriodStartDate, gbc_lblPeriodStartDate);
		}
		{
			textPeriodStartDate = new JTextField();
			textPeriodStartDate.addFocusListener(new FocusAdapter() {
				@Override 
				public void focusGained(FocusEvent arg0) {
					textPeriodStartDate.setText("Start dato: dd-MM-YYYY");
				}
				@Override
				public void focusLost(FocusEvent e) {
					if(textPeriodStartDate.getText() == null || textPeriodStartDate.getText().equals(""))
						textPeriodStartDate.setText("Start dato");
				}
			});
			textPeriodStartDate.setText("Start dato: dd-MM-YYYY");
			GridBagConstraints gbc_textPeriodStartDate = new GridBagConstraints();
			gbc_textPeriodStartDate.insets = new Insets(0, 0, 5, 0);
			gbc_textPeriodStartDate.fill = GridBagConstraints.HORIZONTAL;
			gbc_textPeriodStartDate.gridx = 1;
			gbc_textPeriodStartDate.gridy = 6;
			contentPanel.add(textPeriodStartDate, gbc_textPeriodStartDate);
			textPeriodStartDate.setColumns(10);
		}
		{
			JLabel lblEndDate = new JLabel("Period SlutDato:");
			GridBagConstraints gbc_lblEndDate = new GridBagConstraints();
			gbc_lblEndDate.anchor = GridBagConstraints.EAST;
			gbc_lblEndDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblEndDate.gridx = 0;
			gbc_lblEndDate.gridy = 7;
			contentPanel.add(lblEndDate, gbc_lblEndDate);
		}
		{
			textEndDate = new JTextField();
			textEndDate.addFocusListener(new FocusAdapter() {
				@Override 
				public void focusGained(FocusEvent arg0) {
					textEndDate.setText("Slut dato: dd-MM-YYYY");
				}
				@Override
				public void focusLost(FocusEvent e) {
					if(textEndDate.getText() == null || textEndDate.getText().equals(""))
						textEndDate.setText("Slut dato: dd-MM-YYYY");
				}
			});
			 textEndDate.setText("Slut dato: dd-MM-YYYY");
			GridBagConstraints gbc_textEndDate = new GridBagConstraints();
			gbc_textEndDate.insets = new Insets(0, 0, 5, 0);
			gbc_textEndDate.fill = GridBagConstraints.HORIZONTAL;
			gbc_textEndDate.gridx = 1;
			gbc_textEndDate.gridy = 7;
			contentPanel.add(textEndDate, gbc_textEndDate);
			textEndDate.setColumns(10);
		}
		{
			JLabel lblScheduleType = new JLabel("Schedule Type:");
			lblScheduleType.setVerticalAlignment(SwingConstants.TOP);
			GridBagConstraints gbc_lblScheduleType = new GridBagConstraints();
			gbc_lblScheduleType.insets = new Insets(0, 0, 0, 5);
			gbc_lblScheduleType.anchor = GridBagConstraints.EAST;
			gbc_lblScheduleType.gridx = 0;
			gbc_lblScheduleType.gridy = 8;
			contentPanel.add(lblScheduleType, gbc_lblScheduleType);
		}
		{
			textScheduleType = new JTextField();
			GridBagConstraints gbc_textScheduleType = new GridBagConstraints();
			gbc_textScheduleType.fill = GridBagConstraints.HORIZONTAL;
			gbc_textScheduleType.gridx = 1;
			gbc_textScheduleType.gridy = 8;
			contentPanel.add(textScheduleType, gbc_textScheduleType);
			textScheduleType.setColumns(10);
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
		init(scheduleId);
	}
	
	/**
	 * Makes it possible to make the constructor without parameters.
	 */
	public AddPeriodGUI() {
		this(null);
		setModal(true);
	}
	
	
	private void init(String scheduleId) {
		try {
			periodController = new PeriodController();
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(null, "DataAccessException", "DataAccessException", JOptionPane.ERROR_MESSAGE);
		}
		    addedPeriod= new Period();
		    textScheduleId.setText(scheduleId);
		    textScheduleType.setText("WEEK");
	}
	

	/**
	 * This method creates a new period. 
	 * @return addedPeriod addedPeriod is the period that has been added. 
	 */
	public void buttonSaveClicked() {
		String dateOne = textPeriodStartDate.getText();
		String dateTwo = textEndDate.getText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate startDate = LocalDate.parse(dateOne, formatter);
		LocalDate endDate = LocalDate.parse(dateTwo, formatter);
		List<Employee> employees= new ArrayList<>();
		List<Post> posts= new ArrayList<>();
		String employeeFirstName = textEmployeeFirstName.getText();
		//String employeeMiddleName = textEmployeeMiddleName.getText();
		String postName = textPost.getText();
		int scheduleId = Integer.parseInt(textScheduleId.getText());
		
		try {
			posts = periodController.findAllPosts();
				for (Post post:posts) {
					if (post.getName().equals(postName)) {
						addedPeriod.setPost(post);
					}		
				}
			if(addedPeriod.getPost()!=null)	{
			    employees = periodController.findEmployeesByPostId(addedPeriod.getPost().getId());	
			    for (Employee employee : employees) {
			          if (employee.getFirstName().equals(employeeFirstName)) {
			        	      addedPeriod.setEmployee(employee);
			        	  }
				 } 
			     
			    if (addedPeriod.getEmployee()==null) {    
					  JOptionPane.showMessageDialog(null, "Employee name is wrong", "Employee cannot be found",
					   JOptionPane.ERROR_MESSAGE);
			    }
			}
			if (addedPeriod.getPost()==null) {        		
				JOptionPane.showMessageDialog(null, "Post name is wrong", "Post cannot be found",
						JOptionPane.ERROR_MESSAGE);
		   } 
			else if (addedPeriod.getPost()!=null && addedPeriod.getEmployee()!=null){
				  addedPeriod.setStartDate(startDate);
				  addedPeriod.setEndDate(endDate, ScheduleType.WEEK);
				  int employeeId = addedPeriod.getEmployee().getId();
				  int postId = addedPeriod.getPost().getId();
			      periodController.createInsertPeriod(employeeId, postId, startDate, scheduleId, ScheduleType.WEEK);
			}
		
		  } catch (DataAccessException e){
			JOptionPane.showMessageDialog(null, "DataAccessException", "DataAccessException", JOptionPane.ERROR_MESSAGE);
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


