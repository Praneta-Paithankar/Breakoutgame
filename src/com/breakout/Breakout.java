package com.breakout;

import java.awt.Color;

import com.component.Ball;
import com.component.Brick;
import com.component.Paddle;
import com.dimension.Circle;
import com.dimension.Coordinate;
import com.dimension.Rectangle;
import com.driver.Driver;
import com.infrastruture.Constants;
import com.timer.BreakoutTimer;
import com.ui.GUI;
import com.ui.GamePanel;

public class Breakout {
	
	public static void main(String[] args){
		
		BreakoutTimer timer  = new BreakoutTimer(Constants.tickPerSecond);
		GamePanel boardPanel =new GamePanel();
		
		Ball ball = new Ball(new Circle(15, 0, 200), new Coordinate(20, 40), new Color(128,0,128));
		boardPanel.addElement(ball);

		Paddle paddle = new Paddle(new Rectangle(200, 40, 350, 600), 30, new Color(00, 64 ,192));
		boardPanel.addElement(paddle);
		
		Brick brick = new Brick(new Rectangle(75, 30, 600, 100), true, new Color(128,0,0));
		boardPanel.addElement(brick);
		
		GUI gui = new GUI(boardPanel);
		Driver driver = new Driver(ball, paddle, brick, gui,timer);
		
		gui.addDriver(driver);
		timer.addObserver(driver);
    	timer.startTimer();
       	gui.pack();
		gui.setVisible(true);
			
	}

}
