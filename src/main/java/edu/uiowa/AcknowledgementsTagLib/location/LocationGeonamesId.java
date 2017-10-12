package edu.uiowa.AcknowledgementsTagLib.location;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class LocationGeonamesId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(LocationGeonamesId.class);


	public int doStartTag() throws JspException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			if (!theLocation.commitNeeded) {
				pageContext.getOut().print(theLocation.getGeonamesId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Location for geonamesId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for geonamesId tag ");
		}
		return SKIP_BODY;
	}

	public int getGeonamesId() throws JspTagException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			return theLocation.getGeonamesId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Location for geonamesId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for geonamesId tag ");
		}
	}

	public void setGeonamesId(int geonamesId) throws JspTagException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			theLocation.setGeonamesId(geonamesId);
		} catch (Exception e) {
			log.error("Can't find enclosing Location for geonamesId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for geonamesId tag ");
		}
	}

}
