package edu.uiowa.AcknowledgementsTagLib.fundingAgencyMention;


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
import edu.uiowa.AcknowledgementsTagLib.fundingAgency.FundingAgency;

@SuppressWarnings("serial")
public class FundingAgencyMentionDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int fundingAgencyId = 0;
    int pmcid = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(FundingAgencyMentionDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		FundingAgency theFundingAgency = (FundingAgency)findAncestorWithClass(this, FundingAgency.class);
		if (theFundingAgency!= null)
			parentEntities.addElement(theFundingAgency);

		if (theFundingAgency == null) {
		} else {
			fundingAgencyId = theFundingAgency.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.funding_agency_mention where 1=1"
                                                        + (fundingAgencyId == 0 ? "" : " and funding_agency_id = ? ")
                                                        + (pmcid == 0 ? "" : " and pmcid = ? "));
            if (fundingAgencyId != 0) stat.setInt(webapp_keySeq++, fundingAgencyId);
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating FundingAgencyMention deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating FundingAgencyMention deleter");
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
        fundingAgencyId = 0;
        pmcid = 0;
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



	public int getFundingAgencyId () {
		return fundingAgencyId;
	}

	public void setFundingAgencyId (int fundingAgencyId) {
		this.fundingAgencyId = fundingAgencyId;
	}

	public int getActualFundingAgencyId () {
		return fundingAgencyId;
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
}
