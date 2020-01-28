# Illumio_Coding_Challenge

This challenge was one of the most interesting and fun challenge to do. It involved design decisions, coding decisions, writing unit test cases, generating a test script for performance test cases and what not! After quite some time, I got the feel of how projects are to be made in industries where all the above requirements need to be fulfilled along with functionality. As a whole it was the most fun excerise. A little about my timing: developing the functionality took me close to 45 min. Writing junits and testscript took me 20 min. Remaining documentation took me 10 min. All together this took about 1 hour 15 min to complete.

 ## Design
I have implemented this problem as a tree search problem where every level depicts one rule to be verified. First check would be whether the direction is inbound/outbound. Second is what protocol is being used. This gives us 4 unique combination to begin with (inbound/outbound/tcp/udp). For each combination I have created a list of ports followed by ip addresses and maintained it in a hashmap. Once check 1 and 2 are done, my program searches through the list of containing ports:ipAddresses. It then verifies whether the port is within the range or is equal to the one given in the rules. If the ports are matched then corresponding ip addresses are verified. This way at each level my program does verification.

## Code Structure
I have created 4 classes:
1. Firewall.java: This class reads the rules.csv and creates the map with the concatenation of direction and protocal as keys. It also maintains a list of rules in a string array list.
2. PortAndIPAddressVerification.java: This file has one public method for port verification and one internal implementation of subsequent ip address verification. Both the methods contain inner level logic for the string matching. All the methods are static and do not need class instantiation.
3. GenerateTest.java: This java class is used for creating random test data. It makes sure the data created is valid and also contains ranges and single values. This same class can also be used for creating queries without having any range data. It requires the input of no. of queires to be created and whether these queries are for rules or are input queries.
4. FirewallTest.java: Has unit tests using junit utility to make sure corner cases are covered and functionality is as expected.

## Executing the code
```mvn clean install; java -cp target/my-firewall-app-1.0-SNAPSHOT.jar com.firewall.app.Firewall```

## Complexity
In the worst case, all the rules might fall under one category. This would give complexity of O(n). Space complexity would be again 0(n) (to store all n rules). The reason I opted for this design is that, if hashing would have been used to keep all the unique combinations in the map then the space complexity would could been exponential. I wanted to implement a balance of both time and space complexity and opted for this approach.

## Testing
For testing functionality, corner cases and correctness I have used junit to test the queries. For large scale testing I created the rule file with 500k lines and the queryfile with 42 queries. The time taken to run those 42 queries is about 2 seconds.

## Improvements
If I had more time, then I wanted to implement merging of those rules whose ports could be combined further. That way, I would keep only the IP addresses in the list and the indexing of the map would be based on direction.protocol.port. This would further refine my search.

## Team I would be interested for
Platform team and Data team!
