package edu.uiowa.AcknowledgementsTagLib.collaborant;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class CollaborantPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(CollaborantPmcid.class);


	public int doStartTag() throws JspException {
		try {
			Collaborant theCollaborant = (Collaborant)findAncestorWithClass(this, Collaborant.class);
			if (!theCollaborant.commitNeeded) {
				pageContext.getOut().print(theCollaborant.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborant for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborant for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			Collaborant theCollaborant = (Collaborant)findAncestorWithClass(this, Collaborant.class);
			return theCollaborant.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Collaborant for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborant for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			Collaborant theCollaborant = (Collaborant)findAncestorWithClass(this, Collaborant.class);
			theCollaborant.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborant for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborant for pmcid tag ");
		}
	}

}
