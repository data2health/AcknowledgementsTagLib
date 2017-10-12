package edu.uiowa.AcknowledgementsTagLib.location;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class LocationGeonamesMatchString extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(LocationGeonamesMatchString.class);


	public int doStartTag() throws JspException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			if (!theLocation.commitNeeded) {
				pageContext.getOut().print(theLocation.getGeonamesMatchString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Location for geonamesMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for geonamesMatchString tag ");
		}
		return SKIP_BODY;
	}

	public String getGeonamesMatchString() throws JspTagException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			return theLocation.getGeonamesMatchString();
		} catch (Exception e) {
			log.error(" Can't find enclosing Location for geonamesMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for geonamesMatchString tag ");
		}
	}

	public void setGeonamesMatchString(String geonamesMatchString) throws JspTagException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			theLocation.setGeonamesMatchString(geonamesMatchString);
		} catch (Exception e) {
			log.error("Can't find enclosing Location for geonamesMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for geonamesMatchString tag ");
		}
	}

}
