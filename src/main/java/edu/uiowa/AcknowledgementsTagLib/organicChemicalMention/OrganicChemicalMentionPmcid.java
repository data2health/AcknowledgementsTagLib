package edu.uiowa.AcknowledgementsTagLib.organicChemicalMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganicChemicalMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganicChemicalMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			OrganicChemicalMention theOrganicChemicalMention = (OrganicChemicalMention)findAncestorWithClass(this, OrganicChemicalMention.class);
			if (!theOrganicChemicalMention.commitNeeded) {
				pageContext.getOut().print(theOrganicChemicalMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemicalMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemicalMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			OrganicChemicalMention theOrganicChemicalMention = (OrganicChemicalMention)findAncestorWithClass(this, OrganicChemicalMention.class);
			return theOrganicChemicalMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing OrganicChemicalMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemicalMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			OrganicChemicalMention theOrganicChemicalMention = (OrganicChemicalMention)findAncestorWithClass(this, OrganicChemicalMention.class);
			theOrganicChemicalMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemicalMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemicalMention for pmcid tag ");
		}
	}

}
