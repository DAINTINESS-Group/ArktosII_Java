package GUI;

import java.awt.Font;
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

public class formAggregation extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JComboBox<String> comboBox;
	private JTextField textField_1;
	private JButton okButton;
	private JButton cancelButton;
	

	public formAggregation() {
		setUndecorated(true);
		setTitle("Aggregation");
		setAlwaysOnTop(true);
		setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 310, 130);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
				}
			});
			cancelButton.setActionCommand("Cancel");
		}
		
		JLabel lblPosition = new JLabel("Group by : ");
		lblPosition.setBounds(75, 55, 63, 14);
		lblPosition.setHorizontalAlignment(SwingConstants.CENTER);
		
		textField = new JTextField();
		textField.setBounds(160, 52, 102, 20);
		textField.setColumns(10);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 10, 63, 20);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Sum", "Count", "Min", "Max", "Avg"}));
		comboBox.setMaximumRowCount(5);
		
		textField_1 = new JTextField();
		textField_1.setBounds(95, 10, 102, 20);
		textField_1.setColumns(10);
		panel.setLayout(null);
		panel.add(comboBox);
		panel.add(textField_1);
		panel.add(lblPosition);
		panel.add(textField);
		
		JLabel label = new JLabel("(");
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label.setBounds(82, 0, 14, 38);
		panel.add(label);
		
		JLabel label_1 = new JLabel(")");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_1.setBounds(200, 0, 14, 38);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("(");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_2.setBounds(148, 41, 14, 43);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel(")");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_3.setBounds(265, 41, 14, 43);
		panel.add(label_3);
		getContentPane().setLayout(new MigLayout("", "[127.00px][89.00px][141px]", "[105.00px][23px]"));
		getContentPane().add(okButton, "cell 0 1,alignx right,aligny top");
		getContentPane().add(cancelButton, "cell 2 1,alignx left,aligny top");
		getContentPane().add(panel, "cell 0 0 3 1,grow");
	}
	
	
	public String getGroupFields(){
		return textField.getText().trim();
	}
	
	public String getAggregateField(){
		return textField_1.getText().trim();
	}
	
	public String getAggregateFunc(){
		return comboBox.getSelectedItem().toString().trim();
	}
}
