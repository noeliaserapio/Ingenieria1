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

public class CabinStoppedState implements CabinState {

	private Cabin cabin;

	public CabinStoppedState(Cabin cabin) {
		this.cabin = cabin;
	}

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
		//Falta test para implementar esto
		throw new UnsupportedOperationException();
	}

	@Override
	public void closeDoor() {
		cabin.closeDoorWhenStopped();
	}

	@Override
	public void accept(CabinStateVisitor visitor) {
		visitor.visitCabinStopped(this);
	}

}
