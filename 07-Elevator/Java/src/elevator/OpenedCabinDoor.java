package elevator;

public class OpenedCabinDoor implements CabinDoorState{

	@Override
	public boolean isOpened() {
		return true;
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
		return false;
	}

}
