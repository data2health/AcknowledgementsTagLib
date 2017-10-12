package edu.uiowa.AcknowledgementsTagLib.award;

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
public class Award extends AcknowledgementsTagLibTagSupport {

	static Award currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Award.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String award = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			AwardIterator theAwardIterator = (AwardIterator)findAncestorWithClass(this, AwardIterator.class);

			if (theAwardIterator != null) {
				ID = theAwardIterator.getID();
			}

			if (theAwardIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new Award and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a Award from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select award from pubmed_central_ack_stanford.award where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (award == null)
						award = rs.getString(1);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.award set award = ? where id = ?");
				stmt.setString(1,award);
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
				log.debug("generating new Award " + ID);
			}

			if (award == null)
				award = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.award(id,award) values (?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,award);
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

	public String getAward () {
		if (commitNeeded)
			return "";
		else
			return award;
	}

	public void setAward (String award) {
		this.award = award;
		commitNeeded = true;
	}

	public String getActualAward () {
		return award;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String awardValue() throws JspException {
		try {
			return currentInstance.getAward();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function awardValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		award = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
