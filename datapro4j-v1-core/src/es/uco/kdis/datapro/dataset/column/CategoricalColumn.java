package es.uco.kdis.datapro.dataset.column;

import java.util.List;

/**
 * This class, the abstraction of a categorical column, is the one that should be used by 
 * the programmer, since it hides the actual implementation of the column. Even when the
 * implementation changes, the abstraction must remain unaltered. 
 *
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
 * <li> (AMP|ARQ, 0.3, May 2014) 	New method: getMaxDimension.
 * <li> (JRR|JML, 0.2, March 2012)	Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, June 2011) 		Initial version. 
 * </ul>
 * <p>
 @author Aurora Ramirez Quesada (ARQ, 0.3)
 @author Antonio Jose Muñoz Perez (AMP, 0.3)
 @author Jose Maria Luna (JML, 0.2)
 @author Jose Raul Romero (JRR, 0.2)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.3
 *
 **/
public class CategoricalColumn extends ColumnAbstraction {

	/**
	 * Default constructor
	 @since 0.1
	 **/
	public CategoricalColumn() {
		super("", ColumnType.Categorical);
		oImpl = new CategoricalColumnImpl();
	}
	
	/**
	 * Parameterized constructor
	 * @param sName The name of the column
	 @since 0.1
	 **/
	public CategoricalColumn(String sName) {
		super(sName, ColumnType.Categorical);
		oImpl = new CategoricalColumnImpl();
	}

	/**
	 * Add a new category as a <code>String</code>.
	 * @param szCategory New category in the column.
	 @since 0.1
	 **/
	public void addCategory(String szCategory) {
		((CategoricalColumnImpl) this.oImpl).addCategory(szCategory);
	}

	/**
	 * Get the value/element index stored at the given position.
	 * @param iPos The required index.
	 * @return index at iPos; or -1 if the value is invalid.
	 * @throws IndexOutOfBoundsException 
	 @since 0.1
	 **/
	public Integer getElementIndex(int iPos) throws IndexOutOfBoundsException {
		return ((CategoricalColumnImpl) this.oImpl).getElementIndex(iPos);
	}

	/**
	 * Return the list of current categories for the column.
	 * @return The list comprising the names of the current categories in the column.
	 @since 0.1
	 **/
	public List<Object> getCategoryList() {
		return ((CategoricalColumnImpl) this.oImpl).getCategoryList();
	}

	/**
	 * Get the category at specified index.
	 * @param szCategory The category name to be searched.
	 * @return The index of a given category.
	 @since 0.1
	 **/
	public int getCategoryIndex(String szCategory) {
		return ((CategoricalColumnImpl) this.oImpl).getCategoryIndex(szCategory);
	}

	/**
	 * Get the category by name.
	 * @param iIndex The index that identifies the category.
	 * @return The category name for the index given, or null if the index do not exist.
	 @since 0.1
	 **/
	public String getCategoryName(Integer iIndex) {
		return ((CategoricalColumnImpl) this.oImpl).getCategoryName(iIndex);
	}
	
	/**
	 * Replace a category with another new one maintaining the already existing values in the column.
	 * @param szOldCategory the category to be substituted.
	 * @param szNewCategory the new category value.
	 * @param bJoinCategory This method checks whether the new category indicated already exists. In such a case, if <code>bJoinCategory</code>
	 * is true, then the new category will be considered as merged with the previously existing values.
	 * @return 1 if the category was successfully replaced. 0 otherwise. 
	 @since 0.1
	 **/
	public int replaceCategory (String szOldCategory, String szNewCategory, boolean bJoinCategory){
		return ((CategoricalColumnImpl)this.oImpl).replaceCategory(szOldCategory, szNewCategory, bJoinCategory);
	}
	
	/**
	 * Get the maximum length of the possible values
	 * of all categories.
	 * @return The length of the largest category value.
	 @since 0.3
	 */
	public int getMaxDimension() {
		return ((CategoricalColumnImpl)this.oImpl).getMaxValueLength();
	}

	// CONVERSION METHODS

	/**
	 * Convert into a nominal column. Nominal values
	 * are the strings representing each category.
	 * @return NominalColumn after the conversion process.
	 @since 0.1
	 **/
	public NominalColumn toNominal(){
		return ((CategoricalColumnImpl) this.oImpl).toNominal(this.getName());
	}

	/**
	 * Convert into a binary column. A target value is true if the category string is
	 * found in the parameter list. Otherwise, the resulting value is false.
	 * Note that invalid values are also considered. 
	 * @param aReferenceTrueValues The list of categories that will be considered 
	 * as positive values in the binary column.
	 * @return BinaryColumn after the conversion process.
	 @since 0.1
	 **/
	public BinaryColumn toBinary(List<String> aReferenceTrueValues){
		return ((CategoricalColumnImpl)this.oImpl).toBinary(aReferenceTrueValues,this.getName());
	}

	/**
	 * Convert into a integer column using the numeric values assigned to the categories.
	 * @return IntegerColumn that matches this column.
	 @since 0.1
	 **/
	public IntegerColumn toNumerical(){
		return ((CategoricalColumnImpl)this.oImpl).toNumerical(this.getName());
	}
}
