package edu.uiowa.AcknowledgementsTagLib.skill;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class SkillTechniqueId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SkillTechniqueId.class);


	public int doStartTag() throws JspException {
		try {
			Skill theSkill = (Skill)findAncestorWithClass(this, Skill.class);
			if (!theSkill.commitNeeded) {
				pageContext.getOut().print(theSkill.getTechniqueId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Skill for techniqueId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Skill for techniqueId tag ");
		}
		return SKIP_BODY;
	}

	public int getTechniqueId() throws JspTagException {
		try {
			Skill theSkill = (Skill)findAncestorWithClass(this, Skill.class);
			return theSkill.getTechniqueId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Skill for techniqueId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Skill for techniqueId tag ");
		}
	}

	public void setTechniqueId(int techniqueId) throws JspTagException {
		try {
			Skill theSkill = (Skill)findAncestorWithClass(this, Skill.class);
			theSkill.setTechniqueId(techniqueId);
		} catch (Exception e) {
			log.error("Can't find enclosing Skill for techniqueId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Skill for techniqueId tag ");
		}
	}

}
