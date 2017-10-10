package elevator;

public class Cabin {

	public static final String SENSOR_DESINCRONIZED = "Sensor de cabina desincronizado";
	
	private Elevator elevator;
	private CabinDoor door;
	private Motor motor;
	private int currentFloorNumber;
	
	enum CabinState {
		STOPPED,
		MOVING,
		WAITING_FOR_PEOPLE
	};
	
	// Nuevo
	private CabinState state;
	
	public Cabin(Elevator elevator) {
		this.elevator = elevator;
		this.door = new CabinDoor(this);
		this.motor = new Motor();
		currentFloorNumber = 0;
		
		// Nuevo
		state = CabinState.STOPPED;
	}

	public int currentFloorNumber() {
		return currentFloorNumber;
	}

	//Cabin State
	public boolean isStopped() {
		return state == CabinState.STOPPED;
	}

	public boolean isMoving() {
		return state == CabinState.MOVING;
	}

	public boolean isWaitingForPeople() {
		return state == CabinState.WAITING_FOR_PEOPLE;
	}

	//Cabin Actions
	public void stop() {
		motor.stop();
	}

	public void waitForPeople() {
		throw new UnsupportedOperationException();
	}

	//Cabin events
	public void waitForPeopleTimedOut() {
		throw new UnsupportedOperationException();
	}

	public void onFloor(int aFloorNumber) {
		currentFloorNumber = aFloorNumber;
		state = CabinState.STOPPED;
	//	door.startOpening();
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
		state = CabinState.MOVING;
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
		door.close();
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
