package edu.uiowa.AcknowledgementsTagLib.provider;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ProviderResourceId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ProviderResourceId.class);


	public int doStartTag() throws JspException {
		try {
			Provider theProvider = (Provider)findAncestorWithClass(this, Provider.class);
			if (!theProvider.commitNeeded) {
				pageContext.getOut().print(theProvider.getResourceId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Provider for resourceId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Provider for resourceId tag ");
		}
		return SKIP_BODY;
	}

	public int getResourceId() throws JspTagException {
		try {
			Provider theProvider = (Provider)findAncestorWithClass(this, Provider.class);
			return theProvider.getResourceId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Provider for resourceId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Provider for resourceId tag ");
		}
	}

	public void setResourceId(int resourceId) throws JspTagException {
		try {
			Provider theProvider = (Provider)findAncestorWithClass(this, Provider.class);
			theProvider.setResourceId(resourceId);
		} catch (Exception e) {
			log.error("Can't find enclosing Provider for resourceId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Provider for resourceId tag ");
		}
	}

}
