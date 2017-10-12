package edu.uiowa.AcknowledgementsTagLib.location;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class LocationLocation extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(LocationLocation.class);


	public int doStartTag() throws JspException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			if (!theLocation.commitNeeded) {
				pageContext.getOut().print(theLocation.getLocation());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Location for location tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for location tag ");
		}
		return SKIP_BODY;
	}

	public String getLocation() throws JspTagException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			return theLocation.getLocation();
		} catch (Exception e) {
			log.error(" Can't find enclosing Location for location tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for location tag ");
		}
	}

	public void setLocation(String location) throws JspTagException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			theLocation.setLocation(location);
		} catch (Exception e) {
			log.error("Can't find enclosing Location for location tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for location tag ");
		}
	}

}
