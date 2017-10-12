package edu.uiowa.AcknowledgementsTagLib.discipline;

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
public class Discipline extends AcknowledgementsTagLibTagSupport {

	static Discipline currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Discipline.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String discipline = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			DisciplineIterator theDisciplineIterator = (DisciplineIterator)findAncestorWithClass(this, DisciplineIterator.class);

			if (theDisciplineIterator != null) {
				ID = theDisciplineIterator.getID();
			}

			if (theDisciplineIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new Discipline and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a Discipline from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select discipline from pubmed_central_ack_stanford.discipline where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (discipline == null)
						discipline = rs.getString(1);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.discipline set discipline = ? where id = ?");
				stmt.setString(1,discipline);
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
				log.debug("generating new Discipline " + ID);
			}

			if (discipline == null)
				discipline = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.discipline(id,discipline) values (?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,discipline);
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

	public String getDiscipline () {
		if (commitNeeded)
			return "";
		else
			return discipline;
	}

	public void setDiscipline (String discipline) {
		this.discipline = discipline;
		commitNeeded = true;
	}

	public String getActualDiscipline () {
		return discipline;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String disciplineValue() throws JspException {
		try {
			return currentInstance.getDiscipline();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function disciplineValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		discipline = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
