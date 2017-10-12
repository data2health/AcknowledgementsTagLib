package edu.uiowa.AcknowledgementsTagLib.skill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.person.Person;
import edu.uiowa.AcknowledgementsTagLib.technique.Technique;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class Skill extends AcknowledgementsTagLibTagSupport {

	static Skill currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Skill.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int pmcid = 0;
	int personId = 0;
	int techniqueId = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (thePerson!= null)
				parentEntities.addElement(thePerson);
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			if (theTechnique!= null)
				parentEntities.addElement(theTechnique);

			if (thePerson == null) {
			} else {
				personId = thePerson.getID();
			}
			if (theTechnique == null) {
			} else {
				techniqueId = theTechnique.getID();
			}

			SkillIterator theSkillIterator = (SkillIterator)findAncestorWithClass(this, SkillIterator.class);

			if (theSkillIterator != null) {
				pmcid = theSkillIterator.getPmcid();
				personId = theSkillIterator.getPersonId();
				techniqueId = theSkillIterator.getTechniqueId();
			}

			if (theSkillIterator == null && thePerson == null && theTechnique == null && pmcid == 0) {
				// no pmcid was provided - the default is to assume that it is a new Skill and to generate a new pmcid
				pmcid = Sequence.generateID();
				insertEntity();
			} else if (theSkillIterator == null && thePerson != null && theTechnique == null) {
				// an pmcid was provided as an attribute - we need to load a Skill from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,person_id,technique_id from pubmed_central_ack_stanford.skill where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (personId == 0)
						personId = rs.getInt(2);
					if (techniqueId == 0)
						techniqueId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else if (theSkillIterator == null && thePerson == null && theTechnique != null) {
				// an pmcid was provided as an attribute - we need to load a Skill from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,person_id,technique_id from pubmed_central_ack_stanford.skill where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (personId == 0)
						personId = rs.getInt(2);
					if (techniqueId == 0)
						techniqueId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else {
				// an iterator or pmcid was provided as an attribute - we need to load a Skill from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.skill where pmcid = ? and person_id = ? and technique_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,personId);
				stmt.setInt(3,techniqueId);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.skill set where pmcid = ? and person_id = ? and technique_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,personId);
				stmt.setInt(3,techniqueId);
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
				log.debug("generating new Skill " + pmcid);
			}

			if (personId == 0) {
				personId = Sequence.generateID();
				log.debug("generating new Skill " + personId);
			}

			if (techniqueId == 0) {
				techniqueId = Sequence.generateID();
				log.debug("generating new Skill " + techniqueId);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.skill(pmcid,person_id,technique_id) values (?,?,?)");
			stmt.setInt(1,pmcid);
			stmt.setInt(2,personId);
			stmt.setInt(3,techniqueId);
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

	public int getPersonId () {
		return personId;
	}

	public void setPersonId (int personId) {
		this.personId = personId;
	}

	public int getActualPersonId () {
		return personId;
	}

	public int getTechniqueId () {
		return techniqueId;
	}

	public void setTechniqueId (int techniqueId) {
		this.techniqueId = techniqueId;
	}

	public int getActualTechniqueId () {
		return techniqueId;
	}

	public static Integer pmcidValue() throws JspException {
		try {
			return currentInstance.getPmcid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function pmcidValue()");
		}
	}

	public static Integer personIdValue() throws JspException {
		try {
			return currentInstance.getPersonId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function personIdValue()");
		}
	}

	public static Integer techniqueIdValue() throws JspException {
		try {
			return currentInstance.getTechniqueId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function techniqueIdValue()");
		}
	}

	private void clearServiceState () {
		pmcid = 0;
		personId = 0;
		techniqueId = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
