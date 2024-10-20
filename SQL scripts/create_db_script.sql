/*data schema*/
CREATE TABLE Games
  (
    g_id       INTEGER NOT NULL ,
    g_json_id   INTEGER NOT NULL ,
    game_type   VARCHAR2(5) NOT NULL ,
    season     INTEGER NOT NULL ,
    game_date   TIMESTAMP NOT NULL ,
    away_score  INTEGER NOT NULL ,
    away_team_id INTEGER NOT NULL ,
    home_score  INTEGER NOT NULL ,
    home_team_id INTEGER NOT NULL ,
    venue_id    INTEGER NOT NULL ,
    game_status INTEGER NOT NULL
  );
ALTER TABLE Games ADD CONSTRAINT Games_PK PRIMARY KEY ( g_id );
ALTER TABLE Games ADD CONSTRAINT Games_JsonId UNIQUE ( g_json_id );

CREATE TABLE Game_Status
  (
    code INTEGER NOT NULL,
    name VARCHAR2 (15)
  );
ALTER TABLE Game_Status ADD CONSTRAINT Game_Status_PK PRIMARY KEY ( code );

CREATE TABLE Teams
  (
    t_id         INTEGER NOT NULL ,
    t_json_id     INTEGER NOT NULL ,
    name         VARCHAR2 (50) NOT NULL ,
    abbreviation VARCHAR2 (5) NOT NULL ,
    team_name     VARCHAR2 (15) NOT NULL ,
    short_name    VARCHAR2 (15) NOT NULL ,
    venue_id      INTEGER NOT NULL ,
    time_zone_id   INTEGER NOT NULL ,
    location     VARCHAR2 (25) NOT NULL ,
    first_year    INTEGER ,
    active       VARCHAR2 (5)
  );
ALTER TABLE Teams ADD CONSTRAINT Teams_PK PRIMARY KEY ( t_id );
ALTER TABLE Teams ADD CONSTRAINT Teams_json_id UNIQUE ( t_json_id );
ALTER TABLE Teams ADD CONSTRAINT Teams_abbr UNIQUE ( abbreviation );

CREATE TABLE Venues
  (
    v_id INTEGER NOT NULL ,
    name VARCHAR2 (50) NOT NULL ,
    city VARCHAR2 (25)
  );
ALTER TABLE Venues ADD CONSTRAINT Venues_PK PRIMARY KEY ( v_id );

CREATE TABLE Time_Zones
  (
    tz_id  INTEGER NOT NULL ,
    name   VARCHAR2 (30) NOT NULL ,
    offset INTEGER NOT NULL
  );
ALTER TABLE Time_Zones ADD CONSTRAINT Time_Zones_PK PRIMARY KEY ( tz_id );

CREATE TABLE Divisions
  (
    d_id     INTEGER NOT NULL ,
    d_json_id INTEGER ,
    name     VARCHAR2 (15) NOT NULL
  );
ALTER TABLE Divisions ADD CONSTRAINT Divisions_PK PRIMARY KEY ( d_id );
ALTER TABLE Divisions ADD CONSTRAINT Divisions_Name UNIQUE ( name );

CREATE TABLE Division_Teams (
    division_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    season INTEGER NOT NULL
);
ALTER TABLE Division_Teams ADD CONSTRAINT Division_Teams_PK PRIMARY KEY (division_id, team_id, season);

CREATE TABLE Conferences
  (
    c_id     INTEGER NOT NULL ,
    c_json_id INTEGER ,
    name     VARCHAR2 (15) NOT NULL
  ) ;
ALTER TABLE Conferences ADD CONSTRAINT Conferences_PK PRIMARY KEY ( c_id );
ALTER TABLE Conferences ADD CONSTRAINT Conferences_Name UNIQUE ( name );

CREATE TABLE Conference_Teams (
    conference_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    season INTEGER NOT NULL
);
ALTER TABLE conference_Teams ADD CONSTRAINT Conference_Teams_PK PRIMARY KEY (conference_id, team_id, season);

CREATE TABLE Conference_Division
  (
    conference_id INTEGER NOT NULL ,
    division_id   INTEGER NOT NULL ,
    season INTEGER NOT NULL
  ) ;
ALTER TABLE Conference_Division ADD CONSTRAINT Conference_Division_PK PRIMARY KEY ( conference_id, division_id, season );

