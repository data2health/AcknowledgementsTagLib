package edu.uiowa.AcknowledgementsTagLib.publicationComponentMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PublicationComponentMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PublicationComponentMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			PublicationComponentMention thePublicationComponentMention = (PublicationComponentMention)findAncestorWithClass(this, PublicationComponentMention.class);
			if (!thePublicationComponentMention.commitNeeded) {
				pageContext.getOut().print(thePublicationComponentMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing PublicationComponentMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponentMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			PublicationComponentMention thePublicationComponentMention = (PublicationComponentMention)findAncestorWithClass(this, PublicationComponentMention.class);
			return thePublicationComponentMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing PublicationComponentMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponentMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			PublicationComponentMention thePublicationComponentMention = (PublicationComponentMention)findAncestorWithClass(this, PublicationComponentMention.class);
			thePublicationComponentMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing PublicationComponentMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponentMention for pmcid tag ");
		}
	}

}
