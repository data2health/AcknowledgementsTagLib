package edu.uiowa.AcknowledgementsTagLib.person;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PersonAppendix extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PersonAppendix.class);


	public int doStartTag() throws JspException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (!thePerson.commitNeeded) {
				pageContext.getOut().print(thePerson.getAppendix());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Person for appendix tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for appendix tag ");
		}
		return SKIP_BODY;
	}

	public String getAppendix() throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			return thePerson.getAppendix();
		} catch (Exception e) {
			log.error(" Can't find enclosing Person for appendix tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for appendix tag ");
		}
	}

	public void setAppendix(String appendix) throws JspTagException {
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			thePerson.setAppendix(appendix);
		} catch (Exception e) {
			log.error("Can't find enclosing Person for appendix tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for appendix tag ");
		}
	}

}
