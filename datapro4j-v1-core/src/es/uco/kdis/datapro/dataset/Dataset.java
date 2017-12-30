package es.uco.kdis.datapro.dataset;

import es.uco.kdis.datapro.dataset.column.*;
import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Dataset is the abstract base class for all the types of datasets. This is a bridge
 * between the physical dataset (stored in a file, database, etc.) and its logical
 * handling, where access to attributes/columns and processing methods is provided.
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
 * <li> (ARQ, 0.4, May 2014) 			Support to DatabaseDataset. New exceptions can be thrown in the following methods:
 * 										open(), close(), addAllValues(). 
 * <li> (JRR|JML, 0.3, September 2012) 	Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.2, August 2012) 		Added new types of columns; Modified methods: clone(), copy(); 
 *                  					New methods: remove, getValidBinaryFalseValues, 
 * 										getValidBinaryTrueValues, setValidBinaryFalseValues,
 *										setValidTrueValues, setValidRangeDelimiter, setColumns
 * <li> (JTL, 0.1, May 2011) 			Initial version.
 * </ul>
 * <p>
 *
 @author Aurora Ramirez Quesada (ARQ, 0.4)
 @author Jose Maria Luna (JML, 0.3)
 @author Jose Raul Romero (JRR, 0.3)
 @author Mario Orozco Borrego (MOB, 0.2)
 @author Jose de la Torre Lopez (JTL, 0.1)
 @version 0.4
 *
 **/
public abstract class Dataset {

	/**
	 * <code>sName</code> stores the dataset name
	 @since 0.1
	 **/
	protected String sName;

	/**
	 * <code>iCursor</code> refers to the column being pointed 
	 * in the dataset by the InstanceIterator.
	 @see addAttributeToSpecification()
	 @since 0.1
	 **/
	protected int iCursor;

	/** 
	 * <code>sMissedValue</code> stores the string that will represent a 
	 * missing value in the dataset file.
	 @since 0.2 
	 **/
	protected String sMissingValue;

	/** 
	 * <code>sNullValue</code> stores the string that will represent a 
	 * null value in the dataset file.
	 @since 0.2 
	 **/
	protected String sNullValue;

	/** 
	 * <code>sEmptyValue</code> stores the string that will represent a 
	 * empty value in the dataset file.
	 @since 0.2 
	 **/
	protected String sEmptyValue;

	/** 
	 * <code>rgoColumns</code> is the list of columns stored comprising the dataset.
	 @since 0.1 
	 @see ColumnAbstraction
	 **/
	protected List<ColumnAbstraction> rgoColumns;

	/** 
	 * For range columns, <code>sOpenRangeDelimiter</code> stores the symbol(s) that 
	 * open the numerical range, right before the minimum value: e.g., '[' for [2,3]. This is used during the reading 
	 * and writing of the physical dataset. 
	 @since 0.2 
	 @see RangeColumn
	 **/
	protected String sOpenRangeDelimiter;

	/** 
	 * For range columns, <code>sSeparationRangeDelimiter</code> stores the symbol(s) that 
	 * separate the minimum and maximum values in a numerical range: 
	 * e.g., ',' for [2,3]. This is used during the reading and writing of the 
	 * physical dataset. 
	 @since 0.2 
	 @see RangeColumn
	 **/
	protected String sSeparationRangeDelimiter;

	/** 
	 * For range columns, <code>sCloseRangeDelimiter</code> stores the symbol(s) that 
	 * close the numerical range, right after the maximum value: e.g., ']' for [2,3]. This is used during the reading 
	 * and writing of the physical dataset. 
	 @since 0.2 
	 @see RangeColumn
	 **/
	protected String sCloseRangeDelimiter;

	/** 
	 * For numerical columns, <code>iNumberOfDecimals</code> indicate the number
	 * of decimals to be read or written from/in the dataset file
	 @since 0.2
	 @see NumericalColumn
	 **/
	private int iNumberOfDecimals = 1;

