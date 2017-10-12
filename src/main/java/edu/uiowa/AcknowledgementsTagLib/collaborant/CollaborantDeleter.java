package edu.uiowa.AcknowledgementsTagLib.collaborant;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibBodyTagSupport;
import edu.uiowa.AcknowledgementsTagLib.organization.Organization;
import edu.uiowa.AcknowledgementsTagLib.collaboration.Collaboration;

@SuppressWarnings("serial")
public class CollaborantDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int organizationId = 0;
    int collaborationId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(CollaborantDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
		if (theOrganization!= null)
			parentEntities.addElement(theOrganization);
		Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
		if (theCollaboration!= null)
			parentEntities.addElement(theCollaboration);

		if (theOrganization == null) {
		} else {
			organizationId = theOrganization.getID();
		}
		if (theCollaboration == null) {
		} else {
			collaborationId = theCollaboration.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.collaborant where 1=1"
                                                        + (pmcid == 0 ? "" : " and pmcid = ? ")
                                                        + (organizationId == 0 ? "" : " and organization_id = ? ")
                                                        + (collaborationId == 0 ? "" : " and collaboration_id = ? "));
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            if (collaborationId != 0) stat.setInt(webapp_keySeq++, collaborationId);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating Collaborant deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating Collaborant deleter");
        } finally {
            freeConnection();
        }

        return SKIP_BODY;
    }

	public int doEndTag() throws JspException {
		clearServiceState();
		return super.doEndTag();
	}

    private void clearServiceState() {
        pmcid = 0;
        organizationId = 0;
        collaborationId = 0;
        parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

        this.rs = null;
        this.var = null;
        this.rsCount = 0;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
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

	public int getOrganizationId () {
		return organizationId;
	}

	public void setOrganizationId (int organizationId) {
		this.organizationId = organizationId;
	}

	public int getActualOrganizationId () {
		return organizationId;
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
}
