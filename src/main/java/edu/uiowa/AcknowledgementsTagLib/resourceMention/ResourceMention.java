package edu.uiowa.AcknowledgementsTagLib.resourceMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.resource.Resource;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class ResourceMention extends AcknowledgementsTagLibTagSupport {

	static ResourceMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(ResourceMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int resourceId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			if (theResource!= null)
				parentEntities.addElement(theResource);

			if (theResource == null) {
			} else {
				resourceId = theResource.getID();
			}

			ResourceMentionIterator theResourceMentionIterator = (ResourceMentionIterator)findAncestorWithClass(this, ResourceMentionIterator.class);

			if (theResourceMentionIterator != null) {
				resourceId = theResourceMentionIterator.getResourceId();
				pmcid = theResourceMentionIterator.getPmcid();
			}

			if (theResourceMentionIterator == null && theResource == null && resourceId == 0) {
				// no resourceId was provided - the default is to assume that it is a new ResourceMention and to generate a new resourceId
				resourceId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or resourceId was provided as an attribute - we need to load a ResourceMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.resource_mention where resource_id = ? and pmcid = ?");
				stmt.setInt(1,resourceId);
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
			log.error("JDBC error retrieving resourceId " + resourceId, e);
			throw new JspTagException("Error: JDBC error retrieving resourceId " + resourceId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.resource_mention set where resource_id = ? and pmcid = ?");
				stmt.setInt(1,resourceId);
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
			if (resourceId == 0) {
				resourceId = Sequence.generateID();
				log.debug("generating new ResourceMention " + resourceId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new ResourceMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.resource_mention(resource_id,pmcid) values (?,?)");
			stmt.setInt(1,resourceId);
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

	public int getResourceId () {
		return resourceId;
	}

	public void setResourceId (int resourceId) {
		this.resourceId = resourceId;
	}

	public int getActualResourceId () {
		return resourceId;
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

	public static Integer resourceIdValue() throws JspException {
		try {
			return currentInstance.getResourceId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function resourceIdValue()");
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
		resourceId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
