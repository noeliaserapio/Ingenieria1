package elevator;

public class CabinDoor {

	public static final String SENSOR_DESINCRONIZED = "Sensor de puerta desincronizado";
	
	private Cabin cabin;
	private Motor motor;

	public CabinDoor(Cabin cabin) {
		this.cabin = cabin;
		this.motor = new Motor();
	}

	//State
	public boolean isOpened() {
		throw new UnsupportedOperationException();
	}

	public boolean isOpening() {
		throw new UnsupportedOperationException();
	}

	public boolean isClosing() {
		throw new UnsupportedOperationException();
	}

	public boolean isClosed() {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	public void opened() {
		throw new UnsupportedOperationException();
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
