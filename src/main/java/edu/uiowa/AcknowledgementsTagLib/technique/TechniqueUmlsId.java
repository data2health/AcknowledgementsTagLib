package edu.uiowa.AcknowledgementsTagLib.technique;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class TechniqueUmlsId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(TechniqueUmlsId.class);


	public int doStartTag() throws JspException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			if (!theTechnique.commitNeeded) {
				pageContext.getOut().print(theTechnique.getUmlsId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Technique for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for umlsId tag ");
		}
		return SKIP_BODY;
	}

	public String getUmlsId() throws JspTagException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			return theTechnique.getUmlsId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Technique for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for umlsId tag ");
		}
	}

	public void setUmlsId(String umlsId) throws JspTagException {
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			theTechnique.setUmlsId(umlsId);
		} catch (Exception e) {
			log.error("Can't find enclosing Technique for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Technique for umlsId tag ");
		}
	}

}
