# monitoring-demo
Demo project to show monitoring using TIG (Telegraf, InfluxDB and Grafana)

Screenshots of dashboards during the project load in [monitoring_screenshots](monitoring_screenshots) folder.

## Start the project with docker compose

```bash
$ docker-compose up
```
## Use the script for load generation
[load_script.sh](load_script.sh)

## Services and Ports

### Nginx
- URL http://localhost:8080

### demo-app-service SpringBoot Web application
- Nginx URL: http://localhost:8080/products
- original URL: http://localhost:8081/products

#### Endpoint steps:

1) Search Product in Elasticsearch DB by category containing randomly selected keyword from 'categories_key_words' property from file [application.yml](demo-app-service%2Fsrc%2Fmain%2Fresources%2Fapplication.yml). 
2) Find Inventory in MongoDB by product code for all found Products. 
3) Update 'quantity' field in Inventory with random int value and save Inventory to MongoDB.
4) Metrics 'request.successful.count' and 'request.successful.time' are being sent via UDP to the Telegraf agent using the StatsD protocol.

Product catalog and inventory are imported to databases from file [product_catalog.csv](demo-app-service%2Fsrc%2Fmain%2Fresources%2Fproduct_catalog.csv)  during the first Application startup.

### Grafana
- Nginx URL: http://localhost:8080
- User: admin
- Password: admin
- original URL: http://localhost:3000
- 
### Telegraf
- Port: 8125 UDP (StatsD input)

### InfluxDB
- Port: 8086 (HTTP API)
- User: admin
- Password: admin
- Database: influx

### Elasticsearch
- Port: 9200

### MongoDB
- Port: 27017

## License

The MIT License (MIT). Please see [License File](LICENSE) for more information.