package com.component;

import java.awt.Color;
import java.awt.Graphics;

import org.apache.log4j.Logger;
import org.json.simple.*;

import com.controller.GameController;
import com.dimension.Circle;
import com.dimension.Coordinate;
import com.infrastruture.Constants;
import com.infrastruture.Element;

public class Ball implements Element{
	protected Logger log = Logger.getLogger(Ball.class);
    private Circle circle;
    private Coordinate delta;
    private Color color;
    private JSONObject jsonObject;
    
	public Ball(Circle circle, Coordinate delta,Color color) {
		this.setCircle(circle); 
		this.setDelta(delta);
		this.color = color;
	}
    
	public void draw(Graphics g){
    	int radius =  circle.getRadius();
        int upperLeftX = circle.getCenter().getX() - radius;
        int upperLeftY = circle.getCenter().getY() - radius;
        int diameter = 2 * radius;
        
        g.setColor(color);
        g.fillOval(upperLeftX, upperLeftY, diameter, diameter);
    }

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public Coordinate getDelta() {
		return delta;
	}

	public void setDelta(Coordinate delta) {
		this.delta = delta;
	}
	
	public void reset(){
		circle.setCenter(new Coordinate(Constants.BALL_POS_X,Constants.BALL_POS_Y));
		this.setDelta(new Coordinate(Constants.BALL_DELTA_X, Constants.BALL_DELTA_Y));
	}

	@Override
	public JSONObject save() {
		jsonObject = new JSONObject();
		try {
			jsonObject.put("BallX", this.getCircle().getCenter().getX());
			jsonObject.put("BallY", this.getCircle().getCenter().getY());
			jsonObject.put("BallDeltaX", this.getDelta().getX());
			jsonObject.put("BallDeltaY", this.getDelta().getY());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return jsonObject;
	}

	@Override
	public int load(Object object) {
		// TODO Auto-generated method stub
		jsonObject = (JSONObject) object;
		
		this.getCircle().getCenter().setX((int)(long)(jsonObject.get("BallX")));
		this.getCircle().getCenter().setY((int)(long)(jsonObject.get("BallY")));
		this.getDelta().setX((int)(long)(jsonObject.get("BallDeltaX")));
		this.getDelta().setY((int)(long)(jsonObject.get("BallDeltaY")));
		
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
