rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

set linesize 100
set pagesize 66
set echo on

spool c:/cprg250s/LabUnit10output.txt

rem 1. Display all agents who specialize in US travel packages. Sort by last name.

column last_name format a15 heading 'LAST_NAME'
column first_name format a15 heading 'FIRST_NAME'
column agent_level format a15 heading 'AGENT_CODE'
select distinct last_name , first_name , agent_level as AGENT_CODE from rcv_agent where agent_id in (1,4) order by LAST_NAME;


rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 

set linesize 160;
column dest_description format a80 heading 'Destination Desc';
column country format a15 heading 'COUNTRY';
column city format a10 heading 'CITY';
select dest_description, country, state, city, price from rcv_destination where country in ('France' , 'Spain') and price <=100 order by country , state , city , price;


rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.
column dest_description FORMAT A80
column country FORMAT A15

select first_name as "First", last_name as "Last", customer_phone as "Phone", customer_province as "Prov" from rcv_customer where customer_province = 'CA' and customer_phone like '310%' order by last_name , first_name;

spool off

