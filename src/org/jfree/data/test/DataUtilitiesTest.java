package src.org.jfree.data.test;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.security.InvalidParameterException;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.*;

public class DataUtilitiesTest extends DataUtilities {

	private static Values2D values;
	final double delta = .000000001d;

    private Mockery mockingContextKeyedValues;
    private KeyedValues keyedValues;

    /**
     * Set up the mocking context for the Values2D object.
     */
    
    @BeforeClass
    public static void setUpMethods() {
        Mockery mockingContextValues2D = new Mockery();
        values = mockingContextValues2D.mock(Values2D.class);
        mockingContextValues2D.checking(new Expectations() {{
            allowing(values).getRowCount();
            will(returnValue(2));

            allowing(values).getColumnCount();
            will(returnValue(2));

            allowing(values).getValue(0, 0);
            will(returnValue(7.5));

            allowing(values).getValue(0, 1);
            will(returnValue(6.0));

            allowing(values).getValue(1, 0);
            will(returnValue(2.5));

            allowing(values).getValue(1, 1);
            will(returnValue(2.0));
            
            // test will crash if someone tries get row index 3 or anything thats nto stated here, check for later.
			allowing(values).getValue(0, 2);
            will(throwException(new InvalidParameterException()));
            allowing(values).getValue(2, 0);
            will(throwException(new InvalidParameterException()));
            allowing(values).getValue(0, -1);
            will(throwException(new InvalidParameterException()));
            allowing(values).getValue(-1, 0);
            will(throwException(new InvalidParameterException()));
        }});
    }//checked

    @Before
    public void setUp() {
        mockingContextKeyedValues = new Mockery();
        keyedValues = mockingContextKeyedValues.mock(KeyedValues.class);
    }//checked
	
	//-------------------------------------CalculateColumn Tests-------------------------------------------
	
	
	 @Test
	 public void calculateColumnTotalForTwoValues() {
	     // setup

	     double result = DataUtilities.calculateColumnTotal(values, 0);
	     assertEquals(result, 10.0, .000000001d);
	     
	     //This class works
	 }//checked
	 
	 
	 
	 @Test(expected = InvalidParameterException.class)
	public void AboveRangeCalculateColumnForTwoValues() {
		double y = DataUtilities.calculateColumnTotal(values, 2);
		 //explanation here: the second someone tries to set y to be something outside the scope of the given matrix, we should have an exception thrown, no need to catch it though
	}// this is a better way to catch exceptions in testing and is better practice, the other way should work fine so i wont change the rest but just letting u guys know

//	 public void AboveRangeCalculateColumnForTwoValues()
//	 {
//		// setup
//
//
//	    	 double y = DataUtilities.calculateColumnTotal(values, 2);
//
//	     catch(InvalidParameterException e)
//	     {
//
//	     }
//	     catch(Exception e)
//	     {
//	    	 fail("Parameter within bounds"+e.getMessage());
//	     }
	     //Trying to go out of bounds does not work as expected
	     
//	 }
	 
