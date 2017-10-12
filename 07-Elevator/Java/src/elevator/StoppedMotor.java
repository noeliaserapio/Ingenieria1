package elevator;

public class StoppedMotor implements MotorState{

	@Override
	public boolean isMovingCounterClockwise() {
		return false;
	}

	@Override
	public boolean isMovingClockwise() {
		return false;
	}

	@Override
	public boolean isStopped() {
		return true;
	}

	@Override
	public boolean isMoving() {
		return false;
	}

	@Override
	public void assertIsNotMoving() {
	}


}
