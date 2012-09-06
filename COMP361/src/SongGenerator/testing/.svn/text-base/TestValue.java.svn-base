package SongGenerator.testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.Test;

import SongGenerator.Operator;
import SongGenerator.Value;
import SongGenerator.Value.Type;

/**
 * Test class for the Value class. Tests all basic functions and constructors for each 
 * type of value.
 * @author Alicia Bendz
 *
 */
public class TestValue {

	/**
	 * Test that the default constructor sets all values to null.
	 */
	@Test
	public void testValue() {
		Value v = new Value();
		assertNull(v.getBoolean());
		assertNull(v.getDate());
		assertNull(v.getDouble());
		assertNull(v.getInteger());
		assertNull(v.getString());
	}

	@Test
	public void testValueDate() {
		//create value
		DateTime dt = new DateTime();
		Value v = new Value(dt);
				
		//check that date is not null and other values are
		assertNull(v.getBoolean());
		assertNotNull(v.getDate());
		assertNull(v.getDouble());
		assertNull(v.getInteger());
		assertNull(v.getString());
				
		//check the value stored and the type
		assertTrue(dt.equals(v.getDate()));
		assertTrue(v.isType(Type.DATE));
	}

	/**
	 * Test the Integer constructor by checking all values but the Integer value are null and the type is correct.
	 */
	@Test
	public void testValueInteger() {
		//create value
		Value v = new Value(34);
		
		//check that integer is not null and other values are
		assertNull(v.getBoolean());
		assertNull(v.getDate());
		assertNull(v.getDouble());
		assertNotNull(v.getInteger());
		assertNull(v.getString());
		
		//check the value stored and the type
		assertTrue(v.getInteger().intValue() == 34);
		assertTrue(v.isType(Type.INTEGER));
	}

	/**
	 * Test the Double constructor by checking all values but the Double value are null 
	 * and the type is correct.
	 */
	@Test
	public void testValueDouble() {
		//create the double value
		Value v = new Value(3.14);
		
		//check that the double value is not null and other values are
		assertNull(v.getBoolean());
		assertNull(v.getDate());
		assertNotNull(v.getDouble());
		assertNull(v.getInteger());
		assertNull(v.getString());
		
		//check the value stored and the type
		assertTrue(v.getDouble().doubleValue() == 3.14);
		assertTrue(v.isType(Type.DOUBLE));
	}

	/**
	 * Test the String constructor by checking all the values but the String value are 
	 * correct and the type is correct.
	 */
	@Test
	public void testValueString() {
		//create the String value
		Value v = new Value("Hello World");
		
		//check that the String value is not null and the other values are
		assertNull(v.getBoolean());
		assertNull(v.getDate());
		assertNull(v.getDouble());
		assertNull(v.getInteger());
		assertNotNull(v.getString());
		
		//check the value stored and the type
		assertTrue(v.getString().equals("Hello World"));
		assertTrue(v.isType(Type.STRING));
	}

	/**
	 * Test the Boolean constructor by checking all the values but the Boolean value are 
	 * correct and the type is correct.
	 */
	@Test
	public void testValueBoolean() {
		//create the Boolean Value
		Value v = new Value(true);
		
		//check that the Boolean value is not null and the other values are
		assertNotNull(v.getBoolean());
		assertNull(v.getDate());
		assertNull(v.getDouble());
		assertNull(v.getInteger());
		assertNull(v.getString());
		
		//check the value stored and the type
		assertTrue(v.getBoolean().booleanValue());
		assertTrue(v.isType(Type.BOOLEAN));
	}

