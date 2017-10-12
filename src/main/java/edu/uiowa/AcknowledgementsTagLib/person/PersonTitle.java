package edu.uiowa.AcknowledgementsTagLib.person;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PersonTitle extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PersonTitle.class);


	public int doStartTag() throws JspException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (!thePerson.commitNeeded) {
				pageContext.getOut().print(thePerson.getTitle());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Person for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for title tag ");
		}
		return SKIP_BODY;
	}

	public String getTitle() throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			return thePerson.getTitle();
		} catch (Exception e) {
			log.error(" Can't find enclosing Person for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for title tag ");
		}
	}

	public void setTitle(String title) throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			thePerson.setTitle(title);
		} catch (Exception e) {
			log.error("Can't find enclosing Person for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for title tag ");
		}
	}

}
