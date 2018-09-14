package com.breakout;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.component.Ball;
import com.component.Brick;
import com.component.Clock;
import com.component.Paddle;
import com.controller.GameController;
import com.dimension.Circle;
import com.dimension.Coordinate;
import com.dimension.Rectangle;
import com.infrastruture.Constants;
import com.timer.BreakoutTimer;
import com.ui.GUI;
import com.ui.GamePanel;
import com.ui.StaticPanel;
import com.infrastruture.*;

public class Breakout {
	
	public static void startGame(boolean isRestart){
		
		BreakoutTimer observable  = new BreakoutTimer();
		GamePanel boardPanel =new GamePanel();
		
		Circle c = new Circle(Constants.BALL_RADIUS, Constants.BALL_POS_X,Constants.BALL_POS_Y);
		Ball ball = new Ball(c, new Coordinate(Constants.BALL_DELTA_X, Constants.BALL_DELTA_Y), Constants.BALL_COLOR);
		boardPanel.addElement(ball);

		Rectangle r =new Rectangle(Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT,Constants.PADDLE_POS_X, Constants.PADDLE_POS_Y);
		Paddle paddle = new Paddle(r,Constants.PADDLE_DELTA_X,Constants.PADDLE_COLOR);
		boardPanel.addElement(paddle);
		
		Clock clock = new Clock();
		StaticPanel timerPanel = new StaticPanel();
		timerPanel.addElement(clock);
		
		GUI gui = new GUI(boardPanel,timerPanel);
		
		ArrayList<Brick> bricks = new ArrayList<>();
		int brickPosX = Constants.BRICK_START_X; 
		int brickPosY = Constants.BRICK_START_Y;
		for(int i=0; i< Constants.BRICK_NO; i++) {
			r = new Rectangle(Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT, brickPosX, brickPosY);
			Brick brick = new Brick(r , true,Constants.BRICK_COLOR);
			bricks.add(brick);
			boardPanel.addElement(brick);
			brickPosX += 2* Constants.BRICK_WIDTH+ Constants.BRICK_DISTANCE_X ;
			brickPosY +=  Constants.BRICK_DISTANCE_Y;
		}
		
		
		GameController driver = new GameController(ball, paddle, bricks, gui,observable, clock);
		
		gui.addDriver(driver);
		observable.startTimer();
		gui.setVisible(true);
		if(isRestart)
			observable.registerObserver(driver);
		else
			driver.pause();
	}
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
			
				startGame(false);	
			}
		});
	}
}
