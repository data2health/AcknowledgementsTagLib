package edu.uiowa.AcknowledgementsTagLib.provider;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibBodyTagSupport;
import edu.uiowa.AcknowledgementsTagLib.person.Person;
import edu.uiowa.AcknowledgementsTagLib.resource.Resource;

@SuppressWarnings("serial")
public class ProviderIterator extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int personId = 0;
    int resourceId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(ProviderIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean usePerson = false;
   boolean useResource = false;

	public static String providerCountByPerson(String ID) throws JspTagException {
		int count = 0;
		ProviderIterator theIterator = new ProviderIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.provider where 1=1"
						+ " and person_id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Provider iterator", e);
			throw new JspTagException("Error: JDBC error generating Provider iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean personHasProvider(String ID) throws JspTagException {
		return ! providerCountByPerson(ID).equals("0");
	}

	public static String providerCountByResource(String ID) throws JspTagException {
		int count = 0;
		ProviderIterator theIterator = new ProviderIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.provider where 1=1"
						+ " and resource_id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Provider iterator", e);
			throw new JspTagException("Error: JDBC error generating Provider iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean resourceHasProvider(String ID) throws JspTagException {
		return ! providerCountByResource(ID).equals("0");
	}

	public static Boolean providerExists (String pmcid, String personId, String resourceId) throws JspTagException {
		int count = 0;
		ProviderIterator theIterator = new ProviderIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.provider where 1=1"
						+ " and pmcid = ?"
						+ " and person_id = ?"
						+ " and resource_id = ?"
						);

			stat.setInt(1,Integer.parseInt(pmcid));
			stat.setInt(2,Integer.parseInt(personId));
			stat.setInt(3,Integer.parseInt(resourceId));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Provider iterator", e);
			throw new JspTagException("Error: JDBC error generating Provider iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean personResourceExists (String ID) throws JspTagException {
		int count = 0;
		ProviderIterator theIterator = new ProviderIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.provider where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Provider iterator", e);
			throw new JspTagException("Error: JDBC error generating Provider iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
		Person thePerson = (Person)findAncestorWithClass(this, Person.class);
		if (thePerson!= null)
			parentEntities.addElement(thePerson);
		Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
		if (theResource!= null)
			parentEntities.addElement(theResource);

		if (thePerson == null) {
		} else {
			personId = thePerson.getID();
		}
		if (theResource == null) {
		} else {
			resourceId = theResource.getID();
		}


      try {
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (personId == 0 ? "" : " and person_id = ?")
                                                        + (resourceId == 0 ? "" : " and resource_id = ?")
                                                        +  generateLimitCriteria());
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            if (resourceId != 0) stat.setInt(webapp_keySeq++, resourceId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT pubmed_central_ack_stanford.provider.pmcid, pubmed_central_ack_stanford.provider.person_id, pubmed_central_ack_stanford.provider.resource_id from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (personId == 0 ? "" : " and person_id = ?")
                                                        + (resourceId == 0 ? "" : " and resource_id = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            if (resourceId != 0) stat.setInt(webapp_keySeq++, resourceId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pmcid = rs.getInt(1);
                personId = rs.getInt(2);
                resourceId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating Provider iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating Provider iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("pubmed_central_ack_stanford.provider");
       if (usePerson)
          theBuffer.append(", pubmed_central_ack_stanford.person");
       if (useResource)
          theBuffer.append(", pubmed_central_ack_stanford.resource");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (usePerson)
          theBuffer.append(" and person.ID = provider.person_id");
       if (useResource)
          theBuffer.append(" and resource.ID = provider.resource_id");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "pmcid,person_id,resource_id";
        }
    }

    private String generateLimitCriteria() {
        if (limitCriteria > 0) {
            return " limit " + limitCriteria;
        } else {
            return "";
        }
    }

    public int doAfterBody() throws JspTagException {
        try {
            if (rs.next()) {
                pmcid = rs.getInt(1);
                personId = rs.getInt(2);
                resourceId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across Provider", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across Provider");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending Provider iterator",e);
            throw new JspTagException("Error: JDBC error ending Provider iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        pmcid = 0;
        personId = 0;
        resourceId = 0;
        parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

        this.rs = null;
        this.stat = null;
        this.sortCriteria = null;
        this.var = null;
        this.rsCount = 0;
    }

    public String getSortCriteria() {
        return sortCriteria;
    }

    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public int getLimitCriteria() {
        return limitCriteria;
    }

    public void setLimitCriteria(int limitCriteria) {
        this.limitCriteria = limitCriteria;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }


   public boolean getUsePerson() {
        return usePerson;
    }

    public void setUsePerson(boolean usePerson) {
        this.usePerson = usePerson;
    }

   public boolean getUseResource() {
        return useResource;
    }

    public void setUseResource(boolean useResource) {
        this.useResource = useResource;
    }



	public int getPmcid () {
		return pmcid;
	}

	public void setPmcid (int pmcid) {
		this.pmcid = pmcid;
	}

	public int getActualPmcid () {
		return pmcid;
	}

	public int getPersonId () {
		return personId;
	}

	public void setPersonId (int personId) {
		this.personId = personId;
	}

	public int getActualPersonId () {
		return personId;
	}

	public int getResourceId () {
		return resourceId;
	}

	public void setResourceId (int resourceId) {
		this.resourceId = resourceId;
	}

	public int getActualResourceId () {
		return resourceId;
	}
}
