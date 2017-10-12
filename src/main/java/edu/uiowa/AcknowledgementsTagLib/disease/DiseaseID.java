package edu.uiowa.AcknowledgementsTagLib.disease;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class DiseaseID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(DiseaseID.class);


	public int doStartTag() throws JspException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			if (!theDisease.commitNeeded) {
				pageContext.getOut().print(theDisease.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Disease for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			return theDisease.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Disease for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			theDisease.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Disease for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Disease for ID tag ");
		}
	}

}
