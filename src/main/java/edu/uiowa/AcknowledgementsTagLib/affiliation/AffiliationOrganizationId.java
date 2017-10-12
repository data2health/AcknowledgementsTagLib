package edu.uiowa.AcknowledgementsTagLib.affiliation;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class AffiliationOrganizationId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(AffiliationOrganizationId.class);


	public int doStartTag() throws JspException {
		try {
			Affiliation theAffiliation = (Affiliation)findAncestorWithClass(this, Affiliation.class);
			if (!theAffiliation.commitNeeded) {
				pageContext.getOut().print(theAffiliation.getOrganizationId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Affiliation for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Affiliation for organizationId tag ");
		}
		return SKIP_BODY;
	}

	public int getOrganizationId() throws JspTagException {
		try {
			Affiliation theAffiliation = (Affiliation)findAncestorWithClass(this, Affiliation.class);
			return theAffiliation.getOrganizationId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Affiliation for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Affiliation for organizationId tag ");
		}
	}

	public void setOrganizationId(int organizationId) throws JspTagException {
		try {
			Affiliation theAffiliation = (Affiliation)findAncestorWithClass(this, Affiliation.class);
			theAffiliation.setOrganizationId(organizationId);
		} catch (Exception e) {
			log.error("Can't find enclosing Affiliation for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Affiliation for organizationId tag ");
		}
	}

}
