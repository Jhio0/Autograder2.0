rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

set linesize 100
set pagesize 66
set echo on

spool 'labUnit1Output.txt'

rem 1. Display all agents who specialize in US travel packages. Sort by last name.
SELECT LAST_NAME, FIRST_NAME, AGENT_LEVEL AS AGEN FROM RCV_AGENT WHERE AGENT_SPECIALITY = 'US' ORDER BY last_name;

rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 
SET LINESIZE 160
column dest_description FORMAT A80
column country FORMAT A15
column city FORMAT A10
SELECT DEST_DESCRIPTION, COUNTRY, STATE, CITY, PRICE FROM RCV_DESTINATION WHERE (COUNTRY = 'France' OR COUNTRY = 'Spain') AND PRICE <= 100 ORDER BY COUNTRY ASC, CITY, DEST_DESCRIPTION ASC, PRICE;

rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.
column dest_description FORMAT A80
column country FORMAT A15
SELECT FIRST_NAME AS "First", LAST_NAME AS "Last", CUSTOMER_PHONE AS "Phone", CUSTOMER_PROVINCE AS "Prov" FROM RCV_CUSTOMER WHERE CUSTOMER_PHONE LIKE '%310%' ORDER BY LAST_NAME, FIRST_NAME;

spool off

