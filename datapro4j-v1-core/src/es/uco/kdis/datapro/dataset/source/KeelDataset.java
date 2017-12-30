package es.uco.kdis.datapro.dataset.source;

import es.uco.kdis.datapro.dataset.column.*;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;
import es.uco.kdis.datapro.exception.NotAddedValueException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * KeelDataset is a class that represents a dataset conformant to the KEEL (Knowledge Extraction based on Evolutionary 
 * Learning) standard specification. This type of files is an ASCII text file that describes
 * a list of instances sharing a set of attributes.
 * 
 * The KeelDataset class inherits from ArffDataset since KEEL files are specific types of ARFF files.
 * 
 * Type of attributes:
 * <ul>
 * <li>@ATTRIBUTE name real [value1, value2]. Represents a real attribute
 * <li>@ATTRIBUTE name integer [value1, value2]. Represents an integer attribute
 * <li>@ATTRIBUTE name {value1, value2, ...}. Represents a categorical attribute.
 * </ul>
 * 
 * For a better description see the article:
 * 		<ul>
 * 		<li>KEEL-dataset citation paper: J. Alcala-Fdez, et al. KEEL Data-Mining Software Tool: Data Set Repository, 
 * 			Integration of Algorithms and Experimental Analysis Framework. Journal of Multiple-Valued Logic and 
 * 			Soft Computing 17:2-3 (2011) 255-287.
 *		</ul>
 *
 * Visit the web site {@link http://www.keel.es/}.
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
 * 
 * <ul>
 * <li> (JRR|JML, 0.2, March 2012) Performance, refactoring and designing issues. Improvements.
 * <li> (JTL|MOB, 0.1,  June 2011) Initial version.
 * </ul>
 * <p>
 *
 @author Jose Raul Romero (JRR, 0.2)
 @author Jose Maria Luna (JML, 0.2)
 @author Aurora Ramirez Quesada (ARQ, 0.2)
 @author Mario Orozco Borrego (MOB, 0.1)
 @author Jose de la Torre Lopez (JTL, 0.1)
 @version 0.2
 *
 **/
public final class KeelDataset extends ArffDataset {

	/**
	 * <code>INPUTS</code> is the static constant with the KEEL keyword '@inputs'
	 @since 0.2
	 **/
	protected final String INPUTS = "@inputs";
	
	/**
	 * <code>OUTPUTS</code> is the static constant with the KEEL keyword '@outputs'
	 @since 0.2
	 **/
	protected final String OUTPUTS = "@outputs";
	
	/**
	 * Default KEEL dataset constructor. The following lists are initialized:
	 * <ul>
	 * <li> <code>rgoValidBinaryFalseValues</code>
	 * <li> <code>rgoValidBinaryTrueValues</code>
	 * </ul>
	 @since 0.1
	 @see rgoValidBinaryFalseValues
	 @see rgoValidBinaryTrueValues
	 @see ArffDataset
	 **/
	public KeelDataset() {
		super();
	}

	/**
	 * KEEL dataset constructor having the name of the file as parameter. 
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
	 @see ArffDataset
	 **/
	public KeelDataset(String sFileName) {
		super(sFileName);
	}
	
	/**
	 * Read the metadata of an KEEL file
	 * @param sColumnFormat 
	 * 	<code>String</code> that specifies the types of the columns to be read. Each column
	 *  type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>f</code>: Real column
	 * 	 			<li> <code>i</code>: Integer column  
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>b</code>: Binary column	
	 * 				<li> <code>%</code>: Skip column	
	 * 			</ul>
	 @throws IOException 
	 @since 0.2
	 @see addAttributeToSpecification
	 **/
    @Override
	protected void obtainMetadata(String sColumnFormat)
	throws IOException {
		char cType = 0;
		String sType;
		String sName = null;
		String sLine = null;
	
		/* Initialize iCursor to the first column */
		int iNumberColumns = 0;
		
		/* Check the sColumnFormat expression */
		if(sColumnFormat!=null && !sColumnFormat.matches("(i|f|c|b|%)*"))
			throw new InputMismatchException("Invalid format "+sColumnFormat);
		
		/* Read the first non-empty line in the file */
		try {
			sLine = this.oBufferedReader.readLine();
			while (sLine.startsWith(this.sCommentValue) || sLine.isEmpty())
				sLine = this.oBufferedReader.readLine();
		} catch (Exception e) {
			throw new IOException("File is empty or not a correct Keel format");
		}
		
		StringTokenizer oElementLine = new StringTokenizer(sLine);
		String sElement = oElementLine.nextToken();
		
		/* Read the metadata */
		while (!sElement.equalsIgnoreCase(DATA)) 
		{
			/* Read an attribute */
			if (sElement.equalsIgnoreCase(ATTRIBUTE)) 
			{
				/* Read the dataset using sColumnFormat*/
				if(sColumnFormat!=null)
				{
					/* The number of columns is less than the format specified */
					if(sColumnFormat.length()==iNumberColumns)
						throw new InputMismatchException("Incompatible number of columns");
				
					cType = sColumnFormat.charAt(iNumberColumns);
				}
				
				sName = oElementLine.nextToken();
				sType = oElementLine.nextToken();

				iNumberColumns++;
				
				/* Real attribute */
				if(sType.equalsIgnoreCase("REAL") && 
						(sColumnFormat==null || (sColumnFormat!=null && cType == 'f')))
					this.addAttributeToSpecification('f', sLine.substring(sLine.indexOf("[")+1,sLine.indexOf("]")), sName);

				/* Categorical and Binary attribute */
				else if(sType.startsWith("{") && 
						(sColumnFormat==null || (sColumnFormat!=null && (cType == 'b' || cType == 'c'))))
					this.addAttributeToSpecification(cType, sLine.substring(sLine.indexOf("{")+1,sLine.indexOf("}")), sName);
				
				/* Integer attribute */
				else if(sType.equalsIgnoreCase("INTEGER") && 
						(sColumnFormat==null || (sColumnFormat!=null && cType == 'i')))
					this.addAttributeToSpecification('i', sLine.substring(sLine.indexOf("[")+1,sLine.indexOf("]")), sName);

				/* Invalid attribute */
				else if (cType != '%')
					throw new InputMismatchException("Column "+sName+" (#"+iNumberColumns+") is mismatched with type '"+cType+"'");
			}
			/* Read the relation's name */
			else if (sElement.equalsIgnoreCase(RELATION)) 
				this.sName = oElementLine.nextToken();
			
			/* Read the inputs attributes */
			else if (sElement.equalsIgnoreCase(INPUTS)) 
				;
			
			/* Read the outputs attributes */
			else if (sElement.equalsIgnoreCase(OUTPUTS)) 
				;

			/* Exception. The symbol for comments is required */
			else
				throw new IOException("Line '"+sLine+"' requires the symbol '"+this.sCommentValue+"'");

			/* Skip any empty or commented line */
			sLine = this.oBufferedReader.readLine();
			while (sLine.startsWith(this.sCommentValue) || sLine.isEmpty())
				sLine = this.oBufferedReader.readLine();

			oElementLine = new StringTokenizer(sLine);
			sElement = oElementLine.nextToken();
		}

		/* The number of columns is greater than the format specified */
		if(sColumnFormat!=null && sColumnFormat.length()!=iNumberColumns)
			throw new InputMismatchException("Incompatible number of columns");
	}
	
	/**
	 * Obtain the attribute types once the dataset's header is read.
	 * Create a new column using the attribute type.
	 * @param cType 
	 * 			  Type of column: <code>char</code> that specifies the types of the 
	 * 			  columns to be read. Each column type is represented by the following symbols
	 * 				<ul>
	 * 					<li> <code>f</code>: Real column
	 * 	 				<li> <code>i</code>: Integer column  
	 * 					<li> <code>c</code>: Categorical column
	 * 					<li> <code>b</code>: Binary column	
	 * 					<li> <code>%</code>: Skip column			
	 * 				</ul>
	 * @param sValues Values used in the attribute. Minimum and maximum values if the attribute is numerical, or
	 * a list of categories if the attribute is categorical.
	 * @param sName The name of the column.
	 * 
	 @since 0.2
	 **/
	private void addAttributeToSpecification(char cType, String sValues, String sName) {

		switch(cType)
		{
		/* Create a new numerical column */
		case 'f':
			sValues = sValues.replace(",", " ");
			StringTokenizer oType = new StringTokenizer(sValues);
			NumericalColumn oNumericalColumn = new NumericalColumn(sName);

			try {
				oNumericalColumn.setdMinInterval(Double.valueOf(oType.nextToken()));
				oNumericalColumn.setdMaxInterval(Double.valueOf(oType.nextToken()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
			this.rgoColumns.add(oNumericalColumn);
			
			break;
		
		/* Create a new integer column */
		case 'i':
			sValues = sValues.replace(",", " ");
			oType = new StringTokenizer(sValues);
			IntegerColumn oIntegerColumn = new IntegerColumn(sName);
			
			try {
				oIntegerColumn.setiMinInterval(Integer.valueOf(oType.nextToken()));
				oIntegerColumn.setiMaxInterval(Integer.valueOf(oType.nextToken()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
			this.rgoColumns.add(oIntegerColumn);
			
			break;
			
		/* Create a new binary column */	
		case 'b':	
			sValues = sValues.replace(",", " ");
			oType = new StringTokenizer(sValues);
			
			if(oType.countTokens() != 2)
				throw new InputMismatchException("Column "+sName+" is mismatched with type '"+cType+"'");
			
			else {
				this.rgoColumns.add(new BinaryColumn(sName));
				this.rgoValidBinaryFalseValues.add(oType.nextToken());
				this.rgoValidBinaryTrueValues.add(oType.nextToken());
			}
			break;
			
		/* Create a new categorical column */	
		default:
			this.rgoColumns.add(new CategoricalColumn(sName));
			sValues = sValues.replace(",", " ");
			oType = new StringTokenizer(sValues);
			
			while (oType.hasMoreElements())
				((CategoricalColumn) this.rgoColumns.get(this.iCursor)).addCategory(oType.nextToken());
		
			break;
		}
		
		this.iCursor++;
	}
	
	/**
	 * Method that adds all the values/elements of the @DATA section in the
	 * dataset to the columns.
	 * @param sColumnFormat 
	 * 	 		<code>String</code> that specifies the types of the columns to 
	 * 			be read. Each column type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>f</code>: Real column
	 * 	 			<li> <code>i</code>: Integer column  
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>b</code>: Binary column	
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
		int iColumnFormatIndex;
		
		while (sLine != null) 
		{		
			/* Skip empty or commented lines */
			if (!sLine.isEmpty() && !sLine.startsWith(this.sCommentValue)) 
			{
				sLine = sLine.replace(",", " ");
				StringTokenizer oToken = new StringTokenizer(sLine);
				
				/* Initialize the index that identifies the type of column */
				iColumnFormatIndex = 0;
				for (ColumnAbstraction oColumn : this.getColumns()) 
				{
					sValue = oToken.nextToken();
					
					/* Skip commented columns */
					while(sColumnFormat!=null && sColumnFormat.charAt(iColumnFormatIndex)=='%')
					{
						sValue = oToken.nextToken();
						iColumnFormatIndex++;
					}
					iColumnFormatIndex++;

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
						ColumnType oType = oColumn.getType();
						
						/* Add a value in a real column */
						if (oType.equals(ColumnType.Numerical))
							oColumn.addValue(Double.valueOf(sValue));
						
						/* Add a value in an integer column */
						else if (oType.equals(ColumnType.Integer))
							oColumn.addValue(Integer.valueOf(sValue));

						/* Add a value in a categorical column */
						else if (oType.equals(ColumnType.Categorical)) 
							oColumn.addValue(sValue);
						
						/* Add a value in a binary column */
						else if (oType.equals(ColumnType.Binary)) 
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
	 * Write the metadata in a new file by means of a PrintWriter.
	 * @param oFlow PrintWriter to write the metadata.
	 @since 0.2
	 **/
	private void writeMetadata(PrintWriter oFlow) {
		
		for (ColumnAbstraction oColumn : this.getColumns()) {
			ColumnType oType = oColumn.getType();
			
			oFlow.print(ATTRIBUTE + " " + oColumn.getName() + " ");
					
			/* Write integer attributes */
			if (oType.equals(ColumnType.Integer))
			{
				oFlow.println("integer [" + ((IntegerColumn)oColumn).getiMinInterval() + 
						", " + ((IntegerColumn)oColumn).getiMaxInterval() + "]");
			}
			
			/* Write real attributes */
			else if (oType.equals(ColumnType.Numerical))
			{
				oFlow.println("real [" + ((NumericalColumn)oColumn).getdMinInterval() + 
						", " + ((NumericalColumn)oColumn).getdMaxInterval() + "]");
			}
			
			/* Write categorical attributes */
			else if (oType.equals(ColumnType.Categorical)) {
				List<Object> rgoCategoryList = ((CategoricalColumn) oColumn).getCategoryList();
				
				oFlow.print("{");

				/* Write the categories */
				int iCategory = 0;
				for (; iCategory < rgoCategoryList.size() - 1; iCategory++)
					oFlow.print(rgoCategoryList.get(iCategory) + ", ");
				
				oFlow.println(rgoCategoryList.get(iCategory) + "}");
			} 
			
			/* Write binary attributes */
			else if (oType.equals(ColumnType.Binary))
					oFlow.println("{" + this.rgoValidBinaryFalseValues.get(0) + ", " + 
								this.rgoValidBinaryTrueValues.get(0) + "}");
		}
	}
	
	/**
	 * Write the data in a new file by means of a PrintWriter.
	 * @param oFlow PrintWriter to write the data.
	 @since 0.2
	 **/
	private void writeData(PrintWriter oFlow) {
		oFlow.println(DATA);
		InstanceIterator oInstanceIterator = new InstanceIterator(this);
		List<Object> rgoArray = null;
		double dExp = Math.pow(10, this.getNumberOfDecimals());
		double dValue;
		
		/* Read every instance */
		while (!oInstanceIterator.isDone()) {	
			rgoArray = oInstanceIterator.currentInstance();
			int iColumnIndex = 0;
			for (ColumnAbstraction oColumn : this.rgoColumns) {
				Object oActualValue = rgoArray.get(iColumnIndex);

				if (oActualValue != null) {
					
					if(!(oActualValue instanceof InvalidValue)){
						
						ColumnType oType = oColumn.getType();
						
						/* Write binary attributes */
						if (oType.equals(ColumnType.Binary)) { 
							if (oActualValue.equals(false))
								oFlow.print(this.rgoValidBinaryFalseValues.get(0));
							else
								oFlow.print(this.rgoValidBinaryTrueValues.get(0));
						}
						
						/* Write real attributes */
						else if (oType.equals(ColumnType.Numerical)) { 
							dValue = Double.parseDouble(oActualValue.toString());
							oFlow.print((Math.round(dValue*dExp)/dExp));
						}
						
						/* Write integer attributes */
						else if (oType.equals(ColumnType.Integer)){
							dValue = Double.parseDouble(oActualValue.toString());
							oFlow.print((int)Math.rint(dValue));
						}
						
						/* Write categorical attributes */
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
				
				
				if(iColumnIndex != rgoArray.size()-1)
					oFlow.print(",");
				else
					oFlow.print("\n");
				
				iColumnIndex++;
			}
			oInstanceIterator.next();
		}
	}

	/**
	 * Write in a new file the complete dataset. The following type of columns is only allowed:
	 * 		<ul>
	 * 			<li> Numerical
	 * 			<li> Integer
	 * 			<li> Categorical
	 * 		</ul>
	 * 
	 * @param sOutputFile Name of the file where the dataset is written.
	 @throws IOException		
	 @since 0.2
	 @see writeMetadata
	 @see writeData
	 **/
    @Override
	public void writeDataset(String sOutputFile) 
	throws IOException {
		
		/* Check the type of columns */
		for(int i = 0; i < this.rgoColumns.size(); i++)
			if(!this.rgoColumns.get(i).getType().equals(ColumnType.Numerical) &&					
					!this.rgoColumns.get(i).getType().equals(ColumnType.Integer) &&
					!this.rgoColumns.get(i).getType().equals(ColumnType.Categorical))
				throw new InputMismatchException("Column "+this.rgoColumns.get(i).getName()+" has not valid type of column in Keel");
		
		PrintWriter oFlow = new PrintWriter(new FileWriter(sOutputFile));

		/* Write the name of the file*/
		oFlow.printf("%s %s\n", RELATION, this.sName);

		/* Write the metadata*/
		this.writeMetadata(oFlow);
		
		/* Write the data*/
		this.writeData(oFlow);

		/* Close the writer */
		oFlow.close();
	}
}