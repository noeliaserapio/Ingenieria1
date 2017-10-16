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

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

public class ElevatorConsole implements StateVisitor, CabinStateVisitor, CabinDoorStateVisitor{

	private StringWriter console;
	
	public ElevatorConsole(Elevator elevator) {
		console = new StringWriter();
		elevator.addVisitor(this);
	}

	protected void cabinDoorStateChangedTo(CabinDoorState cabinDoorState) {
		cabinDoorState.accept(this);
	}

	protected void cabinStateChangedTo(CabinState cabinState) {
		cabinState.accept(this);
	}

	public Reader consoleReader() {
		return new StringReader(console.getBuffer().toString());
	}

	public void visitCabinMoving(CabinMovingState cabinMovingState) {
		console.write("Cabina Moviendose\n");
	}

	public void visitCabinStopped(CabinStoppedState cabinStoppedState) {
		console.write("Cabina Detenida\n");
	}

	public void visitCabinWaitingPeople(CabinWaitingForPeopleState cabinWaitingForPeopleState) {
		console.write("Cabina Esperando Gente\n");
	}

	public void visitCabinDoorClosing(CabinDoorClosingState cabinDoorClosingState) {
		console.write("Puerta Cerrandose\n");
	}

	public void visitCabinDoorClosed(CabinDoorClosedState cabinDoorClosedState) {
		console.write("Puerta Cerrada\n");
	}

	public void visitCabinDoorOpened(CabinDoorOpenedState cabinDoorOpenedState) {
		console.write("Puerta Abierta\n");
	}

	public void visitCabinDoorOpening(CabinDoorOpeningState cabinDoorOpeningState) {
		console.write("Puerta Abriendose\n");
	}
}
