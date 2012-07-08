echo "Test Bench started..."
TestBench.jar data/addressbook.json out/addressbook.html
TestBench.jar data/addressbook-20.json out/addressbook-20.html
TestBench.jar data/addressbook-40.json out/addressbook-40.html
TestBench.jar data/addressbook-60.json out/addressbook-60.html
TestBench.jar data/addressbook-80.json out/addressbook-80.html
TestBench.jar data/addressbook-100.json out/addressbook-100.html

echo "Finished."
pause