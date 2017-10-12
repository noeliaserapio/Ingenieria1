package elevator;

import org.hamcrest.core.IsSame;

public class CabinDoor {

	public static final String SENSOR_DESINCRONIZED = "Sensor de puerta desincronizado";
	
	private Cabin cabin;
	private Motor motor;
	
	// Nuevo
	private static CabinDoorState state;

	public CabinDoor(Cabin cabin) {
		this.cabin = cabin;
		this.motor = new Motor();
		
		// Nuevo
		state = new OpenedCabinDoor();
	}

	//State
	public boolean isOpened() {
		return state.isOpened();
	}

	public boolean isOpening() {
		return state.isOpening();
	}

	public boolean isClosing() {
		return state.isClosing();
	}

	public boolean isClosed() {
		return state.isClosed();
	}

	//Actions
	public void startClosing() {
		cabin.assertMotorIsNotMoving();
		
		if(motor.isMoving()){
			motor.stop();
		}
		
		motor.moveClockwise();
		state = new ClosingCabinDoor();
	}

	public void startOpening() {
		cabin.assertMotorIsNotMoving();
		
		motor.moveCounterClockwise();
		
		state = new OpeningCabinDoor();
	}

	//Sensor events
	public void closed() {
		state = new ClosedCabinDoor();
		motor.stop();
	}

	public void opened() {
		state = new OpenedCabinDoor();
		motor.stop();
	}

	//Button events
	public void open() {
	//	startOpening();
	//	state = CabinDoorState.OPENING;
		if(isClosing()) state = new OpeningCabinDoor();
		if(motor.isMovingClockwise()){ 
			motor.stop();
			motor.moveCounterClockwise();
		}
	}

	public void close() {
		startClosing();
	}

	public boolean isMotorMovingClockwise() {
		return motor.isMovingClockwise();
	}

	public boolean isMotorStopped() {
		return motor.isStopped();
	}

	public boolean isMotorMovingCounterClockwise() {
		return motor.isMovingCounterClockwise();
	}
}
