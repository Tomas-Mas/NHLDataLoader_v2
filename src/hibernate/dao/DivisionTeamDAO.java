package hibernate.dao;

import hibernate.entities.DivisionPK;
import hibernate.entities.DivisionTeam;

public interface DivisionTeamDAO {

	public void insert(DivisionTeam divisionTeam);
	public DivisionTeam selectById(DivisionPK divisionPK);
}
