package edu.uiowa.AcknowledgementsTagLib.collaborator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class CollaboratorCollaborationId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(CollaboratorCollaborationId.class);


	public int doStartTag() throws JspException {
		try {
			Collaborator theCollaborator = (Collaborator)findAncestorWithClass(this, Collaborator.class);
			if (!theCollaborator.commitNeeded) {
				pageContext.getOut().print(theCollaborator.getCollaborationId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborator for collaborationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborator for collaborationId tag ");
		}
		return SKIP_BODY;
	}

	public int getCollaborationId() throws JspTagException {
		try {
			Collaborator theCollaborator = (Collaborator)findAncestorWithClass(this, Collaborator.class);
			return theCollaborator.getCollaborationId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Collaborator for collaborationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborator for collaborationId tag ");
		}
	}

	public void setCollaborationId(int collaborationId) throws JspTagException {
		try {
			Collaborator theCollaborator = (Collaborator)findAncestorWithClass(this, Collaborator.class);
			theCollaborator.setCollaborationId(collaborationId);
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborator for collaborationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborator for collaborationId tag ");
		}
	}

}
