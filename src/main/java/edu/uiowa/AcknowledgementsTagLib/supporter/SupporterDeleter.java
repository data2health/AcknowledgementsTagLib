package edu.uiowa.AcknowledgementsTagLib.supporter;


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
import edu.uiowa.AcknowledgementsTagLib.support.Support;
import edu.uiowa.AcknowledgementsTagLib.organization.Organization;

@SuppressWarnings("serial")
public class SupporterDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int supportId = 0;
    int organizationId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(SupporterDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Support theSupport = (Support)findAncestorWithClass(this, Support.class);
		if (theSupport!= null)
			parentEntities.addElement(theSupport);
		Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
		if (theOrganization!= null)
			parentEntities.addElement(theOrganization);

		if (theSupport == null) {
		} else {
			supportId = theSupport.getID();
		}
		if (theOrganization == null) {
		} else {
			organizationId = theOrganization.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.supporter where 1=1"
                                                        + (pmcid == 0 ? "" : " and pmcid = ? ")
                                                        + (supportId == 0 ? "" : " and support_id = ? ")
                                                        + (organizationId == 0 ? "" : " and organization_id = ? "));
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            if (supportId != 0) stat.setInt(webapp_keySeq++, supportId);
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating Supporter deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating Supporter deleter");
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
        supportId = 0;
        organizationId = 0;
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

	public int getSupportId () {
		return supportId;
	}

	public void setSupportId (int supportId) {
		this.supportId = supportId;
	}

	public int getActualSupportId () {
		return supportId;
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
}
