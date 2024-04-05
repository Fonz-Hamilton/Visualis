
 __   ___              _ _    
 \ \ / (_)____  _ __ _| (_)___
  \ V /| (_-< || / _` | | (_-<
   \_/ |_/__/\_,_\__,_|_|_/__/
                              

_______________________________________________________________
Branch versions:
main:         this branch will be setup to work with an apache server
v-localhost:  this branch will be setup to work on localhost
_______________________________________________________________
How it works
Visualis is a website where users can enter a data file and see it
displayed. Along with a generated table, users can choose how to view
their data. Currently only Bar and Scatter plots are supported.

Users must login to access the visual side of the site
_______________________________________________________________
Things to know
Required to already have a database called visualis that can
be connected to (e.g. using Mysql workbench)

The application settings are set to create-drop so everytime the
application is restarted it is a fresh database that gets initialized
with Roles(ROLE_ADMIN, ROLE_USER) as well as an admin user.

email: 		admin@visualis.com
password: 	admin


Database entities:
User: id, firstName, lastName, username, email, password
Role: id, name
Data: id, name, data_info_id
DataInfo: id, name, description, datatype, source link to location on server

Join tables
user_data: user_id, data_id
data_dataInfo: data_id, dataInfo_id
user_roles: user_id, role_id
_______________________________________________________________
Things for apache2 server

Apache2 is being used as a reverse proxy
The plugin the root user uses is cache_sha2_password on mysql
To use the password 'DATABASE_PASSWORD' an environment variable is set in bash

To run in the background use command:
nohup java -jar whatever-visualis-version.jar > visualis.log 2>&1 &

View the log with:
tail -f visualis.log
_______________________________________________________________
https://github.com/Fonz-Hamilton
