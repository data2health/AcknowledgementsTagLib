package edu.uiowa.AcknowledgementsTagLib.diseaseMention;


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
import edu.uiowa.AcknowledgementsTagLib.disease.Disease;

@SuppressWarnings("serial")
public class DiseaseMentionDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int diseaseId = 0;
    int pmcid = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(DiseaseMentionDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
		if (theDisease!= null)
			parentEntities.addElement(theDisease);

		if (theDisease == null) {
		} else {
			diseaseId = theDisease.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.disease_mention where 1=1"
                                                        + (diseaseId == 0 ? "" : " and disease_id = ? ")
                                                        + (pmcid == 0 ? "" : " and pmcid = ? "));
            if (diseaseId != 0) stat.setInt(webapp_keySeq++, diseaseId);
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating DiseaseMention deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating DiseaseMention deleter");
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
        diseaseId = 0;
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



	public int getDiseaseId () {
		return diseaseId;
	}

	public void setDiseaseId (int diseaseId) {
		this.diseaseId = diseaseId;
	}

	public int getActualDiseaseId () {
		return diseaseId;
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
