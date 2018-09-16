package com.commands;

import org.apache.log4j.Logger;

import com.component.Paddle;
import com.dimension.Coordinate;
import com.dimension.Rectangle;
import com.infrastruture.Command;
import com.infrastruture.Constants;

public class PaddleRightMoveCommand implements Command{
	protected Logger log = Logger.getLogger(PaddleRightMoveCommand.class);
	Paddle paddle;
	Coordinate prevTopLeft;
	
	public PaddleRightMoveCommand(Paddle paddle) {
		this.paddle = paddle;
	}
	
	@Override
	public void execute() {
		Rectangle rectangle = paddle.getRectangle();
		int topX = rectangle.getTopLeftCoordinate().getX();
		int topY = rectangle.getTopLeftCoordinate().getY();
		prevTopLeft = rectangle.getTopLeftCoordinate();
	    Coordinate currentTopLeft = new Coordinate(topX + paddle.getDeltaX(), topY);
		rectangle.setTopLeftCoordinate(currentTopLeft);
		checkBounds();
	}
	private void checkBounds() {
		Rectangle rectangle = paddle.getRectangle();
        int right = rectangle.getTopLeftCoordinate().getX() + rectangle.getWidth();
		if(right >= Constants.BOARD_PANEL_WIDTH) {
			Coordinate currentTopLeft = new Coordinate(Constants.BOARD_PANEL_WIDTH - rectangle.getWidth(),rectangle.getTopLeftCoordinate().getY());
			rectangle.setTopLeftCoordinate(currentTopLeft);
		}
	}
	@Override
	public void undo() {
		Rectangle rectangle = paddle.getRectangle();
		rectangle.setTopLeftCoordinate(prevTopLeft);
	}

}
