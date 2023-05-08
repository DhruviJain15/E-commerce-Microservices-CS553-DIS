E-commerce-Microservices-CS553-DIS
==================================

**Note:** You may refer this file for instructions or you may refer the readme.docx file for instructions with pictures.

**Prerequisites:** docker, Java17, maven, docker-compose, Postman

Please install the prerequisites before running the below commands.

1. **Steps for building images locally:**

``mvn clean install -DskipTests=true``

``./run.sh``

Alternatively you can download the images from docker hub using ``docker-compose up``

Wait for some time after running this command until you can see
keycloak running on localhost:8080

2. If you are using Mac, open terminal and type the below command:

``sudo nano /etc/hosts``

Add 127.0.0.1 'tab' keycloak

3. If you are using Windows, open terminal and type the command:

``c:\\Windows\\System32\\Drivers\\etc\\hosts``

Add 127.0.0.1 'tab' keycloak

4. Open your browser, type

``localhost:8080``

**You must see keycloak running**

5. Go to Administration Console

6. Sign in using username as admin and password as admin

7. In the Clients tab select spring-cloud-client

8. In the Credentials tab click on Regenerate Secret.

9. Copy the generated key

10. There are two approaches to execute requests on the application.
  a) Using Postman
  b) Using Curl

If you do not want to use Postman, move to the bottom part of this file
showing the way to execute requests using Curl

**Using Postman**

  - Open Postman

  - On the top left near Scratch Pad there is a button called Import.

  - Click on Import

  - Import all the three files given,

    Order Service.postman_collection.json, Product
    Service.postman_collection.json, Inventory
    Service.postman_collection.json

  - Now click on Authorization

  - From the dropdown in the type select OAuth 2.0

  - On the right to the type in the text box with the label Client Secret, paste the key generated using KeyCloak

  - Now scroll down and select the orange button labeled 'Get New Access Token'

  - Click on Proceed

  - Click on Use token

  - Click on Send

Every service can be implemented in the same manner.

**Using Curl**

  - Go to <https://www.base64encode.org/>

  - Click on Encode

  - Paste the key obtained from Keycloak in this format

    spring-cloud-client:key obtained from keycloak

    **Example:** spring-cloud-client:Hr9tGnoynPDpFoKAkCybri3qM3gzY3S2

  - Now, click on Encode. It will generate the encoded key

  - Copy the encoded key obtained after clicking on encode

    **Example:** c3ByaW5nLWNsb3VkLWNsaWVudDpIcjl0R25veW5QRHBGb0tBa0N5YnJpM3FNM2d6WTNTMg==

  - Inside the 'loadOrder.sh' file and 'loadProduct.sh',

  - Replace the value of the Client_Secret variable in the first line of token.sh with the encoded key.

  - On the terminal write, ``./loadOrder.sh`` and ``./loadProduct.sh``

Every service can be implemented in the this manner.

11. To obtain the metrics

  - Go to your browser and type ``localhost:3000``. This will open Grafana.

  - Login using username as **admin** and password as **password**

  - Click on Add your first data source

  - Select Prometheus

  - Change the URL to ``<http://prometheus:9090>``

  - Scroll down and click on Save and test

  - On the left you can see a '+' symbol. Click on that

  - Click on Import

  - Paste the contents of 'Grafana.json' shared on Github in the 'Import via panel json' text area visible in the image

  - Click on Load

  - Select Prometheus data source from Prometheus drop down and Click on the pink button 'Import (Overwrite)'

**Now you can finally see the generated metrics!**
