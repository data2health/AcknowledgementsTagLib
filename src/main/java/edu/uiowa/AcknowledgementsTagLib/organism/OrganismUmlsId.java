package edu.uiowa.AcknowledgementsTagLib.organism;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganismUmlsId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganismUmlsId.class);


	public int doStartTag() throws JspException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			if (!theOrganism.commitNeeded) {
				pageContext.getOut().print(theOrganism.getUmlsId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Organism for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for umlsId tag ");
		}
		return SKIP_BODY;
	}

	public String getUmlsId() throws JspTagException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			return theOrganism.getUmlsId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Organism for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for umlsId tag ");
		}
	}

	public void setUmlsId(String umlsId) throws JspTagException {
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			theOrganism.setUmlsId(umlsId);
		} catch (Exception e) {
			log.error("Can't find enclosing Organism for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organism for umlsId tag ");
		}
	}

}
