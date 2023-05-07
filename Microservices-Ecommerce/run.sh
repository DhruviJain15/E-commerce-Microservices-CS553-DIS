docker build -t discovery-server-dockerfile discovery-server/
docker build -t apigateway-dockerfile api-gateway/
docker build -t product-service-dockerfile product-service/
docker build -t order-service-dockerfile order-service/
docker build -t inventory-service-dockerfile inventory-service/
docker build -t notification-service notification-service/