	/**
	 * Test the greater than method for Integers, Doubles, Dates, and Strings.
	 * Test the cases where a value is equal, less than, and greater than the target value.
	 * @throws InterruptedException 
	 */
	@Test
	public void testGreaterThan() throws InterruptedException {
		Value a, b;
		
		//Tests for integers.
		a = new Value(2);
		b = new Value(3);
		
		assertFalse(a.greaterThan(b));
		assertTrue(b.greaterThan(a));
		assertFalse(a.greaterThan(a));
		
		//Tests for doubles.
		a = new Value(2.1);
		b = new Value(3.2);
		
		assertFalse(a.greaterThan(b));
		assertTrue(b.greaterThan(a));
		assertFalse(a.greaterThan(a));
		
		//Tests for dates
		a = new Value(new DateTime());
		Thread.sleep(5000);
		b = new Value(new DateTime());
		
		assertFalse(a.greaterThan(b));
		assertTrue(b.greaterThan(a));
		assertFalse(a.greaterThan(a));
		
		//Tests for Strings.
		a = new Value("bat");
		b = new Value("rat");
		
		assertFalse(a.greaterThan(b));
		assertTrue(b.greaterThan(a));
		assertFalse(a.greaterThan(a));
	}

	/**
	 * Test the less than method for Integers, Doubles, Dates, and Strings.
	 * Test the cases where a value is equal, less than, and greater than the target value.
	 * @throws InterruptedException 
	 */
	@Test
	public void testLessThan() throws InterruptedException {
		Value a, b;
		
		//Test for Integers.
		a = new Value(2);
		b = new Value(3);
		
		assertTrue(a.lessThan(b));
		assertFalse(b.lessThan(a));
		assertFalse(a.lessThan(a));
		
		//Test for Doubles.
		a = new Value(2.1);
		b = new Value(3.2);
		
		assertTrue(a.lessThan(b));
		assertFalse(b.lessThan(a));
		assertFalse(a.lessThan(a));
		
		//Tests for Dates.
		a = new Value(new DateTime());
		Thread.sleep(5000);
		b = new Value(new DateTime());
		
		assertTrue(a.lessThan(b));
		assertFalse(b.lessThan(a));
		assertFalse(a.lessThan(a));
		
		//Test for Strings.
		a = new Value("bat");
		b = new Value("rat");
		
		assertTrue(a.lessThan(b));
		assertFalse(b.lessThan(a));
		assertFalse(a.lessThan(a));
	}

	/**
	 * Test the equals method for Integers, Doubles, Dates, Strings, and Booleans.
	 * Test the cases where values are equal, greater, and less than the target value.
	 * @throws InterruptedException 
	 */
	@Test
	public void testEqual() throws InterruptedException {
		Value a, b;
		
		//Integer tests
		a = new Value(2);
		b = new Value(3);
		
		assertTrue(a.equal(a));
		assertFalse(a.equal(b));
		assertTrue(a.equal(new Value(2)));
		
		//Double tests
		a = new Value(2.1);
		b = new Value(3.2);
		
		assertTrue(a.equal(a));
		assertFalse(a.equal(b));
		assertTrue(a.equal(new Value(2.1)));
		
		//Test dates
		a = new Value(new DateTime());
		Thread.sleep(5000);
		b = new Value(new DateTime());
		
		assertFalse(a.equals(b));
		assertFalse(b.equals(a));
		assertTrue(a.equals(a));
		
		
		//String tests
		a = new Value("cat");
		b = new Value("bat");
		
		assertTrue(a.equal(a));
		assertFalse(a.equal(b));
		assertTrue(a.equal(new Value("cat")));
		
		//Boolean tests
		a = new Value(true);
		b = new Value(false);
		
		assertTrue(a.equal(a));
		assertFalse(a.equal(b));
		assertTrue(a.equal(new Value(true)));
	}
	/**
	 * Test the contains method for String values.
	 * Test the cases where one string contains another and where one string does not 
	 * contain another.
	 * Also tests case sensitivity.
	 */
	@Test
	public void testContains() {
		Value a, b;
		
		//create strings 
		a = new Value("at");
		b = new Value("cat");
		
		//containing tests
		assertTrue(b.contains(a));
		assertFalse(a.contains(b));
		
		//case sensitivity tests
		b = new Value("CAT");
		
		assertFalse(b.contains(a));
		
		a = new Value("AT");
		
		assertTrue(b.contains(a));
		assertFalse(a.contains(b));
	}

