package Logical;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;


public abstract class Node implements Cloneable{
	private LinkedList<Integer> producers, consumers;
	protected LinkedList<String> fieldsType;
	private BufferedImage img;
	private Image icon;
	private int imgX, imgY, centerX, centerY, width, height;
	private int itemType,id;
	

	public abstract String getLabel();
	public abstract String getOperator();
	public abstract String getCheckValue();
	public abstract String getProperty();
	public abstract LinkedList<Integer> getSortFields();
	public abstract String getAggregateFunc();
	public abstract int getAggregateField();
	public abstract LinkedList<Integer> getGroupFields();
	public abstract boolean isHash();
	
	public abstract int getFilterField();
	public abstract String getFilePath();
	
	
	public Node(int id, int type, String imgPath){
		producers = new LinkedList<Integer>();
		consumers = new LinkedList<Integer>();
		fieldsType = new LinkedList<>();
		
		this.id = id;
		itemType = type;
		
		try {
    	    img = ImageIO.read(getClass().getResource(imgPath));
    	}catch (IOException e){
    		System.out.println("Can't read image!");
    	}
		
		if(type == 0 || type==1){
			width = 90;
			height = 80;
		}
		else{
			width = 130;
			height = 70;
		}
			
		icon = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		imgX = 150*id/5 + 50*(id%5) + 20;
		imgY = 80*(id%5) + 30;
		

		centerX = imgX + width/2;
		centerY = imgY + height/2;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public Object clone() throws CloneNotSupportedException{
		Node clone = (Node) super.clone();
		
		clone.consumers = (LinkedList<Integer>) consumers.clone();
		clone.producers = (LinkedList<Integer>) producers.clone();
		
		return clone;
	}
	
	
	public int getId(){
		return id;
	}
	
	public void setFieldsType(LinkedList<String> fieldsTp){
		if(fieldsType.isEmpty()){
			fieldsType.addAll(fieldsTp);
		}
		else{
			int size = 0;
			
			if(fieldsTp.size() <= fieldsType.size()){
				size = fieldsTp.size();
			}
			else{
				size = fieldsType.size();
				for(int i=size; i<fieldsTp.size(); i++){
					fieldsType.add(fieldsTp.get(i));
				}
			}
			
			for(int i=0; i<size; i++){
				if( !fieldsTp.get(i).matches(fieldsType.get(i)) ){
					if(fieldsTp.get(i).matches("String")){
						fieldsType.set(i, "String");
					}
					else if(fieldsTp.get(i).matches("double") && fieldsType.get(i).matches("int")){
						fieldsType.set(i, "double");
					}
				}
			}
		}
	}
	
	public LinkedList<String> getFieldsType(){
		return fieldsType;
	}
	
	
	public void addProducer(int producer){
		producers.add(producer);
	}
	
	public void addConsumer(int consumer){
		consumers.add(consumer);
	}
	
	public void removeProducer(int producer){
		for(int i=0; i<producers.size(); i++){
			if(producers.get(i) == producer){
				producers.remove(i);
				
				break;
			}
		}
	}
	
	public void removeConsumer(int consumer){
		for(int i=0; i<consumers.size(); i++){
			if(consumers.get(i) == consumer){
				consumers.remove(i);

				break;
			}
		}
	}
	
	
	public void clearProducers(){
		producers.clear();
	}

	public void clearConsumers(){
		consumers.clear();
	}
	
	
	public LinkedList<Integer> getAllProducers(){
		return producers;
	}
	
	public LinkedList<Integer> getAllConsumers(){
		return consumers;
	}
	
	public void moveImg(int X, int Y){
		centerX = X;
		centerY = Y;

		imgX = centerX - width/2;
		imgY = centerY - height/2;
	}
	
	
	public boolean hasProducerID(int prodId){
		if(!producers.isEmpty()){
			return producers.contains(prodId);
		}
		
		return false;
	}
	
	public boolean hasConsumerID(int consumId){
		if(!consumers.isEmpty()){
			return consumers.contains(consumId);
		}
		
		return false;
	}
	
	public boolean hasCoordinates(int x, int y){
		if((x<getMaxX() && x>getMinX()) && (y<getMaxY() && y>getMinY())){
			return true;
		}
	
		return false;
	}

	
	public int getCenterX(){
		return centerX;
	}
	
	public int getCenterY(){
		return centerY;
	}
	
	public int getMinX(){
		return imgX;
	}
	
	public int getMinY(){
		return imgY;
	}
	
	public int getMaxX(){
		return imgX+width;
	}
	
	public int getMaxY(){
		return imgY+height;
	}

	public Image getImage(){
		return icon;
	}
	
	public int getType(){
		return itemType;
	}

}
