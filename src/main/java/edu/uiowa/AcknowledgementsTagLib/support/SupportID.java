package edu.uiowa.AcknowledgementsTagLib.support;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class SupportID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SupportID.class);


	public int doStartTag() throws JspException {
		try {
			Support theSupport = (Support)findAncestorWithClass(this, Support.class);
			if (!theSupport.commitNeeded) {
				pageContext.getOut().print(theSupport.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Support for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Support for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Support theSupport = (Support)findAncestorWithClass(this, Support.class);
			return theSupport.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Support for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Support for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Support theSupport = (Support)findAncestorWithClass(this, Support.class);
			theSupport.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Support for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Support for ID tag ");
		}
	}

}
