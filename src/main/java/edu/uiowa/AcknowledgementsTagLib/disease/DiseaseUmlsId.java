package edu.uiowa.AcknowledgementsTagLib.disease;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class DiseaseUmlsId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(DiseaseUmlsId.class);


	public int doStartTag() throws JspException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			if (!theDisease.commitNeeded) {
				pageContext.getOut().print(theDisease.getUmlsId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Disease for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for umlsId tag ");
		}
		return SKIP_BODY;
	}

	public String getUmlsId() throws JspTagException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			return theDisease.getUmlsId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Disease for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for umlsId tag ");
		}
	}

	public void setUmlsId(String umlsId) throws JspTagException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			theDisease.setUmlsId(umlsId);
		} catch (Exception e) {
			log.error("Can't find enclosing Disease for umlsId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for umlsId tag ");
		}
	}

}
