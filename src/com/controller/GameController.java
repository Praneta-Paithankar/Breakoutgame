package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.breakout.Breakout;
import com.commands.BallChangeXDirectionCommand;
import com.commands.BallChangeYDirectionCommand;
import com.commands.BallEnactCommand;
import com.commands.BrickEnactCommand;
import com.commands.PaddleLeftMoveCommand;
import com.commands.PaddleRightMoveCommand;
import com.commands.TimerCommand;
import com.component.Ball;
import com.component.Brick;
import com.component.Clock;
import com.component.Paddle;
import com.helper.CollisionChecker;
import com.infrastruture.Command;
import com.infrastruture.Constants;
import com.infrastruture.Direction;
import com.infrastruture.Observer;
import com.timer.BreakoutTimer;
import com.ui.GUI;

public class GameController implements Observer, KeyListener,ActionListener{
	
	private Ball ball;
	private Paddle paddle;
	private ArrayList<Brick> bricks;
    private GUI gui;
    private BreakoutTimer observable;
    private int noOfBricks;
    private BrickEnactCommand[] brickActCommands;
	private BallEnactCommand ballActCommand;
    private TimerCommand timerCommand;
    private Clock clock;
    private boolean isGamePaused = false;
    private CollisionChecker collisionChecker;
    private Deque<Command> commandQueue;
    private BallChangeXDirectionCommand ballChangeXDirectionCommand;
    private BallChangeYDirectionCommand ballChangeYDirectionCommand;
    private PaddleLeftMoveCommand paddleLeftMoveCommand;
    private PaddleRightMoveCommand paddleRightMoveCommand;
    
	public GameController(Ball ball, Paddle paddle, ArrayList<Brick> bricks, GUI gui,BreakoutTimer observable, Clock clock,CollisionChecker collisionChecker) {
		
		this.ball = ball;
		this.paddle = paddle;
		this.bricks = bricks;
		this.gui = gui;
		this.observable = observable;
		this.clock = clock;
		this.collisionChecker = collisionChecker;
		this.noOfBricks = bricks.size();
		brickActCommands = new BrickEnactCommand [noOfBricks];
		commandQueue = new ArrayDeque<Command>();
		timerCommand = new TimerCommand(clock);
		
		initCommands();
    }
	private void initCommands()
	{
		
		int i=0;
		for(Brick b : bricks)
		{
			brickActCommands[i] = new BrickEnactCommand(b);
			i++;
		}
		timerCommand = new TimerCommand(clock);
		ballActCommand = new BallEnactCommand(ball);
		ballChangeXDirectionCommand = new BallChangeXDirectionCommand(ball);
		ballChangeYDirectionCommand = new BallChangeYDirectionCommand(ball);
		
	}

