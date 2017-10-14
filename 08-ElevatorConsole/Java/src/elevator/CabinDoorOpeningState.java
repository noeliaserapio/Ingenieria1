/*
 * Developed by 10Pines SRL
 * License: 
 * This work is licensed under the 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ 
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, 
 * California, 94041, USA.
 *  
 */
package elevator;

public class CabinDoorOpeningState implements CabinDoorState {

	private CabinDoor door;

	public CabinDoorOpeningState(CabinDoor door) {
		this.door = door;
	}

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

	@Override
	public void open() {
		door.openWhenOpening();
	}

	@Override
	public void closed() {
		door.closedWhenOpening();
	}

	@Override
	public void accept(CabinDoorStateVisitor visitor) {
		visitor.visitCabinDoorOpening(this);
	}

}
