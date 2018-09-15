package com.infrastruture;

import java.awt.Graphics;

import org.json.simple.*;

public interface Element {
	 public JSONObject save();
	 public int load(Object object);
	 void draw(Graphics g);
	 void reset();
	 public void addElement(Element e);
	 public void removeElement(Element e);
}
