package edu.uiowa.AcknowledgementsTagLib.awardMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class AwardMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(AwardMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			AwardMention theAwardMention = (AwardMention)findAncestorWithClass(this, AwardMention.class);
			if (!theAwardMention.commitNeeded) {
				pageContext.getOut().print(theAwardMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing AwardMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing AwardMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			AwardMention theAwardMention = (AwardMention)findAncestorWithClass(this, AwardMention.class);
			return theAwardMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing AwardMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing AwardMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			AwardMention theAwardMention = (AwardMention)findAncestorWithClass(this, AwardMention.class);
			theAwardMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing AwardMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing AwardMention for pmcid tag ");
		}
	}

}
