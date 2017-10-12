package edu.uiowa.AcknowledgementsTagLib.organismMention;


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
import edu.uiowa.AcknowledgementsTagLib.organism.Organism;

@SuppressWarnings("serial")
public class OrganismMentionDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int organismId = 0;
    int pmcid = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(OrganismMentionDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
		if (theOrganism!= null)
			parentEntities.addElement(theOrganism);

		if (theOrganism == null) {
		} else {
			organismId = theOrganism.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.organism_mention where 1=1"
                                                        + (organismId == 0 ? "" : " and organism_id = ? ")
                                                        + (pmcid == 0 ? "" : " and pmcid = ? "));
            if (organismId != 0) stat.setInt(webapp_keySeq++, organismId);
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating OrganismMention deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating OrganismMention deleter");
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
        organismId = 0;
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



	public int getOrganismId () {
		return organismId;
	}

	public void setOrganismId (int organismId) {
		this.organismId = organismId;
	}

	public int getActualOrganismId () {
		return organismId;
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