	 @Test
	 public void BelowRangeCalculateColumnForTwoValues()
			 //this tests to see if setting a value to be out of the bounds of a the matrix
	 {
		// setup

	     try
	     {
	    	 double y = DataUtilities.calculateColumnTotal(values, -1);
	     }
	     catch(InvalidParameterException e)
	     {
	    	 //this is empty because?
	     }
	     catch(Exception e)
	     {
	    	 fail("Parameter within bounds"+e.getMessage());
	     }
	     //Trying to go out of bounds does not work as expected

	 }//checked
	 	 
	 
	 @Test
	 public void TestCalculateColumnOneByOne()
	 {
	     Mockery mockingContext = new Mockery();
	     final Values2D values = mockingContext.mock(Values2D.class);
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getRowCount();
	             will(returnValue(1));
	             one(values).getValue(0, 0);
	             will(returnValue(7.5));
	             
	         }
	     });
	     double result = DataUtilities.calculateColumnTotal(values, 0);
	     assertEquals( 7.5,result, delta);
	     //Returns the only element available as expected
	 }//checked


	 
	 @Test
	 public void TestCalculateColumnEmpty()
	 {
		 Mockery mockingContext = new Mockery();
		 final Values2D values = mockingContext.mock(Values2D.class);
		 mockingContext.checking(new Expectations() {
			 {
				 one(values).getRowCount();
				 will(returnValue(0));
				 one(values).getColumnCount();
				 will(returnValue(0));
			 }
		 });
		 double result = calculateColumnTotal(values, 0);
		 assertEquals(0, result, delta);
	 }	 //checked
	 
	 //-------------------------------------CalculateRow Tests-------------------------------------------
	 

	 @Test
	 public void CalculateRowTotalForTwoValues()
			 //one row
	 {
		 double result = DataUtilities.calculateRowTotal(values, 0);
		 assertEquals(13.5,result, delta);

		 //This function has a bug it just returns 7 when it should be returning 13.5
	 }//checked
	 
	 @Test
	 public void AboveRangeCalculateRowForTwoValues()
			 //currently matrix has two rows, this checks to see if someone tries to calculate for the 3rd row. (which is row index 2)
	 {
		// setup

	     try
	     {
	    	 double y = DataUtilities.calculateRowTotal(values, 2);
	     }
	     catch(InvalidParameterException e)
	     {
	    	 
	     }
	     catch(Exception e)
	     {
	    	 fail("Parameter within bounds"+e.getMessage());
	     }
	     //Trying to go out of bounds, exception thrown as expected
	     
	 }//checked
	 
	 @Test
	 public void BelowRangeCalculateRowForTwoValues()
	// same thing as previous test but now checking for row index "-1"
	 {
		// setup

	     try
	     {
	    	 double y = DataUtilities.calculateRowTotal(values, -1);
	     }
	     catch(InvalidParameterException e)
	     {
	    	 
	     }
	     catch(Exception e)
	     {
	    	 fail("Parameter within bounds"+e.getMessage());
	     }
		 //Trying to go out of bounds, exception thrown as expected
	 }//checked
	 	 
	 
	 @Test
	 public void CalculateRowOneByOne()
	 {
		 Mockery mockingContext = new Mockery();
	     final Values2D values = mockingContext.mock(Values2D.class);
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getColumnCount();
	             will(returnValue(1));
	             one(values).getValue(0, 0);
	             will(returnValue(7.5));
			 }
	     });	 
		 double result = DataUtilities.calculateRowTotal(values, 0);
		 assertEquals(7.5, result, delta);
		 	 
	 }//checked
	 @Test
	 public void TestCalculateRowEmpty(){
		// this is when martix, code not written by us should check to call calculateRowTotal, if its empty (THIS CHECK IS NOT DONE BY US) then it should return 0
		 Mockery mockingContext = new Mockery();
	     final Values2D values = mockingContext.mock(Values2D.class);
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getRowCount();
	             will(returnValue(0));
	             one(values).getColumnCount();
	             will(returnValue(0));
	         }
	     });
		 double result = calculateRowTotal(values, 0);
		 assertEquals(0, result, delta);
	 }//checked
//	 public void TestCalculateRowEmpty()
//	 {
//	     Mockery mockingContext = new Mockery();
//	     final Values2D values = mockingContext.mock(Values2D.class);
//	     mockingContext.checking(new Expectations() {
//	         {
//	             one(values).getRowCount();
//	             will(returnValue(0));
//	             one(values).getColumnCount();
//	             will(returnValue(0));
//	         }
//	     });
//	     assertEquals(0, result, delta);
//	     //Returns the only element available as expected, this is "if no matrix exists we throw and exception)
//	 }

	@Test
		 public void CreateValidArray()
	 {
		 double [] valid = {1.0,-2.1,3.2,4.3,5.4,6.5,7.6,8.7,9.8};
		 Number [] test  = DataUtilities.createNumberArray(valid);

		 if (valid.length == test.length){
			 for(int i =0;i<valid.length;i++)
			 {
//			 System.out.println(test[i]);
				 assertEquals(valid[i],test[i]);
			 }
		 }
		 else{
			 fail();
		 }
		 assertEquals(valid.length,test.length); //Make sure it has the same number of elements
	 }//check
