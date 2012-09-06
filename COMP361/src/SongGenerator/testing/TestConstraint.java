package SongGenerator.testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import SongGenerator.Constraint;
import SongGenerator.Operator;
import SongGenerator.Property;
import SongGenerator.Value;

/**
 * Test class for Constraint. Test getters and method that checks if a value satisfies a 
 * constraint.
 * @author Alicia Bendz
 *
 */
public class TestConstraint {
	
	/**
	 * Test get operator. Check that the set operator is returned.
	 */
	@Test
	public void testGetOperator() {
		Constraint c = new Constraint(Property.PLAY_COUNT, Operator.GREATER, new Value(5));
		assertTrue(c.getOperator() == Operator.GREATER);
	}
	
	/**
	 * Test get property. Check that the set property is returned.
	 */
	@Test
	public void testGetProperty() {
		Constraint c = new Constraint(Property.PLAY_COUNT, Operator.GREATER, new Value(5));
		assertTrue(c.getProperty() == Property.PLAY_COUNT);
	}
	
	/**
	 * Test Satisfies. Check that a value satisfying a constraint returns true.
	 * Check values that do not satisfy the constraint return false.
	 */
	@Test
	public void testSatisfies() {
		Constraint c = new Constraint(Property.PLAY_COUNT, Operator.GREATER, new Value(5));
		
		//check correct value
		assertTrue(c.satisfies(new Value(6)));
		
		//check incorrect values of the same type
		assertFalse(c.satisfies(new Value(5)));
		assertFalse(c.satisfies(new Value(4)));
		
		//check values of the wrong type
		assertFalse(c.satisfies(new Value("six")));
	}
}
