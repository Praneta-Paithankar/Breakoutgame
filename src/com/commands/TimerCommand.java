package com.commands;

import com.component.Clock;
import com.infrastruture.Command;
import com.infrastruture.Constants;
import com.timer.BreakoutTimer;
import com.ui.StaticPanel;

public class TimerCommand implements Command {

	private Clock clock;
	
	public TimerCommand(Clock clock) {
		this.clock = clock;
		
	}

	@Override
	public void execute() {
		clock.setMilisecondsElapsed(clock.getMilisecondsElapsed()+Constants.TIMER_COUNT);
	
	}

	@Override
	public void undo() {
		clock.setMilisecondsElapsed(clock.getMilisecondsElapsed()-Constants.TIMER_COUNT);
		
	}
}
