# client-project-team1

## Initial setup

1. After cloning the repository, create a database named 'clientproject'. Make sure your login details are root/comsc for MySQL. If not, you need to edit **both** the build.gradle and the application.properties with the correct setup
2. Run ``` gradle flywayClean``` in terminal
3. Run ``` gradle flywayMigrate``` in terminal
4. To run the project run ```gradle bootRun```. Accessible at http://localhost:8080
4. Repeat whenever testing out a new feature or making database migrations.

## Auto rebuild

1. Run ```gradle build --continuous```
2. Without closing the window run ```gradle bootRun``` in a separate window.


## Configuring project
1. The configuration for the project is available in the application.properties file. It should be completely functional with any up-to-date MySQL database on any server. 
2. The project can be accessed at http://localhost:8080. This is once again configurable through the application.properties file
3. The example details are user/123456 for users and admin/123456 for admins.
4. Unlisted features: http://localhost:8080/admin takes you to the admin page with an admin account, and http://localhost:8080/achievements takes you to the achievements screen
5. Make sure you switch the mailserver from mailtrap to an actual mailserver if planning on using this for an actual version.

## Removing mock data
1. Truncate all the tables except the images and sample_images table

## Configurations
1. Email validation and the way image ranking is calculated can be toggled in the configuration table in the database. 
2. 

## NOTE: THIS PROJECT HAS NOT BEEN TESTED ON MARIADB SO COMPATIBLITY CANNOT BE GUARANTEED.