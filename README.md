# MailRobot

## Description




## Setting up a mock SMTP server

Your mission is to develop a client application that automatically plays pranks on a list of victims:

* The user should be able to **define a list of victims** (concretely, you should be able to create a file containing a list of e-mail addresses).

* The user should be able to **define how many groups of victims should be formed** in a given campaign. In every group of victims, there should be 1 sender and at least 2 recipients (i.e. the minimum size for a group is 3).

* The user should be able to **define a list of e-mail messages**. When a prank is played on a group of victims, then one of these messages should be selected. **The mail should be sent to all group recipients, from the address of the group sender**. In other words, the recipient victims should be lead to believe that the sender victim has sent them.


## Running pranks 

Consider that your program generates a group G1. The group sender is Bob. The group recipients are Alice, Claire and Peter. When the prank is played on group G1, then your program should pick one of the fake messages. It should communicate with a SMTP server, so that Alice, Claire and Peter receive an e-mail, which appears to be sent by Bob.