	/** 
	 * For binary columns, <code>rgoValidBinaryFalseValues</code> contains the list
	 * of values that will be interpreted as <code>False</code> when reading
	 * from the physical dataset. Writing will be performed using the first element
	 * in the list. 
	 @since 0.2
	 @see BinaryColumn
	 **/
	protected ArrayList<String> rgoValidBinaryFalseValues;

	/** 
	 * For binary columns, <code>rgoValidBinaryTrueValues</code> contains the list
	 * of values that will be interpreted as <code>True</code> when reading
	 * from the physical dataset. Writing will be performed using the first element
	 * in the list. 
	 @since 0.2
	 @see BinaryColumn
	 **/
	protected ArrayList<String> rgoValidBinaryTrueValues;

	/**
	 * Default dataset constructor. The following symbols are used as default:
	 * <ul>
	 * <li> <code>sMissedValue</code>: "?"
	 * <li> <code>sNullValue</code>: "?"
	 * <li> <code>sEmptyValue</code>: "?"
	 * <li> <code>sOpenRangeDelimiter</code>: "["
	 * <li> <code>sSeparationRangeDelimiter</code>: ","
	 * <li> <code>sCloseRangeDelimiter</code>: "]"
	 * </ul>
	 @since 0.1
	 **/
	public Dataset() {
		this.rgoColumns = new ArrayList<ColumnAbstraction>();
		this.sMissingValue = "?";
		this.sNullValue = "?";
		this.sEmptyValue = "?";
		this.sOpenRangeDelimiter = "[";
		this.sSeparationRangeDelimiter = ",";
		this.sCloseRangeDelimiter = "]";
	}

	/**
	 * Getter method of property <code>rgoValidBinaryFalseValues</code>.
	 * @return The list of values that will be interpreted as <code>False</code> 
	 @see rgoValidBinaryFalseValues
	 @since 0.2
	 **/
	public ArrayList<String> getValidBinaryFalseValues() {
		return this.rgoValidBinaryFalseValues;
	}

	/**
	 * Setter method of property <code>rgoValidBinaryFalseValues</code>.
	 * @param rgoValidBinaryFalseValues The list of values that will be interpreted as <code>False</code> 
	 @see rgoValidBinaryFalseValues
	 @since 0.2
	 **/
	public void setValidBinaryFalseValues(ArrayList<String> rgoValidBinaryFalseValues) {
		this.rgoValidBinaryFalseValues = rgoValidBinaryFalseValues;
	}

	/**
	 * Getter method of property <code>rgoValidBinaryTrueValues</code>.
	 * @return The list of values that will be interpreted as <code>True</code> 
	 @see rgoValidBinaryTrueValues
	 @since 0.2
	 **/
	public ArrayList<String> getValidBinaryTrueValues() {
		return this.rgoValidBinaryTrueValues;
	}

	/**
	 * Setter method of property <code>rgoValidBinaryTrueValues</code>.
	 * @param rgoValidBinaryTrueValues The list of values that will be interpreted as <code>True</code> 
	 @see rgoValidBinaryTrueValues
	 @since 0.2
	 **/
	public void setValidBinaryTrueValues(ArrayList<String> rgoValidBinaryTrueValues) {
		this.rgoValidBinaryTrueValues = rgoValidBinaryTrueValues;
	}

	/**
	 * Set the valid binary values for the reading and writing of binary columns.
	 * @param rgoFalseList The list comprising the valid <code>False</code> symbols/strings
	 * @param rgoTrueList The list comprising the valid <code>True</code> symbols/strings
	 @since 0.2
	 @see setValidBinaryTrueValues
	 @see setValidBinaryFalseValues
	 **/
	public void setValidBinaryValues(ArrayList<String> rgoFalseList, ArrayList<String> rgoTrueList) {
		this.rgoValidBinaryFalseValues = rgoFalseList;
		this.rgoValidBinaryTrueValues = rgoTrueList;
	}

	/**
	 * Insert a column abstraction given by parameter in the last position of 
	 * the list of columns of the dataset.
	 * @param oColumn Column abstraction to be inserted
	 @since 0.1
	 @see ColumnAbstraction
	 **/
	public void addColumn(ColumnAbstraction oColumn) {
		this.rgoColumns.add(oColumn);
	}

