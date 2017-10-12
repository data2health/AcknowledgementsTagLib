package edu.uiowa.AcknowledgementsTagLib.person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class Person extends AcknowledgementsTagLibTagSupport {

	static Person currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Person.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String firstName = null;
	String middleName = null;
	String lastName = null;
	String title = null;
	String appendix = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			PersonIterator thePersonIterator = (PersonIterator)findAncestorWithClass(this, PersonIterator.class);

			if (thePersonIterator != null) {
				ID = thePersonIterator.getID();
			}

			if (thePersonIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new Person and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a Person from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select first_name,middle_name,last_name,title,appendix from pubmed_central_ack_stanford.person where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (firstName == null)
						firstName = rs.getString(1);
					if (middleName == null)
						middleName = rs.getString(2);
					if (lastName == null)
						lastName = rs.getString(3);
					if (title == null)
						title = rs.getString(4);
					if (appendix == null)
						appendix = rs.getString(5);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			log.error("JDBC error retrieving ID " + ID, e);
			throw new JspTagException("Error: JDBC error retrieving ID " + ID);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.person set first_name = ?, middle_name = ?, last_name = ?, title = ?, appendix = ? where id = ?");
				stmt.setString(1,firstName);
				stmt.setString(2,middleName);
				stmt.setString(3,lastName);
				stmt.setString(4,title);
				stmt.setString(5,appendix);
				stmt.setInt(6,ID);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			clearServiceState();
			freeConnection();
		}
		return super.doEndTag();
	}

	public void insertEntity() throws JspException {
		try {
			if (ID == 0) {
				ID = Sequence.generateID();
				log.debug("generating new Person " + ID);
			}

			if (firstName == null)
				firstName = "";
			if (middleName == null)
				middleName = "";
			if (lastName == null)
				lastName = "";
			if (title == null)
				title = "";
			if (appendix == null)
				appendix = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.person(id,first_name,middle_name,last_name,title,appendix) values (?,?,?,?,?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,firstName);
			stmt.setString(3,middleName);
			stmt.setString(4,lastName);
			stmt.setString(5,title);
			stmt.setString(6,appendix);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
	}

	public int getID () {
		return ID;
	}

	public void setID (int ID) {
		this.ID = ID;
	}

	public int getActualID () {
		return ID;
	}

	public String getFirstName () {
		if (commitNeeded)
			return "";
		else
			return firstName;
	}

	public void setFirstName (String firstName) {
		this.firstName = firstName;
		commitNeeded = true;
	}

	public String getActualFirstName () {
		return firstName;
	}

	public String getMiddleName () {
		if (commitNeeded)
			return "";
		else
			return middleName;
	}

	public void setMiddleName (String middleName) {
		this.middleName = middleName;
		commitNeeded = true;
	}

	public String getActualMiddleName () {
		return middleName;
	}

	public String getLastName () {
		if (commitNeeded)
			return "";
		else
			return lastName;
	}

	public void setLastName (String lastName) {
		this.lastName = lastName;
		commitNeeded = true;
	}

	public String getActualLastName () {
		return lastName;
	}

	public String getTitle () {
		if (commitNeeded)
			return "";
		else
			return title;
	}

	public void setTitle (String title) {
		this.title = title;
		commitNeeded = true;
	}

	public String getActualTitle () {
		return title;
	}

	public String getAppendix () {
		if (commitNeeded)
			return "";
		else
			return appendix;
	}

	public void setAppendix (String appendix) {
		this.appendix = appendix;
		commitNeeded = true;
	}

	public String getActualAppendix () {
		return appendix;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String firstNameValue() throws JspException {
		try {
			return currentInstance.getFirstName();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function firstNameValue()");
		}
	}

	public static String middleNameValue() throws JspException {
		try {
			return currentInstance.getMiddleName();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function middleNameValue()");
		}
	}

	public static String lastNameValue() throws JspException {
		try {
			return currentInstance.getLastName();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function lastNameValue()");
		}
	}

	public static String titleValue() throws JspException {
		try {
			return currentInstance.getTitle();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function titleValue()");
		}
	}

	public static String appendixValue() throws JspException {
		try {
			return currentInstance.getAppendix();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function appendixValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		firstName = null;
		middleName = null;
		lastName = null;
		title = null;
		appendix = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
