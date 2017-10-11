package elevator;

public class ClosingCabinDoor implements CabinDoorState{

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
		return true;
	}

	@Override
	public boolean isClosed() {
		return false;
	}

}
