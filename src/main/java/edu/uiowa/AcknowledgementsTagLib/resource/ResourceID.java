package edu.uiowa.AcknowledgementsTagLib.resource;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ResourceID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResourceID.class);


	public int doStartTag() throws JspException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			if (!theResource.commitNeeded) {
				pageContext.getOut().print(theResource.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			return theResource.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Resource for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			theResource.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for ID tag ");
		}
	}

}
