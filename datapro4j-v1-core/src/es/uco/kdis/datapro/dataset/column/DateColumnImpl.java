package es.uco.kdis.datapro.dataset.column;

import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * DateColumnImpl implements a column of Date instances.
 * This type of column is required by ARFF datasets and 
 * databases.
 * <p>
 * CONTACT INFO:
 * <ul> 
 * <li>Jose Raul Romero, PhD 		[jrromero@uco.es]
 * <p>{@link http://www.jrromero.net}
 * <li>Jose Maria Luna, MSc 		[jmluna@uco.es]
 * <p><p>
 * Knowledge and Discovery Research Group (KDIS) <p>
 * {@link http://www.uco.es/grupos/kdis}
 * </ul>
 * <p>
 * HISTORY:
 * <ul>
 * <li> (ARQ, 0.3, July 2014) New methods: countXValues
 * <li> (JML, 0.2) Date format specification.
 * <li> (JRR, 0.1) Initial version.
 * </ul>
 * <p>
 *
 @author Aurora Ramirez Quesada (AR,0.3)
 @author Jose Maria Luna (JML, 0.2)
 @author Jose Raul Romero (JRR, 0.1)
 @version 0.3
 *
 **/
public class DateColumnImpl extends ColumnImpl {

	/** <code>rgoValues</code> stores the columns. 
	 @since 0.1
	 **/
	private List<Object> rgoValues = new ArrayList<Object>();

	/** <code>oDate</code> stores the date format.
	 @since 0.2
	 **/
	private SimpleDateFormat oDate; 

	/**
	 * Default constructor.
	 @since 0.1
	 **/
	public DateColumnImpl() {
	}

	/**
	 * Add a value at the end of the column.
	 * @param oValue The value to be added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValue(Object oValue) {
		try {
			if (oValue == null) {
				this.rgoValues.add(NullValue.getNullValue());
				return 1;
			}
			if (oValue instanceof String)
			{
				this.rgoValues.add(oDate.parse((String)oValue));
				return 1;
			}
			if(oValue instanceof InvalidValue) 
			{
				this.rgoValues.add(oValue);
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Add a value at the end of the column.
	 * @param oValue The value to be added.
	 * @param bForce This parameter is not considered for this column.
	 * @return the number of elements appended to the column.
	 @since 0.1
	 **/
	@Override
	public int addValue(Object oValue, boolean bForce) {
		return this.addValue(oValue);
	}

	/**
	 * Add a value at the specified index. The rest elements are shifted to the right.
	 * @param oValue The value to be added.
	 * @param iIndex The index where the value is added.
	 * @return The number of values added.
	 @since 0.1
	 **/
	@Override
	public int addValue(Object oValue, int iIndex) {
		try {
			if (oValue == null) {
				this.rgoValues.add(iIndex, NullValue.getNullValue());
				return 1;
			}
			if (oValue instanceof String)
			{
				this.rgoValues.add(iIndex, oDate.parse((String)oValue));
				return 1;
			}
			if(oValue instanceof InvalidValue) 
			{
				this.rgoValues.add(iIndex, oValue);
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Set a value at the specified position.
	 * @param oValue The value to be set.
	 * @param iIndex The position index where the value has to be set.
	 * @return The number of values modified.
	 @since 0.1
	 **/
	@Override
	public int setValue(Object oValue, int iIndex) {
		try {
			if (oValue == null) {
				this.rgoValues.set(iIndex, NullValue.getNullValue());
				return 1;
			}
			if (oValue instanceof String)
			{
				this.rgoValues.set(iIndex, oDate.parse((String)oValue));
				return 1;
			}
			if(oValue instanceof InvalidValue) 
			{
				this.rgoValues.set(iIndex, oValue);
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Remove the value at the specified index.
	 * @param iIndex The position index to be removed.
	 * @throws IndexOutOfBoundsException
	 @since 0.1
	 **/
	public void removeValue(int iIndex) {
		this.rgoValues.remove(iIndex);
	}

	/**
	 * Append a list of values to the end of the column.
	 * @param rgoCol the list of values to be added.
	 @since 0.1
	 **/
	@Override
	public int addAllValues(List<Object> rgoCol) {
		int iSize = 0;

		for (Object o : rgoCol) {
			iSize += this.addValue(o);
		}
		return iSize;
	}

	/**
	 * Get the values in the column.
	 * @return All the values in the column as a list of objects.
	 @since 0.1
	 **/
	@Override
	public List<Object> getValues() {
		return this.rgoValues;
	}

	/**
	 * Get the element stored at specified position.
	 * @param iPos The specified position.
	 * @return The element at <code>iPos</code>.
	 @since 0.1
	 **/
	@Override
	public Object getElement(int iPos) {
		return this.rgoValues.get(iPos);
	}

	/**
	 * Get the number of elements in the column. 
	 * @return The number of elements comprised by the column.
	 @since 0.1
	 **/
	@Override
	public int getSize() {
		return this.rgoValues.size();
	}

	/**
	 * Count the number of missing values in the column.
	 * @return The total missing values in the column.
	 @since 0.3
	 **/
	@Override
	public int countMissingValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();
		Object oValue;
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof MissingValue){
				iCounter++;
			}
		}
		return iCounter;
	}

	/**
	 * Count the number of empty values in the column.
	 * @return The total empty values in the column.
	 @since 0.3
	 **/
	@Override
	public int countEmptyValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();
		Object oValue;	
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof EmptyValue){
				iCounter++;
			}
		}
		return iCounter;
	}

	/**
	 * Count the number of null values in the column.
	 * @return The total null values in the column.
	 @since 0.3
	 **/
	@Override
	public int countNullValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();
		Object oValue;	
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof NullValue){
				iCounter++;
			}
		}return iCounter;
	}

	/**
	 * Count the number of invalid values (missing, null and empty) in the column.
	 * @return The total number of invalid values in the column.
	 @since 0.3
	 **/
	@Override
	public int countInvalidValues() {
		int iCounter = 0;
		int iColSize = this.rgoValues.size();
		Object oValue;	
		for (int i = 0; i< iColSize; i++) {
			oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof InvalidValue){
				iCounter++;
			}
		}
		return iCounter;
	}
	
	/**
	 * Add a date specification.
	 * @param sDate The specification of the date.
	 @since 0.2
	 **/
	public void addDateSpecification(SimpleDateFormat oDate) {
		this.oDate = oDate;
	}

	/**
	 * Get the date specification.
	 * @return The date specification.
	 @since 0.2
	 **/
	public SimpleDateFormat getDateSpecificaiton() {
		return this.oDate;
	}
}