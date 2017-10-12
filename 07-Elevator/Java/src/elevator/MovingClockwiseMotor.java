package elevator;

public class MovingClockwiseMotor implements MotorState{

	@Override
	public boolean isMovingCounterClockwise() {
		return false;
	}

	@Override
	public boolean isMovingClockwise() {
		return true;
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
