package edu.uiowa.AcknowledgementsTagLib.award;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class AwardID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(AwardID.class);


	public int doStartTag() throws JspException {
		try {
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			if (!theAward.commitNeeded) {
				pageContext.getOut().print(theAward.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Award for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Award for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			return theAward.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Award for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Award for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			theAward.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Award for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Award for ID tag ");
		}
	}

}
