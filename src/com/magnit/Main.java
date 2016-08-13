package com.magnit;

public class Main {
    // 4 аргумента - url, пользователь, пароль, количество записей
    private static final int ARGS_COUNT = 4;
    private static final int DEFAULT_N = 1000;

    public static void main(String[] args) {
        if (args.length < ARGS_COUNT) {
            System.out.println("Не достаточно параметров. Запуск с параметрами по умолчанию");
            new JdbcMain().execute();
        } else {
            int n;
            try {
                n = Integer.parseInt(args[3]);
                if (n < 1) {
                    System.out.println("N должно быть положительным. Значение по умолчанию 1000");
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                n = DEFAULT_N;
                System.out.println("N не является числом. Значение по умолчанию 1000");
            }
            new JdbcMain(args[0], args[1], args[2], n).execute();
        }
    }

}