package com.dimension;

import org.apache.log4j.Logger;

import com.helper.CollisionChecker;

public class Circle {
	protected Logger log = Logger.getLogger(Circle.class);
	private int radius;
	private Coordinate center;

	public Circle(int radius, Coordinate center) {
		this.radius = radius;
		this.center = center;
	}
	public Circle(int radius, int centerX, int centerY) {
		super();
		this.radius = radius;
		this.center = new Coordinate(centerX,centerY);
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public Coordinate getCenter() {
		return center;
	}
	public void setCenter(Coordinate center) {
		this.center = center;
	}
	
}