CREATE TABLE Events(
    e_id INTEGER NOT NULL,
    name VARCHAR2(15),
    secondary_type VARCHAR2(50),
    strength VARCHAR2(20),
    empty_net VARCHAR2(10),
    penalty_severity VARCHAR2(30),
    penalty_minutes INTEGER
);
ALTER TABLE Events ADD CONSTRAINT Events_PK PRIMARY KEY (e_id);

CREATE TABLE Game_Events(
    ge_id INTEGER NOT NULL,
    game_id INTEGER NOT NULL,
    game_event_id INTEGER NOT NULL,
    event_id INTEGER NOT NULL,
    period_number INTEGER NOT NULL,
    period_type VARCHAR2(15) NOT NULL,
    period_time VARCHAR2(10) NOT NULL,
    coord_x INTEGER,
    coord_y INTEGER
);
ALTER TABLE Game_Events ADD CONSTRAINT Game_Events_PK PRIMARY KEY(ge_id);
ALTER TABLE Game_Events ADD CONSTRAINT Game_Events_game_event_UN UNIQUE(game_id, game_event_id);

CREATE TABLE Positions
  (
    p_id         INTEGER NOT NULL ,
    name         VARCHAR2 (15) NOT NULL ,
    type         VARCHAR2 (15) NOT NULL ,
    abbreviation VARCHAR2 (2)
  );
ALTER TABLE Positions ADD CONSTRAINT Positions_PK PRIMARY KEY ( p_id );
ALTER TABLE Positions ADD CONSTRAINT Conferences_Unique UNIQUE ( name, type );

CREATE TABLE Players
  (
    p_id          INTEGER NOT NULL ,
    p_json_id      INTEGER NOT NULL ,
    first_name     VARCHAR2 (25) NOT NULL ,
    last_name      VARCHAR2 (25) NOT NULL ,
    primary_number INTEGER ,
    age           INTEGER,
    birth_date     DATE ,
    birth_country  VARCHAR2 (3) ,
    current_team_id INTEGER ,
    position_id    INTEGER NOT NULL
  );
ALTER TABLE Players ADD CONSTRAINT Players_PK PRIMARY KEY ( p_id );
ALTER TABLE Players ADD CONSTRAINT Players_json_id UNIQUE ( p_json_id );

CREATE TABLE Rosters(
    r_id INTEGER NOT NULL,
    g_id INTEGER NOT NULL,
    t_id INTEGER NOT NULL,
    p_id INTEGER NOT NULL,
    pos_id INTEGER NOT NULL,
    time_on_ice VARCHAR2(10) NOT NULL,
    plus_minus INTEGER NOT NULL
);
ALTER TABLE Rosters ADD CONSTRAINT Rosters_PK PRIMARY KEY(r_id);
ALTER TABLE rosters ADD CONSTRAINT Rosters_unique_gtp UNIQUE (g_id, t_id, p_id);
ALTER TABLE rosters ADD CONSTRAINT Rosters_unique_gp UNIQUE (g_id, p_id);

CREATE TABLE Event_Players(
    event_id INTEGER NOT NULL,
    roster_id INTEGER NOT NULL,
    role VARCHAR2(50)
);
ALTER TABLE Event_Players ADD CONSTRAINT Event_Players_PK PRIMARY KEY(event_id, roster_id);

