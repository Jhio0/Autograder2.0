rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

set linesize 100

set pagesize 66

set echo on

spool c:/cprg250s/LabUnit10output.txt

rem 1. Display all agents who specialize in US travel packages. Sort by last name.
SELECT LAST_NAME, FIRST_NAME,AGENT_LEVEL FROM RCV_AGENT WHERE agent_speciality = 'US' ORDER BY LAST_NAME;


rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide.

set linesize 160
column DEST_DESCRIPTION format A80
column country format A15
column city format A10
SELECT DEST_DESCRIPTION,COUNTRY,STATE,CITY,PRICE FROM RCV_DESTINATION
WHERE COUNTRY = 'Spain' and price <= 100 OR COUNTRY = 'France' AND PRICE <= 100
Order by country, state, city, Dest_description, price ;

REM 3. FIND ALL CUSTOMERS IN CALIFORNIA WITH A PHONE NUMBER THAT STARTS WITH A ‘310’ AREA CODE. SORT BY LAST NAME
select first_name as First, last_name as Last, customer_phone, customer_province from rcv_customer
where customer_phone LIKE '310%'
order by Last, First