	@Override
	public void update() {
		
		//initCommands();
		timerCommand.execute();
		ballActCommand.execute();
		commandQueue.addLast(timerCommand);
		commandQueue.addLast(ballActCommand);		
		Direction result = collisionChecker.checkCollisionBetweenBallAndWall(ball);
		changeBallDirectionCommand(result);
		int i= 0;
		
		for(Brick b : bricks) {
			if(b.isVisible())
			{
				result = collisionChecker.checkCollisionBetweenCircleAndRectangle(ball.getCircle(), b.getRectangle());
				if(result != Direction.NONE){
					brickActCommands[i].execute();
					commandQueue.addLast(brickActCommands[i]);
					changeBallDirectionCommand(result);
					noOfBricks--;
				}
			}
			i++;
		}
		if(noOfBricks == 0)
		{   
			// Stopping the observable
			observable.stopTimer();
			gui.removeKeyListner();
			gui.draw(null);
  			SwingUtilities.invokeLater(
  					new Runnable() {

  						@Override
  						public void run() {
  						
  							gameOver();	
  						}
			});
  			return;
		}
		//Check collision between ball and paddle
		result = collisionChecker.checkCollisionBetweenCircleAndRectangle(ball.getCircle(), paddle.getRectangle());
		changeBallDirectionCommand(result);
		gui.draw(null);
		
	}
	private void changeBallDirectionCommand(Direction result) {
		
		if(result == Direction.X) {
			ballChangeXDirectionCommand.execute();
			commandQueue.addLast(ballChangeXDirectionCommand);
		}else if(result == Direction.Y) {
			ballChangeYDirectionCommand.execute();
			commandQueue.addLast(ballChangeYDirectionCommand);
		}else if(result == Direction.BOTH) {
			ballChangeXDirectionCommand.execute();
			ballChangeYDirectionCommand.execute();
			commandQueue.addLast(ballChangeXDirectionCommand);
			commandQueue.addLast(ballChangeYDirectionCommand);
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			setPaddleLeftMoveCommand(new PaddleLeftMoveCommand(paddle));
			paddleLeftMoveCommand.execute();
			commandQueue.addLast(paddleLeftMoveCommand);
			collisionChecker.checkCollisionBetweenCircleAndRectangle(ball.getCircle(),paddle.getRectangle());
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			setPaddleRightMoveCommand(new PaddleRightMoveCommand(paddle));
			paddleRightMoveCommand.execute();
			commandQueue.addLast(paddleRightMoveCommand);
			collisionChecker.checkCollisionBetweenCircleAndRectangle(ball.getCircle(),paddle.getRectangle());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	private void undoAction() {

		int count = 0;
		while(count != Constants.TIMER_COUNT) {
			Command val=commandQueue.pollLast();
			if(val == null)
				break;
			if(val instanceof TimerCommand)
			{
				count++;
			}
			if(val instanceof BrickEnactCommand)
			{
				noOfBricks++;
			}
			val.undo();
		}
		
	}
	
	private void replayAction() {
		// TODO Auto-generated method stub
		
		Iterator<Command> itr = commandQueue.iterator();
		int brickCount = noOfBricks;
		gameReset();
		
		new Thread(){
			public void run(){
				while(itr.hasNext()){
					try {
						SwingUtilities.invokeAndWait(new Runnable(){
							Command val = (Command) itr.next();
							@Override
							public void run() {
								// TODO Auto-generated method stub
								val.execute();
								gui.draw(null);
								try {
									currentThread();
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
					} catch (InvocationTargetException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				noOfBricks = brickCount;
			}
		}.start();
		
	}
	
	
	public void pause() {
		isGamePaused = true;
		if(!observable.isObserverListEmpty()) {
		observable.removeObserver(this);
		}
	}

	public void unPause() {
		isGamePaused = false;
		observable.registerObserver(this);
	}
	
	//Switch between actions when a button is pressed
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String commandText= e.getActionCommand();
		if(commandText.equals("undo")) {
			if(!isGamePaused) {
				pause();
				undoAction();
				unPause();
			} else {
				undoAction();
			}
			gui.changeFocus();
			gui.draw(null);

		}else if(commandText.equals("replay")) {
			pause();
			replayAction();
			gui.changeFocus();
		}else if(commandText.equals("start")) {
			if(isGamePaused) {
				unPause();
				gui.changeFocus();
				gui.draw(null);
			}else {
				gui.dispose();
				gui.revalidate();
				Breakout.startGame(true);
			}
		}else if(commandText.equals("pause")) {
			pause();
			gui.changeFocus();
			gui.draw(null);
		}
		
	}
	
	public void gameReset() {
		
		ball.reset();
		paddle.reset();
		clock.reset();
		noOfBricks = Constants.BRICK_NO;
		for (Brick b : bricks) {
			b.reset();	
		}
	}
	
	public void gameOver() {
		pause();
		Object[] options = {  "Reset", "Exit", "Replay"}; 
		String outputMsg = new String();
		outputMsg = "Your Score is " + clock.getTime();
		int a = JOptionPane.showOptionDialog(gui.getBoardPanel(), outputMsg, "Game Over", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
				, null, options, null);
		
		if(a == JOptionPane.YES_OPTION) {
			gui.dispose();
			gui.revalidate();
			Breakout.startGame(true);			
		}
		else if(a == JOptionPane.CANCEL_OPTION) {
			replayAction();
		}
		else
			System.exit(0);
		isGamePaused = false;
	}
	
	
	public TimerCommand getTimerCommand() {
		return timerCommand;
	}
	public void setTimerCommand(TimerCommand timerCommand) {
		this.timerCommand = timerCommand;
	}
	public PaddleLeftMoveCommand getPaddleLeftMoveCommand() {
		return paddleLeftMoveCommand;
	}
	public void setPaddleLeftMoveCommand(PaddleLeftMoveCommand paddleLeftMoveCommand) {
		this.paddleLeftMoveCommand = paddleLeftMoveCommand;
	}
	public PaddleRightMoveCommand getPaddleRightMoveCommand() {
		return paddleRightMoveCommand;
	}
	public void setPaddleRightMoveCommand(PaddleRightMoveCommand paddleRightMoveCommand) {
		this.paddleRightMoveCommand = paddleRightMoveCommand;
	}
	public BallEnactCommand getBallActCommand() {
		return ballActCommand;
	}
	public void setBallActCommand(BallEnactCommand ballActCommand) {
		this.ballActCommand = ballActCommand;
	}
	public BallChangeXDirectionCommand getBallChangeXDirectionCommand() {
		return ballChangeXDirectionCommand;
	}
	public void setBallChangeXDirectionCommand(BallChangeXDirectionCommand ballChangeXDirectionCommand) {
		this.ballChangeXDirectionCommand = ballChangeXDirectionCommand;
	}
	public BallChangeYDirectionCommand getBallChangeYDirectionCommand() {
		return ballChangeYDirectionCommand;
	}
	public void setBallChangeYDirectionCommand(BallChangeYDirectionCommand ballChangeYDirectionCommand) {
		this.ballChangeYDirectionCommand = ballChangeYDirectionCommand;
	}
	public BrickEnactCommand[] getBrickActCommands() {
			return brickActCommands;
		}
		public void setBrickActCommands(BrickEnactCommand[] brickActCommands) {
			this.brickActCommands = brickActCommands;
		}
}
