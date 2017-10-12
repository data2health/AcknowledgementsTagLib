package edu.uiowa.AcknowledgementsTagLib.skill;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class SkillPersonId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SkillPersonId.class);


	public int doStartTag() throws JspException {
		try {
			Skill theSkill = (Skill)findAncestorWithClass(this, Skill.class);
			if (!theSkill.commitNeeded) {
				pageContext.getOut().print(theSkill.getPersonId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Skill for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Skill for personId tag ");
		}
		return SKIP_BODY;
	}

	public int getPersonId() throws JspTagException {
		try {
			Skill theSkill = (Skill)findAncestorWithClass(this, Skill.class);
			return theSkill.getPersonId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Skill for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Skill for personId tag ");
		}
	}

	public void setPersonId(int personId) throws JspTagException {
		try {
			Skill theSkill = (Skill)findAncestorWithClass(this, Skill.class);
			theSkill.setPersonId(personId);
		} catch (Exception e) {
			log.error("Can't find enclosing Skill for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Skill for personId tag ");
		}
	}

}
