package edu.uiowa.AcknowledgementsTagLib.project;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ProjectID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ProjectID.class);


	public int doStartTag() throws JspException {
		try {
			Project theProject = (Project)findAncestorWithClass(this, Project.class);
			if (!theProject.commitNeeded) {
				pageContext.getOut().print(theProject.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Project for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Project for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Project theProject = (Project)findAncestorWithClass(this, Project.class);
			return theProject.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Project for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Project for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Project theProject = (Project)findAncestorWithClass(this, Project.class);
			theProject.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Project for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Project for ID tag ");
		}
	}

}
