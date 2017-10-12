package edu.uiowa.AcknowledgementsTagLib.skill;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class SkillPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SkillPmcid.class);


	public int doStartTag() throws JspException {
		try {
			Skill theSkill = (Skill)findAncestorWithClass(this, Skill.class);
			if (!theSkill.commitNeeded) {
				pageContext.getOut().print(theSkill.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Skill for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Skill for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			Skill theSkill = (Skill)findAncestorWithClass(this, Skill.class);
			return theSkill.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Skill for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Skill for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			Skill theSkill = (Skill)findAncestorWithClass(this, Skill.class);
			theSkill.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing Skill for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Skill for pmcid tag ");
		}
	}

}
