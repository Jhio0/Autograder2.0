rem Unit 10-11 Lab Simple Select and sorting data


set linesize 100
set pagesize 66
set echo on

spool c:/cprg250s/SimpleSelectandSortingData.txt

rem 1. Display all agents who specialize in US travel packages. Sort by last name.
SELECT last_name, first_name, agent_level
FROM RCV_Agent
WHERE agent_speciality = 'US'
ORDER BY last_name;


rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 
SET LINESIZE 160;

COLUMN dest_description FORMAT A80 HEADING 'Destination Desc.';
COLUMN country FORMAT A15 HEADING 'COUNTRY';
COLUMN city FORMAT A10 HEADING 'CITY';

SELECT dest_description, country, state, city, price
FROM RCV_Destination
WHERE country IN ('France', 'Spain') AND price <= 100
ORDER BY country, state, city, dest_description, price;

CLEAR COLUMNS;

rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.
COLUMN first_name FORMAT A15 HEADING 'First';
COLUMN last_name FORMAT A25 HEADING 'Last';
COLUMN customer_phone FORMAT A15 HEADING 'Phone';
COLUMN customer_province FORMAT A4 HEADING 'Prov';

SELECT first_name, last_name, customer_phone, customer_province
FROM RCV_Customer
WHERE customer_province = 'CA' AND customer_phone LIKE '310%'
ORDER BY last_name, first_name;

CLEAR COLUMNS;



spool off

