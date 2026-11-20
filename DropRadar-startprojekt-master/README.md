# DropRadar

Hvornår åbner billetsalget, udkommer et album eller starter næste sæson? Byg en konsolapp, der holder styr på de udgivelser, I følger.

## Kom i gang

## Øvelse 1. Læs og forklar

| Klasse | Ansvar |
|---|---|
| Main | Opretter repository, service og CLI og starter menuen. |
| DropCli | Viser menuen, læser tekst og omsætter datoer til LocalDate. |
| DropService | Kontrollerer programmets regler, fx at en dato er i dag eller senere. |
| Drop | Samler id, navn, kategori og dato. En record giver adgangsmetoder som name() og date(). |
| DatabaseConnection | Åbner en MySQL-forbindelse med DriverManager. |
| DropRepository | Udfører SQL via JDBC og omsætter rækker til Drop-objekter. |

Vælg 1, og find Midnatssignaler. Følg kaldet **CLI → service → repository → MySQL**. Hvilken værdi erstatter spørgsmålstegnet i SQL'en? Tilføj midlertidigt `System.out.println(dropList);` lige før `return dropList;` i repository. Find id, navn, kategori og dato i udskriften. Hvor kommer id'et fra?


### Undersøg datoen og SQL-kaldet

1. Gem midlertidigt resultatet af `LocalDate.now()` i `LocalDate today` i `findUpcoming`. Udskriv `today` med `System.out.println(today);`, og send den videre til `findFrom(today)`. Indeholder værdien et klokkeslæt?
2. Find ud af, hvad `date.isBefore(today)` ville returnere for i går, i dag og i morgen. Hvilke datoer kommer med, når SQL bruger `>= ?`?
3. En `PreparedStatement` er en SQL-sætning med pladser markeret med `?`. Med fx `statement.setObject(1, date)` giver I JDBC værdien til den første plads. JDBC sender værdien som data, så fx en apostrof i et albumnavn behandles som en del af navnet. Ved UPDATE er plads 1 datoen, og plads 2 er id'et. Find begge bindinger, når I har lavet øvelse 3.
4. `executeQuery()` giver rækker, som I læser med `rows.next()`. `executeUpdate()` giver et antal rækker. Prøv at forklare, hvorfor metoderne bruges til hver sin type opgave.

Fjern jeres midlertidige udskrifter, når I har besvaret spørgsmålene. Navngiv lister med `List` til sidst, fx `dropList` og `categoryList`.
## Øvelse 2. Opret et drop

1. Åbn `DropService.add`. Afvis tomt navn og kategori. Grænserne er 120 og 60 tegn. Kald `validateDate(date)`.
2. Opret `Drop drop = new Drop(name, category, date);` og kald `repository.insert(drop)`. Skriv `System.out.println(drop);` lige før kaldet. Forudsig id'et, kør programmet, og sammenlign med id på listen efter INSERT.
3. Udfyld `DropRepository.insert` med `INSERT INTO drops (name, category, release_date) VALUES (?, ?, ?)`. Kig på forbindelsen i `findFrom`. Bind navn og kategori med `setString` og datoen med `setObject`. Parameterpladserne starter ved 1. Kald `executeUpdate()`.
4. Prøv en ugyldig datotekst, gårsdagens dato, et tomt navn og et gyldigt drop. Genstart, og vis listen igen. Hvorfor kan det gyldige drop stadig ses?

## Øvelse 3. Flyt en udgivelse

Brug `validateId` og `validateDate` i `DropService.changeDate`. Returnér det, der kommer fra `repository.updateDate`. Udfyld SQL'en `UPDATE drops SET release_date = ? WHERE id = ?`. Bind datoen som parameter 1 og id'et som parameter 2. Returnér `executeUpdate() > 0`. Flyt en udgivelse en uge, og kontrollér listen. Hvilke rækker ville blive ramt uden WHERE?

## Øvelse 4. Slet en aflyst udgivelse

Validér id i `DropService.delete`, og returnér resultatet fra `repository.deleteById`. Brug `DELETE FROM drops WHERE id = ?` og bind id med `setInt`. Slet det samme id to gange. Forudsig værdien fra `executeUpdate()` ved begge forsøg, før I kører dem.

## Vis jeres resultat

Alle fire operationer er obligatoriske. Vis under skærmdeling, at I kan oprette, læse, flytte og slette et drop. Bekræft resultatet i Workbench med `SELECT * FROM dropradar.drops ORDER BY id;`. Vis også en dato før i dag og et positivt id uden match. Skriv tre linjer om ansvaret i CLI, service og repository, og forklar hvornår objektet oprettes, og hvornår id'et tildeles.

## Udvidelser

| Opgave | Hint | Afprøvning |
|---|---|---|
| Filtrér på kategori | Tilføj AND category = ? til WHERE. | Gem to kategorier og vis kun den ene. |
| Vis de næste syv dage | Brug dato >= i dag og dato < i dag plus syv. | Dag 6 vises. Dag 7 vises ikke. |
| Redigér hele droppet | Genbrug reglerne fra add og opdatér tre kolonner. | Skift navn, kategori og dato og kontrollér samme id. |
| Markér et drop som aflyst | Tilføj status i tabel og model. Filtrér aflyste fra visningen og tilføj en liste til dem. | Aflys et drop, genstart, og find det på listen over aflyste. |


