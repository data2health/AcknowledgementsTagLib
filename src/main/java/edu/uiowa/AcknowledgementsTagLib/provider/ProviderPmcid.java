package edu.uiowa.AcknowledgementsTagLib.provider;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ProviderPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ProviderPmcid.class);


	public int doStartTag() throws JspException {
		try {
			Provider theProvider = (Provider)findAncestorWithClass(this, Provider.class);
			if (!theProvider.commitNeeded) {
				pageContext.getOut().print(theProvider.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Provider for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Provider for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			Provider theProvider = (Provider)findAncestorWithClass(this, Provider.class);
			return theProvider.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Provider for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Provider for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			Provider theProvider = (Provider)findAncestorWithClass(this, Provider.class);
			theProvider.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing Provider for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Provider for pmcid tag ");
		}
	}

}
