package edu.uiowa.AcknowledgementsTagLib.projectMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ProjectMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ProjectMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			ProjectMention theProjectMention = (ProjectMention)findAncestorWithClass(this, ProjectMention.class);
			if (!theProjectMention.commitNeeded) {
				pageContext.getOut().print(theProjectMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing ProjectMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing ProjectMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			ProjectMention theProjectMention = (ProjectMention)findAncestorWithClass(this, ProjectMention.class);
			return theProjectMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing ProjectMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing ProjectMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			ProjectMention theProjectMention = (ProjectMention)findAncestorWithClass(this, ProjectMention.class);
			theProjectMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing ProjectMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing ProjectMention for pmcid tag ");
		}
	}

}
