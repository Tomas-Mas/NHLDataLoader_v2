package hibernate.dao;

import org.hibernate.Session;

import hibernate.entities.GameStatus;
import main.HibernateUtil;

public class GameStatusDAOImpl implements GameStatusDAO {

	@Override
	public void insert(GameStatus gameStatus) {
		Session session = HibernateUtil.getSession();
		session.save(gameStatus);
	}

	@Override
	public GameStatus selectById(int id) {
		Session session = HibernateUtil.getSession();
		GameStatus gameStatus = (GameStatus) session.get(GameStatus.class, id);
		return gameStatus;
	}

}
