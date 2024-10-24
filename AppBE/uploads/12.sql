rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

set linesize 100
set pagesize 66
set echo on

spool "C:\Users\17\Documents\ClassWork\DataBase\SELECTLAB"

rem 1. Display all agents who specialize in US travel packages. Sort by last name.

SELECT LAST_NAME, FIRST_NAME, AGENT_LEVEL as AGEN
FROM RCV_AGENT
WHERE AGENT_SPECIALITY = 'US'
ORDER BY LAST_NAME;

CLEAR COLUMNS
rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 
SET LINESIZE 160
COLUMN DEST_DESCRIPTION FORMAT A80
COLUMN COUNTRY FORMAT A15
COLUMN CITY FORMAT A10
SELECT DEST_DESCRIPTION AS "Destination Desc.", COUNTRY, STATE AS ST, CITY, PRICE
FROM RCV_DESTINATION
WHERE COUNTRY = 'France' AND PRICE <= 100 OR COUNTRY = 'Spain' AND PRICE <= 100
ORDER BY COUNTRY, CITY;

CLEAR COLUMNS

rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.
-- column dest_description FORMAT A80
-- column country FORMAT A15


SELECT FIRST_NAME as "First", LAST_NAME as "Last", CUSTOMER_PHONE as "Phone", CUSTOMER_PROVINCE as "Prov"
FROM RCV_CUSTOMER
WHERE CUSTOMER_PHONE LIKE '310%'
ORDER BY LAST_NAME, FIRST_NAME;

CLEAR COLUMNS

spool off

