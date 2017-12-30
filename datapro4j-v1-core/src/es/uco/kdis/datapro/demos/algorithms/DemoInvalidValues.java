package es.uco.kdis.datapro.demos.algorithms;


import es.uco.kdis.datapro.datatypes.EmptyValue;
import es.uco.kdis.datapro.datatypes.InvalidValue;
import es.uco.kdis.datapro.datatypes.MissingValue;
import es.uco.kdis.datapro.datatypes.NullValue;
import java.util.ArrayList;

/*
SALIDA DE ESTE PROGRAMA:

run:
Tamaño vector: 10800000
TIME 1: 110; Invalids: 320000
TIME 2: 187; Invalids: 320000
TIME 3: 281; Invalids: 320000
TIME 4: 121; MissingValues: 160000
BUILD SUCCESSFUL (total time: 4 seconds)

 */

/**
 *
 * @author JRRomero
 */
public class DemoInvalidValues {
   public static void main (String args[]){
     ArrayList<Object> array = new ArrayList<Object>();
     for (int i = 0; i < 80000; i++) {
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(MissingValue.getMissingValue());
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(3.2));
        array.add(MissingValue.getMissingValue());
        array.add(new Double(4.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(EmptyValue.getEmptyValue());
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(NullValue.getNullValue());
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(1.2));
        array.add(new Double(2.2));
        array.add(new Double(5.2));
     }
     
     
   System.out.println("Tamaño vector: " + array.size());
 //********* ALTERNATIVA 1a instanceof InvalidValue
   int inv = 0;
    long li = System.currentTimeMillis();
    int tam = array.size();
    for (int i=0; i < tam; i++)
      if (array.get(i) instanceof InvalidValue)
         inv++;
           
    long lf = System.currentTimeMillis() - li;
    System.out.println("TIME 1a: " + lf + "; Invalids: " + inv);
 
   //********* ALTERNATIVA 1b instanceof InvalidValue
   inv = 0;
   li = System.currentTimeMillis();
    for (Object oValue : array)
      if (oValue instanceof InvalidValue)
         inv++;
           
   lf = System.currentTimeMillis() - li;
    System.out.println("TIME 1b: " + lf + "; Invalids: " + inv);


//********* ALTERNATIVA 2  comparando objetos
    inv = 0;
    li = System.currentTimeMillis();
    NullValue nv = NullValue.getNullValue();
    EmptyValue ev = EmptyValue.getEmptyValue();
    MissingValue mv = MissingValue.getMissingValue();
    for (int i=0; i < array.size(); i++)
      if (array.get(i) == nv || array.get(i) == ev || array.get(i) == mv)
         inv++;
           
    lf = System.currentTimeMillis() - li;
    System.out.println("TIME 2: " + lf + "; Invalids: " + inv);

//********* ALTERNATIVA 3  comparando singleton
    inv = 0;
    li = System.currentTimeMillis();
    for (int i=0; i < array.size(); i++)
      if (array.get(i) == NullValue.getNullValue() || array.get(i) == EmptyValue.getEmptyValue() || array.get(i) == MissingValue.getMissingValue())
         inv++;
           
    lf = System.currentTimeMillis() - li;
    System.out.println("TIME 3: " + lf + "; Invalids: " + inv);

    //********* ALTERNATIVA 4  comprobar sólo un tipo de valor
    inv = 0;
    li = System.currentTimeMillis();
    for (int i=0; i < array.size(); i++)
      if (array.get(i) == MissingValue.getMissingValue())
         inv++;
           
    lf = System.currentTimeMillis() - li;
    System.out.println("TIME 4: " + lf + "; MissingValues: " + inv);
   }
 }