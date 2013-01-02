HTTP Server (and Tic Tac Toe) in Java

This HTTP Server is an exercise in creating sockets, parsing requests and serving both static and dynamic content.

##Static Content & Routes

You can use this to serve static content on OS X by placing all of your content and a routes.txt file in a directory and starting the included HTTPserver.jar from Terminal with the following command (be sure that you're in the directory with the Jar):

        java -jar HTTPserver.jar -p 5000 -d /Users/yourUserName/directoryWhereYourContentIs/

-p: the port you would like to use. 
-d: the directory with your content and routes.txt file.

**Routes**

Your routes file should be formatted like this

	PATH  | VERB |  RESOURCE
	/ | GET | index.html
	/show_me_text | GET | textFile.txt

You can see other examples in [resources/CobSpec](https://github.com/mikeebert/http-server/tree/master/resources/CobSpec) and [resources/tictactoe](https://github.com/mikeebert/http-server/tree/master/resources/tictactoe)

##Dynamic Content

I’m serving Dynamic Content using Controllers, and you can see examples of these in [src/HttpServer/CobSpecController](https://github.com/mikeebert/http-server/blob/master/src/HttpServer/CobSpecController.java) and [src/tictactoe/GameController](https://github.com/mikeebert/http-server/blob/master/src/tictactoe/GameController.java). 
In order to use the controllers the routes file just directs the request to a controller action:

	PATH  | VERB |  RESOURCE
	game/update | POST | game/udpate
	
The HTTP router will assume any requested resource that doesn’t have a file extension (i.e. .html, .jpg, etc... ) is a request for dynamic content and it looks for the controller action that it’s mapped to.

##Play Tic Tac Toe

You can play tic-tac-toe versus an unbeatable UI by starting the included Jar with the following command:

	java -jar HTTPserver.jar -p 5000 -d /Path-to-the-tictactoe-resources-directory-in-this-repo
	
##UNIT TESTS

**Test Dependencies:**

1)Junit 4.10 (if using IntelliJ go to Project Structure > Platform Settings > SDKs and include JUnit)

2) Tic-Tac-Toe code. Download my [Tic-Tac-Toe repo](https://github.com/mikeebert/tictactoe-java) and in IntelliJ go to Project Structure > Modules > New Module and add an existing module using the tictactoe-java.iml file.

3) FileReader Binary Test requires org.apache.commons.io to compare byte arrays. In IntelliJ go to Project Structure > Modules. Select HTTP-server and using "+" button on the bottom add Library from Maven. I'm using org.apache.commons.io-2.1 ... or you can just comment out the test to run the rest of the suite.

4) Some of the tests rely on hardcoded paths to mock files. Update the **DIR** constant at the top of these three files:
- ResponderTest
- RouterTest

There are also a few commented-out tests. These are tests that I need to finish but I’m having trouble figuring out the proper mocks. (There may also be a few comments in the code. I know they shouldn’t be there but and as I keep working on this I’ll remove them.)
