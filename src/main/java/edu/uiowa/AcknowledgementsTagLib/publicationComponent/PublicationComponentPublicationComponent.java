package edu.uiowa.AcknowledgementsTagLib.publicationComponent;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PublicationComponentPublicationComponent extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PublicationComponentPublicationComponent.class);


	public int doStartTag() throws JspException {
		try {
			PublicationComponent thePublicationComponent = (PublicationComponent)findAncestorWithClass(this, PublicationComponent.class);
			if (!thePublicationComponent.commitNeeded) {
				pageContext.getOut().print(thePublicationComponent.getPublicationComponent());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing PublicationComponent for publicationComponent tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponent for publicationComponent tag ");
		}
		return SKIP_BODY;
	}

	public String getPublicationComponent() throws JspTagException {
		try {
			PublicationComponent thePublicationComponent = (PublicationComponent)findAncestorWithClass(this, PublicationComponent.class);
			return thePublicationComponent.getPublicationComponent();
		} catch (Exception e) {
			log.error(" Can't find enclosing PublicationComponent for publicationComponent tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponent for publicationComponent tag ");
		}
	}

	public void setPublicationComponent(String publicationComponent) throws JspTagException {
		try {
			PublicationComponent thePublicationComponent = (PublicationComponent)findAncestorWithClass(this, PublicationComponent.class);
			thePublicationComponent.setPublicationComponent(publicationComponent);
		} catch (Exception e) {
			log.error("Can't find enclosing PublicationComponent for publicationComponent tag ", e);
			throw new JspTagException("Error: Can't find enclosing PublicationComponent for publicationComponent tag ");
		}
	}

}
