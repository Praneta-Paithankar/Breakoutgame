package com.component;
import java.awt.Color;
import java.awt.Graphics;

import org.apache.log4j.Logger;
import org.json.simple.JsonObject;

import com.dimension.Coordinate;
import com.dimension.Rectangle;
import com.infrastruture.Constants;
import com.infrastruture.Element;

public class Paddle implements Element{
	protected Logger log = Logger.getLogger(Paddle.class);
	private Rectangle rectangle;
	private int deltaX;
	private Color color;
	private JsonObject jsonObject;
	
	public Paddle(Rectangle rectangle, int deltaX, Color color) {
		this.rectangle = rectangle;
		this.deltaX = deltaX;
		this.color = color;
	}
	
	public Paddle(Paddle p) {
		Rectangle r = p.getRectangle();
		Coordinate t = new Coordinate(r.getTopLeftCoordinate().getX(), r.getTopLeftCoordinate().getY());
		this.rectangle = new Rectangle(r.getWidth(), r.getHeight(), t);
		this.deltaX = p.getDeltaX();
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
	public void reset (Paddle p) {
		Rectangle r = p.getRectangle();
		Coordinate t = new Coordinate(r.getTopLeftCoordinate().getX(), r.getTopLeftCoordinate().getY());
		this.rectangle = new Rectangle(r.getWidth(), r.getHeight(), t);
		this.deltaX = p.getDeltaX();
	}
	@Override
	public void addComponent(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeComponent(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JsonObject save() {
		jsonObject = new JsonObject();
		
		try {
			jsonObject.put("PaddleX", this.getRectangle().getTopLeftCoordinate().getX());
			jsonObject.put("PaddleY", this.getRectangle().getTopLeftCoordinate().getY());
			jsonObject.put("PaddleDeltaX", this.getDeltaX());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return jsonObject;
	}

	@Override
	public int load(Object object) {
		// TODO Auto-generated method stub
		jsonObject = (JsonObject) object;
		
		this.getRectangle().getTopLeftCoordinate().setX(jsonObject.getInteger("PaddleX"));
		this.getRectangle().getTopLeftCoordinate().setY(jsonObject.getInteger("PaddleY"));
		this.setDeltaX(jsonObject.getInteger("PaddleDeltaX"));
		
		return 1;
	}
}