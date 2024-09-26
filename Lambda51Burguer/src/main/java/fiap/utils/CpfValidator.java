package fiap.utils;

public class CpfValidator {

    public static boolean isValid(String cpf) {
        String cleanCpf = cpf.replaceAll("[^0-9]", "");

        if (cleanCpf.length() != 11) return false;

        if (cleanCpf.chars().distinct().count() == 1) return false;

        int sum = 0;
        int weight = 10;

        for (int i = 0; i < 9; i++) {
            sum += (cleanCpf.charAt(i) - '0') * weight--;
        }
        int firstDigit = calculateDigit(sum);
        if (firstDigit != (cleanCpf.charAt(9) - '0')) return false;

        sum = 0;
        weight = 11;
        for (int i = 0; i < 10; i++) {
            sum += (cleanCpf.charAt(i) - '0') * weight--;
        }
        int secondDigit = calculateDigit(sum);
        return secondDigit == (cleanCpf.charAt(10) - '0');
    }

    private static int calculateDigit(int sum) {
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }
}
