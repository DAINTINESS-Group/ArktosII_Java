package Logical;

import java.awt.Point;
import java.util.LinkedList;


public class Edge {
	private LinkedList<Point> p;
	private Node nd1, nd2;
	private int arrowX[] = new int[3], arrowY[] = new int[3];
	
	public Edge(Node first, Node second){
		try {
			nd1 = (Node) first.clone();
			nd2 = (Node) second.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		p = new LinkedList<>();
		p.addAll(findPoints());
		
	}

	public LinkedList<Point> getPoints(){
		return p;
	}
	
	public int[] getArrowX(){
		return arrowX;
	}
	
	public int[] getArrowY(){
		return arrowY;
	}
	
	
	public int getStartId(){
		return nd1.getId();
	}

	public int getEndId(){
		return nd2.getId();
	}
	
	
	public boolean hasNode(int id){
		if(nd1.getId() == id || nd2.getId() == id)
			return true;
		else
			return false;
	}

	
	public boolean hasCoordinates(int x, int y){
		if(!p.isEmpty()){
			if(p.get(1).x == p.get(2).x){
				if(p.get(0).x == p.get(3).x){
					if(p.get(0).x-2<=x && p.get(0).x+2>=x && p.get(0).y+1>=y && p.get(3).y-1<=y){
						return true;
					}
					else if(p.get(0).x-2<=x && p.get(0).x+2>=x && p.get(3).y+1>=y && p.get(0).y-1<=y){
						return true;
					}
				}
				else if(p.get(3).y < p.get(0).y){
					if(p.get(0).y+2>=y && p.get(0).y-2<=y && p.get(0).x-1<=x && p.get(1).x+1>=x){
						return true;
					}
					else if(p.get(3).y+2>=y && p.get(3).y-2<=y && p.get(2).x-1<=x && p.get(3).x+1>=x){
						return true;
					}
					else if(p.get(0).y+2>=y && p.get(0).y-2<=y && p.get(0).x+1>=x && p.get(1).x-1<=x){
						return true;
					}
					else if(p.get(3).y+2>=y && p.get(3).y-2<=y && p.get(2).x+1>=x && p.get(3).x-1<=x){
						return true;
					}
					else if(p.get(1).x-2<=x && p.get(1).x+2>=x && p.get(1).y+1>=y && p.get(2).y-1<=y){
						return true;
					}
				}
				else{
					if(p.get(0).y+2>=y && p.get(0).y-2<=y && p.get(0).x-1<=x && p.get(1).x+1>=x){
						return true;
					}
					else if(p.get(3).y+2>=y && p.get(3).y-2<=y && p.get(2).x-1<=x && p.get(3).x+1>=x){
						return true;
					}
					else if(p.get(0).y+2>=y && p.get(0).y-2<=y && p.get(0).x+1>=x && p.get(1).x-1<=x){
						return true;
					}
					else if(p.get(3).y+2<=y && p.get(3).y-2<=y && p.get(2).x+1>=x && p.get(3).x-1<=x){
						return true;
					}
					else if(p.get(1).x-2<=x && p.get(1).x+2>=x && p.get(1).y-1<=y && p.get(2).y+1>y){
						return true;
					}
				}
			}
			else{
				if(p.get(0).y == p.get(3).y){
					if(p.get(0).y-2<=y && p.get(0).y+2>=y && p.get(3).x+1>=x && p.get(0).x-1<=x){
						return true;
					}
					else if(p.get(0).y-2<=y && p.get(0).y+2>=y && p.get(3).x-1<=x && p.get(0).x+1>=x){
						return true;
					}
				}
				else if(p.get(3).y < p.get(0).y){
					if(p.get(0).x-2<=x && p.get(0).x+2>=x && p.get(0).y+1>=y && p.get(1).y-1<=y){
						return true;
					}
					else if(p.get(3).x-2<=x && p.get(3).x+2>=x && p.get(2).y+1>=y && p.get(3).y-1<=y){
						return true;
					}
					else if(p.get(1).y-2<=y && p.get(1).y+2>=y && p.get(1).x-1<=x && p.get(2).x+1>=x){
						return true;
					}
					else if(p.get(1).y-2<=y && p.get(1).y+2>=y && p.get(1).x+1>=x && p.get(2).x-1<=x){
						return true;
					}
				}
				else{
					if(p.get(0).x-2<=x && p.get(0).x+2>=x && p.get(0).y-1<=y && p.get(1).y+1>=y){
						return true;
					}
					else if(p.get(3).x-2<=x && p.get(3).x+2>=x && p.get(2).y-1<=y && p.get(3).y+1>=y){
						return true;
					}
					else if(p.get(1).y-2<=y && p.get(1).y+2>=y && p.get(1).x-1<=x && p.get(2).x+1>=x){
						return true;
					}
					else if(p.get(1).y-2<=y && p.get(1).y+2>=y && p.get(1).x+1>=x && p.get(2).x-1<=x){
						return true;
					}
				}
			}

		}

		return false;
	}
	
	
	public void updateCoordinates(int x, int y, Node movedNode){
		if(nd1.getId() == movedNode.getId()){
			try {
				nd1 = (Node) movedNode.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		else if(nd2.getId() == movedNode.getId()){
			try {
				nd2 = (Node) movedNode.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}

		p.clear();
		p.addAll(findPoints());
		
	}
	
	
	
	private LinkedList<Point> findPoints(){
		LinkedList<Point> tempPoints = new LinkedList<>();

		int x1 = nd1.getCenterX();
		int y1 = nd1.getCenterY();
		int x2 = nd2.getCenterX();
		int y2 = nd2.getCenterY();
		
		if(x2-x1 > 150){
			int medX = (x2-x1)/2;
			
			tempPoints.add(new Point(nd1.getMaxX(), y1));
			tempPoints.add(new Point(x1+medX, y1));
			tempPoints.add(new Point(x1+medX, y2));
			tempPoints.add(new Point(nd2.getMinX()-5, y2));

			arrowX[0] = nd2.getMinX()-8;
			arrowX[1] = nd2.getMinX()-8;
			arrowX[2] = nd2.getMinX();
			
			arrowY[0] = y2-6;
			arrowY[1] = y2+6;
			arrowY[2] = y2;
			
		}
		else if(x1-x2 > 150){
			int medX = (x1-x2)/2;
			
			tempPoints.add(new Point(nd1.getMinX(), y1));
			tempPoints.add(new Point(x1-medX, y1));
			tempPoints.add(new Point(x1-medX, y2));
			tempPoints.add(new Point(nd2.getMaxX()+5, y2));

			arrowX[0] = nd2.getMaxX();
			arrowX[1] = nd2.getMaxX()+8;
			arrowX[2] = nd2.getMaxX()+8;
			
			arrowY[0] = y2;
			arrowY[1] = y2-6;
			arrowY[2] = y2+6;
			
		}
		else if(y2-y1 > 80){
			int medY = (y2-y1)/2;
			
			tempPoints.add(new Point(x1, nd1.getMaxY()));
			tempPoints.add(new Point(x1, y1+medY));
			tempPoints.add(new Point(x2, y1+medY));
			tempPoints.add(new Point(x2, nd2.getMinY()-5));

			arrowX[0] = x2;
			arrowX[1] = x2+6;
			arrowX[2] = x2-6;
			
			arrowY[0] = nd2.getMinY();
			arrowY[1] = nd2.getMinY()-8;
			arrowY[2] = nd2.getMinY()-8;

		}
		else if(y1-y2 > 80){
			int medY = (y1-y2)/2;
			
			tempPoints.add(new Point(x1, nd1.getMinY()));
			tempPoints.add(new Point(x1, y1-medY));
			tempPoints.add(new Point(x2, y1-medY));
			tempPoints.add(new Point(x2, nd2.getMaxY()+5));

			arrowX[0] = x2;
			arrowX[1] = x2+6;
			arrowX[2] = x2-6;
			
			arrowY[0] = nd2.getMaxY();
			arrowY[1] = nd2.getMaxY()+8;
			arrowY[2] = nd2.getMaxY()+8;
			
		}
		
					
		return tempPoints;
	}
	
	
}
