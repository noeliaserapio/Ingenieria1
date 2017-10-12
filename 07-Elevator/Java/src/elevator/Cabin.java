package elevator;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Cabin {

	public static final String SENSOR_DESINCRONIZED = "Sensor de cabina desincronizado";
	
	private Elevator elevator;
	private CabinDoor door;
//	private List<CabinDoor> door;
	private Motor motor;
	private int currentFloorNumber;
	
/*	enum CabinState {
		STOPPED,
		MOVING,
		WAITING_FOR_PEOPLE
	};  */
	
	// Nuevo
	private CabinState state;
	
	public Cabin(Elevator elevator) {
		this.elevator = elevator;
		this.door = new CabinDoor(this);
	//	this.door = new ArrayList<CabinDoor>();
	//	this.door.add(new CabinDoor(this));
		this.motor = new Motor();
		currentFloorNumber = 0;
		
		// Nuevo
		state = new StoppedCabin();
	}

	public int currentFloorNumber() {
		return currentFloorNumber;
	}

	//Cabin State
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
	}

	public void waitForPeople() {
		state = new WaitingForPeopleCabin();
	}

	//Cabin events
	public void waitForPeopleTimedOut() {
		state = new StoppedCabin();
		door.startClosing();
	}

	public void onFloor(int aFloorNumber) {
		if(aFloorNumber == 0){
			throw new RuntimeException(Cabin.SENSOR_DESINCRONIZED);
		}else{
			if(aFloorNumber ==  currentFloorNumber + 1){
				currentFloorNumber = aFloorNumber;
				state = new StoppedCabin();
				motor.stop();
				door.startOpening();
			}else{
				throw new RuntimeException(Cabin.SENSOR_DESINCRONIZED);
			}	
		}
			
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

	//Door - Sensor events
	public void doorClosed() {
		state = new MovingCabin();
		door.closed();
		motor.moveClockwise();
	}

	public void doorOpened() {
		door.opened();
	}

	//Door - Button events
	public void openDoor() {
		door.open();
	}

	public void closeDoor() {
		if(!isMoving()){
			state = new StoppedCabin();
			door.close();
		}
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
