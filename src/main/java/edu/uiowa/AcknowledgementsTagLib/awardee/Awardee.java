package edu.uiowa.AcknowledgementsTagLib.awardee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.award.Award;
import edu.uiowa.AcknowledgementsTagLib.person.Person;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class Awardee extends AcknowledgementsTagLibTagSupport {

	static Awardee currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Awardee.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int pmcid = 0;
	int awardId = 0;
	int personId = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			if (theAward!= null)
				parentEntities.addElement(theAward);
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (thePerson!= null)
				parentEntities.addElement(thePerson);

			if (theAward == null) {
			} else {
				awardId = theAward.getID();
			}
			if (thePerson == null) {
			} else {
				personId = thePerson.getID();
			}

			AwardeeIterator theAwardeeIterator = (AwardeeIterator)findAncestorWithClass(this, AwardeeIterator.class);

			if (theAwardeeIterator != null) {
				pmcid = theAwardeeIterator.getPmcid();
				awardId = theAwardeeIterator.getAwardId();
				personId = theAwardeeIterator.getPersonId();
			}

			if (theAwardeeIterator == null && theAward == null && thePerson == null && pmcid == 0) {
				// no pmcid was provided - the default is to assume that it is a new Awardee and to generate a new pmcid
				pmcid = Sequence.generateID();
				insertEntity();
			} else if (theAwardeeIterator == null && theAward != null && thePerson == null) {
				// an pmcid was provided as an attribute - we need to load a Awardee from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,award_id,person_id from pubmed_central_ack_stanford.awardee where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (awardId == 0)
						awardId = rs.getInt(2);
					if (personId == 0)
						personId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else if (theAwardeeIterator == null && theAward == null && thePerson != null) {
				// an pmcid was provided as an attribute - we need to load a Awardee from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,award_id,person_id from pubmed_central_ack_stanford.awardee where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (awardId == 0)
						awardId = rs.getInt(2);
					if (personId == 0)
						personId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else {
				// an iterator or pmcid was provided as an attribute - we need to load a Awardee from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.awardee where pmcid = ? and award_id = ? and person_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,awardId);
				stmt.setInt(3,personId);
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
			log.error("JDBC error retrieving pmcid " + pmcid, e);
			throw new JspTagException("Error: JDBC error retrieving pmcid " + pmcid);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.awardee set where pmcid = ? and award_id = ? and person_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,awardId);
				stmt.setInt(3,personId);
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
			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new Awardee " + pmcid);
			}

			if (awardId == 0) {
				awardId = Sequence.generateID();
				log.debug("generating new Awardee " + awardId);
			}

			if (personId == 0) {
				personId = Sequence.generateID();
				log.debug("generating new Awardee " + personId);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.awardee(pmcid,award_id,person_id) values (?,?,?)");
			stmt.setInt(1,pmcid);
			stmt.setInt(2,awardId);
			stmt.setInt(3,personId);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
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

	public int getAwardId () {
		return awardId;
	}

	public void setAwardId (int awardId) {
		this.awardId = awardId;
	}

	public int getActualAwardId () {
		return awardId;
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

	public static Integer pmcidValue() throws JspException {
		try {
			return currentInstance.getPmcid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function pmcidValue()");
		}
	}

	public static Integer awardIdValue() throws JspException {
		try {
			return currentInstance.getAwardId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function awardIdValue()");
		}
	}

	public static Integer personIdValue() throws JspException {
		try {
			return currentInstance.getPersonId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function personIdValue()");
		}
	}

	private void clearServiceState () {
		pmcid = 0;
		awardId = 0;
		personId = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
