# Not expected behavior for Instant/TIMESTAMP_UTC

We have Oracle as database, version 19.x set in Timezone GMT+2
We are running hibernate with TimeZone Europe/Stockholm set.
Column holding Instant is TIMESTAMP.

For this test I added another persistence config for an oracle database.
I do hope you have one available for your tests, because I cannot provide you with a database.

Saving and Getting dateValues does not work as expected.
If you set the "hibernate.type.preferred_instant_jdbc_type" to TIMESTAMP, then test will run OK.
If you keep it to default, that is TIMESTAMP_UTC, test will fail, no entity will be found.

If you run this towards H2 or Postgres it will work, if you run it towards Oracle, it fails.  
Haven't tested any more databases. 