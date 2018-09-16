package com.component;

import java.awt.Color;
import java.awt.Graphics;

import org.apache.log4j.Logger;
import org.json.simple.*;

import com.dimension.Rectangle;
import com.infrastruture.Constants;
import com.infrastruture.Element;

public class Brick implements Element{
	protected Logger log = Logger.getLogger(Brick.class);
	private Rectangle rectangle;
	private boolean visible;
	private Color color;
	private JSONObject jsonObject;
	
	public Brick(Rectangle rectangle, boolean visible,Color color) {
		this.setRectangle(rectangle);
		this.setVisible(visible);
		this.color = color;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	@Override
	public void draw(Graphics g){
		if(visible) {
		g.setColor(color);
		g.fillRect(rectangle.getTopLeftCoordinate().getX(), rectangle.getTopLeftCoordinate().getY(), rectangle.getWidth(), rectangle.getHeight());
		g.setColor(Constants.BRICK_BORDER);
		g.drawRect(rectangle.getTopLeftCoordinate().getX(), rectangle.getTopLeftCoordinate().getY(), rectangle.getWidth(), rectangle.getHeight());
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		this.setVisible(true);
		
	}

	@Override
	public JSONObject save() {
		jsonObject = new JSONObject();
		try {
			jsonObject.put("Brick", this.isVisible());
			jsonObject.put("BrickX", this.getRectangle().getTopLeftCoordinate().getX());
			jsonObject.put("BrickY", this.getRectangle().getTopLeftCoordinate().getY());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return jsonObject;
	}

	@Override
	public int load(Object object) {
		// TODO Auto-generated method stub'
		jsonObject = (JSONObject) object;
		this.setVisible((Boolean)jsonObject.get("Brick"));
		this.getRectangle().getTopLeftCoordinate().setX((int)(long)jsonObject.get("BrickX"));
		this.getRectangle().getTopLeftCoordinate().setY((int)(long)jsonObject.get("BrickY"));
		
		if(this.isVisible() == true) {
			return 1;
		}else {
			return 0;
		}
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
