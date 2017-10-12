package edu.uiowa.AcknowledgementsTagLib.locationMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class LocationMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(LocationMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			LocationMention theLocationMention = (LocationMention)findAncestorWithClass(this, LocationMention.class);
			if (!theLocationMention.commitNeeded) {
				pageContext.getOut().print(theLocationMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing LocationMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing LocationMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			LocationMention theLocationMention = (LocationMention)findAncestorWithClass(this, LocationMention.class);
			return theLocationMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing LocationMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing LocationMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			LocationMention theLocationMention = (LocationMention)findAncestorWithClass(this, LocationMention.class);
			theLocationMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing LocationMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing LocationMention for pmcid tag ");
		}
	}

}
