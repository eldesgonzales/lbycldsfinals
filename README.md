# Rainfall
Rainfall is Cloud-based Storage that uses IBM Services such as ClearDB, and Object Storage. It can also translate texts incase the user can't understand English.

# Technologies used
1. Bootstrap
2. JQuery
3. AJAX (GSON)
4. JSP
5. Object Storage
6. ClearDB
7. Language Translator

# How to use?
1. Clone the app

### A. Clear DB ###
1. Login to https://console.ng.bluemix.net and click "Create Service"
2. Search "ClearDB", and set it into "leave unbound"
3. Go to ClearDB's Dashboard.
4. Click the schema under the "My Databases" table
5. From the schema, click on the "Endpoint Information" tab
6. Get the following information:
    * Schema name: ad_xxxxxxxxxxxxxxx
    * Hostname: (eg. us-cdbr-iron-east-03.cleardb.net)
    * Username
    * Password
7. Go into the app, and find __EnvVariables.java__ from net.tutorial.utilities
8. Find `creds.put("jdbcUrl", "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_168a37774228b80?user=bc2867bbe64153&password=836091b7");` and change it into this format:
  
>`creds.put("jdbcUrl", "jdbc:mysql://[HOSTNAME]/[SCHEMA]?user=[USERNAME]&password=[PASSWORD]");`

### B. Object Storage ###
1. Go back to the dashboard and click "Create Service"
2. Search "Object Storage" under the Storage, and set it into "leave unbound"
3. Once created, click on the service
4. Click on the "Service Credentials" tab
5. From the "Service Credentials" tab, click on "New Credential"
6. Click add, and it will generate a credential
7. View the credential and get the following:
    * username
    * password
    * domainId
    * projectId
  
8. Go back to the app's project, and find __ObjectStorageService.java__ from net.tutorial.utilities
9. Find these and change the following (no square brackets, just ""):
  
  `private static final String USERNAME = [username];`
  
	private static final String PASSWORD = [password];
  
	private static final String DOMAIN_ID = [domainId];
  
	private static final String PROJECT_ID = [projectId];
  
	private static final String OBJECT_STORAGE_AUTH_URL = "https://identity.open.softlayer.com/v3";`

10. Build the gradle

### C. Deploy into Bluemix ###
1. Open the command prompt and go to __lbycldsfinals__ directory
2. Put this command:
  
>`cf login -a https://api.ng.bluemix.net -s [SPACE-NAME]`
  
3. Input your credentials when prompted
4. Enter the following command:
  
>`cf push [LASTNAME]-Rainfall -m 256M -p build/libs/AddressBookAppSQL.war`

### D. Language Translator ###
1. Go to the dashboard, create a Service of Language Translator.
2. Bind it into your app.