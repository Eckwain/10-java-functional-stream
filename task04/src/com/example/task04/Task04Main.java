package com.example.task04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Task04Main {

    public static void main(String[] args) {
        // Читаем из System.in, обязательно указывая кодировку UTF-8
        new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))
                .lines() // Превращаем текст в поток строк
                // Разбиваем каждую строку на слова по разделителю (все, что не буквы и не цифры)
                .flatMap(line -> Arrays.stream(line.split("[^\\p{L}\\p{N}]+")))
                // Убираем пустые строки, которые могут возникнуть из-за split (например, если строка начинается с пробела)
                .filter(word -> !word.isEmpty())
                // Переводим в нижний регистр
                .map(String::toLowerCase)
                // Собираем в Map<String, Long>, где ключ - слово, значение - количество
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream() // Превращаем карту обратно в стрим пар (Слово=Количество)
                // Сортируем: сначала по количеству (убывание), затем по слову (алфавитный порядок)
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(10) // Берем только топ-10
                .forEach(entry -> System.out.print(entry.getKey() + '\n')); // Выводим слова
    }

}