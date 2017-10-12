package edu.uiowa.AcknowledgementsTagLib.resource;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ResourceAltUmlsId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResourceAltUmlsId.class);


	public int doStartTag() throws JspException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			if (!theResource.commitNeeded) {
				pageContext.getOut().print(theResource.getAltUmlsId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for altUmlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for altUmlsId tag ");
		}
		return SKIP_BODY;
	}

	public String getAltUmlsId() throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			return theResource.getAltUmlsId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Resource for altUmlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for altUmlsId tag ");
		}
	}

	public void setAltUmlsId(String altUmlsId) throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			theResource.setAltUmlsId(altUmlsId);
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for altUmlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for altUmlsId tag ");
		}
	}

}
