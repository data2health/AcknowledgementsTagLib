package edu.uiowa.AcknowledgementsTagLib.resource;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ResourceUmlsMatchString extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResourceUmlsMatchString.class);


	public int doStartTag() throws JspException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			if (!theResource.commitNeeded) {
				pageContext.getOut().print(theResource.getUmlsMatchString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for umlsMatchString tag ");
		}
		return SKIP_BODY;
	}

	public String getUmlsMatchString() throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			return theResource.getUmlsMatchString();
		} catch (Exception e) {
			log.error(" Can't find enclosing Resource for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for umlsMatchString tag ");
		}
	}

	public void setUmlsMatchString(String umlsMatchString) throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			theResource.setUmlsMatchString(umlsMatchString);
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for umlsMatchString tag ");
		}
	}

}
