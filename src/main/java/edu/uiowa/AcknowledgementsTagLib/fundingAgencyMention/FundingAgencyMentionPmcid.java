package edu.uiowa.AcknowledgementsTagLib.fundingAgencyMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class FundingAgencyMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(FundingAgencyMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			FundingAgencyMention theFundingAgencyMention = (FundingAgencyMention)findAncestorWithClass(this, FundingAgencyMention.class);
			if (!theFundingAgencyMention.commitNeeded) {
				pageContext.getOut().print(theFundingAgencyMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing FundingAgencyMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgencyMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			FundingAgencyMention theFundingAgencyMention = (FundingAgencyMention)findAncestorWithClass(this, FundingAgencyMention.class);
			return theFundingAgencyMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing FundingAgencyMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgencyMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			FundingAgencyMention theFundingAgencyMention = (FundingAgencyMention)findAncestorWithClass(this, FundingAgencyMention.class);
			theFundingAgencyMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing FundingAgencyMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgencyMention for pmcid tag ");
		}
	}

}
