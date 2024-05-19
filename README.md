# NHLDataLoader_v2

## Improved version of NHLDataLoader
<p> Loads data from api (https://statsapi.web.nhl.com/api/v1/schedule?teamId=[teamid]&startDate=[date]&endDate=[date]) into class model using Gson library. 
    Now works with new api (https://api.nhle.com) that replaced statsapi.
    Maps class models into hibernate entities.
    Stores data into Oracle database using Hibernate.
</p>

## Technologies
<p>
  - Java 8 <br>
  - Oracle 19 <br>
  - gson 2.8.2 <br>
  - jpa 2.0 <br>
  - hibernate 4.2.21 <br>
  - ojdbc 10 <br>
  - json <br>
</p>

## Db scripts included in SQL script folder
db model:
![db model](readme_imgs/db_model.png)

## General description
<p>
  In main method write start year of desired season into seasonYear variable and application loads entire season automatically.
  Program loads data from api using java.net.URL and HttpUrlConnection into Java objects which are then mapped to hibernate entities and stored into database. <br> <br>
  To prevent errors and potantialy storing same data twice, before storing data into database program checks, if said data aren't already stored and if so, it checks whether changes has been made to them based on unique keys or unique combination of columns. <br> 
This will also be important later on when program will be handling live games, but for now trying to load game, that has already been loaded and stored into db won't cause any issues.
</p>