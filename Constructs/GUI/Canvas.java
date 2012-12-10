package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;

import javax.swing.JPanel;

import Logical.Edge;
import Logical.Node;


public class Canvas extends JPanel{
	private static final long serialVersionUID = 1L;
	private LinkedList<Node> AllNodes;
	private LinkedList<Edge> AllEdges;
	private int clickedImg, dblClickedImg, movedImg, clickedLine;
	private boolean line;
	
	
	public Canvas(){
		super();
		setBackground(new Color(250, 235, 215));

		AllNodes = new LinkedList<>();
		AllEdges = new LinkedList<>();
		clickedImg = dblClickedImg = movedImg = clickedLine = -1;
		line  = false;
		
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				if(movedImg != -1){
					redrawScene(arg0.getX(), arg0.getY());
				}
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0){
				if(arg0.getButton() == 1){
					if(line && clickedImg != -1){
						if(findNode(arg0.getX(), arg0.getY()) != -1){
							createLine(clickedImg, findNode(arg0.getX(), arg0.getY()));
						}
						clickedImg = -1;
						line = false;
					}
					else{
						clickedImg = movedImg = findNode(arg0.getX(), arg0.getY());
					}
				}
				else if(arg0.getButton() == 3){
					clickedImg = findNode(arg0.getX(), arg0.getY());
					
					if(clickedImg == -1){
						clickedLine = findEdge(arg0.getX(), arg0.getY());
					}
					
					movedImg = dblClickedImg = -1;
				}
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2){
					if(dblClickedImg != -1){
						if(findNode(arg0.getX(), arg0.getY()) != -1){
							createLine(dblClickedImg, findNode(arg0.getX(), arg0.getY()));
						}
						dblClickedImg = -1;
						clickedImg = -1;
						line = false;
					}
					else{
						dblClickedImg = findNode(arg0.getX(), arg0.getY());
					}
				}
			}
		});

	}
	
	
	/** 245, 242, 219
	 *  214, 204, 200
	 *  255, 250, 220
	 *  240, 232, 220
	 **/
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g.create();
        g2.setPaint(new Color(240, 232, 220));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(new Color(46, 140, 90));
        g2.setStroke(new BasicStroke(1.5f));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawAllEdges(g2);
		drawAllNodes(g2);
	}
	

	private int findNode(int x, int y){
		for(int i=0; i<AllNodes.size(); i++){
			if(AllNodes.get(i).hasCoordinates(x, y) )
				return i;
		}
		
		return -1;
	}
	
	private int findEdge(int x, int y){
		for(int i=0; i<AllEdges.size(); i++){
			if(AllEdges.get(i).hasCoordinates(x, y) )
				return i;
		}
		
		return -1;
	}
	
	private void createLine(int startNode, int endNode){
		if(startNode != endNode){
			AllNodes.get(startNode).addConsumer(AllNodes.get(endNode).getId());
			AllNodes.get(endNode).addProducer(AllNodes.get(startNode).getId());			
			
			AllEdges.add(new Edge(AllNodes.get(startNode), AllNodes.get(endNode)));
		}

		repaint();
	}
	
	
	private void redrawScene(int x, int y){
		if(x>0 && y>0 && x<getWidth() && y<getHeight()){
			AllNodes.get(movedImg).moveImg(x, y);

			for(int i=0; i<AllEdges.size(); i++){
				if(AllEdges.get(i).hasNode(AllNodes.get(movedImg).getId())){
					AllEdges.get(i).updateCoordinates(x, y, AllNodes.get(movedImg));
				}
			}
		}
		
		repaint();
	}
	
	
	public void addNode(Node newItem){
		AllNodes.add(newItem);
		repaint();
	}
	
	public void addEdge(){
		line = true;
	}

	public void eraseNode(){
		if(clickedImg != -1){
			int removedID = AllNodes.get(clickedImg).getId();
					
			for(int i=0; i<AllNodes.size(); i++){
				if(AllNodes.get(i).hasProducerID(removedID)){
					AllNodes.get(i).removeProducer(removedID);
				}
				else if(AllNodes.get(i).hasConsumerID(removedID)){
					AllNodes.get(i).removeConsumer(removedID);
				}
			}
	
			for(int i=0; i<AllEdges.size(); i++){
				if(AllEdges.get(i).hasNode(removedID)){
					AllEdges.remove(i);
					
					i--;
				}
			}
			
			AllNodes.remove(clickedImg);
			clickedImg = -1;
		}
		repaint();
		
	}
	
	public void eraseEdge(){
		if(clickedLine != -1){
			int firstId = AllEdges.get(clickedLine).getStartId();
			int secondId = AllEdges.get(clickedLine).getEndId();
			
			AllEdges.remove(clickedLine);
			for(int i=0; i<AllNodes.size(); i++){
				if(firstId == AllNodes.get(i).getId())
					AllNodes.get(i).removeConsumer(secondId);
				
				if(secondId == AllNodes.get(i).getId())
					AllNodes.get(i).removeProducer(firstId);
				
			}
		
			clickedLine = -1;
		
		}
		
		paintComponent(this.getGraphics());
		repaint();
		
	}
	
	private void drawAllEdges(Graphics2D g2D){
		LinkedList<Point> allPoints = new LinkedList<>();
		
		for(int i=0; i<AllEdges.size(); i++){
			allPoints.addAll(AllEdges.get(i).getPoints());
			
			if(!allPoints.isEmpty()){
				for(int j=0; j<allPoints.size()-1; j++){
					g2D.drawLine(allPoints.get(j).x ,allPoints.get(j).y , allPoints.get(j+1).x ,allPoints.get(j+1).y);
				}
				
				if(allPoints.get(0).y == allPoints.get(1).y){
					g2D.fillRect(allPoints.get(0).x-2,allPoints.get(0).y-4, 3,9);
				}
				else{
					g2D.fillRect(allPoints.get(0).x-4,allPoints.get(0).y-2, 9,3);
				}
				g2D.fillPolygon(AllEdges.get(i).getArrowX(), AllEdges.get(i).getArrowY(), 3);
			}
			
			allPoints.clear();
		}
		
	}

	
	//epeidi oi eikones exoun diaforetika megethi,
	//pros8eto kapoia epipleon pixel aristera/deksia opou xreiazetai...
	private void drawAllNodes(Graphics2D g2D){
		int sourceTextFrameX = 5, sourceTextFrameY = 15;
		int targetTextFrameX = 20, targetTextFrameY = 20;
		int filterTextFrameX = 45, filterTextFrameY = 30, filterLineHeight = 20;
		int sorterTextFrameX = 35, sorterTextFrameY = 28, sorterLineHeight = 16;
		int grouperTextFrameX = 53, grouperTextFrameY = 27, grouperLineHeight = 15;
		int labelX,labelY;
		
		Font f = new Font("Comic Sans Ms", Font.BOLD, 12);
		g2D.setFont(f);
        
		for(int i=0; i<AllNodes.size(); i++){
			g2D.drawImage(AllNodes.get(i).getImage(), AllNodes.get(i).getMinX(), AllNodes.get(i).getMinY(), this);
			String[] str = AllNodes.get(i).getLabel().split("\n");
			
			switch(AllNodes.get(i).getType()){
			case 0:
				labelX = AllNodes.get(i).getMinX() + sourceTextFrameX;
				labelY = AllNodes.get(i).getMaxY() - sourceTextFrameY;
				
				g2D.setPaint(new Color(0, 0, 0));
		        g2D.setStroke(new BasicStroke(4f));
		        g2D.drawString(str[0], labelX, labelY);
		        
				break;
			
			case 1:
				labelX = AllNodes.get(i).getMinX() + targetTextFrameX;
				labelY = AllNodes.get(i).getMinY() + targetTextFrameY;
				
				
				g2D.setPaint(new Color(0, 0, 0));
		        g2D.setStroke(new BasicStroke(4f));
		        g2D.drawString(str[0], labelX, labelY);
		        
				break;
			
			case 4:
		        labelX = AllNodes.get(i).getMinX() + sorterTextFrameX;
				labelY = AllNodes.get(i).getMinY() + sorterTextFrameY;
				
				g2D.setPaint(new Color(250, 250, 240));
		        g2D.setStroke(new BasicStroke(3f));
				for(int j=0; j<str.length; j++){
					g2D.drawString(str[j], labelX, labelY + sorterLineHeight*j);
				}
				
				break;
			
			case 5:
		        labelX = AllNodes.get(i).getMinX() + grouperTextFrameX;
				labelY = AllNodes.get(i).getMinY() + grouperTextFrameY;
				
				g2D.setPaint(new Color(250, 250, 240));
		        g2D.setStroke(new BasicStroke(3f));
				for(int j=0; j<str.length; j++){
					g2D.drawString(str[j], labelX, labelY + grouperLineHeight*j);
				}
				
				break;
			
			default:
		        labelX = AllNodes.get(i).getMinX() + filterTextFrameX;
				labelY = AllNodes.get(i).getMinY() + filterTextFrameY;
				
				g2D.setPaint(new Color(250, 250, 240));
		        g2D.setStroke(new BasicStroke(3f));
				for(int j=0; j<str.length; j++){
					g2D.drawString(str[j], labelX, labelY + filterLineHeight*j);
				}
				
				break;
			
			}
		}
		
	}
	
	public void clearScenario() {
		AllEdges.clear();
		AllNodes.clear();
		
		repaint();
	}
	

	public void setDAG(LinkedList<Node> scnr){
		for(int i=0; i<scnr.size(); i++){
			try {
				AllNodes.add((Node) scnr.get(i).clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		for(int i=0; i<AllNodes.size(); i++){
			LinkedList<Integer> curConsIds = AllNodes.get(i).getAllConsumers();
			
			for(Integer consId:curConsIds){
				for(int j=0; j<AllNodes.size(); j++){
					if(AllNodes.get(j).getId() == consId){
						AllEdges.add(new Edge(AllNodes.get(i), AllNodes.get(j)));
					}
				}
			}
			
		}
		
	}
	

	public LinkedList<Node> getScenario() {
		return AllNodes;
	}
	
	
}
