package edu.uiowa.AcknowledgementsTagLib.skill;


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
import edu.uiowa.AcknowledgementsTagLib.technique.Technique;

@SuppressWarnings("serial")
public class SkillIterator extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int personId = 0;
    int techniqueId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(SkillIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean usePerson = false;
   boolean useTechnique = false;

	public static String skillCountByPerson(String ID) throws JspTagException {
		int count = 0;
		SkillIterator theIterator = new SkillIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.skill where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Skill iterator", e);
			throw new JspTagException("Error: JDBC error generating Skill iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean personHasSkill(String ID) throws JspTagException {
		return ! skillCountByPerson(ID).equals("0");
	}

	public static String skillCountByTechnique(String ID) throws JspTagException {
		int count = 0;
		SkillIterator theIterator = new SkillIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.skill where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Skill iterator", e);
			throw new JspTagException("Error: JDBC error generating Skill iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean techniqueHasSkill(String ID) throws JspTagException {
		return ! skillCountByTechnique(ID).equals("0");
	}

	public static Boolean skillExists (String pmcid, String personId, String techniqueId) throws JspTagException {
		int count = 0;
		SkillIterator theIterator = new SkillIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.skill where 1=1"
						+ " and pmcid = ?"
						+ " and person_id = ?"
						+ " and technique_id = ?"
						);

			stat.setInt(1,Integer.parseInt(pmcid));
			stat.setInt(2,Integer.parseInt(personId));
			stat.setInt(3,Integer.parseInt(techniqueId));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Skill iterator", e);
			throw new JspTagException("Error: JDBC error generating Skill iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean personTechniqueExists (String ID) throws JspTagException {
		int count = 0;
		SkillIterator theIterator = new SkillIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.skill where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Skill iterator", e);
			throw new JspTagException("Error: JDBC error generating Skill iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
		Person thePerson = (Person)findAncestorWithClass(this, Person.class);
		if (thePerson!= null)
			parentEntities.addElement(thePerson);
		Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
		if (theTechnique!= null)
			parentEntities.addElement(theTechnique);

		if (thePerson == null) {
		} else {
			personId = thePerson.getID();
		}
		if (theTechnique == null) {
		} else {
			techniqueId = theTechnique.getID();
		}


      try {
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (personId == 0 ? "" : " and person_id = ?")
                                                        + (techniqueId == 0 ? "" : " and technique_id = ?")
                                                        +  generateLimitCriteria());
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            if (techniqueId != 0) stat.setInt(webapp_keySeq++, techniqueId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT pubmed_central_ack_stanford.skill.pmcid, pubmed_central_ack_stanford.skill.person_id, pubmed_central_ack_stanford.skill.technique_id from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (personId == 0 ? "" : " and person_id = ?")
                                                        + (techniqueId == 0 ? "" : " and technique_id = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            if (techniqueId != 0) stat.setInt(webapp_keySeq++, techniqueId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pmcid = rs.getInt(1);
                personId = rs.getInt(2);
                techniqueId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating Skill iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating Skill iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("pubmed_central_ack_stanford.skill");
       if (usePerson)
          theBuffer.append(", pubmed_central_ack_stanford.person");
       if (useTechnique)
          theBuffer.append(", pubmed_central_ack_stanford.technique");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (usePerson)
          theBuffer.append(" and person.ID = skill.person_id");
       if (useTechnique)
          theBuffer.append(" and technique.ID = skill.technique_id");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "pmcid,person_id,technique_id";
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
                techniqueId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across Skill", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across Skill");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending Skill iterator",e);
            throw new JspTagException("Error: JDBC error ending Skill iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        pmcid = 0;
        personId = 0;
        techniqueId = 0;
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

   public boolean getUseTechnique() {
        return useTechnique;
    }

    public void setUseTechnique(boolean useTechnique) {
        this.useTechnique = useTechnique;
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

	public int getTechniqueId () {
		return techniqueId;
	}

	public void setTechniqueId (int techniqueId) {
		this.techniqueId = techniqueId;
	}

	public int getActualTechniqueId () {
		return techniqueId;
	}
}
