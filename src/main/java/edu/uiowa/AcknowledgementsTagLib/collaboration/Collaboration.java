package edu.uiowa.AcknowledgementsTagLib.collaboration;

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
public class Collaboration extends AcknowledgementsTagLibTagSupport {

	static Collaboration currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Collaboration.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String collaboration = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			CollaborationIterator theCollaborationIterator = (CollaborationIterator)findAncestorWithClass(this, CollaborationIterator.class);

			if (theCollaborationIterator != null) {
				ID = theCollaborationIterator.getID();
			}

			if (theCollaborationIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new Collaboration and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a Collaboration from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select collaboration from pubmed_central_ack_stanford.collaboration where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (collaboration == null)
						collaboration = rs.getString(1);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.collaboration set collaboration = ? where id = ?");
				stmt.setString(1,collaboration);
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
				log.debug("generating new Collaboration " + ID);
			}

			if (collaboration == null)
				collaboration = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.collaboration(id,collaboration) values (?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,collaboration);
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

	public String getCollaboration () {
		if (commitNeeded)
			return "";
		else
			return collaboration;
	}

	public void setCollaboration (String collaboration) {
		this.collaboration = collaboration;
		commitNeeded = true;
	}

	public String getActualCollaboration () {
		return collaboration;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String collaborationValue() throws JspException {
		try {
			return currentInstance.getCollaboration();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function collaborationValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		collaboration = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
