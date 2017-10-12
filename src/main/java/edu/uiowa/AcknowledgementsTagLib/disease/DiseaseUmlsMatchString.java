package edu.uiowa.AcknowledgementsTagLib.disease;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class DiseaseUmlsMatchString extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(DiseaseUmlsMatchString.class);


	public int doStartTag() throws JspException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			if (!theDisease.commitNeeded) {
				pageContext.getOut().print(theDisease.getUmlsMatchString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Disease for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for umlsMatchString tag ");
		}
		return SKIP_BODY;
	}

	public String getUmlsMatchString() throws JspTagException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			return theDisease.getUmlsMatchString();
		} catch (Exception e) {
			log.error(" Can't find enclosing Disease for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for umlsMatchString tag ");
		}
	}

	public void setUmlsMatchString(String umlsMatchString) throws JspTagException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			theDisease.setUmlsMatchString(umlsMatchString);
		} catch (Exception e) {
			log.error("Can't find enclosing Disease for umlsMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for umlsMatchString tag ");
		}
	}

}
