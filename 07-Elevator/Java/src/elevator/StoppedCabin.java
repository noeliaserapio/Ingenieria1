package elevator;

public class StoppedCabin implements CabinState {

	@Override
	public boolean isStopped() {
		return true;
	}

	@Override
	public boolean isMoving() {
		return false;
	}

	@Override
	public boolean isWaitingForPeople() {
		return false;
	}

}
