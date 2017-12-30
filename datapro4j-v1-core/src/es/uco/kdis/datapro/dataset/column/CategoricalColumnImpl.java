package es.uco.kdis.datapro.dataset.column;

import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;

import java.util.*;
import java.util.Map.Entry;

/**
 * This class represents the implementation of a categorical column. It defines
 * the actual implementation of the methods invoked from its abstraction.
 * This class and its methods should not be directly invoked.
 * <p>
 * CONTACT INFO:
 * <ul> 
 * <li>Jose Raul Romero, PhD 		[jrromero@uco.es]
 * <p>{@link http://www.jrromero.net}
 * <li>Jose Maria Luna, MSc 		[jmluna@uco.es]
 * <p><p>
 * Knowledge Discovery and Intelligent Systems Research Group (KDIS) <p>
 * {@link http://www.uco.es/grupos/kdis}
 * </ul>
 * <p>
 * HISTORY:
 * <ul>
 * <li> (AMP|ARQ, 0.4, May 2014) 	New method: getMaxValueLength.
 * <li> (JRR, 0.3, March 2012) Fully recoded. Structure change to HashMap. Other changes:
 *      <ul>
 *          <li>setXValue and getXValue still remain for serialization purposes only. Moved to ColumnImpl</li>
 * <li> (JRR, 0.3, March 2012) Fully recoded. Structure change to HashMapInvalid values are replaced by specific classes. Detailed changes:
 *      <ul>
 *          <li>setXValue and getXValue still remain for serialization purposes only. Moved to ColumnImpl</li>
 *          <li>The following method was removed: setXValue, getSortedCategoryList
 *      </ul>
 * <li> (JRR|JML, 0.2, September 2011) Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, June 2011) Initial version.
 * </ul>
 * <p>
 @author Aurora Ramirez Quesada (ARQ, 0.4)
 @author Antonio Jose Muñoz Perez (AMP, 0.4)
 @author Jose Raul Romero (JRR, 0.2, 0.3)
 @author Jose Maria Luna (JML, 0.2)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.4
 *
 **/
public class CategoricalColumnImpl extends ColumnImpl {

	/** 
	 * <code>rgoValues</code> represents a column
	 @since 0.1
	 **/
	private List<Object> rgoValues = new ArrayList<Object>();

	/**
	 * <code>hCategories</code> are the categories.
	 @since 0.1
	 **/
	private HashMap<String, Integer> hCategories = new HashMap<String, Integer>();

	/**
	 * <code>iCategoryIndex</code> is the number of categories.
	 @since 0.1
	 **/
	private int iCategoryIndex = 0;

	/**
	 * Default constructor
	 **/
	public CategoricalColumnImpl() {
	}

