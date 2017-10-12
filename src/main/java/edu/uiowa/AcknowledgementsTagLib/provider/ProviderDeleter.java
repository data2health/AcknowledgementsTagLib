package edu.uiowa.AcknowledgementsTagLib.provider;


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
import edu.uiowa.AcknowledgementsTagLib.person.Person;
import edu.uiowa.AcknowledgementsTagLib.resource.Resource;

@SuppressWarnings("serial")
public class ProviderDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int personId = 0;
    int resourceId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(ProviderDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Person thePerson = (Person)findAncestorWithClass(this, Person.class);
		if (thePerson!= null)
			parentEntities.addElement(thePerson);
		Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
		if (theResource!= null)
			parentEntities.addElement(theResource);

		if (thePerson == null) {
		} else {
			personId = thePerson.getID();
		}
		if (theResource == null) {
		} else {
			resourceId = theResource.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.provider where 1=1"
                                                        + (pmcid == 0 ? "" : " and pmcid = ? ")
                                                        + (personId == 0 ? "" : " and person_id = ? ")
                                                        + (resourceId == 0 ? "" : " and resource_id = ? "));
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            if (resourceId != 0) stat.setInt(webapp_keySeq++, resourceId);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating Provider deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating Provider deleter");
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
        personId = 0;
        resourceId = 0;
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

	public int getPersonId () {
		return personId;
	}

	public void setPersonId (int personId) {
		this.personId = personId;
	}

	public int getActualPersonId () {
		return personId;
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
}
