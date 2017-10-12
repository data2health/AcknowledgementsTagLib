package edu.uiowa.AcknowledgementsTagLib.project;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ProjectProject extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ProjectProject.class);


	public int doStartTag() throws JspException {
		try {
			Project theProject = (Project)findAncestorWithClass(this, Project.class);
			if (!theProject.commitNeeded) {
				pageContext.getOut().print(theProject.getProject());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Project for project tag ", e);
			throw new JspTagException("Error: Can't find enclosing Project for project tag ");
		}
		return SKIP_BODY;
	}

	public String getProject() throws JspTagException {
		try {
			Project theProject = (Project)findAncestorWithClass(this, Project.class);
			return theProject.getProject();
		} catch (Exception e) {
			log.error(" Can't find enclosing Project for project tag ", e);
			throw new JspTagException("Error: Can't find enclosing Project for project tag ");
		}
	}

	public void setProject(String project) throws JspTagException {
		try {
			Project theProject = (Project)findAncestorWithClass(this, Project.class);
			theProject.setProject(project);
		} catch (Exception e) {
			log.error("Can't find enclosing Project for project tag ", e);
			throw new JspTagException("Error: Can't find enclosing Project for project tag ");
		}
	}

}
