package edu.uiowa.AcknowledgementsTagLib.technique;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class TechniqueTechnique extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(TechniqueTechnique.class);


	public int doStartTag() throws JspException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			if (!theTechnique.commitNeeded) {
				pageContext.getOut().print(theTechnique.getTechnique());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Technique for technique tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for technique tag ");
		}
		return SKIP_BODY;
	}

	public String getTechnique() throws JspTagException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			return theTechnique.getTechnique();
		} catch (Exception e) {
			log.error(" Can't find enclosing Technique for technique tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for technique tag ");
		}
	}

	public void setTechnique(String technique) throws JspTagException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			theTechnique.setTechnique(technique);
		} catch (Exception e) {
			log.error("Can't find enclosing Technique for technique tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for technique tag ");
		}
	}

}
