package elevator;

public class OpeningCabinDoor implements CabinDoorState{

	@Override
	public boolean isOpened() {
		return false;
	}

	@Override
	public boolean isOpening() {
		return true;
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
