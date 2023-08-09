package hibernate.dao;

import hibernate.entities.ConferenceDivision;
import hibernate.entities.ConferenceDivisionPK;

public interface ConferenceDivisionDAO {

	public void insert(ConferenceDivision conferenceDivision);
	public ConferenceDivision selectById(ConferenceDivisionPK conferenceDivisionPk);
}
