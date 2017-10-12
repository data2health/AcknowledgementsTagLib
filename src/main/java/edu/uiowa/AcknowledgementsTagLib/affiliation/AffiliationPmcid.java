package edu.uiowa.AcknowledgementsTagLib.affiliation;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class AffiliationPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(AffiliationPmcid.class);


	public int doStartTag() throws JspException {
		try {
			Affiliation theAffiliation = (Affiliation)findAncestorWithClass(this, Affiliation.class);
			if (!theAffiliation.commitNeeded) {
				pageContext.getOut().print(theAffiliation.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Affiliation for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Affiliation for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			Affiliation theAffiliation = (Affiliation)findAncestorWithClass(this, Affiliation.class);
			return theAffiliation.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Affiliation for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Affiliation for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			Affiliation theAffiliation = (Affiliation)findAncestorWithClass(this, Affiliation.class);
			theAffiliation.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing Affiliation for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Affiliation for pmcid tag ");
		}
	}

}
