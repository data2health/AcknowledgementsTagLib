package edu.uiowa.AcknowledgementsTagLib.locationMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class LocationMentionLocationId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(LocationMentionLocationId.class);


	public int doStartTag() throws JspException {
		try {
			LocationMention theLocationMention = (LocationMention)findAncestorWithClass(this, LocationMention.class);
			if (!theLocationMention.commitNeeded) {
				pageContext.getOut().print(theLocationMention.getLocationId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing LocationMention for locationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing LocationMention for locationId tag ");
		}
		return SKIP_BODY;
	}

	public int getLocationId() throws JspTagException {
		try {
			LocationMention theLocationMention = (LocationMention)findAncestorWithClass(this, LocationMention.class);
			return theLocationMention.getLocationId();
		} catch (Exception e) {
			log.error(" Can't find enclosing LocationMention for locationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing LocationMention for locationId tag ");
		}
	}

	public void setLocationId(int locationId) throws JspTagException {
		try {
			LocationMention theLocationMention = (LocationMention)findAncestorWithClass(this, LocationMention.class);
			theLocationMention.setLocationId(locationId);
		} catch (Exception e) {
			log.error("Can't find enclosing LocationMention for locationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing LocationMention for locationId tag ");
		}
	}

}
