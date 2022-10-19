The application is built using Spring Boot initializr.
RewardsRestController is the main controller. The getRewardResults method is executed when the http://localhost:8080/fetchRewardResults is entered in the URL of a browser. 
SpreadSheetProcessor is the Service class used to read the data from the spread sheet and process the result as per the requirement and sends back the results to the Controller. 

Input Data set is in an Excel file - RetailRewardLog.xlsx. The results are highligted in green. The data is read from an excel spread sheet and is included in the project.

<img width="716" alt="image" src="https://user-images.githubusercontent.com/116025721/196508132-5f8ac0ac-52f8-438e-9963-59c2853aa48b.png">


One way to see the result is start the project using run as java application on RewardappApplication.java file.
Once the project is up and running then type the URL http://localhost:8080/fetchRewardResults in the browser or postman to view the results.

The unit test included is RewardappApplicationTests.java. Can be run through an IDE by  choosing run as junit. The command is mvn test -Dtest="RewardappApplicationTests"

<img width="881" alt="image" src="https://user-images.githubusercontent.com/116025721/196593252-3adaea8d-2b68-4f84-a09c-09b3928d07c2.png">

Use this Url to invoke an exception. http://localhost:8080/invokeException. This is done to give a simple example of Exception invocation and Handling. 
Used Postman, created collections and Monitors to check the health.
