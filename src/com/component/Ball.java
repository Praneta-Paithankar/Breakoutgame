package com.component;

import java.awt.Color;
import java.awt.Graphics;

import org.json.simple.*;

import com.dimension.Circle;
import com.dimension.Coordinate;
import com.infrastruture.Constants;
import com.infrastruture.Element;

public class Ball implements Element{
	
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public void load(Object object) {
		// TODO Auto-generated method stub
		System.out.println("------- Ball Load -------");
		JSONObject jsonObject1 = (JSONObject) object;
		
		this.getCircle().getCenter().setX((int)(long)(jsonObject1.get("BallX")));
		this.getCircle().getCenter().setY((int)(long)(jsonObject1.get("BallY")));
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