	/**
	 * Add a value at the end of the column. 
	 * If the element is not a valid category, nothing is added to the column.
	 * @param oValue the value to be added. It accepts both a String (category by name) and an Integer (category by index).
	 * @return the number of elements added to the column.
	 @since 0.1
	 **/
	@Override
	public int addValue(Object oValue) {
		try {
			// oValue is null, an instance of NullValue is added
			if (oValue == null) { 
				this.rgoValues.add(NullValue.getNullValue());
				return 1;                
			} 
			// oValue is an invalid value
			if (oValue instanceof InvalidValue) {
				this.rgoValues.add(oValue);
				return 1;
			}
			// If oValue is a String, then it is a category name (we need to check if is in the list of categories)
			if (oValue instanceof String && this.hCategories.containsKey(oValue)) {
				this.rgoValues.add(this.hCategories.get(oValue));
				return 1;
			}            
			// If the value is an integer, we need to check that the index is valid in the list
			if (oValue instanceof Integer && this.hCategories.containsValue(oValue)) {
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
	 * If the element is not a valid category, oValue is a string and bForce is true, the category is also appended
	 * to the list of valid categories and its value is added to the column.
	 * @param oValue the value to be added. It accepts both a String (category by name) and an Integer (category by index).
	 * @param bForce if true, it adds the string as parameter to the category list.
	 * @return The number of elements added to the column.
	 @since 0.1
	 **/
	@Override
	public int addValue(Object oValue, boolean bForce) {
		/* Checks if a category exists or not */
		if (bForce && oValue instanceof String && !this.hCategories.containsKey(oValue))
			this.addCategory((String)oValue); 

		return this.addValue(oValue);
	}

	/**
	 * Add a value at the specified index. The following elements in the column are shifted to the right.
	 * @param oValue the value to be added.
	 * @param iIndex the index where value should be added.
	 * @return The number of values added to the column.
	 @since 0.1
	 **/
	@Override
	public int addValue(Object oValue, int iIndex) {
		try {
			// oValue is null, an instance of NullValue is added
			if (oValue == null) { 
				this.rgoValues.add(iIndex, NullValue.getNullValue());
				return 1;                
			} 
			// oValue is an invalid value
			if (oValue instanceof InvalidValue) {
				this.rgoValues.add(iIndex, oValue);
				return 1;
			}
			// If oValue is a String, then it is a category name (we need to check if is in the list of categories)
			if (oValue instanceof String && this.hCategories.containsKey(oValue)) {
				this.rgoValues.add(iIndex, this.hCategories.get(oValue));
				return 1;
			}            
			// If the value is an integer, we need to check that the index is valid in the list
			if (oValue instanceof Integer && this.hCategories.containsValue(oValue)) {
				this.rgoValues.add(iIndex, oValue);
				return 1;
			} 
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Modify a value at the specified index
	 * @param oValue The value to be added.
	 * @param iIndex The index where vale must be added.
	 * @return The number of values modified.
	  @since 0.1
	 **/
	public int setValue(Object oValue, int iIndex) {
		try {
			// oValue is null, an instance of NullValue is added
			if (oValue == null) { 
				this.rgoValues.set(iIndex, NullValue.getNullValue());
				return 1;                
			} 
			// oValue is an invalid value
			if (oValue instanceof InvalidValue) {
				this.rgoValues.set(iIndex, oValue);
				return 1;
			}
			// If oValue is a String, then it is a category name (we need to check if is in the list of categories)
			if (oValue instanceof String && this.hCategories.containsKey(oValue)) {
				this.rgoValues.set(iIndex, this.hCategories.get(oValue));
				return 1;
			}            
			// If the value is an integer, we need to check that the index is valid in the list
			if (oValue instanceof Integer && this.hCategories.containsValue(oValue)) {
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
	@Override
	public void removeValue(int iIndex) {
		this.rgoValues.remove(iIndex);
	}

	/**
	 * Append a list of values at the end of the column.
	 * @param rgoCol The list of values to be added.
	 * @return The number of values added.
	  @since 0.1
	 **/
	@Override
	public int addAllValues(List<Object> rgoCol) {
		int iItems = 0;
		int iSize = rgoCol.size();
		for(int i = 0; i < iSize; i++)
			iItems += this.addValue(rgoCol.get(i));
		return iItems;
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
	 * Get the value (category as String or invalid value) at the given position. 
	 * @return the category (String) at the <code>iPos</code> position of the column or an invalid value
	 @throws IndexOutOfBoundsException if the element at <code>iPos</code> is not accessible.
	 @since 0.1
	 **/
	@Override
	public Object getElement(int iPos) throws IndexOutOfBoundsException {
		// Get the Integer with the category index at the given position
		Object oIndex = this.rgoValues.get(iPos);
		if (oIndex == null) throw new IndexOutOfBoundsException();
		if (oIndex instanceof InvalidValue || !(oIndex instanceof Integer)) 
			return oIndex;
		else 
			return this.getCategoryName((Integer)oIndex);
	}

	/**
	 * Get the value/element category index stored at the given position
	 * @param iPos The required position.
	 * @return index at iPos; or -1 if the value is invalid
	 @throws IndexOutOfBoundsException 
	 @since 0.1
	 **/
	public Integer getElementIndex(int iPos) throws IndexOutOfBoundsException {
		// We get the Integer with the category index at the given position
		Object oIndex = this.rgoValues.get(iPos);
		if (oIndex == null) throw new IndexOutOfBoundsException();
		if (oIndex instanceof InvalidValue || !(oIndex instanceof Integer)) 
			return -1;
		return (Integer) oIndex;
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
	 @since 0.1
	 **/
	@Override
	public int countMissingValues() {
		int iCounter = 0;
		int iSize = this.rgoValues.size();

		for(int i = 0; i < iSize; i++) {
			Object oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof MissingValue) {
				iCounter++;
			}
		}
		return iCounter;
	}

	/**
	 * Count the number of empty values in the column.
	 * @return The total empty values in the column.
	 @since 0.1
	 **/
	@Override
	public int countEmptyValues() {
		int iCounter = 0;
		int iSize = this.rgoValues.size();
		for(int i = 0; i < iSize; i++) {
			Object oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof EmptyValue) {
				iCounter++;
			}
		}
		return iCounter;
	}

	/**
	 * Count the number of null values in the column.
	 * @return The total empty values in the column.
	 @since 0.1
	 **/
	@Override
	public int countNullValues() {
		int iCounter = 0;
		int iSize = this.rgoValues.size();
		for(int i = 0; i < iSize; i++) {
			Object oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof NullValue) {
				iCounter++;
			}
		}
		return iCounter;
	}

	/**
	 * Count the number of invalid values (missing, null and empty) in the column.
	 * @return The total number of invalid values 
	 * (missing, null and empty) in the column.
	 @since 0.1
	 **/
	@Override
	public int countInvalidValues() {
		int iCounter = 0;
		int iSize = this.rgoValues.size();
		for(int i = 0; i < iSize; i++) {
			Object oValue = this.rgoValues.get(i);
			if (oValue != null && oValue instanceof InvalidValue) {
				iCounter++;
			}
		}
		return iCounter;
	}

	/**
	 * Add a new non-existing category to the definition of the column
	 * @param sCat The new category.
	 * @return The index assigned, otherwise -1 if the category already exists or it is not added.
	 @since 0.1
	 **/
	public int addCategory(String sCat) {
		// If the category string is null, or the category already exists... return -1
		if (sCat == null || this.hCategories.containsKey(sCat)) 
			return -1;

		// A category index is required. The size might return an already existing value
		this.hCategories.put(sCat, new Integer(this.iCategoryIndex));
		return this.iCategoryIndex++;
	}

	/**
	 * Get the list of categories.
	 * @return The array with the list of categories (as String) in the category list. This is an unsorted list.
	 @since 0.1
	 **/
	public List<Object> getCategoryList() {
		return new ArrayList<Object>(Arrays.asList( hCategories.keySet().toArray()));
	}

	/**
	 * Get the index of a category.
	 * @return The index of a given category, or -1 if the category do not exist in the category-list.
	 * @param sCategory The name of the category.
	 @since 0.1
	 **/
	public int getCategoryIndex(String sCategory) {
		Integer iIndex = hCategories.get(sCategory);
		if (iIndex == null) 
			return -1;
		else 
			return iIndex.intValue();
	}

	/**
	 * Get the name of a category given his index.
	 * @param iIndex The given associated to the category requested
	 * @return The category name for the index given, or null if the index do not exist.
	 @since 0.1
	 **/
	public String getCategoryName(Integer iIndex) {
		// If the given index is not a valid index in the collection, return null
		if (iIndex == null || !this.hCategories.containsValue(iIndex)) 
			return null;
		Set<Map.Entry<String,Integer>> smeCategories = this.hCategories.entrySet();
		Iterator<Entry<String, Integer>> itCats = smeCategories.iterator();
		// Searching for the first appearance of the value in the category list
		while (itCats.hasNext()) {
			Map.Entry<String,Integer> me = (Map.Entry<String,Integer>) itCats.next();
			if (me.getValue().equals(iIndex))
				return me.getKey();
		}
		// For some reason, the value was not found
		return null;
	}

	/**
	 * Replaces a category as metadata, and substitutes the old category in the column with the new one.
	 * @param sOldCategory old category to substitute for.
	 * @param sNewCategory new category to substitute to.
	 * @param bJoinCategory if true, if the new category already exists in the column, then categories will
	 * be joined, that's to say, a new category is created as the concatenation of old and
	 * new, then oldCategory and new category are substituted with the new one.
	 * @return 1 if done; 0 otherwise.
	 @since 0.1
	 */
	public int replaceCategory (String sOldCategory, String sNewCategory, boolean bJoinCategory) {
		if (!this.hCategories.containsKey(sOldCategory))
			return 0;
		if (!this.hCategories.containsKey(sNewCategory)) {
			/* If the old exists then the old one is removed, and the new is put with the old index */
			int iIndexOldCategory = this.hCategories.get(sOldCategory);
			this.hCategories.remove(sOldCategory);
			this.hCategories.put(sNewCategory, iIndexOldCategory);
			return 1;
		}
		if (!bJoinCategory && this.hCategories.containsKey(sNewCategory)) {

			String sJoinCategory = sOldCategory + sNewCategory;
			int iIndexOldCategory = this.hCategories.get(sOldCategory);
			int iIndexNewCategory = this.hCategories.get(sNewCategory);
			/* Remove both categories */
			this.hCategories.remove(sOldCategory);
			this.hCategories.remove(sNewCategory);

			/* Add a new category with the index of the second parameter category*/
			this.hCategories.put(sJoinCategory, iIndexNewCategory);

			/* All indexes of oldCategory are replaced by the index of the second parameter category */
			int iColSize = this.rgoValues.size();
			for (int i = 0; i < iColSize; i++){
				Object oValue = this.rgoValues.get(i);
				if (oValue != null && oValue.equals(iIndexOldCategory))
					this.rgoValues.set(i, iIndexNewCategory);
			}
			return 1;
		}
		return 0;
	}
	
	/**
	 * Get the maximum length of the possible values
	 * of all categories.
	 * @return The length of the largest category value.
	 @since 0.4
	 */
	public int getMaxValueLength() {
		
		List<Object> rgoCategoryList = this.getCategoryList();
		int iNumberOfCategories = rgoCategoryList.size();
		int iDimensionMax = 1;

		/** Get the maximum dimension of the categories */
		for (int j = 0; j < iNumberOfCategories; j++)
			if(iDimensionMax < rgoCategoryList.get(j).toString().length())
				iDimensionMax = rgoCategoryList.get(j).toString().length();
		
		return iDimensionMax;
	}
	
	// CONVERSION METHODS        

	/**
	 * Convert into a nominal column.
	 * @param sName The name of the column.
	 * @return NominalColumn with the string correspondence of categories.
	 @since 0.1
	 **/
	public NominalColumn toNominal(String sName){
		NominalColumn oResultColumn = new NominalColumn(sName);

		int iColSize = this.rgoValues.size();

		for (int i = 0; i < iColSize; i++){
			Object oValue = this.rgoValues.get(i);

			if(oValue == null)
				oResultColumn.addValue(NullValue.getNullValue());
			else if (oValue instanceof InvalidValue) 
				oResultColumn.addValue(oValue);
			else
				oResultColumn.addValue(this.getCategoryName((Integer)oValue));
		}
		return oResultColumn;
	}

	/**
	 * Convert into a binary column.
	 * If a value is in the parameter array then it will be true, otherwise false. 
	 * Note that if it is a missing/invalid/empty, then the string representation
	 * will be added.
	 * @param aReferenceTrueValues The values that matches with a true value in the categories.
	 * @param sName The name of the column.
	 * @return BinaryColumn with the correspondence between categorical and binary values.
	  @since 0.1
	 **/
	public BinaryColumn toBinary(List<String> aReferenceTrueValues, String sName){
		BinaryColumn oResultColumn = new BinaryColumn(sName);

		int iColSize = this.rgoValues.size();

		for (int i = 0; i < iColSize; i++){
			Object oValue = this.rgoValues.get(i);

			if(oValue == null)
				oResultColumn.addValue(NullValue.getNullValue());
			else if (oValue instanceof InvalidValue) 
				oResultColumn.addValue(oValue);
			else{ /* It's not an invalid value */
				if (aReferenceTrueValues.contains(getCategoryName((Integer) oValue)))
					oResultColumn.addValue(new Boolean(true));
				else
					oResultColumn.addValue(new Boolean(false));
			}
		}
		return oResultColumn;
	}

	/**
	 * Converts into an integer column with the numeric values assigned to categories.
	 * @param sName The name of the column.
	 * @return IntegerColumn with numerical values corresponding to categories.
	 @since 0.1
	 **/
	public IntegerColumn toNumerical(String sName){
		IntegerColumn oResultColumn = new IntegerColumn(sName);

		int iColSize = this.rgoValues.size();

		for (int i = 0; i < iColSize; i++){
			Object oValue = this.rgoValues.get(i);
			oResultColumn.addValue(oValue);
		}

		return oResultColumn;
	}
}
