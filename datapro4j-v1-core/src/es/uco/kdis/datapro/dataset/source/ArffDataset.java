package es.uco.kdis.datapro.dataset.source;

import es.uco.kdis.datapro.dataset.column.*;
import es.uco.kdis.datapro.dataset.FileDataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;
import es.uco.kdis.datapro.exception.NotAddedValueException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * ArffDataset is a class that represents a dataset conformant to the ARFF (Attribute-Relation 
 * File Format) standard specification (as used by Weka). This type of files is an ASCII text file that describes
 * a list of instances sharing a set of attributes.
 * 
 * Type of attributes:
 * <ul>
 * <li>@ATTRIBUTE name numeric. Represents a numeric attribute.
 * <li>@ATTRIBUTE name {value1, value2, ...}. Represents a categorical attribute.
 * <li>@ATTRIBUTE name string. Represents a nominal attribute.
 * <li>@ATTRIBUTE name date. Represents a date attribute. Default date format: "yyyy-MM-dd'T'HH:mm:ss". Other formats can be specified by the user.
 * </ul>
 * 
 * For a further description, visit the web site {@link http://www.cs.waikato.ac.nz/ml/weka/arff.html}. Version November 1st, 2008.
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
 * <li> (ARQ, 0.3, April 2015)			Bug fixed: read/write date values.
 * <li> (JRR|JML|ARQ, 0.2, March 2012) 	Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1,  June 2011) 			Initial version.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.2)
 @author Jose Maria Luna (JML, 0.2)
 @author Aurora Ramirez Quesada (ARQ, 0.2, 0.3)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.2
 *
 **/
public class ArffDataset extends FileDataset {

	/**
	 * <code>RELATION</code> is the static constant with the ARFF keyword '@relation'
	 @since 0.1
	 **/
	protected final String RELATION = "@relation";

	/**
	 * <code>ATTRIBUTE</code> is the static constant with the ARFF keyword '@attribute'
	 @since 0.1
	 **/
	protected final String ATTRIBUTE = "@attribute";

	/**
	 * <code>DATA</code> is the static constant with the ARFF keyword '@data'.
	 @since 0.1
	 **/
	protected final String DATA = "@data";
		
	/**
	 * <code>sDefaultDateFormat</code> is the default date format using by Weka
	 @since 0.3
	 * */
	protected final String sDefaultDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	
	/**
	 * Default ARFF dataset constructor (file is ""). 
	 * The following lists are initialized:
	 * <ul>
	 * <li> <code>rgoValidBinaryFalseValues</code>
	 * <li> <code>rgoValidBinaryTrueValues</code>
	 * </ul>
	 @since 0.1
	 @see rgoValidBinaryFalseValues
	 @see rgoValidBinaryTrueValues
	 **/
	public ArffDataset() {
		super("");
		this.rgoValidBinaryFalseValues = new ArrayList<String>();
		this.rgoValidBinaryTrueValues = new ArrayList<String>();
	}

	/**
	 * ARFF dataset constructor having the name of the file as parameter. 
	 * The following lists are initialized:
	 * <ul>
	 * <li> <code>rgoValidBinaryFalseValues</code>
	 * <li> <code>rgoValidBinaryTrueValues</code>
	 * </ul>
	 * @param sFileName The name of the file that contains the dataset.
	 @since 0.1
	 @see rgoValidBinaryFalseValues
	 @see rgoValidBinaryTrueValues
	 @see sFileName
	 **/
	public ArffDataset(String sFileName) {
		super(sFileName);
		this.rgoValidBinaryFalseValues = new ArrayList<String>();
		this.rgoValidBinaryTrueValues = new ArrayList<String>();
	}

	/**
	 * Open the dataset file (filename should be passed as parameter in the constructor). 
	 @throws FileNotFoundException 
	 @since 0.2
	 @see obtainMetadata
	 @see iCursor
	 **/
    @Override
	protected void open() 
	throws FileNotFoundException {
		/** Initialize iCursor to the first column */
		this.iCursor = 0;
		this.oBufferedReader = new BufferedReader(new FileReader(new File(this.sFileName)));
	}

