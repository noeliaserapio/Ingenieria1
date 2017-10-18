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

public class ElevatorStatusView implements CabinStateVisitor,CabinDoorStateVisitor {

	private String cabinFieldModel;
	private String cabinDoorFieldModel;

	public ElevatorStatusView(Elevator elevator) {
		elevator.addVisitorCabin(this);
		elevator.addVisitorDoor(this);
	}

	protected void cabinDoorStateChangedTo(CabinDoorState cabinDoorState) {
		cabinDoorState.accept(this);
	}

	protected void cabinStateChangedTo(CabinState cabinState) {
		cabinState.accept(this);
	}

	@Override
	public void visitCabinDoorClosing(CabinDoorClosingState cabinDoorClosingState) {
		cabinDoorFieldModel = "Closing";
	}

	@Override
	public void visitCabinDoorClosed(CabinDoorClosedState cabinDoorClosedState) {
		cabinDoorFieldModel = "Closed";
	}

	@Override
	public void visitCabinDoorOpened(CabinDoorOpenedState cabinDoorOpenedState) {
		cabinDoorFieldModel = "Open";
	}

	@Override
	public void visitCabinDoorOpening(CabinDoorOpeningState cabinDoorOpeningState) {
		cabinDoorFieldModel = "Opening";
	}

	@Override
	public void visitCabinMoving(CabinMovingState cabinMovingState) {
		cabinFieldModel = "Moving";
	}

	@Override
	public void visitCabinStopped(CabinStoppedState cabinStoppedState) {
		cabinFieldModel = "Stopped";
	}

	@Override
	public void visitCabinWaitingPeople(CabinWaitingForPeopleState cabinWaitingForPeopleState) {
		cabinFieldModel = "Waiting People";
	}

	public String cabinFieldModel() {
		return cabinFieldModel;
	}

	public String cabinDoorFieldModel() {
		return cabinDoorFieldModel;
	}
	
}