	// TODO ARQ: Los threads no son explícitos pero aparecen en el comentario ???
	/**
	 * Insert a column abstraction in a given position of 
	 * the list of columns of the dataset.
	 * @param oColumn Column abstraction to be inserted
	 * @param iIndex Position index where the column is inserted in the list. The rest
	 * of column items will be shifted  one position to the right.
	 @since 0.1
	 @see ColumnAbstraction
	 @throws UnsupportedOperationException
	 @throws ClassCastException
	 @throws NullPointedException
	 @throws IllegalArgumentException
	 @throws IndexOutOfBoundsException
	 **/
	public void addColumn(ColumnAbstraction oColumn, int iIndex) {
		this.rgoColumns.add(iIndex, oColumn);
	}

	// TODO ARQ: Los threads no son explícitos pero aparecen en el comentario ???
	/**
	 * Remove a column from the column list in the dataset. Notice that any
	 * subsequent column in the list is moved to the left (index decreased). 
	 * @param iIndex Position index where the column to be removed is located.
	 * @return The removed column.
	 @since 0.1
	 @see ColumnAbstraction
	 @throws UnsupportedOperationException
	 @throws IndexOutOfBoundsException
	 **/
	public ColumnAbstraction removeColumn(int iIndex) {
		return this.rgoColumns.remove(iIndex);
	}

	/**
	 * Getter method of property <code>rgoColumns</code>.
	 * @return The list of columns abstractions in the dataset
	 @since 0.1 
	 @see ColumnAbstraction
	 **/
	public List<ColumnAbstraction> getColumns() {
		return this.rgoColumns;
	}

	/**
	 * Setter method of property <code>rgoColumns</code>.
	 * <p>
	 * This method should be used very carefully, and only in those cases when 
	 * it is necessary to replace the entire set of columns in the dataset. To add
	 * and remove columns, the use of the methods addColumn and removeColumn is
	 * strongly recommended. 
	 * @param rgoCols The entire list of columns in the dataset.
	 @since 0.1  
	 @see ColumnAbstraction
	 @see addColumn
	 @see removeColumn
	 **/
	public void setColumns(List<ColumnAbstraction> rgoCols) {
		this.rgoColumns = rgoCols;
	}

	/**
	 * Setter method of property <code>iNumberOfDecimals</code>.
	 * @param iNum The number of decimal digits that will be considered when writing numerical values in 
	 * the dataset file.  
	 @since 0.2
	 @see iNumberOfDecimals
	 @see NumericalColumn
	 **/
	public void setNumberOfDecimals(int iNum){
		this.iNumberOfDecimals = iNum;
	}

	/**
	 * Getter method of property <code>iNumberOfDecimals</code>.
	 * @return The number of digits being considered as decimals when writing numerical values in 
	 * the dataset file.  
	 @since 0.2
	 @see iNumberOfDecimals
	 @see NumericalColumn
	 **/
	public int getNumberOfDecimals() {
		return this.iNumberOfDecimals;
	}

	/**
	 * Query a column abstraction by its index in the column list. Notice that indexes can change if
	 * any column is added or removed to/from intermediate positions.
	 * @param iIndex The index of the queried column.
	 * @return The column abstraction queried.
	 @since 0.1
	 @see rgoColumns
	 **/
	public ColumnAbstraction getColumn(int iIndex) {
		return this.rgoColumns.get(iIndex);
	}

	/**
	 * Obtain the index of a given column. 
	 * @param oCol column to search
	 * @return the index of the parameter column, -1 in other case
	 @since 0.2
	 @see rgoColumns
	 **/
	public int getIndexOfColumn(ColumnAbstraction oCol) {
		return this.rgoColumns.indexOf(oCol);
	}

	/**
	 * Getter method of property <code>sName</code>.
	 * @return The name of the dataset
	 @since 0.1
	 @see sName
	 **/
	public String getName() {
		return this.sName;
	}

