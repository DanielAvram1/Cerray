# ETAPA I
## Punctul a: Lista pe baza temei cu cel putin 10 actiuni/interogari si cel putin 8 tipuri de obiecte

l. Main
l. Person(firstName, lastName, email, phoneNumber)
    l. Worker(jobName, salary)
        l. Courier(meanOfTransport)
    l. Customer()
l. Establishment(name, address, type, description)
l. MenuItem(name, price)
l. Order(customer, date, menuItems[], establishment)
l. Delivery(order, courier)
l. CustomerService
l. CourierService
l. EstablishmentService
l. MenuItemService
l. OrderService
l. DeliveryService

# TO DO
- [x] De adaugat parametri in plus pentru clasa Customer