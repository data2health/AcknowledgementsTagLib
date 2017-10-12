package edu.uiowa.AcknowledgementsTagLib.organicChemical;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganicChemicalUmlsId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganicChemicalUmlsId.class);


	public int doStartTag() throws JspException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			if (!theOrganicChemical.commitNeeded) {
				pageContext.getOut().print(theOrganicChemical.getUmlsId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemical for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for umlsId tag ");
		}
		return SKIP_BODY;
	}

	public String getUmlsId() throws JspTagException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			return theOrganicChemical.getUmlsId();
		} catch (Exception e) {
			log.error(" Can't find enclosing OrganicChemical for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for umlsId tag ");
		}
	}

	public void setUmlsId(String umlsId) throws JspTagException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			theOrganicChemical.setUmlsId(umlsId);
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemical for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for umlsId tag ");
		}
	}

}
