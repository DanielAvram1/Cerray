# DESCRIERE
Cerray este o aplicatie la nivel de consola care va permite proprietarilor de localuri sa isi inregistreze localul si sa isi plaseze meniul, clientilor sa faca comanda, iar curierilor sa o livreze

# ETAPA I
## Definirea sistemului 
Lista pe baza temei cu cel putin 10 actiuni/interogari si cel putin 8 tipuri de obiecte:

1. Main
2. Account( email, phoneNumber, password)
    1. Person(firstName, lastName, jobName, salary)
        1. Worker(jobName, salary)
            1. Courier(meanOfTransport)
        2. Customer()
    2. Establishment(name, address, type, description, income = 0)
3. MenuItem(name, price)
4. Order(customer, date, menuItems[], establishment)
5. Delivery(order, customer, establishment, courier)
6. CustomerService
7. CourierService
8. EstablishmentService
9. MenuItemService
10. OrderService
11. DeliveryService

## Implementare

Sunt clase abstracte si concrete, campuri private, pretected si default. La fel si metode.
Liste obisnuite sunt cel putin in baza de date DB, dar si in clasa Customer si Courier.
Se utilizeaza SortedMap<MenuItem, Integer> in Establishment si Order.
Se utilizeaza mostenirea entitatilor Account -> {Customer, Courier, Establishent} la polimorfismul in timpul logarii si inregistrarii unui nou Account.
Exista clase Service pentru aproape orice clasa "simpla" din aplicatie. MenuItem inca nu are nevoie de una.
Pe post de clasa main vine clasa App din Package main.

# Utilizare

Rulati funcia main din clasa App. Se va porni o simpla aplicatie de consola. 
La orice pas din rularea aplicatiei, in consola se va afisa o lista de comenzi care pot fi introduse in consola.
Nu voi presupune ca aplicatia este foarte self-intuitive la momentul dat, dar in mare parte comenzile sunt self-explicative.

La intrare, te poti loga sau inregistra.
Dupa logare sau inregistrare cu o anumita entitate, apare posibilitatea rularii comenzilor respective pentru fiecare entitate in parte:
1. Customer
    1. order - incepi crearea unei comenzi: alegi localul de unde doresti sa faci comanda si produsele respective dorite.
    2. display_orders - afisezi toate comenzile facute de tine
2. Courier
    1. display_roders - se afiseaza toate comenzile
    2. make_delivery - alegi livrare pe care ai dori sa o realizezi
    3. display_my_deliveries - afisezi toate livrarile alese de tine
    4. get_add_info - obtii informatii amanuntite legate de o livrare de a ta
    5. confirm_delivery - confirmi realizarea unei livrari
    
3. Establishment
    1. display_menu - afisezi meniul localului
    2. add_item - adaugi un produs nou in meniul localului
    3. edit_menu - editezi un produs din meniul localului
    4. income - afisezi venitul total din comenzile facute din localul tau
    

# TO DO
- [x] De adaugat parametri in plus pentru clasa Customer
- [x] De adaugat functionalitatea la clasele Service
- [x] De adaugat autentificarea pentru Worker si Establishment
- [ ] De adaugat subclase pentru Worker, ca Administrator, de ex
- [x] De adaugat restul functionalitatii la EstablishmentService
- [x] De afisat quantity nu in forma hexazecimala
- [ ] De adaugat posibilitatea pentru entitati de a-si vizualiza informatiile personale si sa le modifice
- [ ] De adaugat posibilitatea pentru Customer de a-si modifica Order-ul sau sa il anuleze daca inca nu a fost preluat de nici un Courier
- [ ] De adaugat functionalitate legata de salariul Courier-ului, acest lucru implica crearea clasei Administrator
- [ ] De creat un nou tip de clase, diferit de Service (sau poate una care va inlocui), care sa contina functionalitatea ce tine de clasa sa respectiva atunci cand este accesate de o entitate ne-logata ca acea entitate, care o acceseaza din "exterior" 

# NELAMURIRI
wow so empty 