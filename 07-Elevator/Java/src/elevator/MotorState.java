package elevator;

public interface MotorState {
	
	public boolean isMovingCounterClockwise();

	public boolean isMovingClockwise();

	public boolean isStopped();
	
	public boolean isMoving();

	void assertIsNotMoving();

}
