package edu.uiowa.AcknowledgementsTagLib.supportMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class SupportMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SupportMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			SupportMention theSupportMention = (SupportMention)findAncestorWithClass(this, SupportMention.class);
			if (!theSupportMention.commitNeeded) {
				pageContext.getOut().print(theSupportMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing SupportMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing SupportMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			SupportMention theSupportMention = (SupportMention)findAncestorWithClass(this, SupportMention.class);
			return theSupportMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing SupportMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing SupportMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			SupportMention theSupportMention = (SupportMention)findAncestorWithClass(this, SupportMention.class);
			theSupportMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing SupportMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing SupportMention for pmcid tag ");
		}
	}

}