	/**
	 * Setter method of property <code>sName</code>.
	 * @param sName The name of the dataset.
	 @since 0.1
	 @see sName
	 **/
	public void setName(String sName) {
		this.sName = sName;
	}

	/**
	 * Getter method of property <code>sMissingValue</code>.
	 * @return The symbol/string representing a missing value in the dataset  
	 @since 0.1
	 @see sMissingValue
	 **/
	public String getMissingValue() {
		return this.sMissingValue;
	}

	/**
	 * Setter method of property <code>sMissingValue</code>.
	 * @param sMissing The symbol/string representing a missing value in the dataset
	 @since 0.1
	 @see sMissingValue
	 **/
	public void setMissingValue(String sMissing) {
		this.sMissingValue = sMissing;
	}

	/**
	 * Getter method of property <code>sNullValue</code>.
	 * @return The symbol/string representing a null value in the dataset file  
	 @since 0.1
	 @see sNullValue
	 **/
	public String getNullValue() {
		return this.sNullValue;
	}

	/**
	 * Setter method of property <code>sNullValue</code>.
	 * @param sNull The symbol/string representing a null value in the dataset.
	 @since 0.1
	 @see sNullValue
	 **/
	public void setNullValue(String sNull) {
		this.sNullValue = sNull;
	}

	/**
	 * Getter method of property <code>sEmptyValue</code>.
	 * @return The symbol/string representing an empty value in the dataset file 
	 @since 0.1	 
	 @see sEmptyValue
	 **/
	public String getEmptyValue() {
		return this.sEmptyValue;
	}

	/**
	 * Setter method of property <code>sEmptyValue</code>.
	 * @param sEmptyValue The symbol/string representing a empty value in the dataset  
	 @since 0.1
	 @see sEmptyValue
	 **/
	public void setEmptyValue(String sEmptyValue) {
		this.sEmptyValue = sEmptyValue;
	}

	/**
	 * Setter method of interval delimiters.
	 * @param sInitial The symbol/string the starting delimiter.  
	 * @param sSeparator The symbol/string the values separator.  
	 * @param sEnding The symbol/string the ending delimiter.  
	 @since 0.2
	 @see sOpenRangeDelimiter
	 @see sCloseRangeDelimiter
	 @see sSeparationRangeDelimiter	 
	 **/
	public void setRangeDelimiters(String sInitial, String sSeparator, String sEnding){
		this.sOpenRangeDelimiter = sInitial;
		this.sSeparationRangeDelimiter = sSeparator;
		this.sCloseRangeDelimiter = sEnding;
	}

	/**
	 * Get information about the interval delimiters used in the dataset file. 
	 * @return A list comprising three elements: <code>sOpenRangeDelimiter</code>, 
	 * <code>sSeparationRangeDelimiter</code> and <code>sCloseRangeDelimiter</code>.
	 @see sOpenRangeDelimiter
	 @see sSeparationRangeDelimiter
	 @see sCloseRangeDelimiter
	 @since 0.2
	 **/
	public ArrayList<String> getRangeDelimiters(){
		return new ArrayList<String>(Arrays.asList(this.sOpenRangeDelimiter, this.sSeparationRangeDelimiter, this.sCloseRangeDelimiter));
	}

