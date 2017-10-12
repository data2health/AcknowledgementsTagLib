package edu.uiowa.AcknowledgementsTagLib.resource;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ResourceResource extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResourceResource.class);


	public int doStartTag() throws JspException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			if (!theResource.commitNeeded) {
				pageContext.getOut().print(theResource.getResource());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for resource tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for resource tag ");
		}
		return SKIP_BODY;
	}

	public String getResource() throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			return theResource.getResource();
		} catch (Exception e) {
			log.error(" Can't find enclosing Resource for resource tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for resource tag ");
		}
	}

	public void setResource(String resource) throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			theResource.setResource(resource);
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for resource tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for resource tag ");
		}
	}

}
