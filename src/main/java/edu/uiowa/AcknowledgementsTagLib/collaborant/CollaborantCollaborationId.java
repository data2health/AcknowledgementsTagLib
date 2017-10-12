package edu.uiowa.AcknowledgementsTagLib.collaborant;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class CollaborantCollaborationId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(CollaborantCollaborationId.class);


	public int doStartTag() throws JspException {
		try {
			Collaborant theCollaborant = (Collaborant)findAncestorWithClass(this, Collaborant.class);
			if (!theCollaborant.commitNeeded) {
				pageContext.getOut().print(theCollaborant.getCollaborationId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborant for collaborationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborant for collaborationId tag ");
		}
		return SKIP_BODY;
	}

	public int getCollaborationId() throws JspTagException {
		try {
			Collaborant theCollaborant = (Collaborant)findAncestorWithClass(this, Collaborant.class);
			return theCollaborant.getCollaborationId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Collaborant for collaborationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborant for collaborationId tag ");
		}
	}

	public void setCollaborationId(int collaborationId) throws JspTagException {
		try {
			Collaborant theCollaborant = (Collaborant)findAncestorWithClass(this, Collaborant.class);
			theCollaborant.setCollaborationId(collaborationId);
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborant for collaborationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborant for collaborationId tag ");
		}
	}

}
