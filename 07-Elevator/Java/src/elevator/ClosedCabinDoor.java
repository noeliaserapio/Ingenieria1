package elevator;

public class ClosedCabinDoor implements CabinDoorState{

	@Override
	public boolean isOpened() {
		return false;
	}

	@Override
	public boolean isOpening() {
		return false;
	}

	@Override
	public boolean isClosing() {
		return false;
	}

	@Override
	public boolean isClosed() {
		return true;
	}

}
