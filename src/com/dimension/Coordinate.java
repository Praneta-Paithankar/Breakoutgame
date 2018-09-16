package com.dimension;

import org.apache.log4j.Logger;

public class Coordinate {
	protected Logger log = Logger.getLogger(Coordinate.class);
	private int x;
	private int y;
	
	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
