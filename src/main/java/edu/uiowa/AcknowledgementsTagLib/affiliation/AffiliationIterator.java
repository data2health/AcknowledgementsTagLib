package edu.uiowa.AcknowledgementsTagLib.affiliation;


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
import edu.uiowa.AcknowledgementsTagLib.organization.Organization;

@SuppressWarnings("serial")
public class AffiliationIterator extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int personId = 0;
    int organizationId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(AffiliationIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean usePerson = false;
   boolean useOrganization = false;

	public static String affiliationCountByPerson(String ID) throws JspTagException {
		int count = 0;
		AffiliationIterator theIterator = new AffiliationIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.affiliation where 1=1"
						+ " and person_id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Affiliation iterator", e);
			throw new JspTagException("Error: JDBC error generating Affiliation iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean personHasAffiliation(String ID) throws JspTagException {
		return ! affiliationCountByPerson(ID).equals("0");
	}

	public static String affiliationCountByOrganization(String ID) throws JspTagException {
		int count = 0;
		AffiliationIterator theIterator = new AffiliationIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.affiliation where 1=1"
						+ " and organization_id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Affiliation iterator", e);
			throw new JspTagException("Error: JDBC error generating Affiliation iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean organizationHasAffiliation(String ID) throws JspTagException {
		return ! affiliationCountByOrganization(ID).equals("0");
	}

	public static Boolean affiliationExists (String pmcid, String personId, String organizationId) throws JspTagException {
		int count = 0;
		AffiliationIterator theIterator = new AffiliationIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.affiliation where 1=1"
						+ " and pmcid = ?"
						+ " and person_id = ?"
						+ " and organization_id = ?"
						);

			stat.setInt(1,Integer.parseInt(pmcid));
			stat.setInt(2,Integer.parseInt(personId));
			stat.setInt(3,Integer.parseInt(organizationId));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Affiliation iterator", e);
			throw new JspTagException("Error: JDBC error generating Affiliation iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean personOrganizationExists (String ID) throws JspTagException {
		int count = 0;
		AffiliationIterator theIterator = new AffiliationIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.affiliation where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Affiliation iterator", e);
			throw new JspTagException("Error: JDBC error generating Affiliation iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
		Person thePerson = (Person)findAncestorWithClass(this, Person.class);
		if (thePerson!= null)
			parentEntities.addElement(thePerson);
		Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
		if (theOrganization!= null)
			parentEntities.addElement(theOrganization);

		if (thePerson == null) {
		} else {
			personId = thePerson.getID();
		}
		if (theOrganization == null) {
		} else {
			organizationId = theOrganization.getID();
		}


      try {
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (personId == 0 ? "" : " and person_id = ?")
                                                        + (organizationId == 0 ? "" : " and organization_id = ?")
                                                        +  generateLimitCriteria());
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT pubmed_central_ack_stanford.affiliation.pmcid, pubmed_central_ack_stanford.affiliation.person_id, pubmed_central_ack_stanford.affiliation.organization_id from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (personId == 0 ? "" : " and person_id = ?")
                                                        + (organizationId == 0 ? "" : " and organization_id = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pmcid = rs.getInt(1);
                personId = rs.getInt(2);
                organizationId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating Affiliation iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating Affiliation iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("pubmed_central_ack_stanford.affiliation");
       if (usePerson)
          theBuffer.append(", pubmed_central_ack_stanford.person");
       if (useOrganization)
          theBuffer.append(", pubmed_central_ack_stanford.organization");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (usePerson)
          theBuffer.append(" and person.ID = affiliation.person_id");
       if (useOrganization)
          theBuffer.append(" and organization.ID = affiliation.organization_id");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "pmcid,person_id,organization_id";
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
                organizationId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across Affiliation", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across Affiliation");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending Affiliation iterator",e);
            throw new JspTagException("Error: JDBC error ending Affiliation iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        pmcid = 0;
        personId = 0;
        organizationId = 0;
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

   public boolean getUseOrganization() {
        return useOrganization;
    }

    public void setUseOrganization(boolean useOrganization) {
        this.useOrganization = useOrganization;
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

	public int getOrganizationId () {
		return organizationId;
	}

	public void setOrganizationId (int organizationId) {
		this.organizationId = organizationId;
	}

	public int getActualOrganizationId () {
		return organizationId;
	}
}
