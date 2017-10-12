package edu.uiowa.AcknowledgementsTagLib.technique;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class TechniqueID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(TechniqueID.class);


	public int doStartTag() throws JspException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			if (!theTechnique.commitNeeded) {
				pageContext.getOut().print(theTechnique.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Technique for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			return theTechnique.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Technique for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			theTechnique.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Technique for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for ID tag ");
		}
	}

}
