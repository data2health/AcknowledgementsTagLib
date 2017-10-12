package edu.uiowa.AcknowledgementsTagLib.disciplineMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.discipline.Discipline;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class DisciplineMention extends AcknowledgementsTagLibTagSupport {

	static DisciplineMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(DisciplineMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int disciplineId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Discipline theDiscipline = (Discipline)findAncestorWithClass(this, Discipline.class);
			if (theDiscipline!= null)
				parentEntities.addElement(theDiscipline);

			if (theDiscipline == null) {
			} else {
				disciplineId = theDiscipline.getID();
			}

			DisciplineMentionIterator theDisciplineMentionIterator = (DisciplineMentionIterator)findAncestorWithClass(this, DisciplineMentionIterator.class);

			if (theDisciplineMentionIterator != null) {
				disciplineId = theDisciplineMentionIterator.getDisciplineId();
				pmcid = theDisciplineMentionIterator.getPmcid();
			}

			if (theDisciplineMentionIterator == null && theDiscipline == null && disciplineId == 0) {
				// no disciplineId was provided - the default is to assume that it is a new DisciplineMention and to generate a new disciplineId
				disciplineId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or disciplineId was provided as an attribute - we need to load a DisciplineMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.discipline_mention where discipline_id = ? and pmcid = ?");
				stmt.setInt(1,disciplineId);
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
			log.error("JDBC error retrieving disciplineId " + disciplineId, e);
			throw new JspTagException("Error: JDBC error retrieving disciplineId " + disciplineId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.discipline_mention set where discipline_id = ? and pmcid = ?");
				stmt.setInt(1,disciplineId);
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
			if (disciplineId == 0) {
				disciplineId = Sequence.generateID();
				log.debug("generating new DisciplineMention " + disciplineId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new DisciplineMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.discipline_mention(discipline_id,pmcid) values (?,?)");
			stmt.setInt(1,disciplineId);
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

	public int getDisciplineId () {
		return disciplineId;
	}

	public void setDisciplineId (int disciplineId) {
		this.disciplineId = disciplineId;
	}

	public int getActualDisciplineId () {
		return disciplineId;
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

	public static Integer disciplineIdValue() throws JspException {
		try {
			return currentInstance.getDisciplineId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function disciplineIdValue()");
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
		disciplineId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
