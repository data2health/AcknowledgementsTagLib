package edu.uiowa.AcknowledgementsTagLib.diseaseMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class DiseaseMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(DiseaseMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			DiseaseMention theDiseaseMention = (DiseaseMention)findAncestorWithClass(this, DiseaseMention.class);
			if (!theDiseaseMention.commitNeeded) {
				pageContext.getOut().print(theDiseaseMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing DiseaseMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing DiseaseMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			DiseaseMention theDiseaseMention = (DiseaseMention)findAncestorWithClass(this, DiseaseMention.class);
			return theDiseaseMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing DiseaseMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing DiseaseMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			DiseaseMention theDiseaseMention = (DiseaseMention)findAncestorWithClass(this, DiseaseMention.class);
			theDiseaseMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing DiseaseMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing DiseaseMention for pmcid tag ");
		}
	}

}
