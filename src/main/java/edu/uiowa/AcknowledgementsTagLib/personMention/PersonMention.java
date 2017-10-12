package edu.uiowa.AcknowledgementsTagLib.personMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.person.Person;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class PersonMention extends AcknowledgementsTagLibTagSupport {

	static PersonMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(PersonMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int personId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (thePerson!= null)
				parentEntities.addElement(thePerson);

			if (thePerson == null) {
			} else {
				personId = thePerson.getID();
			}

			PersonMentionIterator thePersonMentionIterator = (PersonMentionIterator)findAncestorWithClass(this, PersonMentionIterator.class);

			if (thePersonMentionIterator != null) {
				personId = thePersonMentionIterator.getPersonId();
				pmcid = thePersonMentionIterator.getPmcid();
			}

			if (thePersonMentionIterator == null && thePerson == null && personId == 0) {
				// no personId was provided - the default is to assume that it is a new PersonMention and to generate a new personId
				personId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or personId was provided as an attribute - we need to load a PersonMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.person_mention where person_id = ? and pmcid = ?");
				stmt.setInt(1,personId);
				stmt.setInt(2,pmcid);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			log.error("JDBC error retrieving personId " + personId, e);
			throw new JspTagException("Error: JDBC error retrieving personId " + personId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.person_mention set where person_id = ? and pmcid = ?");
				stmt.setInt(1,personId);
				stmt.setInt(2,pmcid);
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
			if (personId == 0) {
				personId = Sequence.generateID();
				log.debug("generating new PersonMention " + personId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new PersonMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.person_mention(person_id,pmcid) values (?,?)");
			stmt.setInt(1,personId);
			stmt.setInt(2,pmcid);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
	}

	public int getPersonId () {
		return personId;
	}

	public void setPersonId (int personId) {
		this.personId = personId;
	}

	public int getActualPersonId () {
		return personId;
	}

	public int getPmcid () {
		return pmcid;
	}

	public void setPmcid (int pmcid) {
		this.pmcid = pmcid;
	}

	public int getActualPmcid () {
		return pmcid;
	}

	public static Integer personIdValue() throws JspException {
		try {
			return currentInstance.getPersonId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function personIdValue()");
		}
	}

	public static Integer pmcidValue() throws JspException {
		try {
			return currentInstance.getPmcid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function pmcidValue()");
		}
	}

	private void clearServiceState () {
		personId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
