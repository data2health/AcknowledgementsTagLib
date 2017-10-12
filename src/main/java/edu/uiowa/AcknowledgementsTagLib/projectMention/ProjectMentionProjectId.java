package edu.uiowa.AcknowledgementsTagLib.projectMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ProjectMentionProjectId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ProjectMentionProjectId.class);


	public int doStartTag() throws JspException {
		try {
			ProjectMention theProjectMention = (ProjectMention)findAncestorWithClass(this, ProjectMention.class);
			if (!theProjectMention.commitNeeded) {
				pageContext.getOut().print(theProjectMention.getProjectId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing ProjectMention for projectId tag ", e);
			throw new JspTagException("Error: Can't find enclosing ProjectMention for projectId tag ");
		}
		return SKIP_BODY;
	}

	public int getProjectId() throws JspTagException {
		try {
			ProjectMention theProjectMention = (ProjectMention)findAncestorWithClass(this, ProjectMention.class);
			return theProjectMention.getProjectId();
		} catch (Exception e) {
			log.error(" Can't find enclosing ProjectMention for projectId tag ", e);
			throw new JspTagException("Error: Can't find enclosing ProjectMention for projectId tag ");
		}
	}

	public void setProjectId(int projectId) throws JspTagException {
		try {
			ProjectMention theProjectMention = (ProjectMention)findAncestorWithClass(this, ProjectMention.class);
			theProjectMention.setProjectId(projectId);
		} catch (Exception e) {
			log.error("Can't find enclosing ProjectMention for projectId tag ", e);
			throw new JspTagException("Error: Can't find enclosing ProjectMention for projectId tag ");
		}
	}

}
