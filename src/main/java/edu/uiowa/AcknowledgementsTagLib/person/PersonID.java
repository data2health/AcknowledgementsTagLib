package edu.uiowa.AcknowledgementsTagLib.person;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PersonID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PersonID.class);


	public int doStartTag() throws JspException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (!thePerson.commitNeeded) {
				pageContext.getOut().print(thePerson.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Person for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			return thePerson.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Person for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			thePerson.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Person for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for ID tag ");
		}
	}

}
