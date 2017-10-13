package edu.uiowa.AcknowledgementsTagLib.funder;


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
import edu.uiowa.AcknowledgementsTagLib.organization.Organization;
import edu.uiowa.AcknowledgementsTagLib.award.Award;

@SuppressWarnings("serial")
public class FunderIterator extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int organizationId = 0;
    int awardId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(FunderIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean useOrganization = false;
   boolean useAward = false;

	public static String funderCountByOrganization(String ID) throws JspTagException {
		int count = 0;
		FunderIterator theIterator = new FunderIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.funder where 1=1"
						+ " and organization_id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Funder iterator", e);
			throw new JspTagException("Error: JDBC error generating Funder iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean organizationHasFunder(String ID) throws JspTagException {
		return ! funderCountByOrganization(ID).equals("0");
	}

	public static String funderCountByAward(String ID) throws JspTagException {
		int count = 0;
		FunderIterator theIterator = new FunderIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.funder where 1=1"
						+ " and award_id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Funder iterator", e);
			throw new JspTagException("Error: JDBC error generating Funder iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean awardHasFunder(String ID) throws JspTagException {
		return ! funderCountByAward(ID).equals("0");
	}

	public static Boolean funderExists (String pmcid, String organizationId, String awardId) throws JspTagException {
		int count = 0;
		FunderIterator theIterator = new FunderIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.funder where 1=1"
						+ " and pmcid = ?"
						+ " and organization_id = ?"
						+ " and award_id = ?"
						);

			stat.setInt(1,Integer.parseInt(pmcid));
			stat.setInt(2,Integer.parseInt(organizationId));
			stat.setInt(3,Integer.parseInt(awardId));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Funder iterator", e);
			throw new JspTagException("Error: JDBC error generating Funder iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean organizationAwardExists (String ID) throws JspTagException {
		int count = 0;
		FunderIterator theIterator = new FunderIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.funder where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Funder iterator", e);
			throw new JspTagException("Error: JDBC error generating Funder iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
		Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
		if (theOrganization!= null)
			parentEntities.addElement(theOrganization);
		Award theAward = (Award)findAncestorWithClass(this, Award.class);
		if (theAward!= null)
			parentEntities.addElement(theAward);

		if (theOrganization == null) {
		} else {
			organizationId = theOrganization.getID();
		}
		if (theAward == null) {
		} else {
			awardId = theAward.getID();
		}


      try {
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (organizationId == 0 ? "" : " and organization_id = ?")
                                                        + (awardId == 0 ? "" : " and award_id = ?")
                                                        +  generateLimitCriteria());
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            if (awardId != 0) stat.setInt(webapp_keySeq++, awardId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT pubmed_central_ack_stanford.funder.pmcid, pubmed_central_ack_stanford.funder.organization_id, pubmed_central_ack_stanford.funder.award_id from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (organizationId == 0 ? "" : " and organization_id = ?")
                                                        + (awardId == 0 ? "" : " and award_id = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            if (awardId != 0) stat.setInt(webapp_keySeq++, awardId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pmcid = rs.getInt(1);
                organizationId = rs.getInt(2);
                awardId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating Funder iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating Funder iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("pubmed_central_ack_stanford.funder");
       if (useOrganization)
          theBuffer.append(", pubmed_central_ack_stanford.organization");
       if (useAward)
          theBuffer.append(", pubmed_central_ack_stanford.award");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (useOrganization)
          theBuffer.append(" and organization.ID = funder.organization_id");
       if (useAward)
          theBuffer.append(" and award.ID = funder.award_id");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "pmcid,organization_id,award_id";
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
                organizationId = rs.getInt(2);
                awardId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across Funder", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across Funder");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending Funder iterator",e);
            throw new JspTagException("Error: JDBC error ending Funder iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        pmcid = 0;
        organizationId = 0;
        awardId = 0;
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


   public boolean getUseOrganization() {
        return useOrganization;
    }

    public void setUseOrganization(boolean useOrganization) {
        this.useOrganization = useOrganization;
    }

   public boolean getUseAward() {
        return useAward;
    }

    public void setUseAward(boolean useAward) {
        this.useAward = useAward;
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

	public int getOrganizationId () {
		return organizationId;
	}

	public void setOrganizationId (int organizationId) {
		this.organizationId = organizationId;
	}

	public int getActualOrganizationId () {
		return organizationId;
	}

	public int getAwardId () {
		return awardId;
	}

	public void setAwardId (int awardId) {
		this.awardId = awardId;
	}

	public int getActualAwardId () {
		return awardId;
	}
}
