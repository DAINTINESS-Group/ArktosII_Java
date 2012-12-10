package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import Execution.Monitor;
import Execution.Optimizer;
import Input_Output.AntiParser;
import Input_Output.Parser;
import Input_Output.TextAntiParser;
import Input_Output.TextParser;
import Logical.Aggregator;
import Logical.Filter;
import Logical.Node;
import Logical.NotNull;
import Logical.RecordSet;
import Logical.Sorter;
import Tools.EngineCore;
import javax.swing.border.CompoundBorder;


public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JRadioButtonMenuItem rdbtnmntmAggregationWithHashmap;
	private JRadioButtonMenuItem rdbtnmntmGoogleSort;
	private ButtonGroup btnGrpSort = new ButtonGroup();
	private ButtonGroup btnGrpAggreg = new ButtonGroup();
	private JScrollPane scrollPane_1;
	private Canvas canvas = new Canvas();
	private static int itemId = 0;
	private static long startTime;
	private Monitor mntr;
	private Optimizer optmzer;
	
	public volatile static JTextArea textArea = new JTextArea();
	public static JButton btnRun = new JButton("");
	public static JProgressBar progressBar;

 	
 	
	public Frame() {
		setName("ETL-UoI");
		setTitle("ETL-UoI");
		setPreferredSize(new Dimension(1100, 650));
		setMaximumSize(new Dimension(2800, 1080));
		setMinimumSize(new Dimension(1100, 650));
		
		mntr = new Monitor("Monitor");
		mntr.start();

		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.1);
		splitPane.setBackground(new Color(128, 128, 128));
		splitPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		splitPane.setEnabled(true);
		splitPane.setDividerSize(6);
		splitPane.setMinimumSize(new Dimension(0, 0));
		splitPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(188, 190, 215));
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1084, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 1064, Short.MAX_VALUE)
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		splitPane.setLeftComponent(panel_1);
		canvas.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		canvas.setBackground(new Color(250, 240, 230));
		
		
		progressBar = new JProgressBar();
		progressBar.setOpaque(true);
		progressBar.setForeground(new Color(204, 153, 153));
		progressBar.setRequestFocusEnabled(false);
		progressBar.setBackground(new Color(248, 248, 255));
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setBorder(null);
		splitPane_2.setResizeWeight(0.9);
		splitPane_2.setDividerSize(3);
		splitPane_2.setEnabled(false);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addComponent(splitPane_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
				.addComponent(progressBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(splitPane_2, GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
		);
		
		JPanel panel_5 = new JPanel();
		panel_5.setAlignmentX(Component.LEFT_ALIGNMENT);
		splitPane_2.setRightComponent(panel_5);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(255, 250, 240));
		panel_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_7.setBorder(new TitledBorder(new LineBorder(new Color(72, 61, 139), 1, true), "Tuning Parameters", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(72, 61, 139)));
		
		final JSpinner timeSlot = new JSpinner();
		timeSlot.setOpaque(false);
		timeSlot.setModel(new SpinnerNumberModel(50, 1, 9999, 5));
		timeSlot.setFont(new Font("SansSerif", Font.PLAIN, 11));
		
		JLabel lblTimeSlot = new JLabel("Time slot :");
		lblTimeSlot.setForeground(new Color(0, 0, 0));
		lblTimeSlot.setBackground(new Color(204, 204, 153));
		lblTimeSlot.setFont(new Font("Ebrima", Font.PLAIN, 12));
		
		final JSpinner rowPackSize = new JSpinner();
		rowPackSize.setOpaque(false);
		rowPackSize.setModel(new SpinnerNumberModel(200, 1, 9999, 2));
		rowPackSize.setFont(new Font("SansSerif", Font.PLAIN, 11));
		
		JLabel lblRowpackSize = new JLabel("RowPack Size :");
		lblRowpackSize.setForeground(new Color(0, 0, 0));
		lblRowpackSize.setHorizontalTextPosition(SwingConstants.CENTER);
		lblRowpackSize.setFont(new Font("Ebrima", Font.PLAIN, 12));
		
		JLabel lblDataqueueSize = new JLabel("DataQueue \r\nSize :");
		lblDataqueueSize.setForeground(new Color(0, 0, 0));
		lblDataqueueSize.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDataqueueSize.setFont(new Font("Ebrima", Font.PLAIN, 12));
		
		final JSpinner dataQueueSize = new JSpinner();
		dataQueueSize.setOpaque(false);
		dataQueueSize.setModel(new SpinnerNumberModel(200, 1, 9999, 1));
		dataQueueSize.setName("");
		dataQueueSize.setFont(new Font("SansSerif", Font.PLAIN, 11));
		
		JLabel lblrps = new JLabel("(#RPs) ");
		lblrps.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lbltuples = new JLabel("(#Tuples)");
		lbltuples.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblmsec = new JLabel("(msec)");
		lblmsec.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblTimeSlot, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblRowpackSize, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblDataqueueSize, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(6)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.TRAILING)
						.addComponent(lbltuples, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
						.addComponent(timeSlot, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
						.addComponent(rowPackSize)
						.addComponent(dataQueueSize, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
						.addComponent(lblrps, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
						.addComponent(lblmsec, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGap(29)
					.addComponent(lblmsec)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTimeSlot)
						.addComponent(timeSlot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lbltuples, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
						.addComponent(rowPackSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRowpackSize))
					.addGap(18)
					.addComponent(lblrps, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDataqueueSize)
						.addComponent(dataQueueSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(60, Short.MAX_VALUE))
		);
		panel_7.setLayout(gl_panel_7);
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
		);
		panel_5.setLayout(gl_panel_5);
		
		scrollPane_1 = new JScrollPane();
		splitPane_2.setLeftComponent(scrollPane_1);
		
		final JTree tree = new JTree(getPopulatedTreeRoot());
		tree.setVisibleRowCount(10);
		tree.setToggleClickCount(1);

		if(EngineCore.isWindows)
			tree.setCellRenderer(new CustomTreeRenderer());
		tree.setBackground(new Color(255, 250, 240));
		tree.setRowHeight(20);
		tree.setRootVisible(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				TreePath clickedPath = tree.getPathForLocation(arg0.getX(), arg0.getY());
				
				if(clickedPath != null){
					String selectedPath = clickedPath.toString();
					if(selectedPath.contains(",")){
						String[] splitPath = selectedPath.trim().split(",");
						if(splitPath.length > 2){
							String selectedLeaf = splitPath[2].trim();
							
							switch(selectedLeaf.substring(0, selectedLeaf.length()-1).trim()){
							case "Data Source":
								addSourceNode();
						        break;
								
							case "Data Destination":
								addTargetNode();
								break;
								
							case "Filter":
								addFilterNode();
								break;
								
							case "Not-Null Filter":
								addNotNullNode();
								break;
								
							case "Aggregate":
								addAggregateNode();
								break;
								
							case "Sort":
								addSortNode();
								break;
															
							case "Round Robin":
								DefaultMutableTreeNode nodeRR = (DefaultMutableTreeNode)clickedPath.getLastPathComponent();
								Object userObjRR = nodeRR.getUserObject();
								
								if(userObjRR instanceof RadioNode){
									if(!((RadioNode) userObjRR).selected){
										EngineCore.engineScheduler = EngineCore.RoundRobin;
										for(int i=0; i<nodeRR.getParent().getChildCount(); i++){
											DefaultMutableTreeNode childDS = (DefaultMutableTreeNode)nodeRR.getParent().getChildAt(i);
											((RadioNode)childDS.getUserObject()).setSelected(false);
											((DefaultTreeModel) tree.getModel()).nodeChanged(childDS);
										}
										((RadioNode) userObjRR).setSelected(true);
										((DefaultTreeModel) tree.getModel()).nodeChanged(nodeRR);
									}
								}

								break;
							}
						}
					}
				}
			}
		});

		scrollPane_1.setViewportView(tree);
		splitPane_2.setDividerLocation(250);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 204, 255), new Color(204, 204, 255), new Color(204, 204, 255), new Color(204, 204, 255)));
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setPreferredSize(new Dimension(50, 22));
		mnFile.setHorizontalAlignment(SwingConstants.RIGHT);
		mnFile.setHorizontalTextPosition(SwingConstants.RIGHT);
		mnFile.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		mnFile.setMinimumSize(new Dimension(40, 22));
		mnFile.setIconTextGap(5);
		menuBar.add(mnFile);
		
		JMenu mnEdit = new JMenu("Settings");
		mnEdit.setPreferredSize(new Dimension(60, 22));
		mnEdit.setHorizontalAlignment(SwingConstants.CENTER);
		mnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
		mnEdit.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		mnEdit.setMinimumSize(new Dimension(60, 22));
		menuBar.add(mnEdit);
		
		final JRadioButtonMenuItem rdbtnmntmUnixSort = new JRadioButtonMenuItem("Unix Sort");
		rdbtnmntmUnixSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EngineCore.isUnixSort = true;
			}
		});
		rdbtnmntmUnixSort.setSelected(true);
		mnEdit.add(rdbtnmntmUnixSort);
		btnGrpSort.add(rdbtnmntmUnixSort);
		
		rdbtnmntmGoogleSort = new JRadioButtonMenuItem("Google Sort");
		rdbtnmntmGoogleSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EngineCore.isUnixSort = false;
			}
		});
		mnEdit.add(rdbtnmntmGoogleSort);
		btnGrpSort.add(rdbtnmntmGoogleSort);
		
		JSeparator separator_2 = new JSeparator();
		mnEdit.add(separator_2);
		
		final JRadioButtonMenuItem rdbtnmntmAggregationWithSort = new JRadioButtonMenuItem("Aggregation with Sort");
		rdbtnmntmAggregationWithSort.setSelected(true);
		mnEdit.add(rdbtnmntmAggregationWithSort);
		btnGrpAggreg.add(rdbtnmntmAggregationWithSort);
		
		rdbtnmntmAggregationWithHashmap = new JRadioButtonMenuItem("Aggregation with HashMap");
		mnEdit.add(rdbtnmntmAggregationWithHashmap);
		btnGrpAggreg.add(rdbtnmntmAggregationWithHashmap);
		

		JMenuItem mntmOpenScenario = new JMenuItem("Open Scenario");
		mntmOpenScenario.setIcon(new ImageIcon(Frame.class.getResource("/Icons/openScnrio.gif")));
		mntmOpenScenario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mntmOpenScenario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnmntmGoogleSort.isSelected())
					EngineCore.isUnixSort = false;
				else
					EngineCore.isUnixSort = true;
				
				JFileChooser fileChooser = new JFileChooser();
		        if (fileChooser.showOpenDialog(canvas) == JFileChooser.APPROVE_OPTION){
		        	String inputFile = fileChooser.getSelectedFile().getAbsolutePath().toString();
		        	
		        	textArea.append("Start parsing... ");
		        	Parser tp = new TextParser(inputFile);
	        		canvas.setDAG(tp.getFullScenario());
	        		textArea.append("OK!\nScenario Loaded !!!\n");
	        	
		        	int maxId = tp.getFullScenario().get(0).getId();
		        	for(int i=0; i<tp.getFullScenario().size(); i++){
		        		if(maxId < tp.getFullScenario().get(i).getId())
		        			maxId = tp.getFullScenario().get(i).getId();
		        	}
		        	itemId = maxId+1;
		        	tp.closeParser();
		        	
		        	if(EngineCore.isUnixSort){
		        		rdbtnmntmUnixSort.setSelected(true);
		        	}
		        	else{
		        		rdbtnmntmGoogleSort.setSelected(true);
		        	}
		        	
		        	if(EngineCore.isAggregationWithSort){
		        		rdbtnmntmAggregationWithSort.setSelected(true);
		        	}
		        	else{
		        		rdbtnmntmAggregationWithHashmap.setSelected(true);
		        	}
		        	
		        	canvas.repaint();
		        }
		        else{
		            System.out.println("File access cancelled by user.");
		        }
			}
		});
		mnFile.add(mntmOpenScenario);
		
		JMenuItem mntmSaveScenarioAs = new JMenuItem("Save Scenario As..");
		mntmSaveScenarioAs.setIcon(new ImageIcon(Frame.class.getResource("/Icons/saveScnrio.gif")));
		mntmSaveScenarioAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmSaveScenarioAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser pathChooser = new JFileChooser();
				pathChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
				
		        if (pathChooser.showSaveDialog(canvas) == JFileChooser.APPROVE_OPTION){
		            String outputFile = pathChooser.getSelectedFile().getAbsolutePath().toString();
		            
					textArea.append("Start Optimizing...");
					optmzer = new Optimizer(canvas.getScenario());
					AntiParser tap = new TextAntiParser(outputFile);
		            tap.setFullScenario(optmzer.optimize());
		            tap.closeAntiParser();
 					textArea.append(" OK!\nScenario Saved !!!\n");
					
		        }
		        else{
		            System.out.println("File access cancelled by user.");
		        }
			}
		});
		mnFile.add(mntmSaveScenarioAs);
		
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(2000, 2));
		menuBar.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Quit   ");
		mntmExit.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		mntmExit.setPreferredSize(new Dimension(80, 22));
		mntmExit.setHorizontalTextPosition(SwingConstants.LEFT);
		mntmExit.setHorizontalAlignment(SwingConstants.TRAILING);
		mntmExit.setMinimumSize(new Dimension(100, 22));
		menuBar.add(mntmExit);
		mntmExit.setIcon(new ImageIcon(Frame.class.getResource("/Icons/logout.png")));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mntr.quit();
				
				mntr.join();
				
				File dir;
				if(EngineCore.isWindows){
					dir = new File(System.getProperty("user.dir")+"\\Cache");
				}
				else{
					dir = new File("/"+System.getProperty("user.dir")+"/Cache");
				}
				
				if(dir.exists()){
					File[] files = dir.listFiles();
					for(File fil : files){
						fil.delete();
					}
				}
				dir.delete();
				
				System.exit(0);
			}
		});
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		splitPane_1.setPreferredSize(new Dimension(150, 50));
		splitPane_1.setResizeWeight(0.9);
		splitPane_1.setMinimumSize(new Dimension(0, 0));
		splitPane_1.setOneTouchExpandable(true);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
		);
		
		JPanel panel_3 = new JPanel(){
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g.create();
				Image img = null;
				try {
					img = ImageIO.read(Frame.class.getResource("/Icons/console.gif"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2.drawImage(img, 30, 0, this);
			}
		};
		panel_3.setFont(new Font("Estrangelo Edessa", Font.PLAIN, 14));
		panel_3.setForeground(new Color(0, 0, 0));
		panel_3.setBorder(new CompoundBorder(new EmptyBorder(1, 7, 5, 9), new TitledBorder(new LineBorder(new Color(72, 61, 139), 1, true), "               Console", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, null, new Color(72, 61, 139))));
		panel_3.setBackground(new Color(250, 240, 230));
		splitPane_1.setRightComponent(panel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
		);
		textArea.setBorder(new EmptyBorder(5, 10, 1, 20));
		scrollPane.setViewportView(textArea);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textArea.setEditable(false);
		textArea.setBackground(new Color(255, 250, 250));
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textArea.setFont(new Font("Utsaah", Font.PLAIN, 16));
		panel_3.setLayout(gl_panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setAutoscrolls(true);
		splitPane_1.setLeftComponent(panel_4);
		SpringLayout sl_panel_4 = new SpringLayout();
		sl_panel_4.putConstraint(SpringLayout.NORTH, canvas, 0, SpringLayout.NORTH, panel_4);
		sl_panel_4.putConstraint(SpringLayout.SOUTH, canvas, 0, SpringLayout.SOUTH, panel_4);
		sl_panel_4.putConstraint(SpringLayout.EAST, canvas, 0, SpringLayout.EAST, panel_4);
		panel_4.setLayout(sl_panel_4);
		sl_panel_4.putConstraint(SpringLayout.WEST, canvas, 0, SpringLayout.WEST, panel_4);
		panel_4.add(canvas);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(canvas, popupMenu);

		
		JMenuItem mntmDelete = new JMenuItem("Delete Node");
		mntmDelete.setIcon(new ImageIcon(Frame.class.getResource("/Icons/eraser.png")));
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.eraseNode();
			}
		});
		
		JMenuItem mntmConnection = new JMenuItem("Connect Nodes");
		mntmConnection.setIcon(new ImageIcon(Frame.class.getResource("/Icons/link_add.png")));
		mntmConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.addEdge();
			}
		});
		popupMenu.add(mntmConnection);
		popupMenu.add(mntmDelete);
		
		JMenuItem mntmDeleteLine = new JMenuItem("Delete Line");
		mntmDeleteLine.setIcon(new ImageIcon(Frame.class.getResource("/Icons/eraser.png")));
		mntmDeleteLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.eraseEdge();
			}
		});
		popupMenu.add(mntmDeleteLine);
		splitPane_1.setDividerLocation(400);
		panel_2.setLayout(gl_panel_2);

		
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(new Color(188, 190, 215));
		toolBar.setFont(new Font("Aharoni", Font.PLAIN, 15));
		toolBar.setMaximumSize(new Dimension(600, 30));
		toolBar.setRollover(true);
		toolBar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		toolBar.setMinimumSize(new Dimension(600, 25));
		toolBar.setName("Control Buttons");
		toolBar.setPreferredSize(new Dimension(820, 25));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 1064, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setAutoCreateGaps(true);
		btnRun.setOpaque(false);
		btnRun.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRun.setMaximumSize(new Dimension(30, 30));
		btnRun.setMinimumSize(new Dimension(30, 30));
		btnRun.setIconTextGap(2);
		btnRun.setBackground(new Color(220, 220, 220));
		btnRun.setPreferredSize(new Dimension(30, 30));
		
		btnRun.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRun.setFont(new Font("Estrangelo Edessa", Font.PLAIN, 13));
		btnRun.setToolTipText("Start Scenario");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LinkedList<Node> scnr = canvas.getScenario();
				progressBar.setMaximum(scnr.size()*100000000);
				
				if(scnr != null){
			
 					EngineCore.engineTimeSlot = (int) timeSlot.getValue();
 					EngineCore.enginePackSize = (int) rowPackSize.getValue();
 					EngineCore.engineQueueSize = (int) dataQueueSize.getValue();
			
 					optmzer = new Optimizer(scnr);
 					if(optmzer.isGraphCorrect()){
 						textArea.append("Optimizing graph...");
						mntr.initializeScenario(optmzer.optimize());
						textArea.append(" OK!\nScenario Initialized !!!\n");
						
						String curTime = getCurrentTime();
						startTime = System.currentTimeMillis();
						textArea.append("\n --> Start Time : "+ curTime+"\n");

						btnRun.setEnabled(false);
						
						mntr.signal();
 					}
 					else{
 						textArea.append("Incorrect graph... \n");
 					}
				}
			}
		});
		
		JButton btnOpenScenario = new JButton("");
		btnOpenScenario.setOpaque(false);
		btnOpenScenario.setPreferredSize(new Dimension(30, 30));
		btnOpenScenario.setMaximumSize(new Dimension(30, 30));
		btnOpenScenario.setIcon(new ImageIcon(Frame.class.getResource("/Icons/openScnrio.gif")));
		toolBar.add(btnOpenScenario);
		
		JButton btnSaveScenario = new JButton("");
		btnSaveScenario.setOpaque(false);
		btnSaveScenario.setPreferredSize(new Dimension(30, 30));
		btnSaveScenario.setMinimumSize(new Dimension(30, 30));
		btnSaveScenario.setMaximumSize(new Dimension(30, 30));
		btnSaveScenario.setIcon(new ImageIcon(Frame.class.getResource("/Icons/saveScnrio.gif")));
		toolBar.add(btnSaveScenario);
		btnRun.setIcon(new ImageIcon(Frame.class.getResource("/Icons/play-btn.png")));
		toolBar.add(btnRun);
		
		JButton btnStop = new JButton("");
		btnStop.setOpaque(false);
		btnStop.setHorizontalTextPosition(SwingConstants.CENTER);
		btnStop.setMinimumSize(new Dimension(20, 23));
		btnStop.setMaximumSize(new Dimension(30, 30));
		btnStop.setIconTextGap(2);
		btnStop.setBackground(new Color(220, 220, 220));
		btnStop.setPreferredSize(new Dimension(30, 30));
		btnStop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnStop.setFont(new Font("Estrangelo Edessa", Font.PLAIN, 13));
		btnStop.setToolTipText("stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!btnRun.isEnabled()){
					mntr.stop();
					textArea.append("Execution terminated !\n");
					btnRun.setEnabled(true);
				}
			}
		});
		btnStop.setIcon(new ImageIcon(Frame.class.getResource("/Icons/stop-btn.png")));
		toolBar.add(btnStop);
		
		JButton btnClear = new JButton("");
		btnClear.setOpaque(false);
		btnClear.setHorizontalTextPosition(SwingConstants.CENTER);
		btnClear.setMaximumSize(new Dimension(30, 30));
		btnClear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClear.setBackground(new Color(220, 220, 220));
		btnClear.setMinimumSize(new Dimension(20, 10));
		btnClear.setPreferredSize(new Dimension(30, 30));
		btnClear.setIconTextGap(2);
		btnClear.setFont(new Font("Estrangelo Edessa", Font.PLAIN, 13));
		btnClear.setIcon(new ImageIcon(Frame.class.getResource("/Icons/clear.png")));
		btnClear.setToolTipText("Erase Current Scenario");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(btnRun.isEnabled()){
					canvas.clearScenario();
					itemId = 0;
				}
			}
		});
		toolBar.add(btnClear);
		panel.setLayout(gl_panel);
		splitPane.setDividerLocation(200);
		getContentPane().setLayout(groupLayout);
		
	}
	
	private void addSourceNode(){
		JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(canvas) == JFileChooser.APPROVE_OPTION){
        	String inputFile = fileChooser.getSelectedFile().getAbsolutePath().toString();
        	
            canvas.addNode(new RecordSet(itemId, EngineCore.Reader, inputFile, "/Icons/dtsource.png"));
            itemId++;
        }
        else{
            System.out.println("File access cancelled by user.");
        }
	}
	
	private void addTargetNode(){
		JFileChooser pathChooser = new JFileChooser();
		pathChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
		
        if (pathChooser.showSaveDialog(canvas) == JFileChooser.APPROVE_OPTION){
            String outputFile = pathChooser.getSelectedFile().getAbsolutePath().toString();
            canvas.addNode(new RecordSet(itemId, EngineCore.Writer, outputFile, "/Icons/target.png"));
            itemId++;
        }
        else{
            System.out.println("File access cancelled by user.");
        }
	}
	
	private void addFilterNode(){
		formFilter f = new formFilter();
		f.setLocation(canvas.getLocationOnScreen());
		f.setVisible(true);
		
		int field = 0;
		try{		
		 field = Integer.parseInt(f.getField());
		}catch(Exception e){
			field = 0;
		}
		String condition = f.getCondition();
		String checkVal = f.getCheckValue();
		
		if( !(field == 0 || condition.isEmpty() || checkVal.isEmpty()) ){
			canvas.addNode(new Filter(itemId, EngineCore.Filter, field, condition, checkVal, "/Icons/filter.png"));
			itemId++;
		}
	}
	
	private void addNotNullNode(){
		formNotNull f = new formNotNull();
		f.setLocation(canvas.getLocationOnScreen());
		f.setVisible(true);
		
		int field = 0;
		try{		
		 field = Integer.parseInt(f.getField());
		}catch(Exception e){
			field = 0;
		}
		if(field != 0){
			canvas.addNode(new NotNull(itemId, EngineCore.NotNull, field, "/Icons/filter.png"));
			itemId++;
		}
	}
	
	private void addAggregateNode(){
		formAggregation f = new formAggregation();
		f.setLocation(canvas.getLocationOnScreen());
		f.setVisible(true);
		
		if(f.getGroupFields().isEmpty()){
			return;
		}
		
		LinkedList<Integer> grFlds = new LinkedList<>();
		String[] gflds = f.getGroupFields().split(",");
		for(int i=0; i<gflds.length; i++){
			try{
				grFlds.add(Integer.parseInt(gflds[i].trim()));
			}
			catch(Exception ex){
				return;
			}
		}
		
		int aggregFld =0;
		try{
			aggregFld = Integer.parseInt(f.getAggregateField());
		}
		catch(Exception ex){
			return;
		}
		
		String aggregFunc = f.getAggregateFunc();
		
		if( !(grFlds.isEmpty() || aggregFunc.isEmpty() || aggregFld == 0) ){
			if(rdbtnmntmAggregationWithHashmap.isSelected()){				// Aggregation with HaspMap
				EngineCore.isAggregationWithSort = false;
				canvas.addNode(new Aggregator(itemId, EngineCore.Aggregator, grFlds, aggregFunc, aggregFld, "Hash", "/Icons/group.png"));
			}
			else{															//Aggregation with Sort
				EngineCore.isAggregationWithSort = true;
				canvas.addNode(new Aggregator(itemId, EngineCore.Aggregator, grFlds, aggregFunc, aggregFld, "Sort", "/Icons/group.png"));

			}
			itemId++;
		}
	}
	
	private void addSortNode(){
		if(rdbtnmntmGoogleSort.isSelected()){
			EngineCore.isUnixSort = false;
			
        	formGoogleSort f = new formGoogleSort();
        	f.setLocation(canvas.getLocationOnScreen());
			f.setVisible(true);

			String fields = f.getsFields();
			if(!fields.isEmpty()){
				canvas.addNode(new Sorter(itemId, EngineCore.Sorter, fields, f.getOrder(), "/Icons/sort.png"));
				itemId++;
			}
		}
		else{
			EngineCore.isUnixSort = true;
			
			formUnixSort f = new formUnixSort();
			f.setLocation(canvas.getLocationOnScreen());
			f.setVisible(true);
			
			String fields = f.getsFields();
			if(!fields.isEmpty()){
				canvas.addNode(new Sorter(itemId, EngineCore.Sorter, fields, "/Icons/sort.png"));
				itemId++;
			}
		}
	}
	
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	
	
	public static void printClosingMsg(){
		textArea.append("Monitor exiting !\n");
		long endTime = System.currentTimeMillis();
		
		textArea.append("--> End Time : "+ getCurrentTime() +"\n->  Success !!!\n>Execution Time : "+(endTime - startTime)+" msec\n");
		btnRun.setEnabled(true);
		progressBar.setValue(progressBar.getMaximum());
	}
	
	
	private static String getCurrentTime() {
		Calendar calen = new GregorianCalendar();
		int hour = calen.get(Calendar.HOUR);
		int min = calen.get(Calendar.MINUTE);
		int sec = calen.get(Calendar.SECOND);
		
		return hour+ ":" +min+ ":" +sec;
	}
	

	private DefaultMutableTreeNode getPopulatedTreeRoot(){
		
		DefaultMutableTreeNode treeRecordSet = new DefaultMutableTreeNode(new IconNode(" Data Flow Record Sets ", new ImageIcon(Frame.class.getResource("/Icons/treeNode.gif"))));
		DefaultMutableTreeNode dtSNode = new DefaultMutableTreeNode(new IconNode(" Data Source ",new ImageIcon(Frame.class.getResource("/Icons/sourceIcon.gif"))));
		DefaultMutableTreeNode dtDNode = new DefaultMutableTreeNode(new IconNode(" Data Destination ", new ImageIcon(Frame.class.getResource("/Icons/targetIcon.gif"))));
		treeRecordSet.add(dtSNode);
		treeRecordSet.add(dtDNode);
		
		DefaultMutableTreeNode treeTransformation = new DefaultMutableTreeNode(new IconNode(" Data Flow Transformations ", new ImageIcon(Frame.class.getResource("/Icons/treeNode.gif"))));
		DefaultMutableTreeNode filterNode = new DefaultMutableTreeNode(new IconNode(" Filter ", new ImageIcon(Frame.class.getResource("/Icons/filter_icon.png"))));
		DefaultMutableTreeNode nNullNode = new DefaultMutableTreeNode(new IconNode(" Not-Null Filter", new ImageIcon(Frame.class.getResource("/Icons/filter_icon.png"))));
		DefaultMutableTreeNode aggregNode = new DefaultMutableTreeNode(new IconNode(" Aggregate ", new ImageIcon(Frame.class.getResource("/Icons/aggregate2.png"))));
		DefaultMutableTreeNode sortNode = new DefaultMutableTreeNode(new IconNode(" Sort ", new ImageIcon(Frame.class.getResource("/Icons/sort_icon.gif"))));
		treeTransformation.add(filterNode);
		treeTransformation.add(nNullNode);
		treeTransformation.add(aggregNode);
		treeTransformation.add(sortNode);
		
		DefaultMutableTreeNode treeSPolicy = new DefaultMutableTreeNode(new IconNode(" Scheduling Policy ", new ImageIcon(Frame.class.getResource("/Icons/treeNode.gif"))));
		JRadioButton rBtnRR = new RadioNode(" Round Robin ", true);
	 	
		DefaultMutableTreeNode RRNode = new DefaultMutableTreeNode(rBtnRR);
		treeSPolicy.add(RRNode);
		
		DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode("Palette");
		treeRoot.add(treeRecordSet);
		treeRoot.add(treeTransformation);
		treeRoot.add(treeSPolicy);
		
		
		return treeRoot;
		
	}
}

