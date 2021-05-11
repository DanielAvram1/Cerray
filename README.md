# DESCRIERE
Cerray este o aplicatie la nivel de consola care va permite proprietarilor de localuri sa isi inregistreze localul si sa isi plaseze meniul, clientilor sa faca comanda, iar curierilor sa o livreze

# ETAPA III
## Servicii care sa asigure persistenta folosind JDBC

S-a creat pe localhost un mysql-server care contine o baza de date Cerray si un User Cerray.
Ca si in cazul Etapei II cu CSV, se implementeaza urmatoarele tabele:
MENU_ITEMS, ORDERS, DELIVERIES, ESTABLISHMENTS, COURIERS, CUSTOMERS, ESTABLISHMENT_ASOC_MENU_ITEM, ORDER_ASOC_MENU_ITEM.
Ultimele 2 tabele sunt asociative si reprezinta legaturile dintre Order si Establishment cu MenuItem.
La initializarea singletonului DBService, toate tabele se creaza in baza de date DACA ACESTEA NU EXISTA DEJA. 
De asemenea, au fost modificate clasele pentru a nu contine campuri dinamice, ci doar id-ul acestora din baza de date. Pentru obtinerea entitatii dinamice respective unui id, se folosesc GETTER-i. 

## Servicii care expun operatii CRUD

1. CREATE
    1. La crearea tabelelor.
    2. La crearea unui obiect MenuItem, Order, Delivery, Account(Establishment, Courier, Customer).
    3. La crearea unei legaturi asociative intre Order si Establishment cu MenuItem.
2. READ
    1. Exista o multime de apelari SELECT peste tot in cod.
3. UPDATE
    1. La editarea meniului unui Establishment.
    2. La incrementarea income a unui Establishment.
    3. La setarea unui Delivery ca delivered.
4. DELETE
    1. La stergerea unui MenuItem din meniul unui Establishment.
