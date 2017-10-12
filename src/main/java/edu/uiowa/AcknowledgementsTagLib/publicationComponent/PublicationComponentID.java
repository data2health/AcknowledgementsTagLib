package edu.uiowa.AcknowledgementsTagLib.publicationComponent;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PublicationComponentID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PublicationComponentID.class);


	public int doStartTag() throws JspException {
		try {
			PublicationComponent thePublicationComponent = (PublicationComponent)findAncestorWithClass(this, PublicationComponent.class);
			if (!thePublicationComponent.commitNeeded) {
				pageContext.getOut().print(thePublicationComponent.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing PublicationComponent for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponent for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			PublicationComponent thePublicationComponent = (PublicationComponent)findAncestorWithClass(this, PublicationComponent.class);
			return thePublicationComponent.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing PublicationComponent for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponent for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			PublicationComponent thePublicationComponent = (PublicationComponent)findAncestorWithClass(this, PublicationComponent.class);
			thePublicationComponent.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing PublicationComponent for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponent for ID tag ");
		}
	}

}