	/**
	 * Close the ARFF file. 
	 @throws IOException 
	 @since 0.2
	 @see oBufferedReader
	 **/	
    @Override
	protected void close() 
	throws IOException {
		this.oBufferedReader.close();
	}

	/**
	 * Read the metadata of an ARFF file. Each attribute specification is interpreted and 
	 * adapted to the library column type.
	 * @param sColumnFormat 
	 * 	<code>String</code> comprising a sequence of symbol of column types or the symbol '%' to skip that column.
	 *  <p> These are the types adapted from the ARFF file format specification:
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column type
	 * 				<li> <code>f</code>: Numeric (real or integer) column type
	 * 				<li> <code>c</code>: Categorical column type
	 * 				<li> <code>b</code>: Binary column type
	 * 				<li> <code>d</code>: Date column type	
	 * 				<li> <code>%</code>: Skip this column	
	 * 			</ul>
	 * <p>
	 * For example, the string "bbf%c" indicates that it should read two 
	 * binary columns and a numeric (real) column, skip the forth column and, finally,
	 * read a categorical column.
	 @throws IOException 
	 @throws InputMismatchException 
	 @since 0.2
	 @see addAttributeToSpecification
	 **/
	protected void obtainMetadata(String sColumnFormat) 
	throws IOException, InputMismatchException {
		char cType = 0;
		String sType;
		String sName;
		String sLine = null;
		String sDateFormat;
		int iColumnNumber = 0;
		
		/* Check the sColumnFormat pattern */
		if(sColumnFormat!=null && !sColumnFormat.matches("(s|f|c|b|d|%)*"))
			throw new InputMismatchException("Invalid format for "+sColumnFormat);
		
		/* Read the first non-empty line in the file */
		try {
			sLine = this.oBufferedReader.readLine();
			while (sLine.startsWith(this.sCommentValue) || sLine.trim().isEmpty())
				sLine = this.oBufferedReader.readLine();
		} catch (Exception e) {
			throw new IOException("File is empty or not a correct ARFF format");
		}
		
		/* Interpret the following non-empty line */
		StringTokenizer oElementLine = new StringTokenizer(sLine);
		String sElement = oElementLine.nextToken();
		
		/* Read the metadata until "@DATA" is found */
		while (!sElement.equalsIgnoreCase(DATA)) 
		{
			/* Read an attribute beginning with "@ATTRIBUTE" */
			if (sElement.equalsIgnoreCase(ATTRIBUTE)) 
			{
				/* Read the dataset using sColumnFormat*/
				if(sColumnFormat!=null)
				{
					/* The number of columns is less than the format specified */
					if(sColumnFormat.length()==iColumnNumber)
						throw new InputMismatchException("Incompatible number of columns");
				
					cType = sColumnFormat.charAt(iColumnNumber);
				}
				
				sName = oElementLine.nextToken();
				sType = oElementLine.nextToken();

				iColumnNumber++;

				/* Numerical attribute */
				if(sType.equalsIgnoreCase("NUMERIC") && 
						(sColumnFormat==null || (sColumnFormat!=null && cType == 'f')))
					this.addAttributeToSpecification('f', null, sName);

				/* Categorical and Binary attributes are treated as nominal values in ARFF */
				else if(sType.startsWith("{") && 
						(sColumnFormat==null || (sColumnFormat!=null && (cType == 'b' || cType == 'c')))) {
					if (sColumnFormat == null) cType = 'c'; 
					this.addAttributeToSpecification(cType, sLine.substring(sLine.indexOf("{")+1,sLine.indexOf("}")), sName);
				}

				/* Nominal attributes as strings */
				else if(sType.equalsIgnoreCase("STRING") && 
						(sColumnFormat==null || (sColumnFormat!=null && cType == 's')))
					this.addAttributeToSpecification('s', null, sName);
				
				/* Date attribute */
				else if(sType.equalsIgnoreCase("DATE") && 
						(sColumnFormat==null || (sColumnFormat!=null && cType == 'd'))){
					
					sDateFormat = sLine.substring(sLine.lastIndexOf("date")+4, sLine.length()).trim();
					
					/* Default date format */
					if(sDateFormat.equalsIgnoreCase("")){
						this.addAttributeToSpecification('d', this.sDefaultDateFormat, sName);
					}
									
					/* Specific date format*/
					else{
						this.addAttributeToSpecification('d', sDateFormat, sName);
					}
				}
				
				/* Invalid attribute */
				else if (cType != '%')
					throw new InputMismatchException("Column "+sName+" (#"+iColumnNumber+") is mismatched with type '"+cType+"'");
			}
			
			/* Read the name of the relation (dataset) */
			else if (sElement.equalsIgnoreCase(RELATION)) 
				this.sName = oElementLine.nextToken();
			
			/* Exception. The symbol for comments is required */
			else
				throw new IOException("Line '"+sLine+"' requires the symbol '"+this.sCommentValue+"'");

			/* Skip any empty or commented line */
			try {
				sLine = this.oBufferedReader.readLine();
				while (sLine.startsWith(this.sCommentValue) || sLine.isEmpty())
					sLine = this.oBufferedReader.readLine();
				
				oElementLine = new StringTokenizer(sLine);
				sElement = oElementLine.nextToken();
			} catch (Exception e) {
				throw new IOException ("Unexpected end of file or incorrect ARFF file format.");
			}
		}
		
		/* The number of columns is greater than the format specified */
		if(sColumnFormat!=null && sColumnFormat.length()!=iColumnNumber)
			throw new InputMismatchException("Incompatible number of columns");
	}

