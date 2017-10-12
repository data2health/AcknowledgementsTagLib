package edu.uiowa.AcknowledgementsTagLib.organicChemical;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganicChemicalOrganicChemical extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganicChemicalOrganicChemical.class);


	public int doStartTag() throws JspException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			if (!theOrganicChemical.commitNeeded) {
				pageContext.getOut().print(theOrganicChemical.getOrganicChemical());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemical for organicChemical tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for organicChemical tag ");
		}
		return SKIP_BODY;
	}

	public String getOrganicChemical() throws JspTagException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			return theOrganicChemical.getOrganicChemical();
		} catch (Exception e) {
			log.error(" Can't find enclosing OrganicChemical for organicChemical tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for organicChemical tag ");
		}
	}

	public void setOrganicChemical(String organicChemical) throws JspTagException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			theOrganicChemical.setOrganicChemical(organicChemical);
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemical for organicChemical tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for organicChemical tag ");
		}
	}

}
