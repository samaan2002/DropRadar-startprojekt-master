package dk.zealand.dropradar.service;

import dk.zealand.dropradar.model.Drop;
import dk.zealand.dropradar.repository.DropRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DropService {
    private final DropRepository repository;

    public DropService(DropRepository repository) {
        this.repository = repository;
    }

    public List<Drop> findUpcoming() throws SQLException {
        return repository.findFrom(LocalDate.now());
    }

    public void add(String name, String category, LocalDate date) throws SQLException {
        if (name == null || name.isBlank() || category == null || category.isBlank()) {
            throw new IllegalArgumentException("Navn eller kategori må ikke være tømt.");
        }
        if (name.length() > 120 && category.length() > 60) {
            throw new IllegalArgumentException("Navn må højst være 120 tegn og kategorien må højst være 60 tegn.");
        }
        validateDate(date);

        Drop drop = new Drop(name, category, date);

        repository.insert(drop);
        System.out.println(drop);
    }


    public boolean changeDate(int id, LocalDate date) throws SQLException {

        validateId(id);
        validateDate(date);

        return repository.updateDate(id, date);
    }

    public boolean delete(int id) throws SQLException {

        validateId(id);

        return repository.deleteById(id);
    }

    private void validateDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Datoen skal være i dag eller senere.");
        }
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id skal være positivt.");
        }
    }

}
