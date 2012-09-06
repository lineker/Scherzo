package SongGenerator;


/**
 * Constraint represented by the property, operator, and value for the constraint.
 * A constraints are used in the following way: x satisfies a constraint if for property,
 * x operator value is true. Example: constraint with property "TITLE", value "abc", operator 
 * CONTAIN
 * is satisfied by x = abcdef but not x = xyz.
 * @author Alicia Bendz
 *
 */
public class Constraint {
	/**
	 * The value of the constraint.
	 */
	private Value mValue;
	/**
	 * The operator of the constraint.
	 */
	private Operator mOperator;
	/**
	 * The property of the constraint.
	 */
	private Property mProperty;
	
	/**
	 * Constructor for the constraint
	 * @param p The property.
	 * @param op The operator.
	 * @param val The value.
	 */
	public Constraint(Property p, Operator op, Value val){
		mValue = val;
		mOperator = op;
		mProperty = p;
	}
	
	/**
	 * Given a value, check if this value satisfies this constraint.
	 * @param val Value to check.
	 * @return If the value satisfies the constraint, return true. Else return false.
	 */
	public boolean satisfies(Value val){
		return mValue.check(val, mOperator);
	}
	
	/**
	 * Getter to return the constraint property.
	 * @return The constraint property.
	 */
	public Property getProperty(){
		return mProperty;
	}

	/**
	 * Getter to return the operator of the constraint.
	 * @return The constraint operator.
	 */
	public Operator getOperator() {
		return mOperator;
	}
	
	/**
	 * To String method to display constraints.
	 * @return The String version of the constraint.
	 */
	@Override
	public String toString(){
		String result = mProperty.toString() + " " + mOperator.toString();
		
		if(mValue.getBoolean() != null){
			result += " " + mValue.getBoolean().toString();
		}
		else if(mValue.getDate() != null){
			result += " " + mValue.getDate().toString();
		}
		else if(mValue.getDouble() != null){
			result += " " + mValue.getDouble().toString();
		}
		else if(mValue.getInteger() != null){
			result += " " + mValue.getInteger().toString();
		}
		else{
			result += " " + mValue.getString();
		}
		
		return result;
	}
}
