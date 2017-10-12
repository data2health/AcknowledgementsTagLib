package edu.uiowa.AcknowledgementsTagLib.publicationComponentMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PublicationComponentMentionPublicationComponentId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PublicationComponentMentionPublicationComponentId.class);


	public int doStartTag() throws JspException {
		try {
			PublicationComponentMention thePublicationComponentMention = (PublicationComponentMention)findAncestorWithClass(this, PublicationComponentMention.class);
			if (!thePublicationComponentMention.commitNeeded) {
				pageContext.getOut().print(thePublicationComponentMention.getPublicationComponentId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing PublicationComponentMention for publicationComponentId tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponentMention for publicationComponentId tag ");
		}
		return SKIP_BODY;
	}

	public int getPublicationComponentId() throws JspTagException {
		try {
			PublicationComponentMention thePublicationComponentMention = (PublicationComponentMention)findAncestorWithClass(this, PublicationComponentMention.class);
			return thePublicationComponentMention.getPublicationComponentId();
		} catch (Exception e) {
			log.error(" Can't find enclosing PublicationComponentMention for publicationComponentId tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponentMention for publicationComponentId tag ");
		}
	}

	public void setPublicationComponentId(int publicationComponentId) throws JspTagException {
		try {
			PublicationComponentMention thePublicationComponentMention = (PublicationComponentMention)findAncestorWithClass(this, PublicationComponentMention.class);
			thePublicationComponentMention.setPublicationComponentId(publicationComponentId);
		} catch (Exception e) {
			log.error("Can't find enclosing PublicationComponentMention for publicationComponentId tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponentMention for publicationComponentId tag ");
		}
	}

}
