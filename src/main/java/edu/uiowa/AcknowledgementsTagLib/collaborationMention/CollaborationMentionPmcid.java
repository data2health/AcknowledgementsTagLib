package edu.uiowa.AcknowledgementsTagLib.collaborationMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class CollaborationMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(CollaborationMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			CollaborationMention theCollaborationMention = (CollaborationMention)findAncestorWithClass(this, CollaborationMention.class);
			if (!theCollaborationMention.commitNeeded) {
				pageContext.getOut().print(theCollaborationMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing CollaborationMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing CollaborationMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			CollaborationMention theCollaborationMention = (CollaborationMention)findAncestorWithClass(this, CollaborationMention.class);
			return theCollaborationMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing CollaborationMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing CollaborationMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			CollaborationMention theCollaborationMention = (CollaborationMention)findAncestorWithClass(this, CollaborationMention.class);
			theCollaborationMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing CollaborationMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing CollaborationMention for pmcid tag ");
		}
	}

}
