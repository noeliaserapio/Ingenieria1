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



}
