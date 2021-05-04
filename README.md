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

# ETAPA II
Adaugarea fisierelor CSV

Au fost adaugate fisiere CSV si functiile care sa le poata citi, crea instante ale claselor din informatiile din CSV si insera sau sterge informatii din acele CSV. 

## Singletonul DBCSVService

Clasa DBCSVService reprezinta clasa care incorporeaza functiile ajutatoare pentru manipularea CSVurilor, precum si instantiaza fisierele care lipsesc si introduc obiectele pastrate in CSV in program. Din moment ce unele clase depind de altele, am fost nevoit sa modelez clasele ca tabele intr-o baza de date relationale, ceea ce a venit cu unele schimbari pentru clasele principale ale programului.

## Schimbari in clasele principale
Clasele Customer, Courier, Establishment, Delivery, MenuItem, Order au fost modificate in modul urmator:
1. A fost adaugat campul id pentru a diferentia clasele intre ele
2. A fost scrisa functia statica readFromCSV() care primeste ca parametru un string care este parsat intr-un obiect de tipul clasei corespunzatoare (acum am realizat ca se putea doar de facut un constructor separat ce primea ca parametru acel csv)
3. Desigur, a fost adaugata functia toCSV() care transforma informatia unei clase intr-un string de format csv care poate fi inclus intr-un fisier csv

Din moment ce unele clase depindeau de altele, incarcarea obiectelor din csv trebuia facuta pe anumite stadii. 
1. Mai intai, DBCSVService incarca MenuItemList.
2. Apoi incarca EstablishmentList care contin obiecte de tip MenuItem.
3. Apoi se incarca OrderList care contin obiecte de tip MenuItem si Establishment.
4. Se incarca DeliveryList. clasa Delivery contine obiecte de tipul Order si Establishment.
5. Se incarca celelalte clase care depind de clasele de mai sus.

Aceasta abominatie m-a impus sa schimb un pic singletonul DBCSVService astfel incat clasele sa se incarce in ordinea urmatoare in mai multe stadii, pentru ca crearea clasei la o stadie necesita informatiile din stadiile precedente. Deci, practic singletonul pastreaza 5 instante care contin o cantitate diferita de informatii si care se construiau din instantele precedente: firstSingleInstance -> secondSingleInstance -> thirdSingleInstance -> fourthSingleInstance -> singleInsance.

Se putea de creat clasa intr-un mod mai comprehensiv? DA. Am acum timp pentru asta? Nope, profii au decis sa ne bacseasca cu teme acum, in ajun de sesiune...

## Logger

A fost adaugat logger.csv care contine informatii despre unele actiuni facute de utilizator si timestamp-ul cand fiecare actiune a avut loc.

## TO DO
- [ ] De cautat posibile bugguri.
- [ ] De facut un refactoring turbat, in special de schimbat denumirile unor functii in unele mai comprehensive si de introdus clase Template. Multe functii sunt identice, se deosebeste doar clasele la care se face casting.
- [ ] Poate de adaugat o Super-Super clasa care va fi stramosul tuturor claselor pastrate in csv pentru a incorpora campul id si pentru aface override la functii repetitive.

## NELAMURIRI
If bed bugs live in beds... where do cockroaches live? 0_0
(Sper ca la acest repositoriu se uita doar laborantul)