	/**
	 * According to the type of each attribute, a new column is created.
	 * @param cType 
	 * 			  Type of column: <code>char</code> that specifies the types of the 
	 * 			  columns to be read. Each column type is represented by the following symbols
	 * 				<ul>
	 * 					<li> <code>s</code>: Nominal column
	 * 					<li> <code>f</code>: Real column 
	 * 					<li> <code>c</code>: Categorical column
	 * 					<li> <code>b</code>: Binary column
	 * 					<li> <code>d</code>: Date column		
	 * 				</ul>
	 * @param sValues The values of the column.
	 * @param sName	The name of the column.
	 @since 0.2
	 **/
	private void addAttributeToSpecification(char cType, String sValues, String sName) {
		switch(cType)
		{
		/* Create a new numerical column */
		case 'f':
			this.rgoColumns.add(new NumericalColumn(sName));
			break;

		/* Create a new nominal column */
		case 's':
			this.rgoColumns.add(new NominalColumn(sName));
			break;
			
		/* Create a new binary column */	
		case 'b':	
			StringTokenizer oType = new StringTokenizer(sValues, ",");
			if(oType.countTokens() != 2)
				throw new InputMismatchException("Column "+sName+" is mismatched with type '"+cType+"'. Only 2 values are allowed for binary columns.");

			this.rgoColumns.add(new BinaryColumn(sName));
			this.rgoValidBinaryFalseValues.add(oType.nextToken().trim());
			this.rgoValidBinaryTrueValues.add(oType.nextToken().trim());
			break;

		/* Create a new date column */
		case 'd':
			DateColumn oDateColumn = new DateColumn(sName);
			oDateColumn.addDateSpecification(new SimpleDateFormat(sValues));
			this.addColumn(oDateColumn);
			break;

		/* Create a new categorical column */	
		default:
			this.rgoColumns.add(new CategoricalColumn(sName));
			oType = new StringTokenizer(sValues, ",");

			CategoricalColumn oCColumn = (CategoricalColumn) this.rgoColumns.get(this.iCursor); 
			while (oType.hasMoreElements())
				oCColumn.addCategory(oType.nextToken().trim());
			break;
		}	

		this.iCursor++;
	}
	
