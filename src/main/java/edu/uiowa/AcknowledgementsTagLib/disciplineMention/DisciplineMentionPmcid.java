package edu.uiowa.AcknowledgementsTagLib.disciplineMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class DisciplineMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(DisciplineMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			DisciplineMention theDisciplineMention = (DisciplineMention)findAncestorWithClass(this, DisciplineMention.class);
			if (!theDisciplineMention.commitNeeded) {
				pageContext.getOut().print(theDisciplineMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing DisciplineMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing DisciplineMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			DisciplineMention theDisciplineMention = (DisciplineMention)findAncestorWithClass(this, DisciplineMention.class);
			return theDisciplineMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing DisciplineMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing DisciplineMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			DisciplineMention theDisciplineMention = (DisciplineMention)findAncestorWithClass(this, DisciplineMention.class);
			theDisciplineMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing DisciplineMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing DisciplineMention for pmcid tag ");
		}
	}

}
