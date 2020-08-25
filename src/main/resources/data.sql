
INSERT INTO `property`.`property_type` (property_type_id, name)
SELECT '1', 'Apartment' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM Property_type
                  WHERE `property_type_id`='1' LIMIT 1);

INSERT INTO property.property_type (property_type_id, name)
SELECT '2', 'House' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM Property_type
                  WHERE `property_type_id`='2' LIMIT 1);

INSERT INTO property.property_type (property_type_id, name)
SELECT '3', 'Room' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM Property_type
                  WHERE `property_type_id`='3' LIMIT 1);





INSERT INTO property.transaction_type (`transaction_type_id`,`isincome`, `name`)
SELECT '1', true, 'Rent Income' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM property.transaction_type
                  WHERE `transaction_type_id`='1' LIMIT 1);

INSERT INTO property.transaction_type (`transaction_type_id`,`isincome`, `name`)
SELECT '2', false, 'Maintenance' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM property.transaction_type
                  WHERE `transaction_type_id`='2' LIMIT 1);

INSERT INTO property.transaction_type (`transaction_type_id`,`isincome`, `name`)
SELECT '3', false, 'Repairs' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM property.transaction_type
                  WHERE `transaction_type_id`='3' LIMIT 1);

INSERT INTO property.transaction_type (`transaction_type_id`,`isincome`, `name`)
SELECT '4', true, 'Deposit Received' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM property.transaction_type
                  WHERE `transaction_type_id`='4' LIMIT 1);

INSERT INTO property.transaction_type (`transaction_type_id`,`isincome`, `name`)
SELECT '5', true, 'Deposit Paid' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM property.transaction_type
                  WHERE `transaction_type_id`='5' LIMIT 1);