	/**
	 * Test that values created of a certain type correctly return the type that they are.
	 * Test all types.
	 */
	@Test
	public void testIsType() {
		//Test Integers
		Value v = new Value(34);
		assertTrue(v.isType(Type.INTEGER));
		assertFalse(v.isType(Type.DOUBLE));
		assertFalse(v.isType(Type.STRING));
		assertFalse(v.isType(Type.BOOLEAN));
		assertFalse(v.isType(Type.DATE));
		
		//Test Doubles
		v = new Value(3.14);
		assertTrue(v.isType(Type.DOUBLE));
		assertFalse(v.isType(Type.INTEGER));
		assertFalse(v.isType(Type.STRING));
		assertFalse(v.isType(Type.BOOLEAN));
		assertFalse(v.isType(Type.DATE));
		
		//Date test
		v = new Value(new DateTime());
		assertFalse(v.isType(Type.DOUBLE));
		assertFalse(v.isType(Type.INTEGER));
		assertFalse(v.isType(Type.STRING));
		assertFalse(v.isType(Type.BOOLEAN));
		assertTrue(v.isType(Type.DATE));
		
		//Test Strings
		v = new Value("Hello World");
		assertTrue(v.isType(Type.STRING));
		assertFalse(v.isType(Type.DOUBLE));
		assertFalse(v.isType(Type.INTEGER));
		assertFalse(v.isType(Type.BOOLEAN));
		assertFalse(v.isType(Type.DATE));
		
		//Test Boolean
		v = new Value(true);
		assertTrue(v.isType(Type.BOOLEAN));
		assertFalse(v.isType(Type.DOUBLE));
		assertFalse(v.isType(Type.INTEGER));
		assertFalse(v.isType(Type.STRING));
		assertFalse(v.isType(Type.DATE));		
	}

	/**
	 * Test getter for Date values.
	 */
	@Test
	public void testGetDate() {
		DateTime dt = new DateTime();
		Value v = new Value(dt);
		
		assertTrue(v.getDate().equals(dt));
	}

	/**
	 * Test the getter for the Integer value. Test that a constructed Value returns the 
	 * correct result.
	 */
	@Test
	public void testGetInteger() {
		Value v = new Value(34);
		
		assertTrue(v.getInteger().intValue() == 34);
	}

	/**
	 * Test the getter for the Double value. Test that a constructed Value returns the
	 *  correct result.
	 */
	@Test
	public void testGetDouble() {
		Value v = new Value(3.14);
		
		assertTrue(v.getDouble().doubleValue() == 3.14);
	}

	/**
	 * Test the getter for the String value. Test that a constructed Value returns the 
	 * correct result.
	 */
	@Test
	public void testGetString() {
		Value v = new Value("Hello World");
		
		assertTrue(v.getString().equals("Hello World"));
	}

	/**
	 * Test the getter for the Boolean value. Test that a constructed Value returns the 
	 * correct result.
	 */
	@Test
	public void testGetBoolean() {
		Value v = new Value(true);
		
		assertTrue(v.getBoolean().booleanValue());
	}

