set -u -e

NAME=$1

curl localhost:8083/connectors/$NAME \
     -H "Accept:application/json" \
     -H  "Content-Type:application/json" \
     -X DELETE 
