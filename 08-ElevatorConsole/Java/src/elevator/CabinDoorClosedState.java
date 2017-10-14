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

public class CabinDoorClosedState implements CabinDoorState {

	private CabinDoor door;

	public CabinDoorClosedState(CabinDoor door) {
		this.door = door;
	}

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

	@Override
	public void open() {
		door.openWhenClosed();
	}

	@Override
	public void closed() {
		door.closedWhenClosed();
	}

	@Override
	public void accept(CabinDoorStateVisitor visitor) {
		visitor.visitCabinDoorClosed(this);
	}

}
