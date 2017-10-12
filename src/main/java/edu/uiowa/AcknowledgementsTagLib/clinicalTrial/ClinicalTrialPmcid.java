package edu.uiowa.AcknowledgementsTagLib.clinicalTrial;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ClinicalTrialPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ClinicalTrialPmcid.class);


	public int doStartTag() throws JspException {
		try {
			ClinicalTrial theClinicalTrial = (ClinicalTrial)findAncestorWithClass(this, ClinicalTrial.class);
			if (!theClinicalTrial.commitNeeded) {
				pageContext.getOut().print(theClinicalTrial.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing ClinicalTrial for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing ClinicalTrial for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			ClinicalTrial theClinicalTrial = (ClinicalTrial)findAncestorWithClass(this, ClinicalTrial.class);
			return theClinicalTrial.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing ClinicalTrial for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing ClinicalTrial for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			ClinicalTrial theClinicalTrial = (ClinicalTrial)findAncestorWithClass(this, ClinicalTrial.class);
			theClinicalTrial.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing ClinicalTrial for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing ClinicalTrial for pmcid tag ");
		}
	}

}
