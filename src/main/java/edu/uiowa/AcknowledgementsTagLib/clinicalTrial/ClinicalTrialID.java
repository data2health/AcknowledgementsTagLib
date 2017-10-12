package edu.uiowa.AcknowledgementsTagLib.clinicalTrial;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ClinicalTrialID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ClinicalTrialID.class);


	public int doStartTag() throws JspException {
		try {
			ClinicalTrial theClinicalTrial = (ClinicalTrial)findAncestorWithClass(this, ClinicalTrial.class);
			if (!theClinicalTrial.commitNeeded) {
				pageContext.getOut().print(theClinicalTrial.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing ClinicalTrial for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing ClinicalTrial for ID tag ");
		}
		return SKIP_BODY;
	}

	public String getID() throws JspTagException {
		try {
			ClinicalTrial theClinicalTrial = (ClinicalTrial)findAncestorWithClass(this, ClinicalTrial.class);
			return theClinicalTrial.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing ClinicalTrial for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing ClinicalTrial for ID tag ");
		}
	}

	public void setID(String ID) throws JspTagException {
		try {
			ClinicalTrial theClinicalTrial = (ClinicalTrial)findAncestorWithClass(this, ClinicalTrial.class);
			theClinicalTrial.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing ClinicalTrial for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing ClinicalTrial for ID tag ");
		}
	}

}
