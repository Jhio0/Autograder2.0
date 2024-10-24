rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

-- set linesize 100
-- set pagesize 66
set echo on
set linesize 100
set pagesize 50
set feedback ON
set heading ON
set colsep ' | '

spool c:/cprg250s/LabUnit10output.txt

rem 1. Display all agents who specialize in US travel packages. Sort by last name.

COLUMN "LAST_NAME" A10
COLUMN "FIRST_NAME" A10
COLUMN "AGEN" A10
SELECT last_name as "LAST_NAME",first_name as "FIRST_NAME",agent_level as "AGEN"
FROM   rcv_agent
WHERE  AGENT_SPECIALITY = 'US'
ORDER BY LAST_NAME;    


rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 
SET LINESIZE 160
COLUMN "Destination Desc." FORMAT A73
COLUMN "COUNTRY" FORMAT A15
COLUMN "CITY" FORMAT A10

SELECT dest_description as "Destination Desc.",country as "COUNTRY",state as "ST",city as "CITY",price as "Price"
FROM   RCV_DESTINATION
WHERE  price <= 100 AND (country = 'France' OR country = 'Spain')
ORDER BY country, state, city, price;
 
rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.

SELECT first_name  as "First", last_name as "Last" , customer_phone as "Phone", customer_province as "PROV"
FROM   RCV_CUSTOMER
WHERE CUSTOMER_CITY = 'California' OR CUSTOMER_PHONE LIKE '310%'
ORDER BY LAST_NAME, FIRST_NAME;

spool off

