package edu.uiowa.AcknowledgementsTagLib.organism;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganismUmlsMatchString extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganismUmlsMatchString.class);


	public int doStartTag() throws JspException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			if (!theOrganism.commitNeeded) {
				pageContext.getOut().print(theOrganism.getUmlsMatchString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Organism for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for umlsMatchString tag ");
		}
		return SKIP_BODY;
	}

	public String getUmlsMatchString() throws JspTagException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			return theOrganism.getUmlsMatchString();
		} catch (Exception e) {
			log.error(" Can't find enclosing Organism for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for umlsMatchString tag ");
		}
	}

	public void setUmlsMatchString(String umlsMatchString) throws JspTagException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			theOrganism.setUmlsMatchString(umlsMatchString);
		} catch (Exception e) {
			log.error("Can't find enclosing Organism for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for umlsMatchString tag ");
		}
	}

}
