package edu.uiowa.AcknowledgementsTagLib.clinicalTrial;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ClinicalTrialPrefix extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ClinicalTrialPrefix.class);


	public int doStartTag() throws JspException {
		try {
			ClinicalTrial theClinicalTrial = (ClinicalTrial)findAncestorWithClass(this, ClinicalTrial.class);
			if (!theClinicalTrial.commitNeeded) {
				pageContext.getOut().print(theClinicalTrial.getPrefix());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing ClinicalTrial for prefix tag ", e);
			throw new JspTagException("Error: Can't find enclosing ClinicalTrial for prefix tag ");
		}
		return SKIP_BODY;
	}

	public String getPrefix() throws JspTagException {
		try {
			ClinicalTrial theClinicalTrial = (ClinicalTrial)findAncestorWithClass(this, ClinicalTrial.class);
			return theClinicalTrial.getPrefix();
		} catch (Exception e) {
			log.error(" Can't find enclosing ClinicalTrial for prefix tag ", e);
			throw new JspTagException("Error: Can't find enclosing ClinicalTrial for prefix tag ");
		}
	}

	public void setPrefix(String prefix) throws JspTagException {
		try {
			ClinicalTrial theClinicalTrial = (ClinicalTrial)findAncestorWithClass(this, ClinicalTrial.class);
			theClinicalTrial.setPrefix(prefix);
		} catch (Exception e) {
			log.error("Can't find enclosing ClinicalTrial for prefix tag ", e);
			throw new JspTagException("Error: Can't find enclosing ClinicalTrial for prefix tag ");
		}
	}

}
