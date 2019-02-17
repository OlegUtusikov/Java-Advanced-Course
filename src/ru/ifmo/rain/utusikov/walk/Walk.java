package ru.ifmo.rain.utusikov.walk;

public class Walk {
    public static void main(String[] args) {
        if (args == null) {
            System.err.println("Args cann't be a null!");
        } else  if (args.length < 2) {
            System.err.println("Doesn't enough arguments in command line!");
        } else if (args.length > 2) {
            System.err.println("A lot of arguments in command line!");
        } else  {
            if (args[0] == null || args[1] == null) {
                System.err.println("Input and output files cann't be a null!");
            } else {
                Walker walker = new Walker(args[0], args[1]);
                walker.execute();
            }
        }
    }
}
