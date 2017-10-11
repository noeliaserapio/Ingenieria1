package elevator;

public interface CabinState {
	
	public boolean isStopped();

	public boolean isMoving();

	public boolean isWaitingForPeople();

}
