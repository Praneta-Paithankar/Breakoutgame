package com.component;
import java.awt.Color;
import java.awt.Graphics;

import org.json.simple.*;

import com.dimension.Coordinate;
import com.dimension.Rectangle;
import com.infrastruture.Constants;
import com.infrastruture.Element;

public class Paddle implements Element{

	private Rectangle rectangle;
	private int deltaX;
	private Color color;
	private JSONObject jsonObject;
	
	public Paddle(Rectangle rectangle, int deltaX, Color color) {
		this.rectangle = rectangle;
		this.deltaX = deltaX;
		this.color = color;
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}

	@Override
	public void draw(Graphics g) {  
	      g.setColor(color);
	      g.fillRect(rectangle.getTopLeftCoordinate().getX(), rectangle.getTopLeftCoordinate().getY(), rectangle.getWidth(), rectangle.getHeight());
	}

	
	public void enact() {
		int topX = rectangle.getTopLeftCoordinate().getX();
		int topY = rectangle.getTopLeftCoordinate().getY();

		Coordinate newCoordinate = new Coordinate(topX+ deltaX, topY);
		rectangle.setTopLeftCoordinate(newCoordinate);
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		Coordinate newCoordinate = new Coordinate(Constants.PADDLE_POS_X, Constants.PADDLE_POS_Y);
		rectangle.setTopLeftCoordinate(newCoordinate);
		 
	}

	@Override
	public JSONObject save() {
		jsonObject = new JSONObject();
		try {
			jsonObject.put("PaddleX", this.getRectangle().getTopLeftCoordinate().getX());
			jsonObject.put("PaddleY", this.getRectangle().getTopLeftCoordinate().getY());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public int load(Object object) {
		// TODO Auto-generated method stub
		JSONObject jsonObject1 = (JSONObject) object;
		this.getRectangle().getTopLeftCoordinate().setX((int)(long)jsonObject1.get("PaddleX"));
		this.getRectangle().getTopLeftCoordinate().setY((int)(long)jsonObject1.get("PaddleY"));
		
		return 1;
	}

	@Override
	public void addElement(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeElement(Element e) {
		// TODO Auto-generated method stub
		
	}
}