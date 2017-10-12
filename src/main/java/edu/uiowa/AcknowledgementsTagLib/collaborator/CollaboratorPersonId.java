package edu.uiowa.AcknowledgementsTagLib.collaborator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class CollaboratorPersonId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(CollaboratorPersonId.class);


	public int doStartTag() throws JspException {
		try {
			Collaborator theCollaborator = (Collaborator)findAncestorWithClass(this, Collaborator.class);
			if (!theCollaborator.commitNeeded) {
				pageContext.getOut().print(theCollaborator.getPersonId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborator for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborator for personId tag ");
		}
		return SKIP_BODY;
	}

	public int getPersonId() throws JspTagException {
		try {
			Collaborator theCollaborator = (Collaborator)findAncestorWithClass(this, Collaborator.class);
			return theCollaborator.getPersonId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Collaborator for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborator for personId tag ");
		}
	}

	public void setPersonId(int personId) throws JspTagException {
		try {
			Collaborator theCollaborator = (Collaborator)findAncestorWithClass(this, Collaborator.class);
			theCollaborator.setPersonId(personId);
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborator for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborator for personId tag ");
		}
	}

}
