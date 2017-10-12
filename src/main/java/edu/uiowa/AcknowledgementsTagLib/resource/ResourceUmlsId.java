package edu.uiowa.AcknowledgementsTagLib.resource;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ResourceUmlsId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResourceUmlsId.class);


	public int doStartTag() throws JspException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			if (!theResource.commitNeeded) {
				pageContext.getOut().print(theResource.getUmlsId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for umlsId tag ");
		}
		return SKIP_BODY;
	}

	public String getUmlsId() throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			return theResource.getUmlsId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Resource for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for umlsId tag ");
		}
	}

	public void setUmlsId(String umlsId) throws JspTagException {
		try {
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			theResource.setUmlsId(umlsId);
		} catch (Exception e) {
			log.error("Can't find enclosing Resource for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Resource for umlsId tag ");
		}
	}

}
