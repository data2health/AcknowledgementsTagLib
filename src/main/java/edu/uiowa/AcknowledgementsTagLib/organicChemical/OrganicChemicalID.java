package edu.uiowa.AcknowledgementsTagLib.organicChemical;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganicChemicalID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganicChemicalID.class);


	public int doStartTag() throws JspException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			if (!theOrganicChemical.commitNeeded) {
				pageContext.getOut().print(theOrganicChemical.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemical for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			return theOrganicChemical.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing OrganicChemical for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			theOrganicChemical.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemical for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemical for ID tag ");
		}
	}

}
