package edu.uiowa.AcknowledgementsTagLib.supportMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class SupportMentionSupportId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SupportMentionSupportId.class);


	public int doStartTag() throws JspException {
		try {
			SupportMention theSupportMention = (SupportMention)findAncestorWithClass(this, SupportMention.class);
			if (!theSupportMention.commitNeeded) {
				pageContext.getOut().print(theSupportMention.getSupportId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing SupportMention for supportId tag ", e);
			throw new JspTagException("Error: Can't find enclosing SupportMention for supportId tag ");
		}
		return SKIP_BODY;
	}

	public int getSupportId() throws JspTagException {
		try {
			SupportMention theSupportMention = (SupportMention)findAncestorWithClass(this, SupportMention.class);
			return theSupportMention.getSupportId();
		} catch (Exception e) {
			log.error(" Can't find enclosing SupportMention for supportId tag ", e);
			throw new JspTagException("Error: Can't find enclosing SupportMention for supportId tag ");
		}
	}

	public void setSupportId(int supportId) throws JspTagException {
		try {
			SupportMention theSupportMention = (SupportMention)findAncestorWithClass(this, SupportMention.class);
			theSupportMention.setSupportId(supportId);
		} catch (Exception e) {
			log.error("Can't find enclosing SupportMention for supportId tag ", e);
			throw new JspTagException("Error: Can't find enclosing SupportMention for supportId tag ");
		}
	}

}
