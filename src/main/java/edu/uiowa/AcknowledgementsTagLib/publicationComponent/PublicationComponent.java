package edu.uiowa.AcknowledgementsTagLib.publicationComponent;

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
public class PublicationComponent extends AcknowledgementsTagLibTagSupport {

	static PublicationComponent currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(PublicationComponent.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String publicationComponent = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			PublicationComponentIterator thePublicationComponentIterator = (PublicationComponentIterator)findAncestorWithClass(this, PublicationComponentIterator.class);

			if (thePublicationComponentIterator != null) {
				ID = thePublicationComponentIterator.getID();
			}

			if (thePublicationComponentIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new PublicationComponent and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a PublicationComponent from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select publication_component from pubmed_central_ack_stanford.publication_component where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (publicationComponent == null)
						publicationComponent = rs.getString(1);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.publication_component set publication_component = ? where id = ?");
				stmt.setString(1,publicationComponent);
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
				log.debug("generating new PublicationComponent " + ID);
			}

			if (publicationComponent == null)
				publicationComponent = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.publication_component(id,publication_component) values (?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,publicationComponent);
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

	public String getPublicationComponent () {
		if (commitNeeded)
			return "";
		else
			return publicationComponent;
	}

	public void setPublicationComponent (String publicationComponent) {
		this.publicationComponent = publicationComponent;
		commitNeeded = true;
	}

	public String getActualPublicationComponent () {
		return publicationComponent;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String publicationComponentValue() throws JspException {
		try {
			return currentInstance.getPublicationComponent();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function publicationComponentValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		publicationComponent = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
