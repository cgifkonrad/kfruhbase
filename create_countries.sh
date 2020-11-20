#/bin/bash


curl --request POST \
  --url http://localhost:8080/countries/schema \
  --header 'Accept: text/xml' \
  --header 'Content-Type: text/xml' \
  --data '<?xml version="1.0" encoding="UTF-8"?>
<TableSchema name="countries" IS_META="false">
	<ColumnSchema name="countrycodes" BLOOMFILTER="ROW" VERSIONS="1" IN_MEMORY="true" KEEP_DELETED_CELLS="FALSE" DATA_BLOCK_ENCODING="NONE" TTL="2147483647" COMPRESSION="NONE" MIN_VERSIONS="0" BLOCKCACHE="true" BLOCKSIZE="65536" REPLICATION_SCOPE="0"/>
	<ColumnSchema name="metainformation" BLOOMFILTER="ROW" VERSIONS="10" IN_MEMORY="false" KEEP_DELETED_CELLS="FALSE" DATA_BLOCK_ENCODING="NONE" TTL="2147483647" COMPRESSION="NONE" MIN_VERSIONS="0" BLOCKCACHE="true" BLOCKSIZE="65536" REPLICATION_SCOPE="1"/>
</TableSchema>'


curl --request PUT \
  --url http://localhost:8080/countries/fakerow \
  --header 'Accept: text/xml' \
  --header 'Content-Type: text/xml' \
  -d countries.xml