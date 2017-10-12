package edu.uiowa.AcknowledgementsTagLib.resource;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ResourceAltUmlsMatchString extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResourceAltUmlsMatchString.class);


	public int doStartTag() throws JspException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			if (!theResource.commitNeeded) {
				pageContext.getOut().print(theResource.getAltUmlsMatchString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for altUmlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for altUmlsMatchString tag ");
		}
		return SKIP_BODY;
	}

	public String getAltUmlsMatchString() throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			return theResource.getAltUmlsMatchString();
		} catch (Exception e) {
			log.error(" Can't find enclosing Resource for altUmlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for altUmlsMatchString tag ");
		}
	}

	public void setAltUmlsMatchString(String altUmlsMatchString) throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			theResource.setAltUmlsMatchString(altUmlsMatchString);
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for altUmlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for altUmlsMatchString tag ");
		}
	}

}
