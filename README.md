# DESCRIERE
Cerray este o aplicatie la nivel de consola care va permite proprietarilor de localuri sa isi inregistreze localul si sa isi plaseze meniul, clientilor sa faca comanda, iar curierilor sa o livreze

# ETAPA I
## Punctul a: Lista pe baza temei cu cel putin 10 actiuni/interogari si cel putin 8 tipuri de obiecte

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

# TO DO
- [x] De adaugat parametri in plus pentru clasa Customer
- [ ] De adaugat functionalitatea la clasele Service
- [ ] De adaugat autentificarea pentru Worker si Establishment
- [ ] De adaugat subclase pentru Worker, ca Administrator, de ex
- [x] De adaugat restul functionalitatii la EstablishmentService
- [ ] De afisat quantity nu in forma hexazecimala
# NELAMURIRI
- [ ] Ce ar trebui sa faca o clasa Service
- [ ] Cum de accesat campurile private a claselor componente (menuItemList in Order in OrderService, cum sa adaugi un nou menuItem prin OrderService )
- [ ] Cum de facut functiile findCustomerInList si isEmailLoggedIn ca functii statica a clasei Account, convertand List<Customer> la List<Account>