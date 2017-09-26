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
package portfolio;

public class Transfer {

	public static Transfer registerFor(double value, ReceptiveAccount fromAccount,
			ReceptiveAccount toAccount) {
		throw new UnsupportedOperationException();
	}

	public double value() {
		throw new UnsupportedOperationException();
	}

	public TransferLeg depositLeg() {
		throw new UnsupportedOperationException();
	}
	
	public TransferLeg withdrawLeg() {
		throw new UnsupportedOperationException();
	}


}
