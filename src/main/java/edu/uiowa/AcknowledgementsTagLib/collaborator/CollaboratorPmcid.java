package edu.uiowa.AcknowledgementsTagLib.collaborator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class CollaboratorPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(CollaboratorPmcid.class);


	public int doStartTag() throws JspException {
		try {
			Collaborator theCollaborator = (Collaborator)findAncestorWithClass(this, Collaborator.class);
			if (!theCollaborator.commitNeeded) {
				pageContext.getOut().print(theCollaborator.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborator for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborator for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			Collaborator theCollaborator = (Collaborator)findAncestorWithClass(this, Collaborator.class);
			return theCollaborator.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Collaborator for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborator for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			Collaborator theCollaborator = (Collaborator)findAncestorWithClass(this, Collaborator.class);
			theCollaborator.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborator for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborator for pmcid tag ");
		}
	}

}
