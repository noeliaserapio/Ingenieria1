package elevator;

public class MovingCounterClockwiseMotor implements MotorState{

	@Override
	public boolean isMovingCounterClockwise() {
		return true;
	}

	@Override
	public boolean isMovingClockwise() {
		return false;
	}

	@Override
	public boolean isStopped() {
		return false;
	}

	@Override
	public boolean isMoving() {
		return true;
	}

	@Override
	public void assertIsNotMoving() {
		throw new RuntimeException(Motor.MOTOR_IS_MOVING);	
	}

}
