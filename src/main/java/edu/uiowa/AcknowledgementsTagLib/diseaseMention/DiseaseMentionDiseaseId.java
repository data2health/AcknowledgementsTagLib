package edu.uiowa.AcknowledgementsTagLib.diseaseMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class DiseaseMentionDiseaseId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(DiseaseMentionDiseaseId.class);


	public int doStartTag() throws JspException {
		try {
			DiseaseMention theDiseaseMention = (DiseaseMention)findAncestorWithClass(this, DiseaseMention.class);
			if (!theDiseaseMention.commitNeeded) {
				pageContext.getOut().print(theDiseaseMention.getDiseaseId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing DiseaseMention for diseaseId tag ", e);
			throw new JspTagException("Error: Can't find enclosing DiseaseMention for diseaseId tag ");
		}
		return SKIP_BODY;
	}

	public int getDiseaseId() throws JspTagException {
		try {
			DiseaseMention theDiseaseMention = (DiseaseMention)findAncestorWithClass(this, DiseaseMention.class);
			return theDiseaseMention.getDiseaseId();
		} catch (Exception e) {
			log.error(" Can't find enclosing DiseaseMention for diseaseId tag ", e);
			throw new JspTagException("Error: Can't find enclosing DiseaseMention for diseaseId tag ");
		}
	}

	public void setDiseaseId(int diseaseId) throws JspTagException {
		try {
			DiseaseMention theDiseaseMention = (DiseaseMention)findAncestorWithClass(this, DiseaseMention.class);
			theDiseaseMention.setDiseaseId(diseaseId);
		} catch (Exception e) {
			log.error("Can't find enclosing DiseaseMention for diseaseId tag ", e);
			throw new JspTagException("Error: Can't find enclosing DiseaseMention for diseaseId tag ");
		}
	}

}
