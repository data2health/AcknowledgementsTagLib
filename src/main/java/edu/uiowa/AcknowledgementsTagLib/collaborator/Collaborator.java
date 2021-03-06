package edu.uiowa.AcknowledgementsTagLib.collaborator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.collaboration.Collaboration;
import edu.uiowa.AcknowledgementsTagLib.person.Person;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class Collaborator extends AcknowledgementsTagLibTagSupport {

	static Collaborator currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Collaborator.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int pmcid = 0;
	int collaborationId = 0;
	int personId = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
			if (theCollaboration!= null)
				parentEntities.addElement(theCollaboration);
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (thePerson!= null)
				parentEntities.addElement(thePerson);

			if (theCollaboration == null) {
			} else {
				collaborationId = theCollaboration.getID();
			}
			if (thePerson == null) {
			} else {
				personId = thePerson.getID();
			}

			CollaboratorIterator theCollaboratorIterator = (CollaboratorIterator)findAncestorWithClass(this, CollaboratorIterator.class);

			if (theCollaboratorIterator != null) {
				pmcid = theCollaboratorIterator.getPmcid();
				collaborationId = theCollaboratorIterator.getCollaborationId();
				personId = theCollaboratorIterator.getPersonId();
			}

			if (theCollaboratorIterator == null && theCollaboration == null && thePerson == null && pmcid == 0) {
				// no pmcid was provided - the default is to assume that it is a new Collaborator and to generate a new pmcid
				pmcid = Sequence.generateID();
				insertEntity();
			} else if (theCollaboratorIterator == null && theCollaboration != null && thePerson == null) {
				// an pmcid was provided as an attribute - we need to load a Collaborator from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,collaboration_id,person_id from pubmed_central_ack_stanford.collaborator where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (collaborationId == 0)
						collaborationId = rs.getInt(2);
					if (personId == 0)
						personId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else if (theCollaboratorIterator == null && theCollaboration == null && thePerson != null) {
				// an pmcid was provided as an attribute - we need to load a Collaborator from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,collaboration_id,person_id from pubmed_central_ack_stanford.collaborator where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (collaborationId == 0)
						collaborationId = rs.getInt(2);
					if (personId == 0)
						personId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else {
				// an iterator or pmcid was provided as an attribute - we need to load a Collaborator from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.collaborator where pmcid = ? and collaboration_id = ? and person_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,collaborationId);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.collaborator set where pmcid = ? and collaboration_id = ? and person_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,collaborationId);
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
				log.debug("generating new Collaborator " + pmcid);
			}

			if (collaborationId == 0) {
				collaborationId = Sequence.generateID();
				log.debug("generating new Collaborator " + collaborationId);
			}

			if (personId == 0) {
				personId = Sequence.generateID();
				log.debug("generating new Collaborator " + personId);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.collaborator(pmcid,collaboration_id,person_id) values (?,?,?)");
			stmt.setInt(1,pmcid);
			stmt.setInt(2,collaborationId);
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

	public int getCollaborationId () {
		return collaborationId;
	}

	public void setCollaborationId (int collaborationId) {
		this.collaborationId = collaborationId;
	}

	public int getActualCollaborationId () {
		return collaborationId;
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

	public static Integer collaborationIdValue() throws JspException {
		try {
			return currentInstance.getCollaborationId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function collaborationIdValue()");
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
		collaborationId = 0;
		personId = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
