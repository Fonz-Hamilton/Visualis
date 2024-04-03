
 __   ___              _ _    
 \ \ / (_)____  _ __ _| (_)___
  \ V /| (_-< || / _` | | (_-<
   \_/ |_/__/\_,_\__,_|_|_/__/
                              

_______________________________________________________________
Branch versions:
main:         this branch will be setup to work with an apache server
v-localhost:  this branch will be setup to work on localhost

Will update both as things change
As of writing nothing significant has changed between the two branches
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
https://github.com/Fonz-Hamilton
