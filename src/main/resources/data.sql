INSERT INTO Property_type (property_type_id, name)
SELECT '1', 'Apartment' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM Property_type
                  WHERE `property_type_id`='1' LIMIT 1);

INSERT INTO Property_type (property_type_id, name)
SELECT '2', 'House' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM Property_type
                  WHERE `property_type_id`='2' LIMIT 1);

INSERT INTO Property_type (property_type_id, name)
SELECT '3', 'Room' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM Property_type
                  WHERE `property_type_id`='3' LIMIT 1);



