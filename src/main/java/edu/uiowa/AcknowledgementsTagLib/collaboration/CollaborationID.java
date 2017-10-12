package edu.uiowa.AcknowledgementsTagLib.collaboration;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class CollaborationID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(CollaborationID.class);


	public int doStartTag() throws JspException {
		try {
			Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
			if (!theCollaboration.commitNeeded) {
				pageContext.getOut().print(theCollaboration.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Collaboration for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaboration for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
			return theCollaboration.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Collaboration for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaboration for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
			theCollaboration.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Collaboration for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaboration for ID tag ");
		}
	}

}
