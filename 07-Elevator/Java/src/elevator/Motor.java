package elevator;

public class Motor {
	
	public static final String MOTOR_IS_MOVING = "El motor se esta moviendo";

/*	enum MotorState {
		STOPPED,
		MOVING_CLOCKWISE,
		MOVING_COUNTER_CLOCKWISE
	}; */
	
	private MotorState state;
	
	public Motor() {
		state = new StoppedMotor();
	}
	
	public void stop() {
		state = new StoppedMotor();
	}

	public void moveClockwise() {
		assertIsNotMoving();
		
		state = new MovingClockwiseMotor();
	}

	public void moveCounterClockwise() {
		assertIsNotMoving();
		
		state = new MovingCounterClockwiseMotor();
	}

	public boolean isMovingCounterClockwise() {
		return state.isMovingCounterClockwise();
	}

	public boolean isMovingClockwise() {
		return state.isMovingClockwise();
	}

	public boolean isStopped() {
		return state.isStopped();
	}

	public boolean isMoving() {
		return !isStopped();
	}

	void assertIsNotMoving() {
		if(isMoving()) throw new RuntimeException(Motor.MOTOR_IS_MOVING);
	}
}
