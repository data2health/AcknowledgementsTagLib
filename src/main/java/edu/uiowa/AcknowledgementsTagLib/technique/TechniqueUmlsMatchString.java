package edu.uiowa.AcknowledgementsTagLib.technique;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class TechniqueUmlsMatchString extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(TechniqueUmlsMatchString.class);


	public int doStartTag() throws JspException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			if (!theTechnique.commitNeeded) {
				pageContext.getOut().print(theTechnique.getUmlsMatchString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Technique for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for umlsMatchString tag ");
		}
		return SKIP_BODY;
	}

	public String getUmlsMatchString() throws JspTagException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			return theTechnique.getUmlsMatchString();
		} catch (Exception e) {
			log.error(" Can't find enclosing Technique for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for umlsMatchString tag ");
		}
	}

	public void setUmlsMatchString(String umlsMatchString) throws JspTagException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			theTechnique.setUmlsMatchString(umlsMatchString);
		} catch (Exception e) {
			log.error("Can't find enclosing Technique for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for umlsMatchString tag ");
		}
	}

}
