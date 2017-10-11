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



}
