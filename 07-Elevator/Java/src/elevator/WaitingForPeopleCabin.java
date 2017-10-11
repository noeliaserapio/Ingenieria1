package elevator;

public class WaitingForPeopleCabin implements CabinState {

	@Override
	public boolean isStopped() {
		return false;
	}

	@Override
	public boolean isMoving() {
		return false;
	}

	@Override
	public boolean isWaitingForPeople() {
		return true;
	}

}
