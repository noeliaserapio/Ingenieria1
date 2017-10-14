/*
 * Developed by 10Pines SRL
 * License: 
 * This work is licensed under the 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ 
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, 
 * California, 94041, USA.
 *  
 */
package elevator;

import java.util.SortedSet;
import java.util.TreeSet;

public class Elevator {

	private ElevatorState state;
	private Cabin cabin;
	private SortedSet<Integer> floorsToGo = new TreeSet<Integer>();
	
	public Elevator(){
		cabin = new Cabin(this);
		makeIdle();
	}

	//Elevator state
	private void makeIdle() {
		state = new ElevatorIdleState(this);
	}
	
	private void makeWorking() {
		state = new ElevatorWorkingState(this);
	}
	
	public boolean isIdle() {
		return state.isIdle();
	}

	public boolean isWorking() {
		return state.isWorking();
	}

	//Door state
	public boolean isCabinDoorOpened() {
		return cabin.isDoorOpened();
	}

	public boolean isCabinDoorOpening() {
		return cabin.isDoorOpening();
	}

	public boolean isCabinDoorClosed() {
		return cabin.isDoorClosed();
	}

	public boolean isCabinDoorClosing() {
		return cabin.isDoorClosing();
	}

	//Cabin state
	public int cabinFloorNumber() {
		return cabin.currentFloorNumber();
	}

	public boolean isCabinStopped() {
		return cabin.isStopped();
	}
	
	public boolean isCabinMoving() {
		return cabin.isMoving();
	}

	public boolean isCabinWaitingForPeople() {
		return cabin.isWaitingForPeople();
	}

	//Button Events
	public void goUpPushedFromFloor(int aFloorNumber) {
		state.goUpPushedFromFloor(aFloorNumber);
	}

	public void goUpPushedFromFloorWhenIdle(int aFloorNumber) {
		startWorkingGoingTo(aFloorNumber);
	}

	public void goUpPushedFromFloorWhenWorking(int aFloorNumber) {
		queFloorToGo(aFloorNumber);
	}
	
	private void startWorkingGoingTo(int aFloorNumber) {
		queFloorToGo(aFloorNumber);
		makeWorking();
		cabin.startClosingDoor();
	}

	private void queFloorToGo(int aFloorNumber) {
		floorsToGo.add(aFloorNumber);
	}

	public void openCabinDoor() {
		cabin.openDoor();
	}

	public void closeCabinDoor() {
		state.closeCabinDoor();
	}

	public void closeCabinDoorWhenIdle() {
		//Si estoy idle no tengo que cerrar la puerta
	}

	public void closeCabinDoorWhenWorking() {
		cabin.closeDoor();
	}
	
	//Sensor Events
	public void cabinOnFloor(int aFloorNumber) {
		cabin.onFloor(aFloorNumber);
	}
	
	public void cabinDoorClosed() {
		cabin.doorClosed();
	}
	
	public void cabinDoorOpened() {
		cabin.doorOpened();
		waitForPeopleOrMakeIdle();
	}

	private void waitForPeopleOrMakeIdle() {
		if(hasFloorsToGo())
			cabin.waitForPeople();
		else
			makeIdle();
	}

	public boolean hasFloorsToGo() {
		return !floorsToGo.isEmpty();
	}

	public void waitForPeopleTimedOut() {
		cabin.waitForPeopleTimedOut();
	}

	//Floors queue
	//Esta implementación es muy sencilla y asume que siempre se esta yendo para arriba
	public boolean hasToStopOnCurrentFloor() {
		return floorsToGo.first() == cabin.currentFloorNumber();
	}
	
	public void reachedFloorToStop() {
		floorsToGo.remove(floorsToGo.first());
	}
	
	public boolean isCabinDoorMotorMovingClockwise() {
		return cabin.isDoorMotorMovingClockwise();
	}

	public boolean isCabinMotorStopped() {
		return cabin.isMotorStopped();
	}

	public boolean isCabinDoorMotorStopped() {
		return cabin.isDoorMotorStopped();
	}

	public boolean isCabinMotorMovingClockwise() {
		return cabin.isMotorMovingClockwise();
	}

	public boolean isCabinDoorMotorMovingCounterClockwise() {
		return cabin.isDoorMotorMovingCounterClockwise();
	}
}
