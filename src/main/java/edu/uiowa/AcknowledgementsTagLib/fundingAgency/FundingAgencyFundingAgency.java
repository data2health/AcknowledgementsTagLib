package edu.uiowa.AcknowledgementsTagLib.fundingAgency;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class FundingAgencyFundingAgency extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(FundingAgencyFundingAgency.class);


	public int doStartTag() throws JspException {
		try {
			FundingAgency theFundingAgency = (FundingAgency)findAncestorWithClass(this, FundingAgency.class);
			if (!theFundingAgency.commitNeeded) {
				pageContext.getOut().print(theFundingAgency.getFundingAgency());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing FundingAgency for fundingAgency tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgency for fundingAgency tag ");
		}
		return SKIP_BODY;
	}

	public String getFundingAgency() throws JspTagException {
		try {
			FundingAgency theFundingAgency = (FundingAgency)findAncestorWithClass(this, FundingAgency.class);
			return theFundingAgency.getFundingAgency();
		} catch (Exception e) {
			log.error(" Can't find enclosing FundingAgency for fundingAgency tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgency for fundingAgency tag ");
		}
	}

	public void setFundingAgency(String fundingAgency) throws JspTagException {
		try {
			FundingAgency theFundingAgency = (FundingAgency)findAncestorWithClass(this, FundingAgency.class);
			theFundingAgency.setFundingAgency(fundingAgency);
		} catch (Exception e) {
			log.error("Can't find enclosing FundingAgency for fundingAgency tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgency for fundingAgency tag ");
		}
	}

}