	/**
	 * Adds all the values/elements of the @DATA section to the dataset columns.
	 * @param sColumnFormat 
	 * 	 		<code>String</code> that specifies the types of the columns to 
	 * 			be read. Each column type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column
	 * 				<li> <code>f</code>: Real column 
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>b</code>: Binary column
	 * 				<li> <code>d</code>: Date column	
	 * 				<li> <code>%</code>: Skip column	
	 * 			</ul>
	 * 
	 @throws IndexOutOfBoundsException
	 @throws IOException
	 @throws NotAddedValueException
	 @see addValue()
	 @since 0.2
	 **/
	protected void addAllValues(String sColumnFormat) 
	throws IndexOutOfBoundsException, IOException, NotAddedValueException {
		String sLine = this.oBufferedReader.readLine();
		String sValue = null;
		
		/* Index to identify the type of column*/
		int iColumnIndex;
		
		while (sLine != null) 
		{		
			/* Skip empty and commented lines */
			if (!sLine.isEmpty() && !sLine.startsWith(this.sCommentValue)) 
			{
				StringTokenizer oToken = new StringTokenizer(sLine, ",");

				/* Initialize the index that identifies the type of column */
				iColumnIndex = 0;
				for (ColumnAbstraction oColumn : this.rgoColumns) 
				{
					sValue = oToken.nextToken().trim();
					
					/* If the programmer passed a '%', skip the column */
					while(sColumnFormat!=null && sColumnFormat.charAt(iColumnIndex)=='%')
					{
						iColumnIndex++;
						sValue = oToken.nextToken();
					}
					iColumnIndex++;
					
					/* Insert a missing value */
					if (sValue.equalsIgnoreCase(this.sMissingValue))
						oColumn.addValue(MissingValue.getMissingValue());
					
					/* Insert a null value */
					else if (sValue.equalsIgnoreCase(this.sNullValue))
						oColumn.addValue(NullValue.getNullValue());
					
					/* Insert an empty value */
					else if (sValue.equalsIgnoreCase(this.sEmptyValue))
						oColumn.addValue(EmptyValue.getEmptyValue());
					
					/* Insert a value */
					else
					{
						/* Add a value in a numerical column */
						if (oColumn.getType().equals(ColumnType.Numerical))
							oColumn.addValue(Double.valueOf(sValue));

						/* Add a value in a categorical or nominal column */
						else if (oColumn.getType().equals(ColumnType.Categorical) || oColumn.getType().equals(ColumnType.Nominal)) 
							oColumn.addValue(sValue);
						
						/* Add a value in a date column */
						else if (oColumn.getType().equals(ColumnType.Date)){
							oColumn.addValue(sValue);
						}
						
						/* Add a value in a binary column */
						else if (oColumn.getType().equals(ColumnType.Binary)) 
						{
							if (this.rgoValidBinaryFalseValues.contains(sValue)) 
								oColumn.addValue(new Boolean(false));
						
							else if (this.rgoValidBinaryTrueValues.contains(sValue))
								oColumn.addValue(new Boolean(true));

							else
								throw new NotAddedValueException("Invalid attribute value ("+sValue+") for column " + oColumn.getName());
						}
					} 
				}
			}
			sLine = this.oBufferedReader.readLine();
		}
	}

