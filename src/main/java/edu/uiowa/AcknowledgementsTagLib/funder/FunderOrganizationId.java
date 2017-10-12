package edu.uiowa.AcknowledgementsTagLib.funder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class FunderOrganizationId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(FunderOrganizationId.class);


	public int doStartTag() throws JspException {
		try {
			Funder theFunder = (Funder)findAncestorWithClass(this, Funder.class);
			if (!theFunder.commitNeeded) {
				pageContext.getOut().print(theFunder.getOrganizationId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Funder for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Funder for organizationId tag ");
		}
		return SKIP_BODY;
	}

	public int getOrganizationId() throws JspTagException {
		try {
			Funder theFunder = (Funder)findAncestorWithClass(this, Funder.class);
			return theFunder.getOrganizationId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Funder for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Funder for organizationId tag ");
		}
	}

	public void setOrganizationId(int organizationId) throws JspTagException {
		try {
			Funder theFunder = (Funder)findAncestorWithClass(this, Funder.class);
			theFunder.setOrganizationId(organizationId);
		} catch (Exception e) {
			log.error("Can't find enclosing Funder for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Funder for organizationId tag ");
		}
	}

}
