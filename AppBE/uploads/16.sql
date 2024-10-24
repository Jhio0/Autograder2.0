rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

set linesize 100
set pagesize 66
set echo on

spool c:/cprg250s/Lab4spool.txt

rem 1. Display all agents who specialize in US travel packages. Sort by last name.
select  last_name as Last,first_name as FIRST, agent_level as agen from RCV_Agent where agent_speciality='US' ORDER BY last_name;

rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 
set linesize 260;
COLUMN dest_description FORMAT A80;
COLUMN country FORMAT A15;
COLUMN state FORMAT A15;
COLUMN city FORMAT A10;

select dest_description as destination,country,state as st,city,price from RCV_DESTINATION where (country='France' OR country='Spain') AND  price <=100 ORDER BY country,city,dest_description asc,price desc,state;

rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.

select first_name as First, last_name as Last, customer_phone as Phone, customer_province as Prov from RCV_Customer where customer_province='CA' and customer_phone like '310%' ORDER BY last_name,first_name;

spool off