	/**
	 * Merge the current Dataset with other. 
	 * @param oDataset The dataset to be merged with.
	 * @param sColumnFormat 
	 * 			<code>String</code> that specifies the columns to be added.
	 * 			<ul>
	 * 				<li> <code>x</code>: Column to be added
	 * 				<li> <code>%</code>: Skip column
	 * 			</ul>
	 * 
	 @since 0.3
	 @see ColumnAbstraction
	 **/
	public void merge(Dataset oDataset, String sColumnFormat) {
		int iNumberColumns = this.rgoColumns.size();

		/* Check the sColumnFormat expression */
		if(sColumnFormat!=null && !sColumnFormat.matches("(x|%)*"))
			throw new InputMismatchException("Invalid format "+sColumnFormat);	

		if(sColumnFormat!=null && sColumnFormat.length() != oDataset.rgoColumns.size())
			throw new InputMismatchException("Incompatible number of columns in "+sColumnFormat);

		if(oDataset.rgoColumns.size() != iNumberColumns)
			throw new InputMismatchException("Incompatible number of columns");		


		/* Merge the column values*/
		for(int i = 0; i < iNumberColumns; i++)
		{
			ColumnAbstraction oOriginalColumn = this.rgoColumns.get(i);
			ColumnAbstraction oNewColumn = oDataset.rgoColumns.get(i);

			if(sColumnFormat==null || sColumnFormat.charAt(i) == 'x')
			{
				if(oOriginalColumn.getType().equals(oNewColumn.getType()))
					oOriginalColumn.addAllValues(oNewColumn.getValues());
				else
					throw new InputMismatchException("Incompatible type of columns: "+
							this.sName+"."+oOriginalColumn.getName()+" (#"+i+") and "+
							oDataset.sName+"."+oNewColumn.getName()+" (#"+i+")");
			}
			/* Column not added. Empty values are used instead*/
			else
			{
				int iSize = oOriginalColumn.getValues().size();

				for(int j = 0; j < iSize; j++)
					oOriginalColumn.addValue(EmptyValue.getEmptyValue());
			}
		}
	}

	/**
	 * Merge the current Dataset with other.
	 * @param oDSInjected The dataset to be merged with.
	 @since 0.3
	 **/
	public void merge(Dataset oDSInjected) {
		this.merge(oDSInjected,null);
	}

