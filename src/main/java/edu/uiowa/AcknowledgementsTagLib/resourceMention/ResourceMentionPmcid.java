package edu.uiowa.AcknowledgementsTagLib.resourceMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ResourceMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResourceMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			ResourceMention theResourceMention = (ResourceMention)findAncestorWithClass(this, ResourceMention.class);
			if (!theResourceMention.commitNeeded) {
				pageContext.getOut().print(theResourceMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing ResourceMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing ResourceMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			ResourceMention theResourceMention = (ResourceMention)findAncestorWithClass(this, ResourceMention.class);
			return theResourceMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing ResourceMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing ResourceMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			ResourceMention theResourceMention = (ResourceMention)findAncestorWithClass(this, ResourceMention.class);
			theResourceMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing ResourceMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing ResourceMention for pmcid tag ");
		}
	}

}
