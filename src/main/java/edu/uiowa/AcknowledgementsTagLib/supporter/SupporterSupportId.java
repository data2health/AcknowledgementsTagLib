package edu.uiowa.AcknowledgementsTagLib.supporter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class SupporterSupportId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SupporterSupportId.class);


	public int doStartTag() throws JspException {
		try {
			Supporter theSupporter = (Supporter)findAncestorWithClass(this, Supporter.class);
			if (!theSupporter.commitNeeded) {
				pageContext.getOut().print(theSupporter.getSupportId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Supporter for supportId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Supporter for supportId tag ");
		}
		return SKIP_BODY;
	}

	public int getSupportId() throws JspTagException {
		try {
			Supporter theSupporter = (Supporter)findAncestorWithClass(this, Supporter.class);
			return theSupporter.getSupportId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Supporter for supportId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Supporter for supportId tag ");
		}
	}

	public void setSupportId(int supportId) throws JspTagException {
		try {
			Supporter theSupporter = (Supporter)findAncestorWithClass(this, Supporter.class);
			theSupporter.setSupportId(supportId);
		} catch (Exception e) {
			log.error("Can't find enclosing Supporter for supportId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Supporter for supportId tag ");
		}
	}

}
