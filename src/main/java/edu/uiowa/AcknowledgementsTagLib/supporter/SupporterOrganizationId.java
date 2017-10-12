package edu.uiowa.AcknowledgementsTagLib.supporter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class SupporterOrganizationId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SupporterOrganizationId.class);


	public int doStartTag() throws JspException {
		try {
			Supporter theSupporter = (Supporter)findAncestorWithClass(this, Supporter.class);
			if (!theSupporter.commitNeeded) {
				pageContext.getOut().print(theSupporter.getOrganizationId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Supporter for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Supporter for organizationId tag ");
		}
		return SKIP_BODY;
	}

	public int getOrganizationId() throws JspTagException {
		try {
			Supporter theSupporter = (Supporter)findAncestorWithClass(this, Supporter.class);
			return theSupporter.getOrganizationId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Supporter for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Supporter for organizationId tag ");
		}
	}

	public void setOrganizationId(int organizationId) throws JspTagException {
		try {
			Supporter theSupporter = (Supporter)findAncestorWithClass(this, Supporter.class);
			theSupporter.setOrganizationId(organizationId);
		} catch (Exception e) {
			log.error("Can't find enclosing Supporter for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Supporter for organizationId tag ");
		}
	}

}
