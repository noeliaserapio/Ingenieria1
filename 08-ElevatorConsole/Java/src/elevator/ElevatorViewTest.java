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

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.LineNumberReader;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ElevatorViewTest {
	
	@Test
	public void test01ElevatorConsoleTracksDoorClosingState() throws IOException, InterruptedException {
		final Elevator elevator = new Elevator();
		ElevatorConsole elevatorConsole = new ElevatorConsole(elevator);
		
		elevator.goUpPushedFromFloor(1); 
				
		LineNumberReader reader = new LineNumberReader(elevatorConsole.consoleReader());

		assertEquals("Puerta Cerrandose",reader.readLine());
		assertNull(reader.readLine());
		
	}

	@Test
	public void test02ElevatorConsoleTracksCabinState() throws IOException, InterruptedException {
		final Elevator elevator = new Elevator();
		ElevatorConsole elevatorConsole = new ElevatorConsole(elevator);
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		
		LineNumberReader reader = new LineNumberReader(elevatorConsole.consoleReader());

		assertEquals("Puerta Cerrandose",reader.readLine());
		assertEquals("Puerta Cerrada",reader.readLine());
		assertEquals("Cabina Moviendose",reader.readLine());
		assertNull(reader.readLine());
		
	}

	@Test
	public void test03ElevatorConsoleTracksCabinAndDoorStateChanges() throws IOException, InterruptedException {
		final Elevator elevator = new Elevator();
		ElevatorConsole elevatorConsole = new ElevatorConsole(elevator);
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);
		
		LineNumberReader reader = new LineNumberReader(elevatorConsole.consoleReader());

		assertEquals("Puerta Cerrandose",reader.readLine());
		assertEquals("Puerta Cerrada",reader.readLine());
		assertEquals("Cabina Moviendose",reader.readLine());
		assertEquals("Cabina Detenida",reader.readLine());
		assertEquals("Puerta Abriendose",reader.readLine());
		assertNull(reader.readLine());
		
	}

	@Test
	public void test04ElevatorCanHaveMoreThanOneView() throws IOException, InterruptedException {
		final Elevator elevator = new Elevator();
		ElevatorConsole elevatorConsole = new ElevatorConsole(elevator);
		ElevatorStatusView elevatorStatusView = new ElevatorStatusView(elevator);
			
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);

		LineNumberReader reader = new LineNumberReader(elevatorConsole.consoleReader());

		assertEquals("Puerta Cerrandose",reader.readLine());
		assertEquals("Puerta Cerrada",reader.readLine());
		assertEquals("Cabina Moviendose",reader.readLine());
		assertEquals("Cabina Detenida",reader.readLine());
		assertEquals("Puerta Abriendose",reader.readLine());
		assertNull(reader.readLine());
		
		assertEquals("Stopped",elevatorStatusView.cabinFieldModel());
		assertEquals("Opening",elevatorStatusView.cabinDoorFieldModel());
	}

}
