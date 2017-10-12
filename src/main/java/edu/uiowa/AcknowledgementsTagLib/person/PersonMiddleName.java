package edu.uiowa.AcknowledgementsTagLib.person;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PersonMiddleName extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PersonMiddleName.class);


	public int doStartTag() throws JspException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (!thePerson.commitNeeded) {
				pageContext.getOut().print(thePerson.getMiddleName());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Person for middleName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for middleName tag ");
		}
		return SKIP_BODY;
	}

	public String getMiddleName() throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			return thePerson.getMiddleName();
		} catch (Exception e) {
			log.error(" Can't find enclosing Person for middleName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for middleName tag ");
		}
	}

	public void setMiddleName(String middleName) throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			thePerson.setMiddleName(middleName);
		} catch (Exception e) {
			log.error("Can't find enclosing Person for middleName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for middleName tag ");
		}
	}

}
