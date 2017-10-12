package edu.uiowa.AcknowledgementsTagLib.supporter;


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
import edu.uiowa.AcknowledgementsTagLib.support.Support;
import edu.uiowa.AcknowledgementsTagLib.organization.Organization;

@SuppressWarnings("serial")
public class SupporterIterator extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int supportId = 0;
    int organizationId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(SupporterIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean useSupport = false;
   boolean useOrganization = false;

	public static String supporterCountBySupport(String ID) throws JspTagException {
		int count = 0;
		SupporterIterator theIterator = new SupporterIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.supporter where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Supporter iterator", e);
			throw new JspTagException("Error: JDBC error generating Supporter iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean supportHasSupporter(String ID) throws JspTagException {
		return ! supporterCountBySupport(ID).equals("0");
	}

	public static String supporterCountByOrganization(String ID) throws JspTagException {
		int count = 0;
		SupporterIterator theIterator = new SupporterIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.supporter where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Supporter iterator", e);
			throw new JspTagException("Error: JDBC error generating Supporter iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean organizationHasSupporter(String ID) throws JspTagException {
		return ! supporterCountByOrganization(ID).equals("0");
	}

	public static Boolean supporterExists (String pmcid, String supportId, String organizationId) throws JspTagException {
		int count = 0;
		SupporterIterator theIterator = new SupporterIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.supporter where 1=1"
						+ " and pmcid = ?"
						+ " and support_id = ?"
						+ " and organization_id = ?"
						);

			stat.setInt(1,Integer.parseInt(pmcid));
			stat.setInt(2,Integer.parseInt(supportId));
			stat.setInt(3,Integer.parseInt(organizationId));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Supporter iterator", e);
			throw new JspTagException("Error: JDBC error generating Supporter iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean supportOrganizationExists (String ID) throws JspTagException {
		int count = 0;
		SupporterIterator theIterator = new SupporterIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.supporter where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Supporter iterator", e);
			throw new JspTagException("Error: JDBC error generating Supporter iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
		Support theSupport = (Support)findAncestorWithClass(this, Support.class);
		if (theSupport!= null)
			parentEntities.addElement(theSupport);
		Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
		if (theOrganization!= null)
			parentEntities.addElement(theOrganization);

		if (theSupport == null) {
		} else {
			supportId = theSupport.getID();
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
                                                        + (supportId == 0 ? "" : " and support_id = ?")
                                                        + (organizationId == 0 ? "" : " and organization_id = ?")
                                                        +  generateLimitCriteria());
            if (supportId != 0) stat.setInt(webapp_keySeq++, supportId);
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT pubmed_central_ack_stanford.supporter.pmcid, pubmed_central_ack_stanford.supporter.support_id, pubmed_central_ack_stanford.supporter.organization_id from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (supportId == 0 ? "" : " and support_id = ?")
                                                        + (organizationId == 0 ? "" : " and organization_id = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (supportId != 0) stat.setInt(webapp_keySeq++, supportId);
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pmcid = rs.getInt(1);
                supportId = rs.getInt(2);
                organizationId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating Supporter iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating Supporter iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("pubmed_central_ack_stanford.supporter");
       if (useSupport)
          theBuffer.append(", pubmed_central_ack_stanford.support");
       if (useOrganization)
          theBuffer.append(", pubmed_central_ack_stanford.organization");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (useSupport)
          theBuffer.append(" and support.ID = supporter.support_id");
       if (useOrganization)
          theBuffer.append(" and organization.ID = supporter.organization_id");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "pmcid,support_id,organization_id";
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
                supportId = rs.getInt(2);
                organizationId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across Supporter", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across Supporter");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending Supporter iterator",e);
            throw new JspTagException("Error: JDBC error ending Supporter iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        pmcid = 0;
        supportId = 0;
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


   public boolean getUseSupport() {
        return useSupport;
    }

    public void setUseSupport(boolean useSupport) {
        this.useSupport = useSupport;
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

	public int getSupportId () {
		return supportId;
	}

	public void setSupportId (int supportId) {
		this.supportId = supportId;
	}

	public int getActualSupportId () {
		return supportId;
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
