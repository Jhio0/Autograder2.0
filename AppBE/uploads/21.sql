rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

set linesize 100
set pagesize 66
set echo on

spool c:/cprg250s/LabUnit10output.txt

rem 1. Display all agents who specialize in US travel packages. Sort by last name.

SELECT LAST_NAME,FIRST_NAME,AGENT_LEVEL
FROM RCV_AGENT
WHERE AGENT_SPECIALITY = 'US'
ORDER BY LAST_NAME;

CLEAR COLUMNS;


rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 


SET LINESIZE 160;
COLUMN dest_description FORMAT A80;
COLUMN country FORMAT A15;
COLUMN city FORMAT A10;

SELECT DEST_DESCRIPTION , COUNTRY , STATE, CITY, PRICE
FROM RCV_DESTINATION 
WHERE COUNTRY IN ('France', 'Spain') AND PRICE <= 100
ORDER BY COUNTRY, STATE, CITY, PRICE;

CLEAR COLUMNS;



rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.
column dest_description FORMAT A80
column country FORMAT A15

SELECT FIRST_NAME, LAST_NAME, CUSTOMER_PHONE , CUSTOMER_PROVINCE
FROM RCV_CUSTOMER
WHERE CUSTOMER_PROVINCE = 'CA' AND CUSTOMER_PHONE LIKE '310%'
ORDER BY LAST_NAME, FIRST_NAME;

spool off

