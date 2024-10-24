rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

set linesize 100

set echo on

spool "C:\Users\macintoshsoldat\Documents\Spool file"

rem 1. Display all agents who specialize in US travel packages. Sort by last name.

select 
last_name,
first_name,
agent_level as agen 
from rcv_agent 
where agent_speciality = 'US'
order by last_name; 



rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 
set LINESIZE 160

SELECT
COUNTRY,
state,
city,
price
from RCV_DESTINATION
where price < 100 and country = 'France' or price < 100 and country = 'Spain'
order by country,state,city,price;






rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.

select 
CUSTOMER_PHONE,
LAST_NAME,
FIRST_NAME
FROM RCV_CUSTOMER
where regexp_like (CUSTOMER_PHONE, '310')
order by LAST_NAME,FIRST_NAME;


spool off

