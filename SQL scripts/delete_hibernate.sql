delete from eventPlayers;
delete from gameEvents;
delete from events;
delete from rosters;
delete from goalieStats;
delete from skaterStats;
delete from players;
delete from positions;
delete from games;
delete from gamestatus;
delete from division_teams;
delete from conference_teams;
delete from conference_division;
delete from divisions;
delete from conferences;
delete from teams;
delete from venues;
delete from timeZones;


drop table gameStatus;
drop table venues;
drop table teams;
drop table games;
drop table timeZones;
drop table divisions;
drop table division_Teams;
drop table conference_division;
drop table conference_teams;
drop table conferences;
drop table events;
drop table gameEvents;
drop table positions;
drop table players;
drop table rosters;
drop table eventPlayers;
drop table goalieStats;
drop table skaterStats;


ALTER TABLE Teams DROP CONSTRAINT Teams_Venues_FK;
ALTER TABLE Teams DROP CONSTRAINT Teams_TimeZones_FK;
ALTER TABLE Games DROP CONSTRAINT Games_Venues_FK;
ALTER TABLE Games DROP CONSTRAINT Games_GameStatus_FK;
ALTER TABLE Games DROP CONSTRAINT Games_Teams_FK_Away;
ALTER TABLE Games DROP CONSTRAINT Games_Teams_FK_Home;
ALTER TABLE Division_Teams DROP CONSTRAINT Division_Teams_Teams_FK;
ALTER TABLE Division_Teams DROP CONSTRAINT Division_Teams_Divisions_FK;
ALTER TABLE Conference_Teams DROP CONSTRAINT Conference_Teams_Conferences_FK;
ALTER TABLE Conference_Teams DROP CONSTRAINT Conference_Teams_Teams_FK;
ALTER TABLE Conference_Division DROP CONSTRAINT Conference_Div_Con_FK;
ALTER TABLE Conference_Division DROP CONSTRAINT Con_Div_Division_FK;
ALTER TABLE GameEvents DROP CONSTRAINT GameEvents_Games_FK;
ALTER TABLE GameEvents DROP CONSTRAINT GameEvents_Events_FK;
ALTER TABLE Players DROP CONSTRAINT Players_Positions_FK;
ALTER TABLE Players DROP CONSTRAINT Players_Teams_FK;
ALTER TABLE Rosters DROP CONSTRAINT Rosters_Games_FK;
ALTER TABLE Rosters DROP CONSTRAINT Rosters_Teams_FK;
ALTER TABLE Rosters DROP CONSTRAINT Rosters_Players_FK;
ALTER TABLE Rosters DROP CONSTRAINT Rosters_SkaterStats_FK;
ALTER TABLE Rosters DROP CONSTRAINT Rosters_GoalieStats_FK;
ALTER TABLE EventPlayers DROP CONSTRAINT EventPlayers_GameEvents_FK;
ALTER TABLE EventPlayers DROP CONSTRAINT EventPlayers_Rosters_FK;


DROP SEQUENCE seq_games_id;
DROP SEQUENCE seq_teams_id;
DROP SEQUENCE seq_venues_id;
DROP SEQUENCE seq_timeZones_id;
DROP SEQUENCE seq_divisions_id;
DROP SEQUENCE seq_conferences_id;
DROP SEQUENCE seq_events_id;
DROP SEQUENCE seq_gameEvents_id;
DROP SEQUENCE seq_positions_id;
DROP SEQUENCE seq_players_id;
DROP SEQUENCE seq_rosters_id;
DROP SEQUENCE seq_skaterStats_id;
DROP SEQUENCE seq_goalieStats_id;