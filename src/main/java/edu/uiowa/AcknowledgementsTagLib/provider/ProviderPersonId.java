package edu.uiowa.AcknowledgementsTagLib.provider;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ProviderPersonId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ProviderPersonId.class);


	public int doStartTag() throws JspException {
		try {
			Provider theProvider = (Provider)findAncestorWithClass(this, Provider.class);
			if (!theProvider.commitNeeded) {
				pageContext.getOut().print(theProvider.getPersonId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Provider for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Provider for personId tag ");
		}
		return SKIP_BODY;
	}

	public int getPersonId() throws JspTagException {
		try {
			Provider theProvider = (Provider)findAncestorWithClass(this, Provider.class);
			return theProvider.getPersonId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Provider for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Provider for personId tag ");
		}
	}

	public void setPersonId(int personId) throws JspTagException {
		try {
			Provider theProvider = (Provider)findAncestorWithClass(this, Provider.class);
			theProvider.setPersonId(personId);
		} catch (Exception e) {
			log.error("Can't find enclosing Provider for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Provider for personId tag ");
		}
	}

}
