package edu.uiowa.AcknowledgementsTagLib.organism;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganismID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganismID.class);


	public int doStartTag() throws JspException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			if (!theOrganism.commitNeeded) {
				pageContext.getOut().print(theOrganism.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Organism for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			return theOrganism.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Organism for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			theOrganism.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Organism for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for ID tag ");
		}
	}

}
