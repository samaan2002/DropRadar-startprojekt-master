package dk.zealand.dropradar.cli;
import dk.zealand.dropradar.model.Drop;
import dk.zealand.dropradar.service.DropService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class DropCli {
    private final Scanner input;
    private final DropService service;

    public DropCli(Scanner input, DropService service) {
        this.input = input;
        this.service = service;
    }

    public void run() {
        while (true) {
            System.out.println("\n1. Vis kommende drops\n2. Tilføj\n3. Ret dato\n4. Slet\n0. Afslut");
            try {
                switch (ask("Vælg")) {
                    case "1" -> showUpcoming();
                    case "2" -> add();
                    case "3" -> changeDate();
                    case "4" -> delete();
                    case "0" -> { return; }
                    default -> System.out.println("Vælg fra 0 til 4.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Skriv en gyldig dato som ÅÅÅÅ-MM-DD.");
            } catch (NumberFormatException e) {
                System.out.println("Skriv id som et helt tal.");
            } catch (IllegalArgumentException | UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println("Databasefejl: " + e.getMessage());
            } catch (java.util.NoSuchElementException e) {
                return;
            }
        }
    }

    private void showUpcoming() throws SQLException {
        List<Drop> dropList = service.findUpcoming();
        if (dropList.isEmpty()) {
            System.out.println("Ingen kommende drops.");
        }
        for (Drop drop : dropList) {
            System.out.printf("%d | %s | %s | %s%n", drop.id(), drop.name(), drop.category(), drop.date());
        }
    }

    private void add() throws SQLException {
        String name = ask("Navn");
        String category = ask("Kategori");
        LocalDate date = LocalDate.parse(ask("Dato (ÅÅÅÅ-MM-DD)"));
        service.add(name, category, date);
        System.out.println("Droppet er gemt.");
    }

    private void changeDate() throws SQLException {
        int id = Integer.parseInt(ask("Id"));
        LocalDate date = LocalDate.parse(ask("Ny dato (ÅÅÅÅ-MM-DD)"));
        boolean found = service.changeDate(id, date);
        System.out.println(found ? "Datoen er gemt." : "Id blev ikke fundet.");
    }

    private void delete() throws SQLException {
        int id = Integer.parseInt(ask("Id"));
        boolean found = service.delete(id);
        System.out.println(found ? "Droppet er slettet." : "Id blev ikke fundet.");
    }

    private String ask(String prompt) {
        System.out.print(prompt + ": ");
        return input.nextLine().strip();
    }
}

