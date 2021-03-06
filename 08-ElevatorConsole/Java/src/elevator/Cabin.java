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

import java.util.ArrayList;
import java.util.List;

public class Cabin {

	public static final String SENSOR_DESINCRONIZED = "Sensor de cabina desincronizado";
	


	private Elevator elevator;
	private CabinState state;
	private CabinDoor door;
	private Motor motor;
	private int currentFloorNumber;
	private List<CabinStateVisitor> observers = new ArrayList<CabinStateVisitor>();
	
	public Cabin(Elevator elevator) {
		this.elevator = elevator;
		this.door = new CabinDoor(this);
		this.motor = new Motor();
		currentFloorNumber = 0;
		makeStopped();
	}
	
	public void addObserverCabin(CabinStateVisitor v) {
		observers.add(v);
	}
	
	public void addObserverCabinDoor(CabinDoorStateVisitor v) {
		door.addObserverCabinDoor(v);
	}

	public int currentFloorNumber() {
		return currentFloorNumber;
	}
	
	public Elevator getElevator() {
		return elevator;
	}
	
	private void notifyObservers() {
		for(CabinStateVisitor v : observers){
			this.state.accept(v);
		}
	}

	//Cabin State
	private void makeStopped() {
		this.state = new CabinStoppedState(this);
		notifyObservers();
	}

	private void makeMoving() {
		this.state = new CabinMovingState(this);
		notifyObservers();
	}
	
	private void makeWaitingForPeople() {
		this.state = new CabinWaitingForPeopleState(this);
		notifyObservers();
	}

	public boolean isStopped() {
		return state.isStopped();
	}

	public boolean isMoving() {
		return state.isMoving();
	}

	public boolean isWaitingForPeople() {
		return state.isWaitingForPeople();
	}

	//Cabin Actions
	public void stop() {
		motor.stop();
		makeStopped();
	}

	public void waitForPeople() {
		//TODO: Faltan tests, por ejemplo para asertar que solo lo puede hacer si esta parada
		makeWaitingForPeople();
	}

	//Cabin events
	public void waitForPeopleTimedOut() {
		startClosingDoor();
		makeStopped();
	}

	public void onFloor(int aFloorNumber) {
		assertIsMovingCorrectly(aFloorNumber);
		
		currentFloorNumber = aFloorNumber;
		if (elevator.hasToStopOnCurrentFloor()) reachedFloorToStop();
	}

	public void assertIsMovingCorrectly(int aFloorNumber) {
		//Esta implementacion es muy sencilla y asume que se esta yendo para arriba siempre
		if(currentFloorNumber+1!=aFloorNumber) throw new RuntimeException(SENSOR_DESINCRONIZED);
	}

	public void reachedFloorToStop() {
		elevator.reachedFloorToStop();
		stop();
		startOpeningDoor();
	}

	//Door state
	public boolean isDoorOpened() {
		return door.isOpened();
	}

	public boolean isDoorOpening() {
		return door.isOpening();
	}

	public boolean isDoorClosing() {
		return door.isClosing();
	}

	public boolean isDoorClosed() {
		return door.isClosed();
	}

	//Door actions
	public void startClosingDoor() {
		door.startClosing();
	}

	public void startOpeningDoor() {
		door.startOpening();
	}

	//Door - Sensor events
	public void doorClosed() {
		door.closed();
		//Esto sirve solo porque siempre se esta yendo para arriba :-)
		motor.moveClockwise();
		makeMoving();
	}

	public void doorOpened() {
		door.opened();
	}

	//Door - Button events
	public void openDoor() {
		door.open();
	}

	public void closeDoor() {
		state.closeDoor();
	}

	public void closeDoorWhenWaitingPeople() {
		//Si la gente del habitaculo no quiere esperar puede cerrar la puerta
		door.close();
		makeStopped();		
	}

	public void closeDoorWhenMoving() {
		//Si me estoy moviendo la puerta ya esta cerrada, no hago nada
	}

	public void closeDoorWhenStopped() {
		//Si no estoy haciendo nada la puerta no se puede cerrar
	}

	public void assertMotorIsNotMoving() {
		motor.assertIsNotMoving();
	}
	
	public boolean isDoorMotorMovingClockwise() {
		return door.isMotorMovingClockwise();
	}

	public boolean isMotorStopped() {
		return motor.isStopped();
	}

	public boolean isDoorMotorStopped() {
		return door.isMotorStopped();
	}

	public boolean isMotorMovingClockwise() {
		return motor.isMovingClockwise();
	}

	public boolean isDoorMotorMovingCounterClockwise() {
		return door.isMotorMovingCounterClockwise();
	}
}
