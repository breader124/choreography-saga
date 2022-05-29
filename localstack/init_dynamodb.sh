awslocal dynamodb create-table \
    --table-name OrderDB \
    --attribute-definitions \
        AttributeName=OrderId,AttributeType=S \
    --key-schema \
        AttributeName=OrderId,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --table-class STANDARD

awslocal dynamodb create-table \
    --table-name WarehouseDB \
    --attribute-definitions \
        AttributeName=ItemId,AttributeType=S \
    --key-schema \
        AttributeName=ItemId,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --table-class STANDARD
