DROP TABLE IF EXISTS Tranzactii;
DROP TABLE IF EXISTS Carduri;
DROP TABLE IF EXISTS Conturi;
DROP TABLE IF EXISTS Clienti;

CREATE TABLE Clienti (
    id VARCHAR(50) PRIMARY KEY,
    nume VARCHAR(100) NOT NULL,
    strada VARCHAR(100),
    oras VARCHAR(100),
    codPostal VARCHAR(20),
    telefon VARCHAR(20),
    email VARCHAR(50)
);

CREATE TABLE Conturi (
    numarCont VARCHAR(50) PRIMARY KEY,
    sold DOUBLE NOT NULL,
    clientId VARCHAR(50) NOT NULL,
    dataDeschidere DATE NOT NULL,
    tipCont VARCHAR(20) NOT NULL, -- 'Curent' sau 'Economii'
    limitaDescoperire DOUBLE, -- specific pentru ContCurent
    dobandaAnuala DOUBLE, -- specific pentru ContEconomii
    soldMinim DOUBLE, -- specific pentru ContEconomii
    FOREIGN KEY (clientId) REFERENCES Clienti(id) ON DELETE CASCADE
);

CREATE TABLE Carduri (
    numarCard VARCHAR(50) PRIMARY KEY,
    tip VARCHAR(20) NOT NULL,
    dataExpirare DATE NOT NULL,
    cvv VARCHAR(5) NOT NULL,
    numarContAsociat VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (numarContAsociat) REFERENCES Conturi(numarCont) ON DELETE CASCADE
);

CREATE TABLE Tranzactii (
    id VARCHAR(50) PRIMARY KEY,
    dinCont VARCHAR(50),
    inCont VARCHAR(50),
    suma DOUBLE NOT NULL,
    data DATETIME NOT NULL,
    tip VARCHAR(20) NOT NULL,
    FOREIGN KEY (dinCont) REFERENCES Conturi(numarCont) ON DELETE SET NULL,
    FOREIGN KEY (inCont) REFERENCES Conturi(numarCont) ON DELETE SET NULL
);
