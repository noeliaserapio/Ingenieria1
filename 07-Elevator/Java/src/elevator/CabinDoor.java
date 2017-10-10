package elevator;

public class CabinDoor {

	public static final String SENSOR_DESINCRONIZED = "Sensor de puerta desincronizado";
	
	private Cabin cabin;
	private Motor motor;
	
	enum CabinDoorState {
		OPENED,
		OPENING,
		CLOSING,
		CLOSED
	};
	
	// Nuevo
	private CabinDoorState state;

	public CabinDoor(Cabin cabin) {
		this.cabin = cabin;
		this.motor = new Motor();
		
		// Nuevo
		state = CabinDoorState.OPENED;
	}

	//State
	public boolean isOpened() {
		return state == CabinDoorState.OPENED;
	}

	public boolean isOpening() {
		return state == CabinDoorState.OPENING;
	}

	public boolean isClosing() {
		return state == CabinDoorState.CLOSING;
	}

	public boolean isClosed() {
		return state == CabinDoorState.CLOSED;
	}

	//Actions
	public void startClosing() {
		cabin.assertMotorIsNotMoving();
		
		motor.moveClockwise();
		
		state = CabinDoorState.CLOSING;
	}

	public void startOpening() {
		cabin.assertMotorIsNotMoving();
		
		motor.moveCounterClockwise();
		
		state = CabinDoorState.OPENING;
	}

	//Sensor events
	public void closed() {
		state = CabinDoorState.CLOSED;
		motor.stop();
	}

	public void opened() {
		state = CabinDoorState.OPENED;
	}

	//Button events
	public void open() {
		throw new UnsupportedOperationException();
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
