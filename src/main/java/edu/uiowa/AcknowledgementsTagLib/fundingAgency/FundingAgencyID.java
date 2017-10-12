package edu.uiowa.AcknowledgementsTagLib.fundingAgency;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class FundingAgencyID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(FundingAgencyID.class);


	public int doStartTag() throws JspException {
		try {
			FundingAgency theFundingAgency = (FundingAgency)findAncestorWithClass(this, FundingAgency.class);
			if (!theFundingAgency.commitNeeded) {
				pageContext.getOut().print(theFundingAgency.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing FundingAgency for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgency for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			FundingAgency theFundingAgency = (FundingAgency)findAncestorWithClass(this, FundingAgency.class);
			return theFundingAgency.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing FundingAgency for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgency for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			FundingAgency theFundingAgency = (FundingAgency)findAncestorWithClass(this, FundingAgency.class);
			theFundingAgency.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing FundingAgency for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing FundingAgency for ID tag ");
		}
	}

}
