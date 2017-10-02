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
	}

	public void startOpening() {
		cabin.assertMotorIsNotMoving();
		
		motor.moveCounterClockwise();
	}

	//Sensor events
	public void closed() {
		state = CabinDoorState.CLOSED;
	}

	public void opened() {
		state = CabinDoorState.OPENED;
	}

	//Button events
	public void open() {
		throw new UnsupportedOperationException();
	}

	public void close() {
		throw new UnsupportedOperationException();
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
