package hibernate.dao;

import hibernate.entities.Division;

public interface DivisionDAO {

	public void insert(Division division);
	public void update(Division division);
	public Division selectByJsonId(int jsonId);
	public Division selectByName(String name);
}
