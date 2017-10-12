package edu.uiowa.AcknowledgementsTagLib.organismMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganismMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganismMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			OrganismMention theOrganismMention = (OrganismMention)findAncestorWithClass(this, OrganismMention.class);
			if (!theOrganismMention.commitNeeded) {
				pageContext.getOut().print(theOrganismMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OrganismMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganismMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			OrganismMention theOrganismMention = (OrganismMention)findAncestorWithClass(this, OrganismMention.class);
			return theOrganismMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing OrganismMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganismMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			OrganismMention theOrganismMention = (OrganismMention)findAncestorWithClass(this, OrganismMention.class);
			theOrganismMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing OrganismMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganismMention for pmcid tag ");
		}
	}

}
