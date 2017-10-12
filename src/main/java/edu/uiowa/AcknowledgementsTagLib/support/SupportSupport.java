package edu.uiowa.AcknowledgementsTagLib.support;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class SupportSupport extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SupportSupport.class);


	public int doStartTag() throws JspException {
		try {
			Support theSupport = (Support)findAncestorWithClass(this, Support.class);
			if (!theSupport.commitNeeded) {
				pageContext.getOut().print(theSupport.getSupport());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Support for support tag ", e);
			throw new JspTagException("Error: Can't find enclosing Support for support tag ");
		}
		return SKIP_BODY;
	}

	public String getSupport() throws JspTagException {
		try {
			Support theSupport = (Support)findAncestorWithClass(this, Support.class);
			return theSupport.getSupport();
		} catch (Exception e) {
			log.error(" Can't find enclosing Support for support tag ", e);
			throw new JspTagException("Error: Can't find enclosing Support for support tag ");
		}
	}

	public void setSupport(String support) throws JspTagException {
		try {
			Support theSupport = (Support)findAncestorWithClass(this, Support.class);
			theSupport.setSupport(support);
		} catch (Exception e) {
			log.error("Can't find enclosing Support for support tag ", e);
			throw new JspTagException("Error: Can't find enclosing Support for support tag ");
		}
	}

}
