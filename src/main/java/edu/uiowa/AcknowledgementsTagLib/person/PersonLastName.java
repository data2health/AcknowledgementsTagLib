package edu.uiowa.AcknowledgementsTagLib.person;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PersonLastName extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PersonLastName.class);


	public int doStartTag() throws JspException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (!thePerson.commitNeeded) {
				pageContext.getOut().print(thePerson.getLastName());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Person for lastName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for lastName tag ");
		}
		return SKIP_BODY;
	}

	public String getLastName() throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			return thePerson.getLastName();
		} catch (Exception e) {
			log.error(" Can't find enclosing Person for lastName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for lastName tag ");
		}
	}

	public void setLastName(String lastName) throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			thePerson.setLastName(lastName);
		} catch (Exception e) {
			log.error("Can't find enclosing Person for lastName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for lastName tag ");
		}
	}

}
