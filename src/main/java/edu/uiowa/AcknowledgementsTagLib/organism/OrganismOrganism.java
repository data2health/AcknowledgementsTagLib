package edu.uiowa.AcknowledgementsTagLib.organism;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganismOrganism extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganismOrganism.class);


	public int doStartTag() throws JspException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			if (!theOrganism.commitNeeded) {
				pageContext.getOut().print(theOrganism.getOrganism());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Organism for organism tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for organism tag ");
		}
		return SKIP_BODY;
	}

	public String getOrganism() throws JspTagException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			return theOrganism.getOrganism();
		} catch (Exception e) {
			log.error(" Can't find enclosing Organism for organism tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for organism tag ");
		}
	}

	public void setOrganism(String organism) throws JspTagException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			theOrganism.setOrganism(organism);
		} catch (Exception e) {
			log.error("Can't find enclosing Organism for organism tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for organism tag ");
		}
	}

}