ALTER TABLE Teams ADD CONSTRAINT Teams_Venues_FK FOREIGN KEY ( venue_id ) REFERENCES Venues ( v_id );
ALTER TABLE Teams ADD CONSTRAINT Teams_Time_Zones_FK FOREIGN KEY ( time_zone_id ) REFERENCES Time_Zones ( tz_id );
ALTER TABLE Games ADD CONSTRAINT Games_Venues_FK FOREIGN KEY ( venue_id ) REFERENCES Venues ( v_id );
ALTER TABLE Games ADD CONSTRAINT Games_Game_Status_FK FOREIGN KEY ( game_status ) REFERENCES Game_Status ( code );
ALTER TABLE Games ADD CONSTRAINT Games_Teams_FK_Away FOREIGN KEY ( away_team_id ) REFERENCES Teams ( t_id );
ALTER TABLE Games ADD CONSTRAINT Games_Teams_FK_Home FOREIGN KEY ( home_team_id ) REFERENCES Teams ( t_id );
ALTER TABLE Division_Teams ADD CONSTRAINT Division_Teams_Teams_FK FOREIGN KEY (team_id) REFERENCES Teams (t_id);
ALTER TABLE Division_Teams ADD CONSTRAINT Division_Teams_Divisions_FK FOREIGN KEY (division_id) REFERENCES Divisions (d_id);
ALTER TABLE Conference_Teams ADD CONSTRAINT Conference_Teams_Conferences_FK FOREIGN KEY (conference_id) REFERENCES Conferences (c_id);
ALTER TABLE Conference_Teams ADD CONSTRAINT Conference_Teams_Teams_FK FOREIGN KEY (team_id) REFERENCES Teams (t_id);
ALTER TABLE Conference_Division ADD CONSTRAINT Conference_Div_Con_FK FOREIGN KEY ( conference_id ) REFERENCES Conferences ( c_id );
ALTER TABLE Conference_Division ADD CONSTRAINT Con_Div_Division_FK FOREIGN KEY ( division_id ) REFERENCES Divisions ( d_id );
ALTER TABLE Game_Events ADD CONSTRAINT Game_Events_Games_FK FOREIGN KEY(game_id) REFERENCES Games(g_id);
ALTER TABLE Game_Events ADD CONSTRAINT Game_Events_Events_FK FOREIGN KEY(event_id) REFERENCES Events(e_id);
ALTER TABLE Players ADD CONSTRAINT Players_Positions_FK FOREIGN KEY ( position_id ) REFERENCES Positions ( p_id );
ALTER TABLE Players ADD CONSTRAINT Players_Teams_FK FOREIGN KEY ( current_team_id ) REFERENCES Teams ( t_id );
ALTER TABLE Rosters ADD CONSTRAINT Rosters_Games_FK FOREIGN KEY (g_id) REFERENCES Games (g_id);
ALTER TABLE Rosters ADD CONSTRAINT Rosters_Teams_FK FOREIGN KEY (t_id) REFERENCES Teams (t_id);
ALTER TABLE Rosters ADD CONSTRAINT Rosters_Players_FK FOREIGN KEY (p_id) REFERENCES Players (p_id);
ALTER TABLE Rosters ADD CONSTRAINT Rosters_Positions_FK FOREIGN KEY (pos_id) REFERENCES Positions(p_id);
ALTER TABLE Event_Players ADD CONSTRAINT Event_Players_Game_Events_FK FOREIGN KEY(event_id) REFERENCES Game_Events (ge_id);
ALTER TABLE Event_Players ADD CONSTRAINT Event_Players_Rosters_FK FOREIGN KEY(roster_id) REFERENCES Rosters(r_id);

/

CREATE SEQUENCE seq_games_id
START WITH 200000
INCREMENT BY 1
MINVALUE 200000
NOMAXVALUE
NOCACHE
NOCYCLE;

/

CREATE SEQUENCE seq_teams_id
START WITH 100
INCREMENT BY 1
MINVALUE 100
MAXVALUE 499
NOCACHE
NOCYCLE;

/

CREATE SEQUENCE seq_venues_id
START WITH 500
INCREMENT BY 1
MINVALUE 500
MAXVALUE 999
NOCACHE
NOCYCLE;

/

CREATE SEQUENCE seq_time_zones_id
START WITH 1
INCREMENT BY 1
MINVALUE 1
MAXVALUE 1000
NOCACHE
NOCYCLE;

/

CREATE SEQUENCE seq_divisions_id
START WITH 1
INCREMENT BY 1
MINVALUE 1
MAXVALUE 29
NOCACHE
NOCYCLE;

/

CREATE SEQUENCE seq_conferences_id
START WITH 30
INCREMENT BY 1
MINVALUE 30
MAXVALUE 49
NOCACHE
NOCYCLE;

/

CREATE SEQUENCE seq_events_id
START WITH 1
INCREMENT BY 1
MINVALUE 1
MAXVALUE 10000
NOCACHE
NOCYCLE;

/

CREATE SEQUENCE seq_game_events_id
START WITH 1
INCREMENT BY 1
MINVALUE 1
MAXVALUE 9999999999
CACHE 20
NOCYCLE;

/

CREATE SEQUENCE seq_positions_id
START WITH 100
INCREMENT BY 1
MINVALUE 100
MAXVALUE 1000
NOCACHE
NOCYCLE;

/

CREATE SEQUENCE seq_players_id
START WITH 1000
INCREMENT BY 1
MINVALUE 1000
MAXVALUE 999999
NOCACHE
NOCYCLE;

/

create sequence seq_rosters_id
start with 1
increment by 1
minvalue 1
maxvalue 9999999999
cache 5
nocycle;