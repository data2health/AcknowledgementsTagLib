package edu.uiowa.AcknowledgementsTagLib.affiliation;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class AffiliationPersonId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(AffiliationPersonId.class);


	public int doStartTag() throws JspException {
		try {
			Affiliation theAffiliation = (Affiliation)findAncestorWithClass(this, Affiliation.class);
			if (!theAffiliation.commitNeeded) {
				pageContext.getOut().print(theAffiliation.getPersonId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Affiliation for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Affiliation for personId tag ");
		}
		return SKIP_BODY;
	}

	public int getPersonId() throws JspTagException {
		try {
			Affiliation theAffiliation = (Affiliation)findAncestorWithClass(this, Affiliation.class);
			return theAffiliation.getPersonId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Affiliation for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Affiliation for personId tag ");
		}
	}

	public void setPersonId(int personId) throws JspTagException {
		try {
			Affiliation theAffiliation = (Affiliation)findAncestorWithClass(this, Affiliation.class);
			theAffiliation.setPersonId(personId);
		} catch (Exception e) {
			log.error("Can't find enclosing Affiliation for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Affiliation for personId tag ");
		}
	}

}
