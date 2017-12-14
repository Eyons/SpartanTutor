# SpartanTutor

Program due this upcoming Thursday. Video due this Friday.

Required At Moment:

-Profiles

-Class Search

Database is located at https://spartantutor-d15cf.firebaseio.com/

Users can be accessed at https://spartantutor-d15cf.firebaseio.com/users.json

Messages can be accessed at https://spartantutor-d15cf.firebaseio.com/messages.json

To commit something to the database requires a few simple lines, for example with committing to the user directory:

'Firebase ref = new Firebase("https://spartantutor-d15cf.firebaseio.com/users");'
ref.child(user).child("newCommit").setValue("ValueOfCommit");

Passwords are stored in the the child "password" and the status (tutee/tutor) of the user is stored in the child "status".
