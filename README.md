# rewardApp
Input Data set is in an Excel file - RetailRewardLog. The results are highligted in green. The data is read from an excel spread sheet and is included in the project.

<img width="716" alt="image" src="https://user-images.githubusercontent.com/116025721/196508132-5f8ac0ac-52f8-438e-9963-59c2853aa48b.png">


One way to see the result is start the project using run as java application on RewardappApplication.java file.
I used eclipse as my IDE. Once the project is up then type the URL http://localhost:8080/fetchRewardResults in the browser or postman to view the results.

The unit test included is RewardappApplicationTests.java. Can be run through an IDE by  choosing run as junit. I did not use it to run this particular test case but
maven could be used to run it. The command is mvn test -Dtest="RewardappApplicationTests"

Use this Url to invoke an exception. http://localhost:8080/invokeException. This is done to give a simple example of Exception invokcation and Handling. 
