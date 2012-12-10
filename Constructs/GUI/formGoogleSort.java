package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class formGoogleSort extends JDialog {
	private static final long serialVersionUID = 1L;
	private String order;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton okButton;
	private JButton cancelButton;
	

	public formGoogleSort() {
		setUndecorated(true);
		setTitle("Google Sort");
		setAlwaysOnTop(true);
		setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 310, 130);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		
		JLabel lblPosition = new JLabel("Sort by Fields  :");
		lblPosition.setBounds(10, 16, 82, 14);
		lblPosition.setHorizontalAlignment(SwingConstants.CENTER);
		
		textField = new JTextField();
		textField.setText("3, 4, 5, ....");
		textField.setBounds(106, 13, 95, 20);
		textField.setColumns(10);
		
		JRadioButton rdbtnAscending = new JRadioButton("asc");
		rdbtnAscending.setBounds(170, 45, 41, 23);
		rdbtnAscending.setSelected(true);
		order = "asc";
		rdbtnAscending.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				order = "asc";
			}
		});
		buttonGroup.add(rdbtnAscending);
		
		JRadioButton rdbtnDescending = new JRadioButton("desc");
		rdbtnDescending.setBounds(221, 45, 47, 23);
		rdbtnDescending.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				order = "desc";
			}
		});
		buttonGroup.add(rdbtnDescending);
		
		JLabel lblOrderBy = new JLabel("Order by : ");
		lblOrderBy.setBounds(105, 49, 64, 14);
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
		getContentPane().setLayout(new MigLayout("", "[105.00px][60.00px][120px]", "[105.00px][23px]"));
		getContentPane().add(okButton, "cell 0 1,alignx right,aligny top");
		getContentPane().add(cancelButton, "cell 2 1,alignx left,aligny top");
		getContentPane().add(panel, "cell 0 0 3 1,grow");
		
		JLabel label = new JLabel("(");
		label.setBounds(96, 13, 14, 21);
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel label_1 = new JLabel(")");
		label_1.setBounds(202, 6, 9, 32);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.setLayout(null);
		panel.add(lblPosition);
		panel.add(textField);
		panel.add(label);
		panel.add(label_1);
		panel.add(lblOrderBy);
		panel.add(rdbtnAscending);
		panel.add(rdbtnDescending);
	}
	
	
	public String getsFields(){
		return textField.getText().trim();
	}
	
	public String getOrder(){
		return order;
	}
	
}
