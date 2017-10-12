package edu.uiowa.AcknowledgementsTagLib.resourceMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class ResourceMentionResourceId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResourceMentionResourceId.class);


	public int doStartTag() throws JspException {
		try {
			ResourceMention theResourceMention = (ResourceMention)findAncestorWithClass(this, ResourceMention.class);
			if (!theResourceMention.commitNeeded) {
				pageContext.getOut().print(theResourceMention.getResourceId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing ResourceMention for resourceId tag ", e);
			throw new JspTagException("Error: Can't find enclosing ResourceMention for resourceId tag ");
		}
		return SKIP_BODY;
	}

	public int getResourceId() throws JspTagException {
		try {
			ResourceMention theResourceMention = (ResourceMention)findAncestorWithClass(this, ResourceMention.class);
			return theResourceMention.getResourceId();
		} catch (Exception e) {
			log.error(" Can't find enclosing ResourceMention for resourceId tag ", e);
			throw new JspTagException("Error: Can't find enclosing ResourceMention for resourceId tag ");
		}
	}

	public void setResourceId(int resourceId) throws JspTagException {
		try {
			ResourceMention theResourceMention = (ResourceMention)findAncestorWithClass(this, ResourceMention.class);
			theResourceMention.setResourceId(resourceId);
		} catch (Exception e) {
			log.error("Can't find enclosing ResourceMention for resourceId tag ", e);
			throw new JspTagException("Error: Can't find enclosing ResourceMention for resourceId tag ");
		}
	}

}
