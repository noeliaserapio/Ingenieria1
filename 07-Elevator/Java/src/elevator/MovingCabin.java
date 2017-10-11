package elevator;

public class MovingCabin implements CabinState {

	@Override
	public boolean isStopped() {
		return false;
	}

	@Override
	public boolean isMoving() {
		return true;
	}

	@Override
	public boolean isWaitingForPeople() {
		return false;
	}

}
