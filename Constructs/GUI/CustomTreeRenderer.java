package GUI;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

class CustomTreeRenderer implements TreeCellRenderer{
	JLabel label;
	JRadioButton radioBtn;
	Color textSelectionColor, textNonSelectionColor, backgroundSelectionColor, backgroundNonSelectionColor;

	
	public CustomTreeRenderer(){
		label = new JLabel();
		label.setOpaque(true);
		radioBtn = new JRadioButton();
		radioBtn.setBackground(UIManager.getColor("Tree.background"));
		
		textSelectionColor = UIManager.getColor("Tree.selectionForeground");
		textNonSelectionColor = UIManager.getColor("Tree.textForeground");
		backgroundSelectionColor = UIManager.getColor("Tree.selectionBackground");
		backgroundNonSelectionColor = UIManager.getColor("Tree.textBackground");
	}
	
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object userObj = node.getUserObject();

		Component c = null;
		if(userObj instanceof IconNode){
			IconNode icNd = (IconNode) userObj;
			
			label.setIcon(icNd.getIcon());
			label.setText(icNd.toString());
			label.setEnabled(tree.isEnabled());
			c = label;
			
		}
		else if(userObj instanceof RadioNode){
			RadioNode chckNode = (RadioNode) userObj;
			
			radioBtn.setSelected(chckNode.selected);
			radioBtn.setText(chckNode.toString());
			radioBtn.setEnabled(true);
			c = radioBtn;
		}
		
		
		if(selected){
			c.setForeground(textSelectionColor);
			c.setBackground(backgroundSelectionColor);
		}
		else{
			c.setForeground(textNonSelectionColor);
			c.setBackground(backgroundNonSelectionColor);
		}
		
		
		return c;
		
	}
	
}


class RadioNode extends JRadioButton{
	private static final long serialVersionUID = 1L;
	String name;
	boolean selected;
	
	public RadioNode(String s, boolean b){
		name = s;
		selected = b;
	}
	
	public String toString(){
		return name;
	}
	
	public void setSelected(boolean b){
		selected = b;
	}
	
	public boolean isSelected(){
		return selected;
	}

}


class IconNode{
	String name;
	Icon icon;
	
	public IconNode(String s, Icon i){
		name = s;
		icon = i;
	}
	
	public String toString(){
		return name;
	}
	
	public Icon getIcon(){
		return icon;
	}
}