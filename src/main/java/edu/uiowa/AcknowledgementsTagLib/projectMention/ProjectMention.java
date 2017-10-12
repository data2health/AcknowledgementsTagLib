package edu.uiowa.AcknowledgementsTagLib.projectMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.project.Project;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class ProjectMention extends AcknowledgementsTagLibTagSupport {

	static ProjectMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(ProjectMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int projectId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Project theProject = (Project)findAncestorWithClass(this, Project.class);
			if (theProject!= null)
				parentEntities.addElement(theProject);

			if (theProject == null) {
			} else {
				projectId = theProject.getID();
			}

			ProjectMentionIterator theProjectMentionIterator = (ProjectMentionIterator)findAncestorWithClass(this, ProjectMentionIterator.class);

			if (theProjectMentionIterator != null) {
				projectId = theProjectMentionIterator.getProjectId();
				pmcid = theProjectMentionIterator.getPmcid();
			}

			if (theProjectMentionIterator == null && theProject == null && projectId == 0) {
				// no projectId was provided - the default is to assume that it is a new ProjectMention and to generate a new projectId
				projectId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or projectId was provided as an attribute - we need to load a ProjectMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.project_mention where project_id = ? and pmcid = ?");
				stmt.setInt(1,projectId);
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
			log.error("JDBC error retrieving projectId " + projectId, e);
			throw new JspTagException("Error: JDBC error retrieving projectId " + projectId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.project_mention set where project_id = ? and pmcid = ?");
				stmt.setInt(1,projectId);
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
			if (projectId == 0) {
				projectId = Sequence.generateID();
				log.debug("generating new ProjectMention " + projectId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new ProjectMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.project_mention(project_id,pmcid) values (?,?)");
			stmt.setInt(1,projectId);
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

	public int getProjectId () {
		return projectId;
	}

	public void setProjectId (int projectId) {
		this.projectId = projectId;
	}

	public int getActualProjectId () {
		return projectId;
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

	public static Integer projectIdValue() throws JspException {
		try {
			return currentInstance.getProjectId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function projectIdValue()");
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
		projectId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