	/**
	 * Create a new dataset exactly with the same metadata and column structure.
	 * Instances from the original dataset are not copied to the new one.
	 * @return A new dataset
	 @since 0.3
	 @see ColumnAbstraction
	 **/
	// TODO Design issue: Recode this method so that it does not explicitly depend on specific column types and make it metadata-independent.
	@SuppressWarnings("unchecked")
	@Override
	public Dataset clone(){
		Dataset oNewDataset = null;
		Class<? extends Dataset> classDataset;

		try {
			classDataset = (Class<? extends Dataset>) Class.forName(this.getClass().getCanonicalName());
			oNewDataset = classDataset.newInstance();

			// Include here any other metadata
			oNewDataset.sName = this.sName;
			oNewDataset.iCursor = this.iCursor;
			oNewDataset.sEmptyValue = this.sEmptyValue;
			oNewDataset.sMissingValue = this.sMissingValue;
			oNewDataset.sNullValue = this.sNullValue;	
			oNewDataset.iNumberOfDecimals = this.iNumberOfDecimals;
			oNewDataset.sOpenRangeDelimiter = this.sOpenRangeDelimiter;
			oNewDataset.sCloseRangeDelimiter = this.sCloseRangeDelimiter;
			oNewDataset.rgoValidBinaryFalseValues = this.rgoValidBinaryFalseValues;
			oNewDataset.rgoValidBinaryTrueValues = this.rgoValidBinaryTrueValues;

			// Include here any metadata from columns
			/** Create the columns */
			int size = this.rgoColumns.size();

			for (int i = 0; i < size; i++) 
			{
				ColumnAbstraction oCol = this.rgoColumns.get(i);
				Class<? extends ColumnAbstraction> classColumnAbs = (Class<? extends ColumnAbstraction>) Class.forName(oCol.getClass().getCanonicalName());
				ColumnAbstraction oNewColumn = classColumnAbs.newInstance();

				oNewColumn.setName(oCol.getName());
				oNewColumn.setEmptyValue(oCol.getEmptyValue());
				oNewColumn.setMissingValue(oCol.getMissingValue());
				oNewColumn.setNullValue(oCol.getNullValue());		

				/* Metadata for each column is assigned */
				if (oCol.getType().equals(ColumnType.Numerical) ||
						oCol.getType().equals(ColumnType.Integer)) {
					((NumericalColumn) oNewColumn).setdMinInterval(((NumericalColumn) oCol).getdMinInterval());
					((NumericalColumn) oNewColumn).setdMaxInterval(((NumericalColumn) oCol).getdMaxInterval());				
				}

				/* Categorical columns */
				if (oCol.getType().equals(ColumnType.Categorical)) {

					List<Object> rgoCategories = ((CategoricalColumn) oCol).getCategoryList();

					for(int j = 0; j < rgoCategories.size(); j++)
						((CategoricalColumn) oNewColumn).addCategory(((CategoricalColumn) oCol).getCategoryName(j));
				}

				/* Range columns: No metadata */

				/* Date columns: No metadata */

				/* Nominal columns: No metadata */

				/* Binary columns: No metadata */

				/* New column inserted */
				oNewDataset.addColumn(oNewColumn);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}	

		return oNewDataset;
	}

	/**
	 * Create a new dataset exactly with the same type, column structure and data.
	 * Instances from the original dataset are also copied to the new one.
	 * @return A copy of the dataset
	 @since 0.3
	 @see ColumnAbstraction
	 **/
	public Dataset copy() {
		/* First, the dataset structure is cloned */
		Dataset oNewDataset = this.clone();
		int index = 0;

		/* Copy the column values */
		for(ColumnAbstraction oCol : this.rgoColumns)
			oNewDataset.getColumn(index++).addAllValues(oCol.getValues());

		return oNewDataset;
	}

	/**
	 * Swap two columns in the column list of the dataset, both structure and data.
	 * <p>
	 * The programmer is prevented from using this method due to performance issues. 
	 * 
	 * @param oColumn1 The first column to swap
	 * @param oColumn2 The second column to swap
	 @since 0.1
	 @see ColumnAbstraction
	 @throws UnsupportedOperationException
	 @throws ClassCastException
	 @throws NullPointedException
	 @throws IllegalArgumentException
	 @throws IndexOutOfBoundsException
	 **/
	public void swapColumns(ColumnAbstraction oColumn1, ColumnAbstraction oColumn2) {
		int iIndexCol1 = this.rgoColumns.indexOf(oColumn1);
		int iIndexCol2 = this.rgoColumns.indexOf(oColumn2);
		ColumnAbstraction oAuxCol = oColumn1;
		this.rgoColumns.set(iIndexCol1, oColumn2);
		this.rgoColumns.set(iIndexCol2, oAuxCol);
	}

	/**
	 * This method returns the first column instance with the name passed as parameter. 
	 * @param sName The name of the column to be returned (no case-sensitive)
	 * @return The column named <code>sName</code>. <code>null</code> otherwise.
	 @since 0.3
	 @see ColumnAbstraction
	 **/
	public ColumnAbstraction getColumnByName(String sName) {
		for(ColumnAbstraction oCol : this.rgoColumns)
			if(oCol.getName().equalsIgnoreCase(sName))
				return oCol;

		return null;
	}

	/**
	 * Abstract method. The dataset source is opened, and the metadata processed.
	 @since 0.1
	 @throws FileNotFoundException
	 @throws IOException
	 @throws IllegalFormatSpecificationException
	 **/
	protected abstract void open() 
			throws FileNotFoundException, IOException, IllegalFormatSpecificationException, ClassNotFoundException, SQLException;

	/**
	 * Abstract method. The dataset source is closed.
	 @throws IOException 
	 @since 0.1
	 **/
	protected abstract void close() throws IOException, SQLException;

	/**
	 * Abstract method. Column values are inserted into the dataset structure.
	 * <p>Instance duplication is not checked. 
	 * @param sColumnFormat <code>String</code> that specifies the types of the columns to 
	 * be added. Types depend on the specific dataset.
	 @since 0.1
	 @throws IOException
	 @throws IllegalFormatSpecificationException
	 @throws NotAddedValue
	 @throws IndexOutOfBoundsException
	 **/
	protected abstract void addAllValues(String sColumnFormat) 
			throws IndexOutOfBoundsException, IOException, NotAddedValueException, IllegalFormatSpecificationException, CharacterCodingException, SQLException;
}