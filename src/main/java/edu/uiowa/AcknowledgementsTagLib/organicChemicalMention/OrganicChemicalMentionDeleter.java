package edu.uiowa.AcknowledgementsTagLib.organicChemicalMention;


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
import edu.uiowa.AcknowledgementsTagLib.organicChemical.OrganicChemical;

@SuppressWarnings("serial")
public class OrganicChemicalMentionDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int organicChemicalId = 0;
    int pmcid = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(OrganicChemicalMentionDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
		if (theOrganicChemical!= null)
			parentEntities.addElement(theOrganicChemical);

		if (theOrganicChemical == null) {
		} else {
			organicChemicalId = theOrganicChemical.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.organic_chemical_mention where 1=1"
                                                        + (organicChemicalId == 0 ? "" : " and organic_chemical_id = ? ")
                                                        + (pmcid == 0 ? "" : " and pmcid = ? "));
            if (organicChemicalId != 0) stat.setInt(webapp_keySeq++, organicChemicalId);
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating OrganicChemicalMention deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating OrganicChemicalMention deleter");
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
        organicChemicalId = 0;
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



	public int getOrganicChemicalId () {
		return organicChemicalId;
	}

	public void setOrganicChemicalId (int organicChemicalId) {
		this.organicChemicalId = organicChemicalId;
	}

	public int getActualOrganicChemicalId () {
		return organicChemicalId;
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
