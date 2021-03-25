package table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.PeriodController;
import controller.PostController;
import database.DataAccessException;
import model.Employee;
import model.Period;
import model.Post;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class EditPeriodGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textEmployeeFirstName;
	private JTextField textPost;
	private Period editedPeriod;
	private JTextField textEmployeeMiddleName;
	private PeriodController periodController;
	private JTextField textScheduleId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditPeriodGUI dialog = new EditPeriodGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditPeriodGUI(Period currentPeriod,int sceduleId) {
		setModal(true);
		setBounds(100, 100, 560, 386);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{136, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{14, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblRedigerVagtplanen = new JLabel("Rediger vagtplanen ");
			lblRedigerVagtplanen.setFont(new Font("Tahoma", Font.PLAIN, 14));
			GridBagConstraints gbc_lblRedigerVagtplanen = new GridBagConstraints();
			gbc_lblRedigerVagtplanen.insets = new Insets(0, 0, 5, 5);
			gbc_lblRedigerVagtplanen.anchor = GridBagConstraints.NORTH;
			gbc_lblRedigerVagtplanen.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblRedigerVagtplanen.gridx = 0;
			gbc_lblRedigerVagtplanen.gridy = 0;
			contentPanel.add(lblRedigerVagtplanen, gbc_lblRedigerVagtplanen);
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
			gbc_lblArbejdspost.gridy = 6;
			contentPanel.add(lblArbejdspost, gbc_lblArbejdspost);
		}
		{
			textPost = new JTextField();
			GridBagConstraints gbc_textPost = new GridBagConstraints();
			gbc_textPost.insets = new Insets(0, 0, 5, 0);
			gbc_textPost.fill = GridBagConstraints.HORIZONTAL;
			gbc_textPost.gridx = 1;
			gbc_textPost.gridy = 6;
			contentPanel.add(textPost, gbc_textPost);
			textPost.setColumns(10);
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
				// shortcut key for buttonSave Alt + g
				buttonSave.setMnemonic(KeyEvent.VK_G);
			}
			{
				JButton buttonCancel = new JButton("Annuller");
				buttonCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonCancelClicked();
					}
				});
				buttonCancel.setActionCommand("Cancel");
				buttonPane.add(buttonCancel);
				// shortcut key for buttonEditWeekScheduleClicked() Alt + a
				buttonCancel.setMnemonic(KeyEvent.VK_A);
			}
			
			
		}
		init(currentPeriod, sceduleId);
	}
	/**
	 * Makes it possible to make the constructor without parameters.
	 */
	public EditPeriodGUI() {
		this(null, 0);
		setModal(true);
	}
	public Period showDialog() {
		this.setVisible(true);
		return editedPeriod;
		
	}
	
	private void init(Period currentPeriod,int sceduleId) {
		try {
			periodController = new PeriodController();
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(null, "DataAccessException", "DataAccessException", JOptionPane.ERROR_MESSAGE);
		}
				
		this.editedPeriod = currentPeriod;
		if(editedPeriod != null) {
			textEmployeeFirstName.setText(editedPeriod.getEmployee().getFirstName());
			textScheduleId.setText(""+sceduleId);
			if(editedPeriod.getEmployee().getMiddleName() != null) {
				textEmployeeMiddleName.setText(editedPeriod.getEmployee().getMiddleName());
			}
			textPost.setText(editedPeriod.getPost().getName());
		}
	}

	/**
	 * This method saves the changes made to a period. 
	 * @return editedPeriod editedPeriod is the period that has been changed. 
	 */
	private void buttonSaveClicked() {
		List<Employee> employees= new ArrayList<>();
		List<Post> posts= new ArrayList<>();
		String employeeFirstName = textEmployeeFirstName.getText();
		String employeeMiddleName = textEmployeeMiddleName.getText();
		String postName = textPost.getText();
		try {
			employees = periodController.findAllEmployees();
			posts = periodController.findAllPosts();
			
		    if(editedPeriod != null) {
		    	for (Employee employee : employees) {
			          if (employee.getFirstName().equalsIgnoreCase(employeeFirstName)) {
			        	  if(employee.getMiddleName()!=null && employeeMiddleName!=null) {
			        		  if(employee.getMiddleName().equalsIgnoreCase(employeeMiddleName)) {
			        		     editedPeriod.setEmployee(employee);
			        	      } else {
	        		              JOptionPane.showMessageDialog(null, "Employee middlename is wrong", "Employee cannot be found",
							       JOptionPane.ERROR_MESSAGE);
			        	      }      
			        	   } else {	  
			        	      editedPeriod.setEmployee(employee);
			        	  }
				     } 
				}      
				for (Post post:posts) {
					if (post.getName().equalsIgnoreCase(postName)) {
						editedPeriod.setPost(post);
					}		
				}
				if (!editedPeriod.getEmployee().getFirstName().equalsIgnoreCase(employeeFirstName)) {    
						  JOptionPane.showMessageDialog(null, "Employee name is wrong", "Employee cannot be found",
						   JOptionPane.ERROR_MESSAGE);
				 }	else if (!editedPeriod.getPost().getName().equalsIgnoreCase(postName)) {        		
						JOptionPane.showMessageDialog(null, "Post name is wrong", "Post cannot be found",
								JOptionPane.ERROR_MESSAGE);
				  } else {
				    periodController.updatePeriod(editedPeriod.getId(),editedPeriod.getPost().getId(),editedPeriod.getEmployee().getId());
				  }
		    } 
		  } catch (DataAccessException e) {
			JOptionPane.showMessageDialog(null, "DataAccessException", "DataAccessException", JOptionPane.ERROR_MESSAGE);
		  }	
		buttonCancelClicked();
	}


	/**
	 * This method closes the EditPeriodGUI. 
	 */
	private void buttonCancelClicked() {
		this.setVisible(false);
		this.dispose();
	}
}
