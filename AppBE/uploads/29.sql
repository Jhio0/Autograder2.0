rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

set linesize 100
set pagesize 66
set echo on

spool c:/cprg250s/LabUnit10output.txt

rem 1. Display all agents who specialize in US travel packages. Sort by last name.


SELECT LAST_NAME, FIRST_NAME, AGENT_LEVEL
FROM RCV_AGENT
WHERE AGENT_SPECIALITY = 'US'
ORDER BY LAST_NAME;


rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 


COLUMN DEST_DESCRIPTION FORMAT A80 HEADING 'Destination Desc';
COLUMN COUNTRY FORMAT A15;
COLUMN CITY FORMAT A10;
COLUMN PRICE FORMAT 9999.99 HEADING 'Price';

SELECT DEST_DESCRIPTION, COUNTRY, STATE, CITY, PRICE
FROM RCV_DESTINATION
WHERE (COUNTRY = 'Spain' OR COUNTRY = 'France')
  AND PRICE <= 100
ORDER BY COUNTRY, STATE, CITY, PRICE;

CLEAR COLUMNS;


rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.


COLUMN FIRST_NAME FORMAT A20 HEADING 'First Name';
COLUMN LAST_NAME FORMAT A25 HEADING 'Last Name';
COLUMN CUSTOMER_PHONE FORMAT A12 HEADING 'Phone';

SELECT FIRST_NAME, LAST_NAME, 
       REGEXP_REPLACE(CUSTOMER_PHONE, '(\d{3})(\d{3})(\d{4})', '\1.\2.\3') AS CUSTOMER_PHONE
FROM RCV_CUSTOMER
WHERE CUSTOMER_PROVINCE = 'CA'
  AND CUSTOMER_PHONE LIKE '310%'
ORDER BY LAST_NAME, FIRST_NAME;

CLEAR COLUMNS;


spool off

