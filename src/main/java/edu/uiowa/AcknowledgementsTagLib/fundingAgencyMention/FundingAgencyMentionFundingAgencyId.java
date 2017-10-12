package edu.uiowa.AcknowledgementsTagLib.fundingAgencyMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class FundingAgencyMentionFundingAgencyId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(FundingAgencyMentionFundingAgencyId.class);


	public int doStartTag() throws JspException {
		try {
			FundingAgencyMention theFundingAgencyMention = (FundingAgencyMention)findAncestorWithClass(this, FundingAgencyMention.class);
			if (!theFundingAgencyMention.commitNeeded) {
				pageContext.getOut().print(theFundingAgencyMention.getFundingAgencyId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing FundingAgencyMention for fundingAgencyId tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgencyMention for fundingAgencyId tag ");
		}
		return SKIP_BODY;
	}

	public int getFundingAgencyId() throws JspTagException {
		try {
			FundingAgencyMention theFundingAgencyMention = (FundingAgencyMention)findAncestorWithClass(this, FundingAgencyMention.class);
			return theFundingAgencyMention.getFundingAgencyId();
		} catch (Exception e) {
			log.error(" Can't find enclosing FundingAgencyMention for fundingAgencyId tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgencyMention for fundingAgencyId tag ");
		}
	}

	public void setFundingAgencyId(int fundingAgencyId) throws JspTagException {
		try {
			FundingAgencyMention theFundingAgencyMention = (FundingAgencyMention)findAncestorWithClass(this, FundingAgencyMention.class);
			theFundingAgencyMention.setFundingAgencyId(fundingAgencyId);
		} catch (Exception e) {
			log.error("Can't find enclosing FundingAgencyMention for fundingAgencyId tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgencyMention for fundingAgencyId tag ");
		}
	}

}
