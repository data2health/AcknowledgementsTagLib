package edu.uiowa.AcknowledgementsTagLib.fundingAgency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class FundingAgency extends AcknowledgementsTagLibTagSupport {

	static FundingAgency currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(FundingAgency.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String fundingAgency = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			FundingAgencyIterator theFundingAgencyIterator = (FundingAgencyIterator)findAncestorWithClass(this, FundingAgencyIterator.class);

			if (theFundingAgencyIterator != null) {
				ID = theFundingAgencyIterator.getID();
			}

			if (theFundingAgencyIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new FundingAgency and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a FundingAgency from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select funding_agency from pubmed_central_ack_stanford.funding_agency where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (fundingAgency == null)
						fundingAgency = rs.getString(1);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			log.error("JDBC error retrieving ID " + ID, e);
			throw new JspTagException("Error: JDBC error retrieving ID " + ID);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.funding_agency set funding_agency = ? where id = ?");
				stmt.setString(1,fundingAgency);
				stmt.setInt(2,ID);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			clearServiceState();
			freeConnection();
		}
		return super.doEndTag();
	}

	public void insertEntity() throws JspException {
		try {
			if (ID == 0) {
				ID = Sequence.generateID();
				log.debug("generating new FundingAgency " + ID);
			}

			if (fundingAgency == null)
				fundingAgency = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.funding_agency(id,funding_agency) values (?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,fundingAgency);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
	}

	public int getID () {
		return ID;
	}

	public void setID (int ID) {
		this.ID = ID;
	}

	public int getActualID () {
		return ID;
	}

	public String getFundingAgency () {
		if (commitNeeded)
			return "";
		else
			return fundingAgency;
	}

	public void setFundingAgency (String fundingAgency) {
		this.fundingAgency = fundingAgency;
		commitNeeded = true;
	}

	public String getActualFundingAgency () {
		return fundingAgency;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String fundingAgencyValue() throws JspException {
		try {
			return currentInstance.getFundingAgency();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function fundingAgencyValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		fundingAgency = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
