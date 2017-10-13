package edu.uiowa.AcknowledgementsTagLib.collaborant;


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
import edu.uiowa.AcknowledgementsTagLib.collaboration.Collaboration;

@SuppressWarnings("serial")
public class CollaborantIterator extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int organizationId = 0;
    int collaborationId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(CollaborantIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean useOrganization = false;
   boolean useCollaboration = false;

	public static String collaborantCountByOrganization(String ID) throws JspTagException {
		int count = 0;
		CollaborantIterator theIterator = new CollaborantIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.collaborant where 1=1"
						+ " and organization_id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Collaborant iterator", e);
			throw new JspTagException("Error: JDBC error generating Collaborant iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean organizationHasCollaborant(String ID) throws JspTagException {
		return ! collaborantCountByOrganization(ID).equals("0");
	}

	public static String collaborantCountByCollaboration(String ID) throws JspTagException {
		int count = 0;
		CollaborantIterator theIterator = new CollaborantIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.collaborant where 1=1"
						+ " and collaboration_id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Collaborant iterator", e);
			throw new JspTagException("Error: JDBC error generating Collaborant iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean collaborationHasCollaborant(String ID) throws JspTagException {
		return ! collaborantCountByCollaboration(ID).equals("0");
	}

	public static Boolean collaborantExists (String pmcid, String organizationId, String collaborationId) throws JspTagException {
		int count = 0;
		CollaborantIterator theIterator = new CollaborantIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.collaborant where 1=1"
						+ " and pmcid = ?"
						+ " and organization_id = ?"
						+ " and collaboration_id = ?"
						);

			stat.setInt(1,Integer.parseInt(pmcid));
			stat.setInt(2,Integer.parseInt(organizationId));
			stat.setInt(3,Integer.parseInt(collaborationId));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Collaborant iterator", e);
			throw new JspTagException("Error: JDBC error generating Collaborant iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean organizationCollaborationExists (String ID) throws JspTagException {
		int count = 0;
		CollaborantIterator theIterator = new CollaborantIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.collaborant where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Collaborant iterator", e);
			throw new JspTagException("Error: JDBC error generating Collaborant iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
		Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
		if (theOrganization!= null)
			parentEntities.addElement(theOrganization);
		Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
		if (theCollaboration!= null)
			parentEntities.addElement(theCollaboration);

		if (theOrganization == null) {
		} else {
			organizationId = theOrganization.getID();
		}
		if (theCollaboration == null) {
		} else {
			collaborationId = theCollaboration.getID();
		}


      try {
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (organizationId == 0 ? "" : " and organization_id = ?")
                                                        + (collaborationId == 0 ? "" : " and collaboration_id = ?")
                                                        +  generateLimitCriteria());
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            if (collaborationId != 0) stat.setInt(webapp_keySeq++, collaborationId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT pubmed_central_ack_stanford.collaborant.pmcid, pubmed_central_ack_stanford.collaborant.organization_id, pubmed_central_ack_stanford.collaborant.collaboration_id from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (organizationId == 0 ? "" : " and organization_id = ?")
                                                        + (collaborationId == 0 ? "" : " and collaboration_id = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (organizationId != 0) stat.setInt(webapp_keySeq++, organizationId);
            if (collaborationId != 0) stat.setInt(webapp_keySeq++, collaborationId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pmcid = rs.getInt(1);
                organizationId = rs.getInt(2);
                collaborationId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating Collaborant iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating Collaborant iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("pubmed_central_ack_stanford.collaborant");
       if (useOrganization)
          theBuffer.append(", pubmed_central_ack_stanford.organization");
       if (useCollaboration)
          theBuffer.append(", pubmed_central_ack_stanford.collaboration");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (useOrganization)
          theBuffer.append(" and organization.ID = collaborant.organization_id");
       if (useCollaboration)
          theBuffer.append(" and collaboration.ID = collaborant.collaboration_id");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "pmcid,organization_id,collaboration_id";
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
                collaborationId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across Collaborant", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across Collaborant");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending Collaborant iterator",e);
            throw new JspTagException("Error: JDBC error ending Collaborant iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        pmcid = 0;
        organizationId = 0;
        collaborationId = 0;
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

   public boolean getUseCollaboration() {
        return useCollaboration;
    }

    public void setUseCollaboration(boolean useCollaboration) {
        this.useCollaboration = useCollaboration;
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

	public int getCollaborationId () {
		return collaborationId;
	}

	public void setCollaborationId (int collaborationId) {
		this.collaborationId = collaborationId;
	}

	public int getActualCollaborationId () {
		return collaborationId;
	}
}
