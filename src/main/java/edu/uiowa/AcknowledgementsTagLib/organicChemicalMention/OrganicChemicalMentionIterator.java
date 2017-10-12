package edu.uiowa.AcknowledgementsTagLib.organicChemicalMention;


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
import edu.uiowa.AcknowledgementsTagLib.organicChemical.OrganicChemical;

@SuppressWarnings("serial")
public class OrganicChemicalMentionIterator extends AcknowledgementsTagLibBodyTagSupport {
    int organicChemicalId = 0;
    int pmcid = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(OrganicChemicalMentionIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

	public static String organicChemicalMentionCountByOrganicChemical(String ID) throws JspTagException {
		int count = 0;
		OrganicChemicalMentionIterator theIterator = new OrganicChemicalMentionIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.organic_chemical_mention where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating OrganicChemicalMention iterator", e);
			throw new JspTagException("Error: JDBC error generating OrganicChemicalMention iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean organicChemicalHasOrganicChemicalMention(String ID) throws JspTagException {
		return ! organicChemicalMentionCountByOrganicChemical(ID).equals("0");
	}

	public static Boolean organicChemicalMentionExists (String organicChemicalId, String pmcid) throws JspTagException {
		int count = 0;
		OrganicChemicalMentionIterator theIterator = new OrganicChemicalMentionIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.organic_chemical_mention where 1=1"
						+ " and organic_chemical_id = ?"
						+ " and pmcid = ?"
						);

			stat.setInt(1,Integer.parseInt(organicChemicalId));
			stat.setInt(2,Integer.parseInt(pmcid));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating OrganicChemicalMention iterator", e);
			throw new JspTagException("Error: JDBC error generating OrganicChemicalMention iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
		OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
		if (theOrganicChemical!= null)
			parentEntities.addElement(theOrganicChemical);

		if (theOrganicChemical == null) {
		} else {
			organicChemicalId = theOrganicChemical.getID();
		}


      try {
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (organicChemicalId == 0 ? "" : " and organic_chemical_id = ?")
                                                        +  generateLimitCriteria());
            if (organicChemicalId != 0) stat.setInt(webapp_keySeq++, organicChemicalId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT pubmed_central_ack_stanford.organic_chemical_mention.organic_chemical_id, pubmed_central_ack_stanford.organic_chemical_mention.pmcid from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (organicChemicalId == 0 ? "" : " and organic_chemical_id = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (organicChemicalId != 0) stat.setInt(webapp_keySeq++, organicChemicalId);
            rs = stat.executeQuery();

            if (rs.next()) {
                organicChemicalId = rs.getInt(1);
                pmcid = rs.getInt(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating OrganicChemicalMention iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating OrganicChemicalMention iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("pubmed_central_ack_stanford.organic_chemical_mention");
      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "organic_chemical_id,pmcid";
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
                organicChemicalId = rs.getInt(1);
                pmcid = rs.getInt(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across OrganicChemicalMention", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across OrganicChemicalMention");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending OrganicChemicalMention iterator",e);
            throw new JspTagException("Error: JDBC error ending OrganicChemicalMention iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        organicChemicalId = 0;
        pmcid = 0;
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



	public int getOrganicChemicalId () {
		return organicChemicalId;
	}

	public void setOrganicChemicalId (int organicChemicalId) {
		this.organicChemicalId = organicChemicalId;
	}

	public int getActualOrganicChemicalId () {
		return organicChemicalId;
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
}