	/**
	 * The check method for Values.
	 * Tests for each operator that applies to each type a value could be.
	 * Test values that differ and values that are equal.
	 * @throws InterruptedException 
	 */
	@Test
	public void testCheck() throws InterruptedException {
		Value a, b;
		
		//test for unequal Integers
		a = new Value(1);
		b = new Value(2);
		
		assertTrue(a.check(b, Operator.GREATER));
		assertFalse(a.check(b, Operator.LESS));
		assertFalse(a.check(b, Operator.EQUALS));
		assertFalse(a.check(b, Operator.LESS_EQUAL));
		assertTrue(a.check(b, Operator.GREATER_EQUAL));
		assertTrue(a.check(b, Operator.NOT_EQUAL));
		
		//test for equal Integers
		a = new Value(1);
		b = new Value(1);
		
		assertFalse(a.check(b, Operator.GREATER));
		assertFalse(a.check(b, Operator.LESS));
		assertTrue(a.check(b, Operator.EQUALS));
		assertTrue(a.check(b, Operator.LESS_EQUAL));
		assertTrue(a.check(b, Operator.GREATER_EQUAL));
		assertFalse(a.check(b, Operator.NOT_EQUAL));
		
		//test for unequal Doubles
		a = new Value(1.1);
		b = new Value(2.2);
		
		assertTrue(a.check(b, Operator.GREATER));
		assertFalse(a.check(b, Operator.LESS));
		assertFalse(a.check(b, Operator.EQUALS));
		assertFalse(a.check(b, Operator.LESS_EQUAL));
		assertTrue(a.check(b, Operator.GREATER_EQUAL));
		assertTrue(a.check(b, Operator.NOT_EQUAL));
		
		//test for equal Doubles
		a = new Value(1.1);
		b = new Value(1.1);
		
		assertFalse(a.check(b, Operator.GREATER));
		assertFalse(a.check(b, Operator.LESS));
		assertTrue(a.check(b, Operator.EQUALS));
		assertTrue(a.check(b, Operator.LESS_EQUAL));
		assertTrue(a.check(b, Operator.GREATER_EQUAL));
		assertFalse(a.check(b, Operator.NOT_EQUAL));
		
		//test for unequal Strings
		a = new Value("cat");
		b = new Value("dog");
		
		assertTrue(a.check(b, Operator.GREATER));
		assertFalse(a.check(b, Operator.LESS));
		assertFalse(a.check(b, Operator.EQUALS));
		assertFalse(a.check(b, Operator.LESS_EQUAL));
		assertTrue(a.check(b, Operator.GREATER_EQUAL));
		assertTrue(a.check(b, Operator.NOT_EQUAL));
		assertTrue(a.check(b, Operator.NOT_CONTAINS));
		assertFalse(a.check(b, Operator.CONTAINS));
		
		//test for equal Strings
		a = new Value("cat");
		b = new Value("cat");
		
		assertFalse(a.check(b, Operator.GREATER));
		assertFalse(a.check(b, Operator.LESS));
		assertTrue(a.check(b, Operator.EQUALS));
		assertTrue(a.check(b, Operator.LESS_EQUAL));
		assertTrue(a.check(b, Operator.GREATER_EQUAL));
		assertFalse(a.check(b, Operator.NOT_EQUAL));
		assertFalse(a.check(b, Operator.NOT_CONTAINS));
		assertTrue(a.check(b, Operator.CONTAINS));
		
		//test for unequal Booleans
		a = new Value(true);
		b = new Value(false);
		
		assertFalse(a.check(b, Operator.EQUALS));
		assertTrue(a.check(b, Operator.NOT_EQUAL));
		
		//test for equal Booleans
		a = new Value(true);
		b = new Value(true);
		
		assertTrue(a.check(b, Operator.EQUALS));
		assertFalse(a.check(b, Operator.NOT_EQUAL));
		
		//test for unequal Dates
		a = new Value(new DateTime());
		Thread.sleep(5000);
		b = new Value(new DateTime());
		
		assertTrue(a.check(b, Operator.GREATER));
		assertFalse(a.check(b, Operator.LESS));
		assertFalse(a.check(b, Operator.EQUALS));
		assertFalse(a.check(b, Operator.LESS_EQUAL));
		assertTrue(a.check(b, Operator.GREATER_EQUAL));
		assertTrue(a.check(b, Operator.NOT_EQUAL));
		
		//test for equal Dates
		DateTime dt = new DateTime();
		a = new Value(dt);
		b = new Value(dt);
		
		assertFalse(a.check(b, Operator.GREATER));
		assertFalse(a.check(b, Operator.LESS));
		assertTrue(a.check(b, Operator.EQUALS));
		assertTrue(a.check(b, Operator.LESS_EQUAL));
		assertTrue(a.check(b, Operator.GREATER_EQUAL));
		assertFalse(a.check(b, Operator.NOT_EQUAL));
		
	}

}
