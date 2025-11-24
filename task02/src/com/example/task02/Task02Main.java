package com.example.task02;

import java.util.stream.IntStream;

public class Task02Main {

    public static void main(String[] args) {
        /*
        cycleGrayCode(2)
                .limit(10)
                .forEach(System.out::println);
        */
    }

    public static IntStream cycleGrayCode(int n) {
        // Проверка корректности входного параметра
        if (n < 1 || n > 16) {
            throw new IllegalArgumentException("n must be between 1 and 16");
        }

        // Вычисляем количество кодов Грея для n бит
        int totalCodes = 1 << n; // 2^n

        // Создаем массив для хранения всех кодов Грея
        int[] grayCodes = new int[totalCodes];

        // Заполняем массив кодами Грея
        for (int i = 0; i < totalCodes; i++) {
            // Формула преобразования двоичного числа в код Грея
            grayCodes[i] = i ^ (i >> 1);
        }

        // Создаем бесконечный циклический поток
        return IntStream.iterate(0, i -> (i + 1) % totalCodes)
                .map(i -> grayCodes[i]);
    }
}