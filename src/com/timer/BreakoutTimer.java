package com.timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.infrastruture.Observer;
import com.ui.StaticPanel;
import com.infrastruture.Constants;
import com.infrastruture.Observable;


public class BreakoutTimer implements Observable{
	protected Logger log = Logger.getLogger(BreakoutTimer.class);
	private Timer timer;
	ArrayList<Observer> observers = new ArrayList<>();

	public BreakoutTimer(){
				
        ActionListener actionPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               notifyObservers();
            }
        };
         
        timer = new Timer(Constants.TIMER_COUNT ,(ActionListener) actionPerformer);
        startTimer();
	}
	
	@Override
	public void registerObserver(Observer o) {
		
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.remove(observers.indexOf(o));
	}

	@Override
	public void notifyObservers() {
		for(Observer observer: observers) {
			observer.update();
		}
	}
	
	public boolean isObserverListEmpty() {
		if(this.observers.isEmpty()) return true;
		return false;
	}
	
	public void startTimer() {
		timer.start();
	}
	
	public void stopTimer() {
		timer.stop();
	}
}
