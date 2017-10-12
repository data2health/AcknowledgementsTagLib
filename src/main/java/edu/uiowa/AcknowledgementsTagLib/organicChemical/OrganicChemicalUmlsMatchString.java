package edu.uiowa.AcknowledgementsTagLib.organicChemical;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganicChemicalUmlsMatchString extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganicChemicalUmlsMatchString.class);


	public int doStartTag() throws JspException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			if (!theOrganicChemical.commitNeeded) {
				pageContext.getOut().print(theOrganicChemical.getUmlsMatchString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemical for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for umlsMatchString tag ");
		}
		return SKIP_BODY;
	}

	public String getUmlsMatchString() throws JspTagException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			return theOrganicChemical.getUmlsMatchString();
		} catch (Exception e) {
			log.error(" Can't find enclosing OrganicChemical for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for umlsMatchString tag ");
		}
	}

	public void setUmlsMatchString(String umlsMatchString) throws JspTagException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			theOrganicChemical.setUmlsMatchString(umlsMatchString);
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemical for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for umlsMatchString tag ");
		}
	}

}
