package com.commands;

import com.infrastruture.Command;

import org.apache.log4j.Logger;

import com.component.Brick;

public class BrickEnactCommand implements Command {
	protected Logger log = Logger.getLogger(BrickEnactCommand.class);
	Brick brick;

	
	public BrickEnactCommand(Brick brick) {
		this.brick = brick;

	}

	@Override
	public void execute() {
		brick.setVisible(false);
	}

	@Override
	public void undo() {
		brick.setVisible(!brick.isVisible());

	}

}
