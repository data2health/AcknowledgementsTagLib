package edu.uiowa.AcknowledgementsTagLib.location;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class LocationID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(LocationID.class);


	public int doStartTag() throws JspException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			if (!theLocation.commitNeeded) {
				pageContext.getOut().print(theLocation.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Location for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			return theLocation.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Location for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			theLocation.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Location for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Location for ID tag ");
		}
	}

}
