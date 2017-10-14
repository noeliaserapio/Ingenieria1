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

public class Motor {
	
	public static final String MOTOR_IS_MOVING = "El motor se esta moviendo";

	enum MotorState {
		STOPPED,
		MOVING_CLOCKWISE,
		MOVING_COUNTER_CLOCKWISE
	};
	
	private MotorState state;
	
	public Motor() {
		state = MotorState.STOPPED;
	}
	
	public void stop() {
		state = MotorState.STOPPED;
	}

	public void moveClockwise() {
		assertIsNotMoving();
		
		state = MotorState.MOVING_CLOCKWISE;
	}

	public void moveCounterClockwise() {
		assertIsNotMoving();
		
		state = MotorState.MOVING_COUNTER_CLOCKWISE;
	}

	public boolean isMovingCounterClockwise() {
		return state==MotorState.MOVING_COUNTER_CLOCKWISE;
	}

	public boolean isMovingClockwise() {
		return state==MotorState.MOVING_CLOCKWISE;
	}

	public boolean isStopped() {
		return state==MotorState.STOPPED;
	}

	public boolean isMoving() {
		return !isStopped();
	}

	void assertIsNotMoving() {
		if(isMoving()) throw new RuntimeException(Motor.MOTOR_IS_MOVING);
	}
}
