package edu.uiowa.AcknowledgementsTagLib.awardMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class AwardMentionAwardId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(AwardMentionAwardId.class);


	public int doStartTag() throws JspException {
		try {
			AwardMention theAwardMention = (AwardMention)findAncestorWithClass(this, AwardMention.class);
			if (!theAwardMention.commitNeeded) {
				pageContext.getOut().print(theAwardMention.getAwardId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing AwardMention for awardId tag ", e);
			throw new JspTagException("Error: Can't find enclosing AwardMention for awardId tag ");
		}
		return SKIP_BODY;
	}

	public int getAwardId() throws JspTagException {
		try {
			AwardMention theAwardMention = (AwardMention)findAncestorWithClass(this, AwardMention.class);
			return theAwardMention.getAwardId();
		} catch (Exception e) {
			log.error(" Can't find enclosing AwardMention for awardId tag ", e);
			throw new JspTagException("Error: Can't find enclosing AwardMention for awardId tag ");
		}
	}

	public void setAwardId(int awardId) throws JspTagException {
		try {
			AwardMention theAwardMention = (AwardMention)findAncestorWithClass(this, AwardMention.class);
			theAwardMention.setAwardId(awardId);
		} catch (Exception e) {
			log.error("Can't find enclosing AwardMention for awardId tag ", e);
			throw new JspTagException("Error: Can't find enclosing AwardMention for awardId tag ");
		}
	}

}
