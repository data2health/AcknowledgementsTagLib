package edu.uiowa.AcknowledgementsTagLib.collaboration;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class CollaborationCollaboration extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(CollaborationCollaboration.class);


	public int doStartTag() throws JspException {
		try {
			Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
			if (!theCollaboration.commitNeeded) {
				pageContext.getOut().print(theCollaboration.getCollaboration());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Collaboration for collaboration tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaboration for collaboration tag ");
		}
		return SKIP_BODY;
	}

	public String getCollaboration() throws JspTagException {
		try {
			Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
			return theCollaboration.getCollaboration();
		} catch (Exception e) {
			log.error(" Can't find enclosing Collaboration for collaboration tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaboration for collaboration tag ");
		}
	}

	public void setCollaboration(String collaboration) throws JspTagException {
		try {
			Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
			theCollaboration.setCollaboration(collaboration);
		} catch (Exception e) {
			log.error("Can't find enclosing Collaboration for collaboration tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaboration for collaboration tag ");
		}
	}

}
