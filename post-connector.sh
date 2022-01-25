set -u -e

FILENAME=$1

curl localhost:8083/connectors \
     -H "Accept:application/json" \
     -H  "Content-Type:application/json" \
     -X POST \
     -d @$FILENAME
