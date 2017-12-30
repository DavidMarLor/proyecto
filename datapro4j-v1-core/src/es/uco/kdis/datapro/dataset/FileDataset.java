package es.uco.kdis.datapro.dataset;

import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class represents the file that contains the Dataset. It is used to manipulate the name and flow of the file.
 * 
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
 * <li> (JRR|JML, 0.2, September 2011) Performance, refactoring and designing issues. Improvements.
 * <li> (JTL, 0.1, May 2011) Initial version.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.2)
 @author Jose Maria Luna (JML, 0.2)
 @author Jose de la Torre Lopez (JTL, 0.1)
 @version 0.2
 *
 **/
public abstract class FileDataset extends Dataset {

	/** 
	 * <code>sCommentedValue</code> stores the string that will represent a 
	 * comment line in the dataset file.
	 @since 0.2 
	 **/
	protected String sCommentValue;

	/** 
	 * <code>sSeparationSymbol</code> stores the symbol/string that will represent a 
	 * separator between values of the same instance-row (i.e., a line of the dataset file).
	 @since 0.2 
	 **/
	protected String sSeparationSymbol;

	/** 
	 * <code>sFileName</code> is the name of the file source that contains the dataset.
	 @since 0.2 
	 **/
	protected String sFileName;

	/** 
	 * <code>oBufferedReader</code> is a buffer to read the file.
	 @since 0.2 
	 **/
	protected BufferedReader oBufferedReader;

	/** 
	 * Default constructor. The following symbols are used as default:
	 * <ul>
	 * <li> <code>sCommentValue</code>: "%"
	 * <li> <code>sSeparationSymbol</code>: ","
	 * </ul>
	 @since 0.2 
	 **/
	public FileDataset() {
		super();
		this.sCommentValue = "%";
		this.sSeparationSymbol = ",";
	}

	/** 
	 * Constructor that receives the name of the file. The following symbols are used as default:
	 * <ul>
	 * <li> <code>sCommentValue</code>: "%"
	 * <li> <code>sSeparationSymbol</code>: ","
	 * </ul>
	 * @param sFileName Name of the file source
	 @since 0.2 
	 **/
	public FileDataset(String sFileName) {
		super();
		this.sFileName = sFileName;
		this.sCommentValue = "%";
		this.sSeparationSymbol = ",";
	}

	/** 
	 * Obtain the name of the file source.
	 * @return sFileName The name of the file source.
	 @since 0.1 
	 **/
	public String getFileName() {
		return this.sFileName;
	}

	/** 
	 * Set the name of the file source.
	 * @param sFileName The name of the file source.
	 * 
	 @since 0.1 
	 **/
	public void setFileName(String sFileName) {
		this.sFileName = sFileName;
	}

	/**
	 * Getter method of property <code>sCommentValue</code>.
	 * @return The symbol/string indicating a comment line in the dataset file.  
	 @see sCommentValue
	 @since 0.2
	 **/
	public String getCommentValue(){
		return this.sCommentValue;
	}

	/**
	 * Setter method of property <code>sCommentValue</code>.
	 * @param sComment The symbol/string indicating a comment line in the dataset file.  
	 @see sCommentValue
	 @since 0.2
	 **/
	protected void setCommentValue(String sComment){
		this.sCommentValue = sComment;
	}

	/**
	 * Getter method of property <code>sSeparationSymbol</code>.
	 * @return The symbol/string used to separate different instances/values in the dataset file.  
	 @see sSeparationSymbol
	 @since 0.2
	 **/
	public String getSeparationSymbol(){
		return this.sSeparationSymbol;
	}

	/**
	 * Setter method of property <code>sSeparationSymbol</code>.
	 * @param sSeparator The symbol/string used to separate different instances/values in the dataset file.
	 @see sSeparationSymbol
	 @since 0.2
	 **/
	protected void setSeparationSymbol(String sSeparator){
		this.sSeparationSymbol = sSeparator;
	}

	/**
	 * Create a new dataset exactly with the same type and column structure.
	 * Instances from the original dataset are not copied to the new one.
	 * @return A new dataset.
	 @since 0.2
	 @see clone()
	 **/
	@Override
	public Dataset clone() {
		Dataset oDataset = super.clone();
		((FileDataset)oDataset).sCommentValue = this.sCommentValue;
		((FileDataset)oDataset).sSeparationSymbol = this.sSeparationSymbol;

		return oDataset;
	}

	/**
	 * Create a new dataset exactly with the same type, column structure and data.
	 * Instances from the original dataset are also copied to the new one.
	 * @return A copy of the dataset.
	 @since 0.2
	 @see copy()
	 **/
	@Override
	public Dataset copy() {
		Dataset oDataset = super.copy();
		((FileDataset)oDataset).sCommentValue = this.sCommentValue;
		((FileDataset)oDataset).sSeparationSymbol = this.sSeparationSymbol;

		return oDataset;
	}

	/**
	 * Abstract method. The dataset is read from its source file.
	 * @param sContentFormat 
	 * 	<code>String</code> that specifies format of the database file
	 * 		  <ul> 
	 * 			<li> <code>%</code> to omit a line (one line)
	 * 			<li> <code>%name</code> to read name of columns (one line)
	 * 			<li> <code>%col</code> to read all the data (zero, one or more lines)
	 * 		  </ul> 
	 * 		  Example: %%%col%%name omits the first two lines, reads all the columns, 
	 * 			omitting an empty line and another line comprising the column names<p>
	 *
	 * @param sColumnFormat 
	 * 	<code>String</code> that specifies the types of the columns to be read. Each column
	 *  type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column
	 * 				<li> <code>f</code>: Real column 
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>b</code>: Binary column
	 * 				<li> <code>i</code>: Integer column		
	 * 			</ul>
	 * 
	 @throws FileNotFoundException
	 @throws IOException
	 @throws IllegalFormatSpecificationException
	 @throws NotAddedValueException
	 @throws IndexOutOfBoundsException
	 @since 0.1
	 */
	public abstract void readDataset(String sContentFormat, String sColumnFormat)
			throws NotAddedValueException, FileNotFoundException, IOException, IllegalFormatSpecificationException, IndexOutOfBoundsException;

	/**
	 * Abstract method. The dataset is saved to a file.
	 * @param sOutputFile The path where the dataset will be saved.
	 @throws IOException 
	 @since 0.1
	 */
	public abstract void writeDataset(String sOutputFile) throws IOException;
}
