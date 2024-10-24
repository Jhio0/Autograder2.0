rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

set linesize 100
set pagesize 66
set echo on

spool 'D:/SAIT/CPRG250 - Database/Select/spool_select_TeruelBBA.txt'

rem 1. Display all agents who specialize in US travel packages. Sort by last name.

select * from rcv_agent 
where agent_speciality = 'US' 
order by last_name;

clear column

rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 

set LINESIZE 160
column dest_description FORMAT A80
column country FORMAT A15
column city FORMAT A10

select * from rcv_destination 
where country in ('France', 'Spain') and price <= 100
order by Country, state, city, price;

clear column

rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.

select * from rcv_customer 
where customer_province = 'CA' and customer_phone like '310%' 
order by last_name, first_name;

clear column

spool off

