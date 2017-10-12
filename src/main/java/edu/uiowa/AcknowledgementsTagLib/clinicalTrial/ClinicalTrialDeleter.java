package edu.uiowa.AcknowledgementsTagLib.clinicalTrial;


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

@SuppressWarnings("serial")
public class ClinicalTrialDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    String prefix = null;
    String ID = null;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(ClinicalTrialDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {



        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.clinical_trial where 1=1"
                                                        + (pmcid == 0 ? "" : " and pmcid = ? ")
                                                        + (prefix == null ? "" : " and prefix = ? ")
                                                        + (ID == null ? "" : " and id = ? "));
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            if (prefix != null) stat.setString(webapp_keySeq++, prefix);
            if (ID != null) stat.setString(webapp_keySeq++, ID);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating ClinicalTrial deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating ClinicalTrial deleter");
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
        prefix = null;
        ID = null;
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

	public String getPrefix () {
		return prefix;
	}

	public void setPrefix (String prefix) {
		this.prefix = prefix;
	}

	public String getActualPrefix () {
		return prefix;
	}

	public String getID () {
		return ID;
	}

	public void setID (String ID) {
		this.ID = ID;
	}

	public String getActualID () {
		return ID;
	}
}
