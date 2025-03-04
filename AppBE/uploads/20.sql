rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

set linesize 100
set pagesize 66
set echo on

spool c:/cprg250s/LabUnit10output.txt

rem 1. Display all agents who specialize in US travel packages. Sort by last name.

'Write code here'

rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide. 

'Write code here'

rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.
column dest_description FORMAT A80
column country FORMAT A15

'Write code here'

spool off

