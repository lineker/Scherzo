package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

public class FeedbackService extends DataAccess {
	
	/**
	 * Default Constructor
	 * @throws ClassNotFoundException
	 */
	public FeedbackService() throws ClassNotFoundException
	{
		super();
	}

	/**
	 * Get all feedbacks
	 * @return collection of Feedbacks
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Collection<Feedback> getFeedbacks() throws Exception
	{
		Collection<Feedback> feedbacks = null;
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("select * from Feedback");
		feedbacks = (Collection<Feedback>) this.ExecuteSelect(prep, new FeedbackMapper(), false,connection);
		
		return feedbacks;
	}
	/**
	 * Get feedback by id
	 * @param id
	 * @return feedback
	 * @throws Exception
	 */
	public Feedback getFeedbackById(int id) throws Exception
	{
		Feedback feedback = null;
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("select * from Feedback where Id ="+id);
		
		feedback = (Feedback) this.ExecuteSelect(prep, new FeedbackMapper(), true,connection);
		
		return feedback;
	}
	
	/**
	 * This method returns a collection of feedback between start date and end date
	 * @param from -> if from == null, assume beginning of time
	 * @param to -> if to == null, assume present date
	 * @return collection of Feedback
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Collection<Feedback> getFeedbackByDateRange(java.util.Date from, java.util.Date to) throws Exception
	{
		Collection<Feedback> feedbacks = null;
		
		if(from == null)
			from = beginDate();
		if(to == null)
			to = new java.util.Date();
		
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("select * from Feedback where date BETWEEN ? AND ?");
		prep.setString(1, dateformatYYYYMMDD.format( from ));
		prep.setString(2, dateformatYYYYMMDD.format( to ));
		feedbacks = (Collection<Feedback>) this.ExecuteSelect(prep, new FeedbackMapper(), false,connection);
		
		return feedbacks;
	}
	
	
	
	/**
	 * Insert new feedback
	 * @param src
	 * @param text
	 * @return id of feedback
	 * @throws Exception
	 */
	public int insertFeedback(String src, String text) throws Exception
	{
		// create a database connection
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("insert into main.Feedback (text,source,date) values " +
											"(?,?,?)", Statement.RETURN_GENERATED_KEYS);
		//"+ feedback.getTitle()+","+feedback.getText()+","+feedback.getSource()+","+feedback.getDate()+"
		
		prep.setString(1, text);
		prep.setString(2, src);
		prep.setString(3, dateformatYYYYMMDD.format( new java.util.Date() ));
		int id = this.ExecuteInsertUpdateDelete(prep,connection);

	    return id;
	}
	
	/**
	 * Delete a feedback 
	 * @param id
	 * @throws Exception
	 */
	public void deleteFeedback(int id) throws Exception
	{
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("DELETE from Feedback where Id ="+id);
		
		this.ExecuteInsertUpdateDelete(prep,connection);

	}
	
	@Deprecated
	/**
	 * You are not supposed to be using this method, since we dont want to change user feedbacks
	 */
	public void updateFeedback(Feedback feedback) throws Exception
	{
		// create a database connection
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("update main.Feedback SET (text=?,source=?,date=?) " +
											"Where id=?");
		//"+ feedback.getTitle()+","+feedback.getText()+","+feedback.getSource()+","+feedback.getDate()+"
		
		prep.setString(1, feedback.getText());
		prep.setString(2, feedback.getSource());
		prep.setString(3, dateformatYYYYMMDD.format( feedback.getDate() ));
		prep.setInt(4, feedback.getId());
		
		this.ExecuteInsertUpdateDelete(prep,connection);

	}
}
