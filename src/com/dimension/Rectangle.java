package com.dimension;

import org.apache.log4j.Logger;

public class Rectangle {
	protected Logger log = Logger.getLogger(Rectangle.class);
	private int width;
	private int height;
	private Coordinate topLeft;
	
	public Rectangle(int width, int height, Coordinate topLeft) {
		super();
		this.width = width;
		this.height = height;
		this.topLeft = topLeft;
	}
	public Rectangle(int width, int height, int topLeftX,int topLeftY) {
		super();
		this.width = width;
		this.height = height;
		this.topLeft = new Coordinate(topLeftX,topLeftY);
	}
	public Coordinate getTopLeftCoordinate() {
		return topLeft;
	}
	public void setTopLeftCoordinate(Coordinate topLeft) {
		this.topLeft = topLeft;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
