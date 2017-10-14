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

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ElevatorTest {
	
	@Test
	public void test01ElevatorStartsIdleWithDoorOpenOnFloorZero(){
		Elevator elevator = new Elevator();
		
		assertTrue(elevator.isIdle());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorOpened());
		assertEquals(0,elevator.cabinFloorNumber());
	}
	
	@Test
	public void test02CabinDoorStartsClosingWhenElevatorGetsCalled(){
		Elevator elevator = new Elevator();
	
		elevator.goUpPushedFromFloor(1);
		
		assertFalse(elevator.isIdle());
		assertTrue(elevator.isWorking());
		
		assertTrue(elevator.isCabinStopped());
		assertFalse(elevator.isCabinMoving());
		
		assertFalse(elevator.isCabinDoorOpened());
		assertFalse(elevator.isCabinDoorOpening());
		assertTrue(elevator.isCabinDoorClosing());
		assertFalse(elevator.isCabinDoorClosed());
		
		assertTrue(elevator.isCabinDoorMotorMovingClockwise());
		assertTrue(elevator.isCabinMotorStopped());
	}
	
	@Test
	public void test03CabinStartsMovingWhenDoorGetsClosed(){
		Elevator elevator = new Elevator();
	
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		
		assertFalse(elevator.isIdle());
		assertTrue(elevator.isWorking());
	
		assertFalse(elevator.isCabinStopped());
		assertTrue(elevator.isCabinMoving());
		
		assertFalse(elevator.isCabinDoorOpened());
		assertFalse(elevator.isCabinDoorOpening());
		assertFalse(elevator.isCabinDoorClosing());
		assertTrue(elevator.isCabinDoorClosed());

		assertTrue(elevator.isCabinDoorMotorStopped());
		assertTrue(elevator.isCabinMotorMovingClockwise());
}
	
	@Test
	public void test04CabinStopsAndStartsOpeningDoorWhenGetsToDestination(){
		Elevator elevator = new Elevator();
	
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);

		assertFalse(elevator.isIdle());
		assertTrue(elevator.isWorking());
		
		assertTrue(elevator.isCabinStopped());
		assertFalse(elevator.isCabinMoving());
				
		assertFalse(elevator.isCabinDoorOpened());
		assertTrue(elevator.isCabinDoorOpening());
		assertFalse(elevator.isCabinDoorClosing());
		assertFalse(elevator.isCabinDoorClosed());

		assertEquals(1,elevator.cabinFloorNumber());
		
		assertTrue(elevator.isCabinDoorMotorMovingCounterClockwise());
		assertTrue(elevator.isCabinMotorStopped());
	}
		
	@Test
	public void test05ElevatorGetsIdleWhenDoorGetOpened(){
		Elevator elevator = new Elevator();
	
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);
		elevator.cabinDoorOpened();
		
		assertTrue(elevator.isIdle());
		assertFalse(elevator.isWorking());
		
		assertTrue(elevator.isCabinStopped());
		assertFalse(elevator.isCabinMoving());

		assertTrue(elevator.isCabinDoorOpened());
		assertFalse(elevator.isCabinDoorOpening());
		assertFalse(elevator.isCabinDoorClosing());
		assertFalse(elevator.isCabinDoorClosed());
		
		assertEquals(1,elevator.cabinFloorNumber());
		
		assertTrue(elevator.isCabinDoorMotorStopped());
		assertTrue(elevator.isCabinMotorStopped());
		
	}

	// STOP HERE!
	
	@Test
	public void test06DoorKeepsOpenedWhenOpeningIsRequested(){
		Elevator elevator = new Elevator();
		
		assertTrue(elevator.isCabinDoorOpened());
		assertTrue(elevator.isCabinDoorMotorStopped());		
		
		elevator.openCabinDoor();

		assertTrue(elevator.isCabinDoorOpened());
		assertTrue(elevator.isCabinDoorMotorStopped());		
	}

	@Test
	public void test07DoorMustBeOpenedWhenCabinIsStoppedAndClosingDoors(){
		Elevator elevator = new Elevator();
	
		elevator.goUpPushedFromFloor(1);
		
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorClosing());
		
		elevator.openCabinDoor();
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorOpening());

		assertTrue(elevator.isCabinDoorMotorMovingCounterClockwise());
		assertTrue(elevator.isCabinMotorStopped());
	}

	@Test
	public void test08CanNotOpenDoorWhenCabinIsMoving(){
		Elevator elevator = new Elevator();
	
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinMoving());
		assertTrue(elevator.isCabinDoorClosed());

		elevator.openCabinDoor();
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinMoving());
		assertTrue(elevator.isCabinDoorClosed());
		
		assertTrue(elevator.isCabinDoorMotorStopped());
		assertTrue(elevator.isCabinMotorMovingClockwise());
	}

	@Test
	public void test09DoorKeepsOpeneingWhenItIsOpeneing(){
		Elevator elevator = new Elevator();
	
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);

		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorOpening());

		elevator.openCabinDoor();
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorOpening());

		assertTrue(elevator.isCabinDoorMotorMovingCounterClockwise());
		assertTrue(elevator.isCabinMotorStopped());	
	}

	// STOP HERE!!
	
	@Test
	public void test10RequestToGoUpAreEnqueueWhenRequestedWhenCabinIsMoving(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);
		elevator.goUpPushedFromFloor(2);
		elevator.cabinDoorOpened();
	
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinWaitingForPeople());
		assertTrue(elevator.isCabinDoorOpened());

		assertTrue(elevator.isCabinDoorMotorStopped());
		assertTrue(elevator.isCabinMotorStopped());
	
	}

	@Test
	public void test11CabinDoorStartClosingAfterWaitingForPeople(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);
		elevator.goUpPushedFromFloor(2);
		elevator.cabinDoorOpened();
		elevator.waitForPeopleTimedOut();

		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorClosing());
		
		assertTrue(elevator.isCabinDoorMotorMovingClockwise());
		assertTrue(elevator.isCabinMotorStopped());
		
	}

	@Test
	public void test12StopsWaitingForPeopleIfCloseDoorIsPressed(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);
		elevator.goUpPushedFromFloor(2);
		elevator.cabinDoorOpened();
	
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinWaitingForPeople());
		assertTrue(elevator.isCabinDoorOpened());

		elevator.closeCabinDoor();

		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorClosing());
		
		assertTrue(elevator.isCabinDoorMotorMovingClockwise());
		assertTrue(elevator.isCabinMotorStopped());
	}

	@Test
	public void test13CloseDoorDoesNothingIfIdle(){
		Elevator elevator = new Elevator();
		
		elevator.closeCabinDoor();

		assertTrue(elevator.isIdle());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorOpened());

		assertTrue(elevator.isCabinDoorMotorStopped());
		assertTrue(elevator.isCabinMotorStopped());
	}

	@Test
	public void test14CloseDoorDoesNothingWhenCabinIsMoving(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
	
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinMoving());
		assertTrue(elevator.isCabinDoorClosed());

		elevator.closeCabinDoor();

		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinMoving());
		assertTrue(elevator.isCabinDoorClosed());

		assertTrue(elevator.isCabinDoorMotorStopped());
		assertTrue(elevator.isCabinMotorMovingClockwise());
	}

	@Test
	public void test15CloseDoorDoesNothingWhenOpeningTheDoorToWaitForPeople(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);
	
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorOpening());

		elevator.closeCabinDoor();

		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorOpening());
		
		assertTrue(elevator.isCabinDoorMotorMovingCounterClockwise());
		assertTrue(elevator.isCabinMotorStopped());
	}
	
	// STOP HERE!!

	@Test
	public void test16ElevatorHasToEnterEmergencyIfStoppedAndOtherFloorSensorTurnsOn(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);
		try {
			elevator.cabinOnFloor(0);
			fail();
		} catch (RuntimeException elevatorEmergency) {
			assertEquals (Cabin.SENSOR_DESINCRONIZED,elevatorEmergency.getMessage());
		}
	}

	@Test
	public void test17ElevatorHasToEnterEmergencyIfFalling(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(2);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);
		try {
			elevator.cabinOnFloor(0);
			fail();
		} catch (RuntimeException elevatorEmergency) {
			assertEquals (Cabin.SENSOR_DESINCRONIZED,elevatorEmergency.getMessage());
		}
	}

	@Test
	public void test18ElevatorHasToEnterEmergencyIfJumpsFloors(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(3);
		elevator.cabinDoorClosed();
		try {
			elevator.cabinOnFloor(3);
			fail();
		} catch (RuntimeException elevatorEmergency) {
			assertEquals (Cabin.SENSOR_DESINCRONIZED,elevatorEmergency.getMessage());
		}
	}

	@Test
	public void test19ElevatorHasToEnterEmergencyIfDoorClosesAutomatically(){
		Elevator elevator = new Elevator();
		
		try {
			elevator.cabinDoorClosed();
			fail();
		} catch (RuntimeException elevatorEmergency) {
			assertEquals (CabinDoor.SENSOR_DESINCRONIZED,elevatorEmergency.getMessage());
		}
	}

	@Test
	public void test20ElevatorHasToEnterEmergencyIfDoorClosedSensorTurnsOnWhenClosed(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		try {
			elevator.cabinDoorClosed();
			fail();
		} catch (RuntimeException elevatorEmergency) {
			assertEquals (CabinDoor.SENSOR_DESINCRONIZED,elevatorEmergency.getMessage());
		}
	}

	@Test
	public void test21ElevatorHasToEnterEmergencyIfDoorClosesWhenOpening(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(1);
		try {
			elevator.cabinDoorClosed();
			fail();
		} catch (RuntimeException elevatorEmergency) {
			assertEquals (CabinDoor.SENSOR_DESINCRONIZED,elevatorEmergency.getMessage());
		}
	}

	// STOP HERE!!
	// More tests here to verify bad sensor function
	
	@Test
	public void test22CabinHasToStopOnTheFloorsOnItsWay(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.goUpPushedFromFloor(2);
		elevator.cabinOnFloor(1);

		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorOpening());
		
		assertTrue(elevator.isCabinDoorMotorMovingCounterClockwise());
		assertTrue(elevator.isCabinMotorStopped());
		
	}
	
	@Test
	public void test23ElevatorCompletesAllTheRequests(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(1);
		elevator.cabinDoorClosed();
		elevator.goUpPushedFromFloor(2);
		elevator.cabinOnFloor(1);
		elevator.cabinDoorOpened();
		elevator.waitForPeopleTimedOut();
		elevator.cabinDoorClosed();
		elevator.cabinOnFloor(2);
		
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorOpening());
		
		assertTrue(elevator.isCabinDoorMotorMovingCounterClockwise());
		assertTrue(elevator.isCabinMotorStopped());
		
	}
	
	@Test
	public void test24CabinHasToStopOnFloorsOnItsWayNoMatterHowTheyWellCalled(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(2);
		elevator.cabinDoorClosed();
		elevator.goUpPushedFromFloor(1);
		elevator.cabinOnFloor(1);
		
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorOpening());
		
		assertTrue(elevator.isCabinDoorMotorMovingCounterClockwise());
		assertTrue(elevator.isCabinMotorStopped());
		
	}
	
	@Test
	public void test25CabinHasToStopAndWaitForPeopleOnFloorsOnItsWayNoMatterHowTheyWellCalled(){
		Elevator elevator = new Elevator();
		
		elevator.goUpPushedFromFloor(2);
		elevator.cabinDoorClosed();
		elevator.goUpPushedFromFloor(1);
		elevator.cabinOnFloor(1);
		elevator.cabinDoorOpened();
		elevator.waitForPeopleTimedOut();
		
		assertTrue(elevator.isWorking());
		assertTrue(elevator.isCabinStopped());
		assertTrue(elevator.isCabinDoorClosing());
		
		assertTrue(elevator.isCabinDoorMotorMovingClockwise());
		assertTrue(elevator.isCabinMotorStopped());
		
	}
}

