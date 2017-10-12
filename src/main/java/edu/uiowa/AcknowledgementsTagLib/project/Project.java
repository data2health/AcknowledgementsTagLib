package edu.uiowa.AcknowledgementsTagLib.project;

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
public class Project extends AcknowledgementsTagLibTagSupport {

	static Project currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Project.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String project = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			ProjectIterator theProjectIterator = (ProjectIterator)findAncestorWithClass(this, ProjectIterator.class);

			if (theProjectIterator != null) {
				ID = theProjectIterator.getID();
			}

			if (theProjectIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new Project and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a Project from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select project from pubmed_central_ack_stanford.project where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (project == null)
						project = rs.getString(1);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.project set project = ? where id = ?");
				stmt.setString(1,project);
				stmt.setInt(2,ID);
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
				log.debug("generating new Project " + ID);
			}

			if (project == null)
				project = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.project(id,project) values (?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,project);
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

	public String getProject () {
		if (commitNeeded)
			return "";
		else
			return project;
	}

	public void setProject (String project) {
		this.project = project;
		commitNeeded = true;
	}

	public String getActualProject () {
		return project;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String projectValue() throws JspException {
		try {
			return currentInstance.getProject();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function projectValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		project = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
