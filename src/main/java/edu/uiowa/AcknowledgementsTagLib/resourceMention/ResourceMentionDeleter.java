package edu.uiowa.AcknowledgementsTagLib.resourceMention;


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
import edu.uiowa.AcknowledgementsTagLib.resource.Resource;

@SuppressWarnings("serial")
public class ResourceMentionDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int resourceId = 0;
    int pmcid = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(ResourceMentionDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
		if (theResource!= null)
			parentEntities.addElement(theResource);

		if (theResource == null) {
		} else {
			resourceId = theResource.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.resource_mention where 1=1"
                                                        + (resourceId == 0 ? "" : " and resource_id = ? ")
                                                        + (pmcid == 0 ? "" : " and pmcid = ? "));
            if (resourceId != 0) stat.setInt(webapp_keySeq++, resourceId);
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating ResourceMention deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating ResourceMention deleter");
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
        resourceId = 0;
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



	public int getResourceId () {
		return resourceId;
	}

	public void setResourceId (int resourceId) {
		this.resourceId = resourceId;
	}

	public int getActualResourceId () {
		return resourceId;
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
