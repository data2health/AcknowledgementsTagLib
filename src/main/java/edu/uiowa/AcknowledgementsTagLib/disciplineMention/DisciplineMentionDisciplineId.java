package edu.uiowa.AcknowledgementsTagLib.disciplineMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class DisciplineMentionDisciplineId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(DisciplineMentionDisciplineId.class);


	public int doStartTag() throws JspException {
		try {
			DisciplineMention theDisciplineMention = (DisciplineMention)findAncestorWithClass(this, DisciplineMention.class);
			if (!theDisciplineMention.commitNeeded) {
				pageContext.getOut().print(theDisciplineMention.getDisciplineId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing DisciplineMention for disciplineId tag ", e);
			throw new JspTagException("Error: Can't find enclosing DisciplineMention for disciplineId tag ");
		}
		return SKIP_BODY;
	}

	public int getDisciplineId() throws JspTagException {
		try {
			DisciplineMention theDisciplineMention = (DisciplineMention)findAncestorWithClass(this, DisciplineMention.class);
			return theDisciplineMention.getDisciplineId();
		} catch (Exception e) {
			log.error(" Can't find enclosing DisciplineMention for disciplineId tag ", e);
			throw new JspTagException("Error: Can't find enclosing DisciplineMention for disciplineId tag ");
		}
	}

	public void setDisciplineId(int disciplineId) throws JspTagException {
		try {
			DisciplineMention theDisciplineMention = (DisciplineMention)findAncestorWithClass(this, DisciplineMention.class);
			theDisciplineMention.setDisciplineId(disciplineId);
		} catch (Exception e) {
			log.error("Can't find enclosing DisciplineMention for disciplineId tag ", e);
			throw new JspTagException("Error: Can't find enclosing DisciplineMention for disciplineId tag ");
		}
	}

}
