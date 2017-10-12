package edu.uiowa.AcknowledgementsTagLib.collaborationMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class CollaborationMentionCollaborationId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(CollaborationMentionCollaborationId.class);


	public int doStartTag() throws JspException {
		try {
			CollaborationMention theCollaborationMention = (CollaborationMention)findAncestorWithClass(this, CollaborationMention.class);
			if (!theCollaborationMention.commitNeeded) {
				pageContext.getOut().print(theCollaborationMention.getCollaborationId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing CollaborationMention for collaborationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing CollaborationMention for collaborationId tag ");
		}
		return SKIP_BODY;
	}

	public int getCollaborationId() throws JspTagException {
		try {
			CollaborationMention theCollaborationMention = (CollaborationMention)findAncestorWithClass(this, CollaborationMention.class);
			return theCollaborationMention.getCollaborationId();
		} catch (Exception e) {
			log.error(" Can't find enclosing CollaborationMention for collaborationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing CollaborationMention for collaborationId tag ");
		}
	}

	public void setCollaborationId(int collaborationId) throws JspTagException {
		try {
			CollaborationMention theCollaborationMention = (CollaborationMention)findAncestorWithClass(this, CollaborationMention.class);
			theCollaborationMention.setCollaborationId(collaborationId);
		} catch (Exception e) {
			log.error("Can't find enclosing CollaborationMention for collaborationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing CollaborationMention for collaborationId tag ");
		}
	}

}
