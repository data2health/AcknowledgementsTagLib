package edu.uiowa.AcknowledgementsTagLib.publicationComponentMention;


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
import edu.uiowa.AcknowledgementsTagLib.publicationComponent.PublicationComponent;

@SuppressWarnings("serial")
public class PublicationComponentMentionDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int publicationComponentId = 0;
    int pmcid = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(PublicationComponentMentionDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		PublicationComponent thePublicationComponent = (PublicationComponent)findAncestorWithClass(this, PublicationComponent.class);
		if (thePublicationComponent!= null)
			parentEntities.addElement(thePublicationComponent);

		if (thePublicationComponent == null) {
		} else {
			publicationComponentId = thePublicationComponent.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.publication_component_mention where 1=1"
                                                        + (publicationComponentId == 0 ? "" : " and publication_component_id = ? ")
                                                        + (pmcid == 0 ? "" : " and pmcid = ? "));
            if (publicationComponentId != 0) stat.setInt(webapp_keySeq++, publicationComponentId);
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating PublicationComponentMention deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating PublicationComponentMention deleter");
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
        publicationComponentId = 0;
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



	public int getPublicationComponentId () {
		return publicationComponentId;
	}

	public void setPublicationComponentId (int publicationComponentId) {
		this.publicationComponentId = publicationComponentId;
	}

	public int getActualPublicationComponentId () {
		return publicationComponentId;
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