	/**
	 * Open the source file and obtain all its information (both metadata and data).
	 * 
	 * @param sContentFormat 
	 * 			<code>null</code> in this dataset format
	 * @param sColumnFormat 
	 * 	 		<code>String</code> that specifies the types of the columns to 
	 * 			be read. Each column type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column
	 * 				<li> <code>f</code>: Real column 
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>b</code>: Binary column
	 * 				<li> <code>d</code>: Date column	
	 * 				<li> <code>%</code>: Skip column	
	 * 			</ul>
	 * 
	 @throws NotAddedValueException
	 @throws IOException 
	 @throws IndexOutOfBoundsException 
	 @since 0.2
	 @see open()
	 @see obtainMetadat()
	 @see addAllValues()
	 @see close()
	 **/
    @Override
	public void readDataset(String sContentFormat, String sColumnFormat) 
	throws IOException, IndexOutOfBoundsException, NotAddedValueException {
		this.open();
		this.obtainMetadata(sColumnFormat);
		this.addAllValues(sColumnFormat);
		this.close();
	}

	/**
	 * Open the source file and obtain all its information (metadata and data).
	 * 
	 * @param sColumnFormat 
	 * 	<code>String</code> that specifies the types of the columns to be read. Each column
	 *  type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column
	 * 				<li> <code>f</code>: Real column 
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>b</code>: Binary column
	 * 				<li> <code>d</code>: Date column
	 * 				<li> <code>%</code>: Skip column
	 * 			</ul>
	 * 
	 @throws NotAddedValueException
	 @throws IOException 
	 @throws IndexOutOfBoundsException 
	 @since 0.2
	 @see open()
	 @see obtainMetadat()
	 @see addAllValues()
	 @see close()
	 **/
	public void readDataset(String sColumnFormat) 
			throws IOException, IndexOutOfBoundsException, NotAddedValueException {
		this.readDataset(null, sColumnFormat);
	}
	
	/**
	 * Open the source file and obtain all its information (metadata and data).
	 * 
	 @throws NotAddedValueException
	 @throws IOException 
	 @throws IndexOutOfBoundsException 
	 @since 0.2
	 @see readDataset()
	 **/
	public void readDataset() 
	throws IOException, IndexOutOfBoundsException, NotAddedValueException {
		this.readDataset(null, null);
	}
	
	/**
	 * Write the metadata in a new file by means of a PrintWriter.
	 * @param oFlow PrintWriter to write the metadata
	 @since 0.2
	 **/
	private void writeMetadata(PrintWriter oFlow) {
		ColumnType oType;
		for (ColumnAbstraction oColumn : this.getColumns()) {
			oType = oColumn.getType();
			oFlow.printf("%s %s ", ATTRIBUTE, oColumn.getName());

			/* Write numerical attributes */
			if (oType.equals(ColumnType.Numerical)) {
				oFlow.println("numeric");
				continue;
			}
			
			/* Write categorical attributes */
			if (oType.equals(ColumnType.Categorical)) {
				List<Object> rgoCategoryList = ((CategoricalColumn) oColumn).getCategoryList();
				int iNumberOfCategories = rgoCategoryList.size();

				/* Write the categories */
				oFlow.print("{");
				for (int i = 0; i < iNumberOfCategories - 1; i++)
					oFlow.print(rgoCategoryList.get(i) + ", ");
				oFlow.println(rgoCategoryList.get(iNumberOfCategories-1) + "}");
				continue;
			} 
			
			/* Write binary attributes */
			if (oType.equals(ColumnType.Binary)) {
				//The first value in the binary values set is taken
				oFlow.println("{" + this.rgoValidBinaryFalseValues.get(0) + ", " + 
						this.rgoValidBinaryTrueValues.get(0) + "}");
				continue;
			}
			
			/* Write nominal attributes */
			if (oType.equals(ColumnType.Nominal)) {
				oFlow.println("string");
				continue;
			}
			/* Write date attributes */
			if (oType.equals(ColumnType.Date))
				oFlow.println("date \""+((DateColumn) oColumn).getDateSpecification().toPattern()+"\"");
		}
	}
	
