README

Important Note for GREP:
As the requirements stated, the "ctrl-z" is said to be parse as standard input to stop any ongoing process, 
instead of having a listener to detect ctrl-z.

Thus it clashes with the Grep Standard input logic, which means if the user type in
"ctrl-z" during the input prompt for Grep, Grep will interpret it as a standard input and 
tries to match it with the pattern given while at the same time, the thread will see the "ctrl-z"
as a sign to stop the Grep process. 

As a result, "ctrl-z" of the THREAD is disabled for Grep and it is implemented locally at the GREPTool class,
thus for single/multiple files input instead of standard input, "ctrl-z" of the thread will NOT be able to 
stop the process.

Important Note for CAT:
In particular, the command cat - should either return an error or nothing or never return at all (interuptable only by Ctrl-Z).
For our implementation, cat - returns nothing.

Important Note for IntegrateTest:
There is a var “os” which need to be set first before running the test because there is extra files in the mac os like “.DS_Store”. Therefore will product different result. Please specify os=0 if you’re running window and os=1 if you are running mac

Important note for ECHOToolTest:
echoValidSystemVariableTest(), executeEchoValidSystemVariableTest() can be only runs in windows

Important note for Hackathon
All the failing tests are compiled a separate file - “FailingTestCases.java” 