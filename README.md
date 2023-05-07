You may refer this file for instructions or you may refer the readme.docx file for instructions with pictures.
Prerequisites: docker, Java17, maven, docker-compose, Postman

#Please install the prerequisites before running the below commands

#If you want to build images locally, these are the steps.

mvn clean install -DskipTests=true

./run.sh

#Alternatively you can download the images from docker hub

docker-compose up

#Wait for some time after running this command until you can see
keycloak running on localhost:8080

#If you are using Mac, open terminal and type the command:

sudo nano /etc/hosts

Add 127.0.0.1 'tab' keycloak

#If you are using Windows, open terminal and type the command:

c:\\Windows\\System32\\Drivers\\etc\\hosts

Add 127.0.0.1 'tab' keycloak

#Open your browser, type

Localhost:8080

#You must see keycloak running


#Go to Administration Console

#Sign in using username as admin and password as admin


\# In the Clients tab select spring-cloud-client

#In the Credentials tab click on Regenerate Secret.

#Copy the generated key

#There are two approaches to execute requests on the application.

a)  Using Postman

b)  Using Curl

If you do not want to use Postman, move to the bottom part of this file
showing the way to execute requests using Curl

**Using Postman**

#Open Postman

#On the top left near Scratch Pad there is a button called Import.

\# Click on Import

Import all the three files given,

Order Service.postman_collection.json, Product
Service.postman_collection.json, Inventory
Service.postman_collection.json

#Now click on Authorization

#From the dropdown in the type select OAuth 2.0

#On the right to the type in the text box with the label Client Secret,
paste the key generated using KeyCloak

#Now scroll down and select the orange button labeled 'Get New Access
Token'

#Click on Proceed

#Click on Use token

Click on Send


Every service can be implemented in the same manner.

**Using Curl**

#Go to <https://www.base64encode.org/>

Click on Encode

#Paste the key obtained from Keycloak in this format

spring-cloud-client:key obtained from keycloak

Example: spring-cloud-client:Hr9tGnoynPDpFoKAkCybri3qM3gzY3S2

Now, click on Encode.

It will generate the encoded key


#Copy the encoded key obtained after clicking on encode

#Example:

c3ByaW5nLWNsb3VkLWNsaWVudDpIcjl0R25veW5QRHBGb0tBa0N5YnJpM3FNM2d6WTNTMg==

#Inside the 'token.sh' file,

#Replace the value of the Client_Secret variable in the first line of
token.sh with the encoded key.

On the terminal write,

./token.sh

Every service can be implemented in the this manner.

**To obtain the metrics**

#Go to your browser and type

localhost:3000

#This will open Grafana

#Login using username as **admin** and password as **password**

#Click on Add your first data source

#Select Prometheus

#Change the URL to

<http://prometheus:9090>

#Scroll down and click on Save and test

#On the left you can see a '+' symbol. Click on that


#Click on Import

#Paste the contents of 'Grafana.json' shared on Github in the 'Import
via panel json' text area visible in the image

#Click on Load

#Select Prometheus data source from Prometheus drop down and Click on
the pink button 'Import (Overwrite)'

And you can finally see the generated metrics