	/**
	 * Write the data in a new file by means of a PrintWriter.
	 * @param oFlow PrintWriter to write the data
	 @since 0.2
	 **/
	private void writeData(PrintWriter oFlow) {
		
		InstanceIterator oInstanceIterator = new InstanceIterator(this);
		List<Object> rgoArray = null;
		SimpleDateFormat oDateFormat;
		double dExp = Math.pow(10, this.getNumberOfDecimals());

		/* Data label */
		oFlow.println(DATA);
		
		/* Read every instance in the dataset */
		while (!oInstanceIterator.isDone()) {	
			rgoArray = oInstanceIterator.currentInstance();
			int iColumnIndex = 0;
			for (ColumnAbstraction oColumn : this.rgoColumns) {
				Object oActualValue = rgoArray.get(iColumnIndex);

				if (oActualValue != null) {
					
					if(!(oActualValue instanceof InvalidValue)){
						
						ColumnType oCType = oColumn.getType();
						
						/* Write binary attributes */
						if (oCType.equals(ColumnType.Binary)) { 
							if (oActualValue.equals(false))
								oFlow.print(this.rgoValidBinaryFalseValues.get(0));
							else
								oFlow.print(this.rgoValidBinaryTrueValues.get(0));
						}
					
						/* Write numerical attributes */
						else if (oCType.equals(ColumnType.Numerical) || 
								oColumn.getType().equals(ColumnType.Integer)) { 
							double dValue = Double.parseDouble(oActualValue.toString());
							oFlow.print((Math.round(dValue*dExp)/dExp));
						}
						
						/* Write date attributes */
						else if(oCType.equals(ColumnType.Date)){
							oDateFormat = ((DateColumn)oColumn).getDateSpecification();
							oFlow.print(oDateFormat.format(oActualValue));
						}
						
						/* Write nominal/categorical attributes */
						else 
							oFlow.print(oActualValue);
					} 					
					/* The value is missing/null/empty */
					else {
						if (oActualValue instanceof NullValue)
							oFlow.print(this.sNullValue);
						else if (oActualValue instanceof MissingValue) 
							oFlow.print(this.sMissingValue);
						else
							oFlow.print(this.sEmptyValue);
					}
				}
				
				/* The value is null */
				else {
					oFlow.print(this.sNullValue);
				}
				
				if(iColumnIndex++ == rgoArray.size()-1)
					oFlow.println();
				else
					oFlow.print(",");
			}
			oInstanceIterator.next();
		}
	}

	/**
	 * Store the dataset in a new file . The following column types are allowed.
	 * On the contrary, an <code>InputMismatchException</code> is thrown.
	 * 		<ul>
	 * 			<li> Numerical
	 * 			<li> Date
	 * 			<li> Nominal (saved as Weka strings)
	 * 			<li> Categorical (saved as Weka nominals)
	 * 			<li> Binary (saved as categorical values)
	 * 		</ul>
	 * 
	 * @param sOutputFile File name of the dataset
	 @throws InputMismatchException 
	 @throws IOException 		
	 @since 0.2
	 @see writeMetadata
	 @see writeData
	 **/
	public void writeDataset(String sOutputFile) 
	throws IOException, InputMismatchException {
		
		/* Check the type of columns */
		int iNumberOfColumns = this.rgoColumns.size();
		ColumnType oCType = null;
		for(int i = 0; i < iNumberOfColumns; i++) {
			oCType = this.rgoColumns.get(i).getType();
			// These are the column types allowed
			if(!(oCType.equals(ColumnType.Numerical) ||					
					oCType.equals(ColumnType.Date) ||
					oCType.equals(ColumnType.Nominal) ||
					oCType.equals(ColumnType.Binary) ||
					oCType.equals(ColumnType.Categorical)))
				throw new InputMismatchException("Column "+this.rgoColumns.get(i).getName()+" has not a valid type of column");
		}
		
		PrintWriter oFlow = new PrintWriter(new FileWriter(sOutputFile));

		/* Write the name of the file*/
		oFlow.printf("%s %s\n", RELATION, this.sName);
		/* Write metadata*/
		this.writeMetadata(oFlow);
		/* Write data*/
		this.writeData(oFlow);
		/* Close the writer */
		oFlow.close();
	}
}