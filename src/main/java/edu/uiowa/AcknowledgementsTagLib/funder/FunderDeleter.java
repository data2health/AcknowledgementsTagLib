package edu.uiowa.AcknowledgementsTagLib.funder;


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
import edu.uiowa.AcknowledgementsTagLib.award.Award;

@SuppressWarnings("serial")
public class FunderDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int organizationId = 0;
    int awardId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(FunderDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
		if (theOrganization!= null)
			parentEntities.addElement(theOrganization);
		Award theAward = (Award)findAncestorWithClass(this, Award.class);
		if (theAward!= null)
			parentEntities.addElement(theAward);

		if (theOrganization == null) {
		} else {
			organizationId = theOrganization.getID();
		}
		if (theAward == null) {
		} else {
			awardId = theAward.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.funder where 1=1"
                                                        + (pmcid == 0 ? "" : " and pmcid = ? ")
                                                        + (organizationId == 0 ? "" : " and organization_id = ? ")
                                                        + (awardId == 0 ? "" : " and award_id = ? "));
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            if (awardId != 0) stat.setInt(webapp_keySeq++, awardId);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating Funder deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating Funder deleter");
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
        awardId = 0;
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

	public int getAwardId () {
		return awardId;
	}

	public void setAwardId (int awardId) {
		this.awardId = awardId;
	}

	public int getActualAwardId () {
		return awardId;
	}
}
