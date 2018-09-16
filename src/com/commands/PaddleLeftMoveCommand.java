package com.commands;

import org.apache.log4j.Logger;

import com.component.Paddle;
import com.dimension.Coordinate;
import com.dimension.Rectangle;
import com.infrastruture.Command;

public class PaddleLeftMoveCommand implements Command {
	protected Logger log = Logger.getLogger(PaddleLeftMoveCommand.class);
	Paddle paddle;
	Coordinate prevTopLeft;
	
	public PaddleLeftMoveCommand(Paddle paddle) {
		this.paddle = paddle;
	}

	@Override
	public void execute() {
		Rectangle rectangle = paddle.getRectangle();
		prevTopLeft = rectangle.getTopLeftCoordinate();
		int topX = rectangle.getTopLeftCoordinate().getX();
		int topY = rectangle.getTopLeftCoordinate().getY();
	    Coordinate currentTopLeft = new Coordinate(topX - paddle.getDeltaX(), topY);
		rectangle.setTopLeftCoordinate(currentTopLeft);
		checkBounds();
	}
	private void checkBounds() {
		Rectangle rectangle = paddle.getRectangle();
		int left = rectangle.getTopLeftCoordinate().getX();
		if(left <= 0) {
			rectangle.setTopLeftCoordinate(new Coordinate(0,rectangle.getTopLeftCoordinate().getY()));
		}
	}
	@Override
	public void undo() {
		Rectangle rectangle = paddle.getRectangle();
		rectangle.setTopLeftCoordinate(prevTopLeft);
	}

}
