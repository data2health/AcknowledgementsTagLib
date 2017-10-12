package edu.uiowa.AcknowledgementsTagLib.organicChemicalMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganicChemicalMentionOrganicChemicalId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganicChemicalMentionOrganicChemicalId.class);


	public int doStartTag() throws JspException {
		try {
			OrganicChemicalMention theOrganicChemicalMention = (OrganicChemicalMention)findAncestorWithClass(this, OrganicChemicalMention.class);
			if (!theOrganicChemicalMention.commitNeeded) {
				pageContext.getOut().print(theOrganicChemicalMention.getOrganicChemicalId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemicalMention for organicChemicalId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemicalMention for organicChemicalId tag ");
		}
		return SKIP_BODY;
	}

	public int getOrganicChemicalId() throws JspTagException {
		try {
			OrganicChemicalMention theOrganicChemicalMention = (OrganicChemicalMention)findAncestorWithClass(this, OrganicChemicalMention.class);
			return theOrganicChemicalMention.getOrganicChemicalId();
		} catch (Exception e) {
			log.error(" Can't find enclosing OrganicChemicalMention for organicChemicalId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemicalMention for organicChemicalId tag ");
		}
	}

	public void setOrganicChemicalId(int organicChemicalId) throws JspTagException {
		try {
			OrganicChemicalMention theOrganicChemicalMention = (OrganicChemicalMention)findAncestorWithClass(this, OrganicChemicalMention.class);
			theOrganicChemicalMention.setOrganicChemicalId(organicChemicalId);
		} catch (Exception e) {
			log.error("Can't find enclosing OrganicChemicalMention for organicChemicalId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganicChemicalMention for organicChemicalId tag ");
		}
	}

}
