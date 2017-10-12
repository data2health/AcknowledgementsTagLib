package edu.uiowa.AcknowledgementsTagLib.person;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PersonFirstName extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PersonFirstName.class);


	public int doStartTag() throws JspException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (!thePerson.commitNeeded) {
				pageContext.getOut().print(thePerson.getFirstName());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Person for firstName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for firstName tag ");
		}
		return SKIP_BODY;
	}

	public String getFirstName() throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			return thePerson.getFirstName();
		} catch (Exception e) {
			log.error(" Can't find enclosing Person for firstName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for firstName tag ");
		}
	}

	public void setFirstName(String firstName) throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			thePerson.setFirstName(firstName);
		} catch (Exception e) {
			log.error("Can't find enclosing Person for firstName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for firstName tag ");
		}
	}

}
