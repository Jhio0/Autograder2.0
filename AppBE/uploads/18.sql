rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

set linesize 160
set pagesize 66
set echo on

spool c:/cprg250s/LabUnit10output.txt

rem 1. Display all agents who specialize in US travel packages. Sort by last name.

SELECT agent_id, first_name, last_name
FROM rcv_agent
WHERE agent_speciality = 'US'
ORDER BY last_name;

rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 

column dest_description FORMAT A80
column country FORMAT A15
column city FORMAT A10
SELECT d.dest_description, d.country, d.city, d.price
FROM rcv_destination d
JOIN rcv_tour_destination td ON d.dest_code = td.dest_code
JOIN rcv_vacation_tour vt ON td.tour_code = vt.tour_code
WHERE d.country IN ('France', 'Spain')
AND d.price <= 100
ORDER BY d.country, d.state, d.city, d.price;

rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.

column dest_description FORMAT A80
column country FORMAT A15
SELECT first_name, last_name, customer_phone
FROM rcv_customer
WHERE customer_province = 'CA'
AND customer_phone LIKE '310%'
ORDER BY last_name, first_name;

spool off

