package carsharing;

import carsharing.services.CompanyService;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Optional<String> fileNameOpt = Optional.ofNullable(args[1]);
        String fileName = fileNameOpt.isEmpty() ? "carsharing" : fileNameOpt.get();
        new CompanyService(fileName);
    }
}