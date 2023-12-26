import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ATM {
    private static double accountBalance = 0.0; // Баланс счета

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Вход в систему
        if (login(scanner)) {
            // Основной цикл банкомата
            boolean exit = false;
            while (!exit) {
                System.out.println("Выберите действие:");
                System.out.println("1. Просмотреть счет");
                System.out.println("2. Вложить деньги");
                System.out.println("3. Снять деньги");
                System.out.println("4. Выйти");
                System.out.println("5. Вложить из .txt");
                System.out.print("Введите номер действия: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        viewAccountBalance();
                        break;
                    case 2:
                        depositMoney(scanner);
                        break;
                    case 3:
                        withdrawMoney(scanner);
                        break;
                    case 4:
                        exit = true;
                        break;
                    case 5:
                        depositMoneyFromFile();
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            }
        } else {
            System.out.println("Неверный пароль. Завершение программы.");
        }

        scanner.close();
    }


    // Метод для входа в систему
    private static boolean login(Scanner scanner) {
        int correctPassword = 1234; // Пароль по умолчанию
        for (int attempts = 0; attempts < 3; attempts++) {
            System.out.print("Введите пароль: ");
            String inputLine = scanner.nextLine().trim(); // Считываем всю строку ввода и убираем пробелы

            // Разбираем первое целое число в вводе
            Scanner lineScanner = new Scanner(inputLine);
            if (!lineScanner.hasNextInt()) {
                System.out.println("Неверный формат пароля. Значение должно быть числом.");
                continue; // Продолжаем цикл, если ввод не является числовым
            }

            int password = lineScanner.nextInt();
            // Проверяем, что после числа ничего нет
            if (lineScanner.hasNext()) {
                System.out.println("Лишние символы после пароля. Попробуйте снова.");
                continue;
            }
            lineScanner.close();

            if (password == correctPassword) {
                return true;
            } else {
                System.out.println("Неверный пароль. У вас осталось " + (2 - attempts) + " попытки(-ок).");
            }
        }
        return false;
    }


    // Метод для просмотра баланса счета
    private static void viewAccountBalance() {
        System.out.println("Баланс счета: " + accountBalance);
    }

    // Метод для вложения денег на счет
    private static void depositMoney(Scanner scanner) {
        while (true) {
            System.out.print("Введите сумму для вложения (или 'отмена' для выхода): ");
            String input = scanner.next();
            if ("отмена".equalsIgnoreCase(input)) {
                System.out.println("Внесение денег отменено.");
                break;
            }
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0) {
                    accountBalance += amount;
                    System.out.println("Деньги успешно вложены.");
                    break;
                } else {
                    System.out.println("Сумма должна быть больше 0. Попробуйте еще раз.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Пожалуйста введите числовое значение.");
            }
        }
    }



    // Метод для снятия денег со счета
    private static void withdrawMoney(Scanner scanner) {
        while (true) {
            System.out.print("Введите сумму для снятия (или 'отмена' для выхода): ");
            String input = scanner.next();
            if ("отмена".equalsIgnoreCase(input)) {
                System.out.println("Снятие денег отменено.");
                break;
            }
            try {
                double amount = Double.parseDouble(input);
                if (accountBalance >= amount && amount > 0) {
                    accountBalance -= amount;
                    System.out.println("Деньги успешно сняты.");
                    break;
                } else {
                    System.out.println("Недостаточно средств или некорректная сумма. Попробуйте еще раз.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Пожалуйста введите числовое значение.");
            }
        }
    }

    private static void depositMoneyFromFile() {
        try {
            File file = new File("C:\\Users\\Administrator\\untitled\\money.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                try {
                    double amount = Double.parseDouble(line);
                    if (amount > 0) {
                        accountBalance += amount;
                        System.out.println("Деньги успешно вложены из файла.");
                    } else {
                        System.out.println("Сумма должна быть больше 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный формат суммы в файле.");
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
        }
    }
}