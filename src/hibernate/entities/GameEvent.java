package hibernate.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Game_Events")
public class GameEvent {

	private int id;
	private Game game;
	private int jsonId;
	private Event event;
	private int periodNumber;
	private String periodType;
	private String periodTime;
	private int coordX;
	private int coordY;
	private List<EventPlayer> players;
	
	@Id
	@SequenceGenerator(name = "gameEventIdGenerator", sequenceName = "SEQ_GAME_EVENTS_ID", allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gameEventIdGenerator")
	@Column(name = "ge_id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "game_id")
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	@Column(name = "game_event_id", unique = true)
	public int getJsonId() {
		return jsonId;
	}
	public void setJsonId(int jsonId) {
		this.jsonId = jsonId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "event_id")
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	
	@Column(name = "period_number")
	public int getPeriodNumber() {
		return periodNumber;
	}
	public void setPeriodNumber(int periodNumber) {
		this.periodNumber = periodNumber;
	}
	
	@Column(name = "period_type", length = 15)
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	@Column(name = "period_time", length = 10)
	public String getPeriodTime() {
		return periodTime;
	}
	public void setPeriodTime(String periodTime) {
		this.periodTime = periodTime;
	}
	
	@Column(name = "coord_x")
	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	
	@Column(name = "coord_y")
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	
	@OneToMany(mappedBy = "id.event", fetch = FetchType.LAZY)
	public List<EventPlayer> getPlayers() {
		return players;
	}
	public void setPlayers(List<EventPlayer> players) {
		this.players = players;
	}
}
