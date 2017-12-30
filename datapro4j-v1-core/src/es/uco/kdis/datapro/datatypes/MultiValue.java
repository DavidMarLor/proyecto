package es.uco.kdis.datapro.datatypes;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a template for any kind of multivalue. 
 * A multivalue is a set of values with the same type.
 * <p>
 * CONTACT INFO:
 * <ul> 
 * <li>Jose Raul Romero, PhD 		[jrromero@uco.es]
 * <p>{@link http://www.jrromero.net}<p>
 * <li>Aurora Ramirez Quesada, MSc 	[aramirez@uco.es]
 * <p><p>
 * Knowledge Discovery and Intelligent Systems Research Group (KDIS) <p>
 * {@link http://www.uco.es/grupos/kdis}
 * </ul>
 * <p>
 * HISTORY:
 * <ul>
 * <li>	(ARQ, 0.2, August 2015)			Performance, refactoring and designing issues. Improvements.</li>
 * <li> (IMA|RPB, 0.1, February 2014) 	Initial version.</li> 
 * </ul>
 * <p>
 * @param <C>
 * 
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Rocio Pinillos Bermudez (RPB, 0.1)
 @author Ignacio Monferrer Altava (IMA, 0.1)
 @version 0.2
 **/
public abstract class MultiValue<T> {
	
	/**
	 * <code>rgoMultivalues</code> is the set of values.
	 @since 0.1
	 **/
	protected ArrayList<T> rgoMultivalues;
	
	/**
	 * Add a value at the end of the multivalue.
	 * @param oValue The value to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	public abstract int addValue(T oValue);
	
	/**
	 * Add a value at the specified index.
	 * @param oValue The value to be added.
	 * @param iIndex The position index where the object has to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	public abstract int addValue(T oValue, int iIndex);
	
	/**
	 * Append a list of values at the end of the multivalue.
	 * @param rgoValues The list of values to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	public abstract int addAllValues(List<T> rgoValues);
	
	/**
	 * Append a list of values between two positions.
	 * @param rgoValues The list of values to be added.
	 * @param iFromIndex The initial position index.
	 * @param iToIndex The final position index.
	 * @return The number of values added.
	 @since 0.1
	 **/
	public abstract int addValues(List<T> rgoValues, int iFromIndex, int iToIndex);
	
	/**
	 * Set a value at the specified position.
	 * @param oValue The value to be added.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values successfully set.
	 @since 0.1
	 **/
	public abstract int setValue(T oValue, int iIndex);
	
	/**
	 * Remove a value at the specified position.
	 * @param iIndex The position index of the value to be removed.
	 @since 0.1
	 **/
	public void removeValue(int iIndex){
		this.rgoMultivalues.remove(iIndex);
	}
	
	/**
	 * Remove the values stored between two specified positions.
	 * @param iFromIndex The first position index.
	 * @param iToIndex The last position index.
	 @throws IndexOutOfBoundsException
	 @since 0.1
	 **/
	public void removeValues(int iFromIndex, int iToIndex) {
		for (int i = iToIndex; i >= iFromIndex; i--)
			this.rgoMultivalues.remove(i);
	}
	
	/**
	 * Remove all the values of the multivalue.
	 * @since 0.1
	 **/
	public void removeAllValues(){
		this.rgoMultivalues.removeAll(getAllValues());
	}
	
	/**
	 * Get the element stored at the specified position.
	 * @param iIndex The position index of the value to be obtained.
	 * @return The element at <code>iIndex</code>.
	 @throws IndexOutOfBoundsException
	 @since 0.1
	 **/
	public T getValue(int iIndex) {
		return this.rgoMultivalues.get(iIndex);
	}
	
	/**
	 * Get a set of values stored between two specified positions.
	 * @param iFromIndex The initial position.
	 * @param iToIndex The final position.
	 * @return List containing the values stored between <code>iFromIndex</code> and <code>iToIndex</code>.
	 * @throws IndexOutOfBoundsException
	 @since 0.1
	 **/
	public ArrayList<T> getValues(int iFromIndex, int iToIndex) {
		
		ArrayList<T> oResult = new ArrayList<T>();
		
		for (int i = iFromIndex; i <= iToIndex; i++)
			oResult.add(this.rgoMultivalues.get(i));
		
		return oResult;
	}
	
	/**
	 * Get all the values of the multivalue.
	 * @return List containing all the values stored in the multivalue.
	 * @since 0.1
	 **/
	public ArrayList<T> getAllValues() {
		return this.rgoMultivalues;
	}
	
	/**
	 * Get the size of the multivalue
	 * @return Number of elements in the multivalue.
	 @since 0.1
	 **/
	public int getSize() {
		return this.rgoMultivalues.size();
	}
	
	/**
	 * Check whether the multivalue is empty.
	 * @return True if the multivalue does not contain elements, false otherwise.
	 @since 0.1
	 **/
	public boolean isEmpty() {
		return this.rgoMultivalues.isEmpty();
	}
	
	/**
	 * Check if the multivalue contains an element.
	 * @param oValue The element to be search in the multivalue.
	 * @return True if the multivalue contains the element, false otherwise.
	 @since 0.1
	 **/
	public boolean contains(T oValue) {
		return this.rgoMultivalues.contains(oValue);
	}
}
