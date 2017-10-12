package edu.uiowa.AcknowledgementsTagLib.organismMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganismMentionOrganismId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganismMentionOrganismId.class);


	public int doStartTag() throws JspException {
		try {
			OrganismMention theOrganismMention = (OrganismMention)findAncestorWithClass(this, OrganismMention.class);
			if (!theOrganismMention.commitNeeded) {
				pageContext.getOut().print(theOrganismMention.getOrganismId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OrganismMention for organismId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganismMention for organismId tag ");
		}
		return SKIP_BODY;
	}

	public int getOrganismId() throws JspTagException {
		try {
			OrganismMention theOrganismMention = (OrganismMention)findAncestorWithClass(this, OrganismMention.class);
			return theOrganismMention.getOrganismId();
		} catch (Exception e) {
			log.error(" Can't find enclosing OrganismMention for organismId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganismMention for organismId tag ");
		}
	}

	public void setOrganismId(int organismId) throws JspTagException {
		try {
			OrganismMention theOrganismMention = (OrganismMention)findAncestorWithClass(this, OrganismMention.class);
			theOrganismMention.setOrganismId(organismId);
		} catch (Exception e) {
			log.error("Can't find enclosing OrganismMention for organismId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganismMention for organismId tag ");
		}
	}

}
