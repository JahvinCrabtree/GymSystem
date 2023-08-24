## Welcome to Spartan Fitness.

This is going to be a newer version of my first real from scartch independant project PPF, taking what I've learend since developing that project and challenging myself to create a far better application with more moving parts, cleaner code and interfaces and just an overall improvement on the original applicaiton!

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `resources` : the folder with all the images and whatnot that are used for the GUI.

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

## - Started fleshing out the general setup of the dashboards, got a the first iteration of the login and signup page. 


<img width="460" alt="loginPage" src="https://github.com/JahvinCrabtree/GymSystem/assets/108539156/5d5c24f2-2127-4629-9eee-81d7886e5ed9">

<img width="461" alt="signupPage" src="https://github.com/JahvinCrabtree/GymSystem/assets/108539156/e667304d-8ab4-4a49-abfb-c1696355caf9">

## Login page functional, database connection functional, dashboard 50% fleshed out register page tonight.

The register page will be implementing data into the database to allow the user to essentially have an account and then login to enter the dashboard.

## Register page

Register page setup, can now insert data into the database so that user is able to then log into application and access the dashboard - learned a new way to do this instead of using string concatenation which is prone to SQL injection attacks but instead using a PreparedStatement to securely insert the user data into the database instead. This can be seen on the registerControlled under the "registerUser()" function.

<img width="450" alt="registerTest1" src="https://github.com/JahvinCrabtree/GymSystem/assets/108539156/c5f08adf-6695-4009-981d-2ea6cb331f51">

Also implemented some quality of life alerts if the passwords dont match when registering - going to try and implement something along the lines of if the details are already in the database also spit out an error so there can't be 2 people with the same usernames. 

Implemented some new features to the register page, learned about implementing delays to the alerts and creating automatic redirects. So when the user registers to the application, it shows an alert that tells them it was successful and then redirects the user back to the login page to then sign in.

## Dashboard Page

started working on the dashboard - cleaning up folder structure and learning a bit more about dependancy injection or in this case FXML injection where I'm inserting the data to populate my tableView columns. Which I've got working but it's not the way I want it to work currently, so I'll be looking at that problem and solving it as and when I figure it out.

Overall happy with progress so far and interested to see how I can progress the first few pages of this dashboard before getting into the fun stuff with the maps and web views etc.

Populating database so that videos can be displayed from it tot he applicaiton as tutorials. 
