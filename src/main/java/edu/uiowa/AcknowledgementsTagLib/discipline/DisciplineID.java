package edu.uiowa.AcknowledgementsTagLib.discipline;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class DisciplineID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(DisciplineID.class);


	public int doStartTag() throws JspException {
		try {
			Discipline theDiscipline = (Discipline)findAncestorWithClass(this, Discipline.class);
			if (!theDiscipline.commitNeeded) {
				pageContext.getOut().print(theDiscipline.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Discipline for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Discipline for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Discipline theDiscipline = (Discipline)findAncestorWithClass(this, Discipline.class);
			return theDiscipline.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Discipline for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Discipline for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Discipline theDiscipline = (Discipline)findAncestorWithClass(this, Discipline.class);
			theDiscipline.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Discipline for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Discipline for ID tag ");
		}
	}

}
