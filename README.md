# People-You-Might-Know
This repository contains the source code for People You Might know Algorithm using Map Reduce framework
CS 6350.001
ASSIGNMENT 1 Solution
Akshay Thakare
netid: ast140230

Que1 Solution.
i. How to run Friends.jar:
Command: 
hadoop jar ast140230jar/Friends.jar FriendSuggestionApplication /socNetData/networkdata /ast140230/<output_dir_name>

here, Friends.jar : Jar name for program 1
          /socNetData/networkdata : input data for program
          /ast140230/<output_dir_name> : output directory 	 	

ii. Recommendations for the users:
924:	 439,2409,6995,11860,15416,43748,45881
8941:	8943,8944,8940
8942:	8939,8940,8943,8944
9019:	9022,317,9023
9020:	9021,9016,9017,9022,317,9023
9021:	9020,9016,9017,9022,317,9023
9022:	9019,9020,9021,317,9016,9017,9023
9990:	13134,13478,13877,34299,34485,34642,37941
9992:	9987,9989,35667,9991
9993:	9991,13134,13478,13877,34299,34485,34642,37941

Que2 Solution.
i. How to run MutualFriends.jar:
Command: 
hadoop jar ast140230jar/MutualFriends.jar /socNetData/networkdata /ast140230/output2 userA userB
here, MutualFriends.jar : Jar name for program 2
          /socNetData/networkdata : input data for program
          /ast140230/<output_dir_name> : output directory
userA, userB: Users for whom we are searching the mutual friends.
ii. Sample runs:
{cs6360:~} hadoop jar ast140230jar/MutualFriends.jar /socNetData/networkdata /ast140230/output2 1 2
{cs6360:~} hdfs dfs -cat /ast140230/output2/*
output:  1,2     0,135
{cs6360:~} hadoop jar ast140230jar/MutualFriends.jar /socNetData/networkdata /ast140230/output2.1 9995 9997
{cs6360:~} hdfs dfs -cat /ast140230/output2.1/*
output:  9995,9997     87

Que3 Solution.
i. How to run MutualFriendsInMemory.jar:
Command: 
hadoop jar ast140230jar/MutualFriendsInMemory.jar MutualFriendInMemoryApplication /socNetData/networkdata  /socNetData/userdata userA userB /ast140230/output3.1
here, MutualFriendsInMemory.jar : Jar name for program 3
          /socNetData/networkdata : input data for program
          /socNetData/userdata : input data for program	
          /ast140230/<output_dir_name> : output directory
userA, userB: Users for whom we are searching the mutual friends.
ii. Sample runs:
{cs6360:~} hadoop jar ast140230jar/MutualFriendsInMemory.jar MutualFriendInMemoryApplication /socNetData/networkdata  /socNetData/userdata 9995 9997 /ast140230/output3
{cs6360:~} hdfs dfs -cat /ast140230/output3/*                                                                                                output: 9995 9997       [Robert:10011]
{cs6360:~} hadoop jar ast140230jar/MutualFriendsInMemory.jar MutualFriendInMemoryApplication /socNetData/networkdata  /socNetData/userdata 1 2 /ast140230/output3.1
{cs6360:~} hdfs dfs -cat /ast140230/output3.1/*                                                                                                  output: 1 2     [Joshua:8901,Evangeline:45140]

Que4 Solution.
i. How to run AverageAge.jar:
Command: 
{cs6360:~} hadoop jar ast140230jar/AverageAge.jar AverageFriendAgeApplication /ast140230/output4

here, AverageAge.jar : Jar name for program 4
          /ast140230/<output_dir_name> : output directory
ii. Sample run:
{cs6360:~} hadoop jar ast140230jar/Average.jar /ast140230/output4
{cs6360:~} hdfs dfs -cat /ast140230/output4/*                                                                                               6822, 518 Modoc Alley,Boise,Idaho,83703,US      86
48963, 4773 Crestview Manor,Indianapolis,Indiana,46214,US       85
7153, 1100 Matthews Street,Sterling,Illinois,61081,US   84
6872, 1465 Sunny Day Drive,Riverside,California,92501,US        83
9750, 4248 Chandler Drive,Joplin,Missouri,64801,US      82
823, 2225 Havanna Street,Creston,North Carolina,28615,US        81
9158, 921 Upton Avenue,South China,Maine,4358,US        80
9191, 2471 Star Route,Hillside,Illinois,60162,US        79
9041, 2312 Sweetwood Drive,Denver,Colorado,80216,US     78
7825, 2367 Meadow View Drive,West Hartford,Connecticut,6105,US  77
935, 1937 Palmer Road,Columbus,Ohio,43215,US    76
9149, 1481 Maloy Court,Wamego,Kansas,66547,US   75
9381, 2156 Cook Hill Road,Stamford,Connecticut,6905,US  74
9756, 4872 Sugar Camp Road,Owatonna,Minnesota,55060,US  73
990, 907 Ferrell Street,Cass Lake,Minnesota,56633,US    72
953, 1991 Roguski Road,Shreveport,Louisiana,71107,US    71
9625, 4277 Carriage Court,Ridgecrest,California,93555,US        70
9340, 761 Buffalo Creek Road,Nashville,Tennessee,37228,US       69
9428, 1359 Grant Street,Longview,Texas,75601,US 68
998, 1415 Layman Avenue,Fayetteville,North Carolina,28306,US    67

