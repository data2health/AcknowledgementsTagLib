package edu.uiowa.AcknowledgementsTagLib.disciplineMention;


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
import edu.uiowa.AcknowledgementsTagLib.discipline.Discipline;

@SuppressWarnings("serial")
public class DisciplineMentionDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int disciplineId = 0;
    int pmcid = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(DisciplineMentionDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Discipline theDiscipline = (Discipline)findAncestorWithClass(this, Discipline.class);
		if (theDiscipline!= null)
			parentEntities.addElement(theDiscipline);

		if (theDiscipline == null) {
		} else {
			disciplineId = theDiscipline.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.discipline_mention where 1=1"
                                                        + (disciplineId == 0 ? "" : " and discipline_id = ? ")
                                                        + (pmcid == 0 ? "" : " and pmcid = ? "));
            if (disciplineId != 0) stat.setInt(webapp_keySeq++, disciplineId);
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating DisciplineMention deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating DisciplineMention deleter");
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
        disciplineId = 0;
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



	public int getDisciplineId () {
		return disciplineId;
	}

	public void setDisciplineId (int disciplineId) {
		this.disciplineId = disciplineId;
	}

	public int getActualDisciplineId () {
		return disciplineId;
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