@Test(expected = InvalidParameterException.class)
	public void CreateInvalidArray(){
		double [] nullArray = null;
		Number [] actualOutput = createNumberArray(nullArray);

		assertSame(null, actualOutput);

}//for later

		//Helps us observe the behaviour of how this method works
	@Test()
	public void CreateZeroArray()
	{
		double [] zeroArray = {};
		Number [] test = DataUtilities.createNumberArray(zeroArray);

		assertEquals(0, test.length);
	}//checked

	 //------------------------------------------------CreateNumber Array Test---------------------------------------------


	 //----------------------------------------------createNumber2D Array test---------------------------------------------------
	 
	 @Test
	 public void createValid2D2x2ArrayTest()
	 {
		 double [][] valid2D = {{1,2},{3,4}};
		 Number [][] test = DataUtilities.createNumberArray2D(valid2D);
		 
		 for(int i=0;i<valid2D.length;i++)
		 {
			 for(int j=0;j<valid2D[i].length;j++)
			 {
				 
				 assertEquals(valid2D[i][j],test[i][j]);
			 }
		 }
		 
	 }
	 @Test
	 public void createValid2D2x1ArrayTest()
	 {
		 double [][] valid2D = {{1,2},{3}};
		 Number [][] test = DataUtilities.createNumberArray2D(valid2D);
		 
		 for(int i=0;i<valid2D.length;i++)
		 {
			 for(int j=0;j<valid2D[i].length;j++)
			 {
				 System.out.println(test[i][j]); 
				 assertEquals(valid2D[i][j],test[i][j]);
			 }
		 }
		 
	 }
	 
	 @Test
	 public void CreateZero2DArrayTest()
	 {
		 double [][] zeroArray = {};
		 Number [][] test = DataUtilities.createNumberArray2D(zeroArray);
		 
		 assertEquals(0, test.length);
	 }
	 	 
	 //--------------------------------------------------getCumulativePercentages Test------------------------------------------
	 
	 @Test	 
	 public void validPositivePercentages()
	 {
		 mockingContextKeyedValues.checking(new Expectations() {
	            {
	                //set length of array
	                allowing(keyedValues).getItemCount();
	                will(returnValue(5));
	                //set keys of the array
	                allowing(keyedValues).getKey(0);
	                will(returnValue(0));
	                allowing(keyedValues).getKey(1);
	                will(returnValue(1));
	                allowing(keyedValues).getKey(2);
	                will(returnValue(2));
	                allowing(keyedValues).getKey(3);
	                will(returnValue(3));
	                allowing(keyedValues).getKey(4);
	                will(returnValue(4));
	                //set values of the array
	                allowing(keyedValues).getValue(0);
	                will(returnValue(Double.valueOf(1)));
	                allowing(keyedValues).getValue(1);
	                will(returnValue(Double.valueOf(2)));
	                allowing(keyedValues).getValue(2);
	                will(returnValue(Double.valueOf(3)));
	                allowing(keyedValues).getValue(3);
	                will(returnValue(Double.valueOf(4)));
	                allowing(keyedValues).getValue(4);
	                will(returnValue(Double.valueOf(6)));
	            }
	        });
	        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);
	        assertEquals(0.0625, result.getValue(0));
	        assertEquals(0.1875, result.getValue(1));
	        assertEquals(0.375, result.getValue(2));
	        assertEquals(0.625, result.getValue(3));
	        assertEquals(1.0, result.getValue(4));
		 
	 }
	 
	 @Test	 
	 public void validPositiveAndZeroPercentages()
	 {
		 mockingContextKeyedValues.checking(new Expectations() {
	            {
	                //set length of array
	                allowing(keyedValues).getItemCount();
	                will(returnValue(5));
	                //set keys of the array
	                allowing(keyedValues).getKey(0);
	                will(returnValue(0));
	                allowing(keyedValues).getKey(1);
	                will(returnValue(1));
	                allowing(keyedValues).getKey(2);
	                will(returnValue(2));
	                allowing(keyedValues).getKey(3);
	                will(returnValue(3));
	                allowing(keyedValues).getKey(4);
	                will(returnValue(4));
	                //set values of the array
	                allowing(keyedValues).getValue(0);
	                will(returnValue(Double.valueOf(1)));
	                allowing(keyedValues).getValue(1);
	                will(returnValue(Double.valueOf(2)));
	                allowing(keyedValues).getValue(2);
	                will(returnValue(Double.valueOf(3)));
	                allowing(keyedValues).getValue(3);
	                will(returnValue(Double.valueOf(4)));
	                allowing(keyedValues).getValue(4);
	                will(returnValue(Double.valueOf(0)));
	            }
	        });
	        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);
	        assertEquals(0.1, result.getValue(0));
	        assertEquals(0.3, result.getValue(1));
	        assertEquals(0.6, result.getValue(2));
	        assertEquals(1.0, result.getValue(3));
	        assertEquals(1.0, result.getValue(4));
		 
	 }

	 @Test	 
	 public void InvalidNegativePercentages()
	 {
		 mockingContextKeyedValues.checking(new Expectations() {
	            {
	                //set length of array
	                allowing(keyedValues).getItemCount();
	                will(returnValue(5));
	                //set keys of the array
	                allowing(keyedValues).getKey(0);
	                will(returnValue(0));
	                allowing(keyedValues).getKey(1);
	                will(returnValue(1));
	                allowing(keyedValues).getKey(2);
	                will(returnValue(2));
	                allowing(keyedValues).getKey(3);
	                will(returnValue(3));
	                allowing(keyedValues).getKey(4);
	                will(returnValue(4));
	                //set values of the array
	                allowing(keyedValues).getValue(0);
	                will(returnValue(Double.valueOf(1)));
	                allowing(keyedValues).getValue(1);
	                will(returnValue(Double.valueOf(2)));
	                allowing(keyedValues).getValue(2);
	                will(returnValue(Double.valueOf(3)));
	                allowing(keyedValues).getValue(3);
	                will(returnValue(Double.valueOf(4)));
	                allowing(keyedValues).getValue(4);
	                will(returnValue(Double.valueOf(-5)));
	            }
	        });
		 try {
	        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);
		 }
		 catch (InvalidParameterException e) {return;}
		 catch (Exception e) {fail();}
		 fail();
	 }
	 
	 @Test	 
	 public void InvalidCharPercentages()
	 {
		 mockingContextKeyedValues.checking(new Expectations() {
	            {
	                //set length of array
	                allowing(keyedValues).getItemCount();
	                will(returnValue(5));
	                //set keys of the array
	                allowing(keyedValues).getKey(0);
	                will(returnValue(0));
	                allowing(keyedValues).getKey(1);
	                will(returnValue(1));
	                allowing(keyedValues).getKey(2);
	                will(returnValue(2));
	                allowing(keyedValues).getKey(3);
	                will(returnValue(3));
	                allowing(keyedValues).getKey(4);
	                will(returnValue(4));
	                //set values of the array
	                allowing(keyedValues).getValue(0);
	                will(returnValue(Double.valueOf(1)));
	                allowing(keyedValues).getValue(1);
	                will(returnValue(Double.valueOf(2)));
	                allowing(keyedValues).getValue(2);
	                will(returnValue(Double.valueOf(3)));
	                allowing(keyedValues).getValue(3);
	                will(returnValue(Double.valueOf(4)));
	                allowing(keyedValues).getValue(4);
	                will(returnValue("a"));
	            }
	        });
		 try {
	        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);
		 }
		 catch (InvalidParameterException e) {return;}
		 catch (Exception e) {fail();}
		 fail();
	 }
	 @Test	 
	 public void BooleanPercentages()
	 {
		 mockingContextKeyedValues.checking(new Expectations() {
	            {
	                //set length of array
	                allowing(keyedValues).getItemCount();
	                will(returnValue(2));
	                //set keys of the array
	                allowing(keyedValues).getKey(0);
	                will(returnValue(0));
	                allowing(keyedValues).getKey(1);
	                will(returnValue(1));

	                //set values of the array
	                allowing(keyedValues).getValue(0);
	                will(returnValue(Double.valueOf(1)));
	                allowing(keyedValues).getValue(1);
	                will(returnValue(true));
	            }
	        });
		 try {
	        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);
		 }
		 catch (InvalidParameterException e) {return;}
		 catch (Exception e) {fail();}
		 fail();
	 }
	 @Test	 
	 public void EmptyPercentages()
	 {
		 mockingContextKeyedValues.checking(new Expectations() {
	            {
	                //set length of array
	                allowing(keyedValues).getItemCount();
	                will(returnValue(0));
	            }
	        });
		 try {
	        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);
		 }
		 catch (InvalidParameterException e) {return;}
		 catch (Exception e) {fail();}
		 fail();
	 }	 
	 @Test	 
	 public void DivisionByZeroPercentages()
	 {
		 mockingContextKeyedValues.checking(new Expectations() {
	            {
	                //set length of array
	                allowing(keyedValues).getItemCount();
	                will(returnValue(1));
	                //set keys of the array
	                allowing(keyedValues).getKey(0);
	                will(returnValue(0));

	                //set values of the array
	                allowing(keyedValues).getValue(0);
	                will(returnValue(Double.valueOf(0)));

	            }
	        });
		 try {
	        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);
		 }
		 catch (Exception e) {return;}
		 fail();
	 }	 
	 
	    @After
	    public void tearDown() {
	        // Clean up resources, close connections, etc.
	        mockingContextKeyedValues.assertIsSatisfied();  // Ensure all expectations were met
	    }
}
