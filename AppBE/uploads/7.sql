rem Unit 10-11 Lab Simple Select and sorting data
rem Summer 2024
rem Dave Leskiw amended by Anwar Alabbas

SET LINESIZE 160
set echo on

START "A:\SAIT\cprg250s\SelectLab\Really Cheap Vacations\reallycheapvacationcreatetable.sql"
START "A:\SAIT\cprg250s\SelectLab\Really Cheap Vacations\reallycheapvacationspopulate.sql"
spool "A:\SAIT\cprg250s\SelectLab\SelectLab.txt"

rem 1. Display all agents who specialize in US travel packages. Sort by last name.

SELECT
    LAST_NAME AS "LAST_NAME", FIRST_NAME AS "FIRST_NAME",
    AGENT_LEVEL AS "AGEN"
FROM
    rcv_agent
WHERE
    AGENT_SPECIALITY = 'US'
ORDER BY
    LAST_NAME;
 rem 2. Find all tours in France and Spain that cost $100 or less. Sort by country, state, city and price. Line width should be 160, the destination desc column should be 80 characters, country 15 characters and city 10 characters wide.

COLUMN dest_description FORMAT A80
COLUMN country FORMAT A15
COLUMN city FORMAT A10


SELECT
    dest_description,
    country AS "COUNTRY",
    state,
    city AS "CITY",
    price AS "PRICE"
FROM
    RCV_DESTINATION
WHERE
    COUNTRY IN ('France', 'Spain')
    AND PRICE <= 100
ORDER BY
    COUNTRY,
    city,
    price,
    state;

 rem 3. Find all customers in California with a phone number that starts with a ‘310’ area code. Sort by last name and first name.
    SELECT
        first_name as "FIRST" ,
        last_name AS "LAST", 
        customer_phone AS "PHONE",
        customer_province AS "PROV"
    FROM
        rcv_customer
    WHERE
        customer_phone LIKE '310%'
    ORDER BY
        last_name;

SPOOL OFF