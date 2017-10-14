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

public class ElevatorWorkingState implements ElevatorState {

	private Elevator elevator;

	public ElevatorWorkingState(Elevator elevator) {
		this.elevator = elevator;
	}

	@Override
	public boolean isIdle() {
		return false;
	}

	@Override
	public boolean isWorking() {
		return true;
	}

	@Override
	public void goUpPushedFromFloor(int aFloorNumber) {
		elevator.goUpPushedFromFloorWhenWorking(aFloorNumber);
	}

	@Override
	public void closeCabinDoor() {
		elevator.closeCabinDoorWhenWorking();
	}

}
