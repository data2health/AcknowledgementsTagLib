package edu.uiowa.AcknowledgementsTagLib.disease;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class DiseaseDisease extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(DiseaseDisease.class);


	public int doStartTag() throws JspException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			if (!theDisease.commitNeeded) {
				pageContext.getOut().print(theDisease.getDisease());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Disease for disease tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for disease tag ");
		}
		return SKIP_BODY;
	}

	public String getDisease() throws JspTagException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			return theDisease.getDisease();
		} catch (Exception e) {
			log.error(" Can't find enclosing Disease for disease tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for disease tag ");
		}
	}

	public void setDisease(String disease) throws JspTagException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			theDisease.setDisease(disease);
		} catch (Exception e) {
			log.error("Can't find enclosing Disease for disease tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for disease tag ");
		}
	}

}
