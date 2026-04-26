# Proiect Java - Aplicatie bancara

**Tema**: Aplicatie bancara
**Student**: Peiculeasa Sergiu-Stefan
**Grupa**: 234

---

## 1. Actiuni / interogari posibile in sistem

1. **Creeaza un client nou** — inregistreaza un client cu date personale si adresa
2. **Deschide un cont** — creeaza un cont curent sau de economii pentru un client
3. **Efectueaza o depunere** — adauga fonduri intr-un cont existent
4. **Efectueaza o retragere** — extrage fonduri dintr-un cont
5. **Efectueaza un transfer** — muta fonduri intre doua conturi din sistem
6. **Vizualizeaza soldul unui cont** — returneaza soldul curent al unui cont
7. **Emite un card** — asociaza un card debit/credit la un cont
8. **Blocheaza / activeaza un card** — schimba statusul unui card
9. **Genereaza un extras de cont** — listeaza tranzactiile dintr-o perioada cu sold initial si final
10. **Listeaza conturile unui client** — returneaza toate conturile deschise pe un client
11. **Cauta client dupa ID sau nume** — interogare in serviciul de clienti
12. **Sterge / inchide un cont** — elimina un cont din sistem

---

## 2. Obiecte

| Clasa | Descriere |
|-------|-----------|
| `Client` | Persoana care detine conturi in banca |
| `Adresa` | Adresa fizica a unui client |
| `Cont` | Clasa de baza pentru orice tip de cont |
| `ContCurent` | Cont cu limita de descoperire |
| `ContEconomii` | Cont cu dobanda anuala si sold minim |
| `Card` | Card debit sau credit asociat unui cont |
| `Tranzactie` | Inregistrarea unei operatiuni financiare |
| `ExtrasDeCont` | Raport de tranzactii pe o perioada |
| `IBANCod` | Obiect valoare reprezentand un IBAN validat |
