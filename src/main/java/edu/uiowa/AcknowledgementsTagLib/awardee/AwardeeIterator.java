package edu.uiowa.AcknowledgementsTagLib.awardee;


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
import edu.uiowa.AcknowledgementsTagLib.award.Award;
import edu.uiowa.AcknowledgementsTagLib.person.Person;

@SuppressWarnings("serial")
public class AwardeeIterator extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int awardId = 0;
    int personId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(AwardeeIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean useAward = false;
   boolean usePerson = false;

	public static String awardeeCountByAward(String ID) throws JspTagException {
		int count = 0;
		AwardeeIterator theIterator = new AwardeeIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.awardee where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Awardee iterator", e);
			throw new JspTagException("Error: JDBC error generating Awardee iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean awardHasAwardee(String ID) throws JspTagException {
		return ! awardeeCountByAward(ID).equals("0");
	}

	public static String awardeeCountByPerson(String ID) throws JspTagException {
		int count = 0;
		AwardeeIterator theIterator = new AwardeeIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.awardee where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Awardee iterator", e);
			throw new JspTagException("Error: JDBC error generating Awardee iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean personHasAwardee(String ID) throws JspTagException {
		return ! awardeeCountByPerson(ID).equals("0");
	}

	public static Boolean awardeeExists (String pmcid, String awardId, String personId) throws JspTagException {
		int count = 0;
		AwardeeIterator theIterator = new AwardeeIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.awardee where 1=1"
						+ " and pmcid = ?"
						+ " and award_id = ?"
						+ " and person_id = ?"
						);

			stat.setInt(1,Integer.parseInt(pmcid));
			stat.setInt(2,Integer.parseInt(awardId));
			stat.setInt(3,Integer.parseInt(personId));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Awardee iterator", e);
			throw new JspTagException("Error: JDBC error generating Awardee iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean awardPersonExists (String ID) throws JspTagException {
		int count = 0;
		AwardeeIterator theIterator = new AwardeeIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.awardee where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Awardee iterator", e);
			throw new JspTagException("Error: JDBC error generating Awardee iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
		Award theAward = (Award)findAncestorWithClass(this, Award.class);
		if (theAward!= null)
			parentEntities.addElement(theAward);
		Person thePerson = (Person)findAncestorWithClass(this, Person.class);
		if (thePerson!= null)
			parentEntities.addElement(thePerson);

		if (theAward == null) {
		} else {
			awardId = theAward.getID();
		}
		if (thePerson == null) {
		} else {
			personId = thePerson.getID();
		}


      try {
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (awardId == 0 ? "" : " and award_id = ?")
                                                        + (personId == 0 ? "" : " and person_id = ?")
                                                        +  generateLimitCriteria());
            if (awardId != 0) stat.setInt(webapp_keySeq++, awardId);
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT pubmed_central_ack_stanford.awardee.pmcid, pubmed_central_ack_stanford.awardee.award_id, pubmed_central_ack_stanford.awardee.person_id from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (awardId == 0 ? "" : " and award_id = ?")
                                                        + (personId == 0 ? "" : " and person_id = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (awardId != 0) stat.setInt(webapp_keySeq++, awardId);
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pmcid = rs.getInt(1);
                awardId = rs.getInt(2);
                personId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating Awardee iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating Awardee iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("pubmed_central_ack_stanford.awardee");
       if (useAward)
          theBuffer.append(", pubmed_central_ack_stanford.award");
       if (usePerson)
          theBuffer.append(", pubmed_central_ack_stanford.person");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (useAward)
          theBuffer.append(" and award.ID = awardee.award_id");
       if (usePerson)
          theBuffer.append(" and person.ID = awardee.person_id");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "pmcid,award_id,person_id";
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
                awardId = rs.getInt(2);
                personId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across Awardee", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across Awardee");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending Awardee iterator",e);
            throw new JspTagException("Error: JDBC error ending Awardee iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        pmcid = 0;
        awardId = 0;
        personId = 0;
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


   public boolean getUseAward() {
        return useAward;
    }

    public void setUseAward(boolean useAward) {
        this.useAward = useAward;
    }

   public boolean getUsePerson() {
        return usePerson;
    }

    public void setUsePerson(boolean usePerson) {
        this.usePerson = usePerson;
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

	public int getAwardId () {
		return awardId;
	}

	public void setAwardId (int awardId) {
		this.awardId = awardId;
	}

	public int getActualAwardId () {
		return awardId;
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
}
