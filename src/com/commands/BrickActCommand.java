package com.commands;

import com.infrastruture.Command;
import com.component.Brick;

public class BrickActCommand implements Command {

	Brick brick;
	boolean prevState;
	
	public BrickActCommand(Brick brick) {
		this.brick = brick;
		this.prevState = false;
	}

	@Override
	public void execute() {
		prevState = brick.isVisible();
		brick.setVisible(false);
	}

	@Override
	public void undo() {
		brick.setVisible(prevState);

	}

}
