package edu.uiowa.AcknowledgementsTagLib.techniqueMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class TechniqueMentionTechniqueId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(TechniqueMentionTechniqueId.class);


	public int doStartTag() throws JspException {
		try {
			TechniqueMention theTechniqueMention = (TechniqueMention)findAncestorWithClass(this, TechniqueMention.class);
			if (!theTechniqueMention.commitNeeded) {
				pageContext.getOut().print(theTechniqueMention.getTechniqueId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing TechniqueMention for techniqueId tag ", e);
			throw new JspTagException("Error: Can't find enclosing TechniqueMention for techniqueId tag ");
		}
		return SKIP_BODY;
	}

	public int getTechniqueId() throws JspTagException {
		try {
			TechniqueMention theTechniqueMention = (TechniqueMention)findAncestorWithClass(this, TechniqueMention.class);
			return theTechniqueMention.getTechniqueId();
		} catch (Exception e) {
			log.error(" Can't find enclosing TechniqueMention for techniqueId tag ", e);
			throw new JspTagException("Error: Can't find enclosing TechniqueMention for techniqueId tag ");
		}
	}

	public void setTechniqueId(int techniqueId) throws JspTagException {
		try {
			TechniqueMention theTechniqueMention = (TechniqueMention)findAncestorWithClass(this, TechniqueMention.class);
			theTechniqueMention.setTechniqueId(techniqueId);
		} catch (Exception e) {
			log.error("Can't find enclosing TechniqueMention for techniqueId tag ", e);
			throw new JspTagException("Error: Can't find enclosing TechniqueMention for techniqueId tag ");
		}
	}

}
