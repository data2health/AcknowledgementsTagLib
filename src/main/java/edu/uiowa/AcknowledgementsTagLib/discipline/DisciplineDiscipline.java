package edu.uiowa.AcknowledgementsTagLib.discipline;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class DisciplineDiscipline extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(DisciplineDiscipline.class);


	public int doStartTag() throws JspException {
		try {
			Discipline theDiscipline = (Discipline)findAncestorWithClass(this, Discipline.class);
			if (!theDiscipline.commitNeeded) {
				pageContext.getOut().print(theDiscipline.getDiscipline());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Discipline for discipline tag ", e);
			throw new JspTagException("Error: Can't find enclosing Discipline for discipline tag ");
		}
		return SKIP_BODY;
	}

	public String getDiscipline() throws JspTagException {
		try {
			Discipline theDiscipline = (Discipline)findAncestorWithClass(this, Discipline.class);
			return theDiscipline.getDiscipline();
		} catch (Exception e) {
			log.error(" Can't find enclosing Discipline for discipline tag ", e);
			throw new JspTagException("Error: Can't find enclosing Discipline for discipline tag ");
		}
	}

	public void setDiscipline(String discipline) throws JspTagException {
		try {
			Discipline theDiscipline = (Discipline)findAncestorWithClass(this, Discipline.class);
			theDiscipline.setDiscipline(discipline);
		} catch (Exception e) {
			log.error("Can't find enclosing Discipline for discipline tag ", e);
			throw new JspTagException("Error: Can't find enclosing Discipline for discipline tag ");
		}
	}

}
