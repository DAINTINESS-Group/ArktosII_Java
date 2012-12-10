package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class formFilter extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JButton okButton;
	private JButton cancelButton;
	private JComboBox<String> comboBox;

	public formFilter() {
		setUndecorated(true);
		setTitle("Filter");
		setAlwaysOnTop(true);
		setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 310, 150);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		textField = new JTextField();
		textField.setBounds(103, 11, 120, 20);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(173, 57, 102, 20);
		textField_1.setColumns(10);
		
		JLabel lblCondition = new JLabel("Condition :");
		lblCondition.setBounds(58, 59, 65, 17);
		lblCondition.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblFilterFields = new JLabel("Filter Fields :");
		lblFilterFields.setBounds(31, 13, 65, 17);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"=", "<", ">", "<=", ">=", "<>"}));
		comboBox.setBounds(126, 57, 41, 20);
		{
			okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					textField.setText("");
					textField_1.setText("");
				}
			});
			cancelButton.setActionCommand("Cancel");
		}
		panel.setLayout(null);
		panel.add(lblCondition);
		panel.add(lblFilterFields);
		panel.add(comboBox);
		panel.add(textField_1);
		panel.add(textField);
		getContentPane().setLayout(new MigLayout("", "[109px][62.00px][117.00px]", "[115.00px][23px]"));
		getContentPane().add(okButton, "cell 0 1,alignx right,aligny top");
		getContentPane().add(cancelButton, "cell 2 1,alignx left,aligny top");
		getContentPane().add(panel, "cell 0 0 3 1,grow");
	}
	
	public String getField(){
		return textField.getText();
	}
	
	public String getCondition(){
		return comboBox.getModel().getSelectedItem().toString();
	}
	
	public String getCheckValue(){
		return textField_1.getText();
	}
}
