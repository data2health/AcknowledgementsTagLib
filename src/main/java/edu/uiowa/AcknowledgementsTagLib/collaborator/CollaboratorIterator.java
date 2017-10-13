package edu.uiowa.AcknowledgementsTagLib.collaborator;


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
import edu.uiowa.AcknowledgementsTagLib.collaboration.Collaboration;
import edu.uiowa.AcknowledgementsTagLib.person.Person;

@SuppressWarnings("serial")
public class CollaboratorIterator extends AcknowledgementsTagLibBodyTagSupport {
    int pmcid = 0;
    int collaborationId = 0;
    int personId = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(CollaboratorIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean useCollaboration = false;
   boolean usePerson = false;

	public static String collaboratorCountByCollaboration(String ID) throws JspTagException {
		int count = 0;
		CollaboratorIterator theIterator = new CollaboratorIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.collaborator where 1=1"
						+ " and collaboration_id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Collaborator iterator", e);
			throw new JspTagException("Error: JDBC error generating Collaborator iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean collaborationHasCollaborator(String ID) throws JspTagException {
		return ! collaboratorCountByCollaboration(ID).equals("0");
	}

	public static String collaboratorCountByPerson(String ID) throws JspTagException {
		int count = 0;
		CollaboratorIterator theIterator = new CollaboratorIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.collaborator where 1=1"
						+ " and person_id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Collaborator iterator", e);
			throw new JspTagException("Error: JDBC error generating Collaborator iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean personHasCollaborator(String ID) throws JspTagException {
		return ! collaboratorCountByPerson(ID).equals("0");
	}

	public static Boolean collaboratorExists (String pmcid, String collaborationId, String personId) throws JspTagException {
		int count = 0;
		CollaboratorIterator theIterator = new CollaboratorIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.collaborator where 1=1"
						+ " and pmcid = ?"
						+ " and collaboration_id = ?"
						+ " and person_id = ?"
						);

			stat.setInt(1,Integer.parseInt(pmcid));
			stat.setInt(2,Integer.parseInt(collaborationId));
			stat.setInt(3,Integer.parseInt(personId));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Collaborator iterator", e);
			throw new JspTagException("Error: JDBC error generating Collaborator iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean collaborationPersonExists (String ID) throws JspTagException {
		int count = 0;
		CollaboratorIterator theIterator = new CollaboratorIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.collaborator where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Collaborator iterator", e);
			throw new JspTagException("Error: JDBC error generating Collaborator iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
		Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
		if (theCollaboration!= null)
			parentEntities.addElement(theCollaboration);
		Person thePerson = (Person)findAncestorWithClass(this, Person.class);
		if (thePerson!= null)
			parentEntities.addElement(thePerson);

		if (theCollaboration == null) {
		} else {
			collaborationId = theCollaboration.getID();
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
                                                        + (collaborationId == 0 ? "" : " and collaboration_id = ?")
                                                        + (personId == 0 ? "" : " and person_id = ?")
                                                        +  generateLimitCriteria());
            if (collaborationId != 0) stat.setInt(webapp_keySeq++, collaborationId);
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT pubmed_central_ack_stanford.collaborator.pmcid, pubmed_central_ack_stanford.collaborator.collaboration_id, pubmed_central_ack_stanford.collaborator.person_id from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (collaborationId == 0 ? "" : " and collaboration_id = ?")
                                                        + (personId == 0 ? "" : " and person_id = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (collaborationId != 0) stat.setInt(webapp_keySeq++, collaborationId);
            if (personId != 0) stat.setInt(webapp_keySeq++, personId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pmcid = rs.getInt(1);
                collaborationId = rs.getInt(2);
                personId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating Collaborator iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating Collaborator iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("pubmed_central_ack_stanford.collaborator");
       if (useCollaboration)
          theBuffer.append(", pubmed_central_ack_stanford.collaboration");
       if (usePerson)
          theBuffer.append(", pubmed_central_ack_stanford.person");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (useCollaboration)
          theBuffer.append(" and collaboration.ID = collaborator.collaboration_id");
       if (usePerson)
          theBuffer.append(" and person.ID = collaborator.person_id");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "pmcid,collaboration_id,person_id";
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
                collaborationId = rs.getInt(2);
                personId = rs.getInt(3);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across Collaborator", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across Collaborator");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending Collaborator iterator",e);
            throw new JspTagException("Error: JDBC error ending Collaborator iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        pmcid = 0;
        collaborationId = 0;
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


   public boolean getUseCollaboration() {
        return useCollaboration;
    }

    public void setUseCollaboration(boolean useCollaboration) {
        this.useCollaboration = useCollaboration;
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

	public int getCollaborationId () {
		return collaborationId;
	}

	public void setCollaborationId (int collaborationId) {
		this.collaborationId = collaborationId;
	}

	public int getActualCollaborationId () {
		return collaborationId;
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
