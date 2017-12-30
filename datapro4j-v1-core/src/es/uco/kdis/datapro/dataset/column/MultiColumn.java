package es.uco.kdis.datapro.dataset.column;


/**
 * This class represents the abstraction of a multivalue column. It is the one that should be 
 * used by the programmer, since it hides the actual implementation of the column. 
 * Even when the implementation changes, the abstraction must remain unaltered. 
 *
 * CONTACT INFO:
 * <ul> 
 * <li>Jose Raul Romero, PhD 		[jrromero@uco.es]
 * <p>{@link http://www.jrromero.net}
 * <li>Aurora Ramirez Quesada, MSc 	[aramirez@uco.es]
 * <p><p>
 * Knowledge Discovery and Intelligent Systems Research Group (KDIS) <p>
 * {@link http://www.uco.es/grupos/kdis}
 * </ul>
 * <p>
 * HISTORY:
 * <ul>
 * <li> (ARQ, 0.2, August 2015)			Performance, refactoring and designing issues. Improvements.
 * 										All the methods to manage multivalues are specified in the abstract class.</li>
 * <li> (IMA|RPB, 0.1, February 2014) 	Initial version. </li>
 * </ul>
 * <p>
 *
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Rocio Pinillos Bermudez (RPB, 0.1)
 @author Ignacio Monferrer Altava (IMA, 0.1)
 @version 0.2
 *
 **/
public class MultiColumn extends ColumnAbstraction {

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public MultiColumn() {
		super("", ColumnType.Multivalue);
	}	
	
	/**
	 * Parameterized constructor.
	 * @param sName The name of the column.
	 @since 0.1
	 **/
	public MultiColumn(String sName) {
		super(sName, ColumnType.Multivalue);
	}
	
	/**
	 * Parameterized Constructor.
	 * @param sName The name of the column.
	 * @param ctColumnType The type of the column.
	 @since 0.1
	 **/
	public MultiColumn(String sName, ColumnType ctColumnType) {
		super(sName, ctColumnType);
	}
	
	/**
	 * Add a value at the end of a multivalue.
	 * @param oValue The value to be added.
	 * @param iIndex The position index of the multivalue.
	 * @return The number of values added.
	 @since 0.2
	 **/
	public int addElementToMultivalue(Object oValue, int iIndex) {
		return ((MultiColumnImpl)this.oImpl).addElementToMultivalue(oValue, iIndex);
	}
	
	/**
	 * Add a value at the specified position of a multivalue.
	 * @param oValue The value to be added.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @param iInternalIndex The position index of the value to be added.
	 * @return The number of values added.
	 @since 0.2
	 **/
	public int addElementToMultivalue(Object oValue, int iIndex, int iInternalIndex) {
		return ((MultiColumnImpl)this.oImpl).addElementToMultivalue(oValue, iIndex, iInternalIndex);
	}
	
	/**
	 * Get the element stored at the specified position of a multivalue.
	 * @param iIndex The index of the multivalue queried (element in the column).
	 * @param iInternalIndex The position of the value to be retrieved.
	 * @return The element queried.
	 @since 0.1
	 **/
	public Object getElementOfMultivalue(int iIndex, int iInternalIndex) {
		return ((MultiColumnImpl)this.oImpl).getElementOfMultivalue(iIndex, iInternalIndex);
	}
	
	/**
	 * Set a value at the specified index of a multivalue.
	 * @param oValue The new value.
	 * @param iIndex The position index of the multivalue (element in the column).
	 * @param iInternalIndex The position index of the value to be modified.
	 * @return The number of values modified.
	 @since 0.2
	 * */
	public int setElementOfMultivalue(Object oValue, int iIndex, int iInternalIndex) {
		return ((MultiColumnImpl)this.oImpl).setElementOfMultivalue(oValue, iIndex, iInternalIndex);
	}
	
	/**
	 * Get the size of a multivalue sequence.
	 * @param iIndex The index of the multivalue.
	 * @return The number of elements in the multivalue.
	 @since 0.1
	 **/
	public int getMultivalueSize(int iIndex) {
		return ((MultiColumnImpl)this.oImpl).getMultivalueSize(iIndex);
	}
	
	/**
	 * Remove the element stored at the specified position of the multivalue.
	 * @param iIndex The index of the multivalue (element in the column).
	 * @param iInternalIndex The position index of the value to be removed.
	 @since 0.1
	 **/
	public void removeElementOfMultivalue(int iIndex, int iInternalIndex) {
		((MultiColumnImpl)this.oImpl).removeElementOfMultivalue(iIndex, iInternalIndex);
	}
	
	/**
	 * Remove the elements stored between two positions in the specified multivalue.
	 * @param iIndex The index of the multivalue (element in the column).
	 * @param iFromIndex The position index of the first value to be removed.
	 * @param iToIndex The position index of the last value to be removed.
	 @since 0.1
	 **/
	public void removeElementsOfMultivalue(int iIndex, int iFromIndex, int iToIndex) {
		((MultiColumnImpl)this.oImpl).removeElementsOfMultivalue(iIndex, iFromIndex, iToIndex);
	}
}
