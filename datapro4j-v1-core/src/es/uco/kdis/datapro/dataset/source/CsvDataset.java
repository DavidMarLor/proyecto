package es.uco.kdis.datapro.dataset.source;

import es.uco.kdis.datapro.dataset.column.*;
import es.uco.kdis.datapro.dataset.FileDataset;
import es.uco.kdis.datapro.dataset.InstanceIterator;
import es.uco.kdis.datapro.datatypes.MultiDoubleValue;
import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.MultiIntegerValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MultiLongValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.MultiValue;
import es.uco.kdis.datapro.datatypes.NullValue;
import es.uco.kdis.datapro.datatypes.MultiStringValue;
import es.uco.kdis.datapro.exception.IllegalFormatSpecificationException;
import es.uco.kdis.datapro.exception.NotAddedValueException;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * CsvDataset is a class that represents a dataset that conforms to the comma separated values (CSV) format. CSV 
 * has been used for exchanging and converting data between various spreadsheet programs for quite some time.
 * 
 * For a further description, visit the web site {@link http://tools.ietf.org/html/rfc4180}. Version October, 2005.
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
 * <li> (ARQ|IMA|RPB, 0.3, April 2015)	Bug fixed: load invalid values. Support for long and multi columns.
 * <li> (JRR|JML|ARQ, 0.2, March 2012) 	Performance, refactoring and designing issues. Improvements.
 * <li> (MOB, 0.1, June 2011) 			Initial version.
 * </ul>
 * <p>
 @author Rocio Pinillos Bermudez (RPB, 0.3)
 @author Ignacio Monferrer Altava (IMA, 0.3)
 @author Jose Raul Romero (JRR, 0.2)
 @author Jose Maria Luna (JML, 0.2)
 @author Aurora Ramirez Quesada (ARQ, 0.2, 0.3)
 @author Mario Orozco Borrego (MOB, 0.1)
 @version 0.3
 **/
public class CsvDataset extends FileDataset {

	/**
	 * Default CSV dataset constructor. The following lists are initialized:
	 * <ul>
	 * <li> <code>rgoValidBinaryFalseValues</code>
	 * <li> <code>rgoValidBinaryTrueValues</code>
	 * </ul>
	 @since 0.1
	 @see rgoValidBinaryFalseValues
	 @see rgoValidBinaryTrueValues
	 **/
	public CsvDataset() {
		super();
		this.rgoValidBinaryFalseValues = new ArrayList<String>();
		this.rgoValidBinaryTrueValues = new ArrayList<String>();
	}

	/**
	 * CSV dataset constructor having the name of the file as parameter. 
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
	public CsvDataset(String sFileName) {
		super(sFileName);
		this.rgoValidBinaryFalseValues = new ArrayList<String>();
		this.rgoValidBinaryTrueValues = new ArrayList<String>();
	}

	/**
	 * Open a CSV file. 
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
	 * Close the CSV file. 
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
	 * Read the metadata of the CSV file
	 * 	@param sContentFormat 
	 * 	<code>String</code> that specifies the format file. The following symbols are used:
	 * 			<ul>
	 * 				<li> <code>n</code>: Obtain the attribute names
	 * 				<li> <code>v</code>: Obtain the attribute values
	 * 				<li> <code>%</code>: Skip a row	
	 * 			</ul>
	 * @param sColumnFormat <code>String</code> that specifies the types of the columns to be read. Each column
	 *  type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column
	 * 				<li> <code>f</code>: Real column 
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>i</code>: Integer column
	 * 				<li> <code>l</code>: Long column
	 * 				<li> <code>m</code>: Multivalue column
	 * 				<li> <code>%</code>: Skip column	
	 * 			</ul>
	 @throws IOException 
	 @throws IllegalFormatSpecificationException 
	 @since 0.2
	 @see addAttributeToSpecification
	 **/
	protected void obtainMetadata(String sContentFormat, String sColumnFormat)
			throws IOException {

		if(sContentFormat==null)
			sContentFormat = "nv";
		for (int i = 0; ; i++)
		{
			/* Obtain the name of the attributes */
			if(sContentFormat.charAt(i)=='n')
			{
				/* Read a line*/
				String sLine = this.oBufferedReader.readLine();
				String sNames = sLine.replace(",", " ");
				StringTokenizer oType = new StringTokenizer(sNames);

				int iLen = sColumnFormat.length();
				int iCounter = 0;

				/* Counts the number of m's to know the real number of columns*/
				if(sColumnFormat.indexOf('m') != -1){
					for(int z=0;z<iLen;z++){
						if(sColumnFormat.charAt(z) == 'm')
							iCounter++;
					}
					iLen -= iCounter;
				}
				
				if(sColumnFormat!=null && iLen!=oType.countTokens())
					throw new InputMismatchException("Incompatible number of columns");

				else
				{
					int iColumnFormat = sColumnFormat.length();
					int j=0;
					while (j < iColumnFormat) {
						switch (sColumnFormat.charAt(j)) {

						/* Nominal column */
						case 's': 
							this.rgoColumns.add(new NominalColumn(oType.nextToken()));
							break;

							/* Categorical column */
						case 'c': 
							this.rgoColumns.add(new CategoricalColumn(oType.nextToken()));
							break;

							/* Real column */
						case 'f': 
							this.rgoColumns.add(new NumericalColumn(oType.nextToken()));
							break;

							/* Integer column */
						case 'i': 
							this.rgoColumns.add(new IntegerColumn(oType.nextToken()));
							break;

							/* Long column */
						case 'l':
							this.rgoColumns.add(new LongColumn(oType.nextToken()));
							break;

							/* Multivalue column */
						case 'm':
							j++;
							switch (sColumnFormat.charAt(j)) {

								/* String values */
							case 's': 
								this.rgoColumns.add(new MultiStringColumn(oType.nextToken()));
								break;

								/* Real values */
							case 'f': 
								this.rgoColumns.add(new MultiDoubleColumn(oType.nextToken()));
								break;

								/* Integer values */
							case 'i': 
								this.rgoColumns.add(new MultiIntegerColumn(oType.nextToken()));
								break;

								/* Long values */
							case 'l':
								this.rgoColumns.add(new MultiLongColumn(oType.nextToken()));
								break;
							}
							break;

							/* skip column */
						default: 
							oType.nextToken();
							this.iCursor--;
							break;
						}
						this.iCursor++;
						
						j++;
						
					}
				}
			}
			/* Obtain first row for the attribute values */
			else if(sContentFormat.charAt(i)=='v')
				break;
			/* skip line*/
			else
				/* Read a line*/
				this.oBufferedReader.readLine();
		}
	}

	/**
	 * Method that adds all the values/elements in the dataset to the columns
	 * @param sColumnFormat <code>String</code> that specifies the types of the columns to 
	 * 			be read. Each column type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column
	 * 				<li> <code>f</code>: Real column 
	 * 				<li> <code>i</code>: Integer column
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>l</code>: Long column
	 * 				<li> <code>m</code>: Multivalue column
	 * 				<li> <code>%</code>: Skip column	
	 * 			</ul>
	 * 
	 @throws IndexOutOfBoundsException
	 @throws IOException
	 @throws NotAddedValueException
	 @see addValue()
	 @since 0.2
	 **/
	@Override
	protected void addAllValues(String sColumnFormat) 
			throws IndexOutOfBoundsException, IOException, NotAddedValueException {
		
		String sLine = this.oBufferedReader.readLine();
		String sValue;
		MultiIntegerValue rgoMultiInt;
		MultiLongValue rgoMultiLong;
		MultiStringValue rgoMultiStr;
		MultiDoubleValue rgoMultiDo;
		String aValues [];
		int iSize;

		while (sLine != null) {		

			rgoMultiInt = new MultiIntegerValue();
			rgoMultiLong = new MultiLongValue();
			rgoMultiStr = new MultiStringValue();
			rgoMultiDo = new MultiDoubleValue();

			/* Skip empty or commented lines */
			if (!sLine.isEmpty() && !sLine.startsWith(this.sCommentValue)) {

				if (sColumnFormat.contains("m")) {
					sLine = removeSpecialCharacters(sLine);
				}
				
				sLine = sLine.replace(",", " ");
				StringTokenizer oToken = new StringTokenizer(sLine);
				int iColumnIndex = 0;

				int iColumnFormat = sColumnFormat.length();

				int j = 0;
				while (j < iColumnFormat) {
					sValue = oToken.nextToken();

					/* Skip the column if the symbol '%' is reached */
					if(sColumnFormat.charAt(j) != '%'){ 

						/* Insert a missing value */
						if (sValue.equalsIgnoreCase(this.sMissingValue)){
							this.rgoColumns.get(iColumnIndex).addValue(MissingValue.getMissingValue());
							if (sColumnFormat.charAt(j) == 'm')
								j++;
						}	

						/* Insert a null value */
						else if (sValue.equalsIgnoreCase(this.sNullValue)){
							this.rgoColumns.get(iColumnIndex).addValue(NullValue.getNullValue());
							if (sColumnFormat.charAt(j) == 'm')
								j++;
						}	

						/* Insert an empty value */
						else if (sValue.equalsIgnoreCase(this.sEmptyValue)){
							this.rgoColumns.get(iColumnIndex).addValue(EmptyValue.getEmptyValue());
							if (sColumnFormat.charAt(j) == 'm')
								j++;
						}

						else{
							switch (sColumnFormat.charAt(j)) {

							/* Nominal column */
							case 's': 
								this.rgoColumns.get(iColumnIndex).addValue(sValue);
								break;

								/* Categorical column */
							case 'c': 
								this.rgoColumns.get(iColumnIndex).addValue(sValue,true);
								break;

								/* Integer column */
							case 'i': 
								this.rgoColumns.get(iColumnIndex).addValue(Integer.valueOf(sValue));
								break;

								/* Real column */
							case 'f': 
								this.rgoColumns.get(iColumnIndex).addValue(Double.valueOf(sValue));
								break;

								/* Long column */
							case 'l':
								this.rgoColumns.get(iColumnIndex).addValue(Long.valueOf(sValue));
								break;

								/* Multivalue column */
							case 'm':
								j++;
								switch (sColumnFormat.charAt(j)) {

								/* String values */
								case 's':
									aValues = sValue.split(";");
									iSize = aValues.length;
									for (int i=0; i<iSize; i++)
										rgoMultiStr.addValue(aValues[i]);
									this.rgoColumns.get(iColumnIndex).addValue(rgoMultiStr);
									break;

									/* Real values */
								case 'f':
									aValues = sValue.split(";");
									iSize = aValues.length;
									for (int i=0; i<iSize; i++)
										rgoMultiDo.addValue(Double.valueOf(aValues[i]));
									this.rgoColumns.get(iColumnIndex).addValue(rgoMultiDo);
									break;

									/* Integer values */
								case 'i': 
									aValues = sValue.split(";");
									iSize = aValues.length;
									for (int i=0; i<iSize; i++)
										rgoMultiInt.addValue(Integer.valueOf(aValues[i]));
									this.rgoColumns.get(iColumnIndex).addValue(rgoMultiInt);
									break;

									/* Long values */
								case 'l':
									aValues = sValue.split(";");
									iSize = aValues.length;
									for (int i=0; i<iSize; i++)
										rgoMultiLong.addValue(Long.valueOf(aValues[i]));
									this.rgoColumns.get(iColumnIndex).addValue(rgoMultiLong);
									break;
								}
								break;

							}
						}
						iColumnIndex++;
					}
					j++;
				
				}
			}
			sLine = this.oBufferedReader.readLine();
		}
	}

	/**
	 * Open the source file and obtain all its information (metadata and data).
	 * @param sContentFormat 
	 * 			<code>String</code> that specifies the format file. The following symbols are used:
	 * 			<ul>
	 * 				<li> <code>n</code>: Obtain the attribute names
	 * 				<li> <code>v</code>: Obtain the attribute values
	 * 				<li> <code>%</code>: Skip a row	
	 * 			</ul>
	 * 				Example: %n%%v (1) omits the first line, (2) reads the column names, (3) omit
	 * 						the two following lines, (4) read the dataset values and
	 * @param sColumnFormat 
	 * 	 		<code>String</code> that specifies the types of the columns to 
	 * 			be read. Each column type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column
	 * 				<li> <code>f</code>: Real column 
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>i</code>: Integer column
	 * 				<li> <code>l</code>: Long column
	 * 				<li> <code>m</code>: Multivalue column
	 * 				<li> <code>%</code>: Skip column	
	 * 			</ul>
	 * 
	 @throws NotAddedValueException
	 @throws IOException 
	 @throws IndexOutOfBoundsException 
	 @throws IllegalFormatSpecificationException 
	 @since 0.2
	 @see open()
	 @see obtainMetadat()
	 @see addAllValues()
	 @see close()
	 **/
	@Override
	public void readDataset(String sContentFormat, String sColumnFormat) 
			throws IOException, IndexOutOfBoundsException, NotAddedValueException, IllegalFormatSpecificationException {

		/* Check the sColumnFormat expression */
		if(!sColumnFormat.matches("(s|f|c|i|l|m|%)*"))
			throw new InputMismatchException("Invalid format "+sColumnFormat);

		/* Check the sContentFormat expression */
		if(sContentFormat!=null && !sContentFormat.matches("%*n%*v"))
			throw new InputMismatchException("Invalid format "+sContentFormat);

		this.open();
		this.obtainMetadata(sContentFormat,sColumnFormat);
		this.addAllValues(sColumnFormat);
		this.close();
	}

	/**
	 * Open the source file and obtain all its information (metadata and data).
	 * @param sColumnFormat 
	 * 	<code>String</code> that specifies the types of the columns to be read. Each column
	 *  type is represented by the following symbols
	 * 			<ul>
	 * 				<li> <code>s</code>: Nominal column
	 * 				<li> <code>f</code>: Real column 
	 * 				<li> <code>c</code>: Categorical column
	 * 				<li> <code>l</code>: Long column
	 * 				<li> <code>m</code>: Multivalue column
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
			throws IOException, IndexOutOfBoundsException, NotAddedValueException, IllegalFormatSpecificationException {
		this.readDataset(null, sColumnFormat);
	}

	/**
	 * Write the metadata in a new file by means of a PrintWriter. 
	 * @param oFlow PrintWriter to write the metadata
	 @since 0.2
	 **/
	private void writeMetadata(PrintWriter oFlow) {

		/* Write the name of the attribute */
		int iNumberColumns = this.rgoColumns.size();
		for (int i = 0; i < iNumberColumns-1; i++)
			oFlow.print(this.getColumn(i).getName() + ",");

		oFlow.print(this.getColumn(iNumberColumns-1).getName()+"\n");
	}

	/**
	 * Write the data in a new file by means of a PrintWriter.
	 * @param oFlow PrintWriter to write the data
	 @since 0.2
	 **/
	
	@SuppressWarnings("unchecked")
	private void writeData(PrintWriter oFlow) {
		InstanceIterator oInstanceIterator = new InstanceIterator(this);
		List<Object> rgoArray;
		double dExp = Math.pow(10, this.getNumberOfDecimals());
		double dValue;
		boolean bContainsMulti = false;

		// Check if there exists multivalues
		for(ColumnAbstraction oColumn: this.rgoColumns){
			if(oColumn.getType().equals(ColumnType.Multivalue)){
				bContainsMulti=true;
				break;
			}
		}

		/* Read every instance */
		while (!oInstanceIterator.isDone()) {	
			rgoArray = oInstanceIterator.currentInstance();
			int iColumnIndex = 0;

			if (bContainsMulti)
				oFlow.print("\"");

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

						/* Write numerical attributes */
						else if (oType.equals(ColumnType.Numerical)) { 
							dValue = Double.parseDouble(oActualValue.toString());
							oFlow.print((Math.round(dValue*dExp)/dExp));
						}

						/* Write integer attributes */
						else if (oType.equals(ColumnType.Integer)) { 
							dValue = Double.parseDouble(oActualValue.toString());
							oFlow.print((int)Math.rint(dValue));
						}

						/* Write long attributes */
						else if (oType.equals(ColumnType.Long)) {
							dValue = Double.parseDouble(oActualValue.toString());
							oFlow.print((long)Math.rint(dValue));
						}

						/* Write Multivalue attributes */
						else if (oType.equals(ColumnType.Multivalue)) {

							// The multivalue only contains an invalid value
							if (oActualValue instanceof InvalidValue) {
								Object oInvalidValue = oActualValue;
								if (oInvalidValue instanceof NullValue)
									oFlow.print("\"\"" + this.sNullValue + "\"\"");
								else if (oInvalidValue instanceof MissingValue)
									oFlow.print("\"\"" + this.sMissingValue + "\"\"");
								else
									oFlow.print("\"\"" + this.sEmptyValue + "\"\"");
							}
							else {
								String sValue = new String("\"\"");
								int iSize = ((MultiValue<Object>)oActualValue).getSize()-1;
								for (int i=0; i<iSize; i++)
									sValue = sValue + ((MultiValue<Object>)oActualValue).getValue(i) + ",";

								sValue = sValue + ((MultiValue<Object>)oActualValue).getValue(iSize) + "\"\"";

								oFlow.print(sValue);
							}
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
					if (oColumn.getType().equals(ColumnType.Multivalue)){
						oFlow.print("\"\"");
						oFlow.print(this.sNullValue);
						oFlow.print("\"\"");
					}
					else{
						oFlow.print(this.sNullValue);
					}
				}

				if(iColumnIndex != rgoArray.size()-1)
					oFlow.print(",");
				else{
					if (bContainsMulti)
						oFlow.print("\"");
					oFlow.print("\n");
				}
				iColumnIndex++;
			}
			oInstanceIterator.next();
		}
	}

	/**
	 * Write in a new file the complete dataset. The following type of columns is only allowed:
	 * 		<ul>
	 * 			<li> Numerical (real, integer, long)
	 * 			<li> Nominal
	 * 			<li> Categorical
	 * 			<li> Multi values
	 * 
	 * @param sOutputFile The name of the file where the dataset is written
	 @throws IOException  		
	 @since 0.2
	 @see writeMetadata
	 @see writeData
	 **/
	@Override
	public void writeDataset(String sOutputFile) 
			throws IOException {
		PrintWriter oFlow = new PrintWriter(new FileWriter(sOutputFile));

		/* Write the metadata*/
		this.writeMetadata(oFlow);

		/* Write the data*/
		this.writeData(oFlow);

		/* Close the writer */
		oFlow.close();
	}


	/**
	 * Removes quotes and internal commas in the string read 
	 * 	from file that contains multivalues.
	 * @param sLine Actual value (as read from the CSV file).
	 * @return The processed string without quotes and internal commas.
	 * @since 0.3
	 **/
	private String removeSpecialCharacters (String sLine) {
		String sNewLine = sLine.replace(" ", "");

		int iBegin, iEnd, iActualPos = 0;
		int iPos = 0, iQuotesAtBegin = 0, iQuotesAtEnd = 0;

		// Number of quotes at the beginning
		while(iPos<sLine.length() && sLine.charAt(iPos)=='\"'){
			iQuotesAtBegin++;
			iPos++;
		}

		// Number of quotes at the end
		iPos=sLine.length()-1;
		while(iPos>=0 && sLine.charAt(iPos)=='\"'){
			iQuotesAtEnd++;
			iPos--;
		}

		// Remove quotes at the beginning and the end
		// only when groups are not equals or it is an unique pair
		if(iQuotesAtBegin!=iQuotesAtEnd || (iQuotesAtBegin==1 && iQuotesAtEnd==1)){
			sNewLine = sNewLine.substring(1,sNewLine.length()-1);
		}

		// Remove groups of quotes (when more than one pair appears)
		while (iActualPos < sNewLine.length()-1) {
			if (sNewLine.charAt(iActualPos) == '\"' && sNewLine.charAt(iActualPos+1) == '\"') {
				sNewLine = sNewLine.substring(0, iActualPos) + sNewLine.substring(iActualPos+1);
			}
			else
				iActualPos++;
		}

		// Change internal delimiter
		iBegin = sNewLine.indexOf("\"");
		iEnd = sNewLine.indexOf("\"", iBegin+1);

		while (iBegin != -1 && iEnd != -1) {
			sNewLine = sNewLine.substring(0, iBegin) + 
					sNewLine.substring(iBegin, iEnd).replace(",", ";") + 
					sNewLine.substring(iEnd, sNewLine.length());

			// Next multivalues
			iBegin = sNewLine.indexOf("\"", iEnd+1);
			iEnd = sNewLine.indexOf("\"", iBegin+1);
		}

		// Remove last quotes
		sNewLine = sNewLine.replace("\"", "");

		return sNewLine;
	}
}