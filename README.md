HTTP Server (and Tic Tac Toe) in Java

*Note: as of 12/24/12 the Jar does not contain the org.apache.commons.io.FileUtils library required to serve images so it’s just text. Still figuring the dependency out in IntelliJ*

This HTTP Server is an exercise in creating sockets, parsing requests and serving both static and dynamic responses.

##Static Content & Routes

You can use this to serve static content on OS X by placing all of your content and a routes.txt file in a directory and starting the HTTPserver.jar from Terminal with the following command:

        java -jar -p 5000 -d /Users/yourUserName/directoryWhereYourContentIs/

-p: the port you would like to use. 
-d: the directory with your content and routes.txt file.

**Routes**

Your routes file should be formatted like this

	PATH  | VERB |  RESOURCE
	/ | GET | index.html
	/show_me_text | GET | textFile.txt

You can see other examples in [resources/CobSpec](https://github.com/mikeebert/http-server/tree/master/resources/CobSpec) and [resources/tictactoe](https://github.com/mikeebert/http-server/tree/master/resources/tictactoe)

##Dynamic Content

I’m serving Dynamic Content using Controllers, and you can see examples of these in [src/HttpServer/CobSpecController](https://github.com/mikeebert/http-server/blob/master/src/HttpServer/CobSpecController.java) and [src/tictactoe/GameController](https://github.com/mikeebert/http-server/blob/master/src/tictactoe/GameController.java). In order to use these the routes file just directs the request to a controller action:

	PATH  | VERB |  RESOURCE
	game/update | POST | game/udpate
	
The HTTP router will assume any requested resource that doesn’t have an extension is a request for dynamic content and it looks for the controller action that it’s mapped to.

##Play Tic Tac Toe

You can play tic-tac-toe versus an unbeatable UI by starting the Jar with the following command:

	java -jar -p 5000 -d /Path-to-the-tictactoe-resources-directory-in-this-repo
	
##TESTS

Some of these tests rely on hardcoded paths to mock files. For the test suite to properly run update the **DIR** constant at the top of these four files:
- CobSpecControllerTest
- ResponderTest
- ResponseBuilderTest
- RouterTest

There are a few commented-out tests. These are tests that I need to finish but am having trouble figuring out the proper mocks. (There are also a few comments in the code. I know they shouldn’t be there but since this is an exercise those are important notes to myself.)
