package edu.uiowa.AcknowledgementsTagLib.collaborator;


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
import edu.uiowa.AcknowledgementsTagLib.collaboration.Collaboration;
import edu.uiowa.AcknowledgementsTagLib.person.Person;

@SuppressWarnings("serial")
public class CollaboratorDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int collaborationId = 0;
    int personId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(CollaboratorDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
		if (theCollaboration!= null)
			parentEntities.addElement(theCollaboration);
		Person thePerson = (Person)findAncestorWithClass(this, Person.class);
		if (thePerson!= null)
			parentEntities.addElement(thePerson);

		if (theCollaboration == null) {
		} else {
			collaborationId = theCollaboration.getID();
		}
		if (thePerson == null) {
		} else {
			personId = thePerson.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.collaborator where 1=1"
                                                        + (pmcid == 0 ? "" : " and pmcid = ? ")
                                                        + (collaborationId == 0 ? "" : " and collaboration_id = ? ")
                                                        + (personId == 0 ? "" : " and person_id = ? "));
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            if (collaborationId != 0) stat.setInt(webapp_keySeq++, collaborationId);
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating Collaborator deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating Collaborator deleter");
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
        collaborationId = 0;
        personId = 0;
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

	public int getCollaborationId () {
		return collaborationId;
	}

	public void setCollaborationId (int collaborationId) {
		this.collaborationId = collaborationId;
	}

	public int getActualCollaborationId () {
		return collaborationId;
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
}
