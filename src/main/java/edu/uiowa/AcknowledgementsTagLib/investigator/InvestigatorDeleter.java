package edu.uiowa.AcknowledgementsTagLib.investigator;


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
import edu.uiowa.AcknowledgementsTagLib.person.Person;

@SuppressWarnings("serial")
public class InvestigatorDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int personId = 0;
    int organizationId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(InvestigatorDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
		if (theOrganization!= null)
			parentEntities.addElement(theOrganization);
		Person thePerson = (Person)findAncestorWithClass(this, Person.class);
		if (thePerson!= null)
			parentEntities.addElement(thePerson);

		if (theOrganization == null) {
		} else {
			organizationId = theOrganization.getID();
		}
		if (thePerson == null) {
		} else {
			personId = thePerson.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.investigator where 1=1"
                                                        + (pmcid == 0 ? "" : " and pmcid = ? ")
                                                        + (personId == 0 ? "" : " and person_id = ? ")
                                                        + (organizationId == 0 ? "" : " and organization_id = ? "));
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating Investigator deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating Investigator deleter");
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
        organizationId = 0;
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

	public int getOrganizationId () {
		return organizationId;
	}

	public void setOrganizationId (int organizationId) {
		this.organizationId = organizationId;
	}

	public int getActualOrganizationId () {
		return organizationId;
	}
}
