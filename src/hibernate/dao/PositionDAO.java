package hibernate.dao;

import hibernate.entities.Position;

public interface PositionDAO {

	public void insert(Position position);
	public Position selectByData(Position position);
}
