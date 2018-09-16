package com.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.apache.log4j.Logger;
import org.json.simple.*;

import com.infrastruture.Constants;
import com.infrastruture.Observer;
import com.infrastruture.Element;


public class Clock implements Element{
	protected Logger log = Logger.getLogger(Clock.class);
	private long milisecondsElapsed;
	private JSONObject jsonObject;

	public Clock() {
		milisecondsElapsed = 0;
	}

	public String getTime() {
		if (getSeconds() >= 10) {
			return Integer.toString(getMinutes()) + ":" + Integer.toString(getSeconds());
		} else {
			return Integer.toString(getMinutes()) + ":0" + Integer.toString(getSeconds());
		}
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO center box around the time 
		g.drawRect(0, 150, 250, 100);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		String time = getTime();
		g.drawString(time, 10+ 5, 200);
	}

	@Override
	public void reset() {
		milisecondsElapsed = 0;	
	}
	
	public long getMilisecondsElapsed() {
		return milisecondsElapsed;
	}

	public void setMilisecondsElapsed(long milisecondsElapsed) {
		this.milisecondsElapsed = milisecondsElapsed;
	}

	private int getMinutes() {
		return (int) (milisecondsElapsed / 60000);
	}

	private int getSeconds() {
		return (int) ((milisecondsElapsed / 1000) % 60);
	}

	@Override
	public JSONObject save() {
		jsonObject = new JSONObject();
		try {
			jsonObject.put("Clock", this.getMilisecondsElapsed());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return jsonObject;
	}

	@Override
	public int load(Object object) {
		// TODO Auto-generated method stub
		jsonObject = (JSONObject) object;
		this.setMilisecondsElapsed((long)(jsonObject.get("Clock")));
		
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