package dk.zealand.dropradar;
import dk.zealand.dropradar.repository.DropRepository;
import dk.zealand.dropradar.service.DropService;
import dk.zealand.dropradar.cli.DropCli;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        DropRepository repository = new DropRepository();
        DropService service = new DropService(repository);
        try (Scanner input = new Scanner(System.in)) {
            new DropCli(input, service).run();
        }
    }
}

