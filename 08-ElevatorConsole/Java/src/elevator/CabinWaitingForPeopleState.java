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

public class CabinWaitingForPeopleState implements CabinState {

	private Cabin cabin;

	public CabinWaitingForPeopleState(Cabin cabin) {
		this.cabin = cabin;
	}

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

	@Override
	public void closeDoor() {
		cabin.closeDoorWhenWaitingPeople();
	}

	@Override
	public void accept(CabinStateVisitor visitor) {
		visitor.visitCabinWaitingPeople(this);
	}

}
