package com.example.task03;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class Task03Main {

    public static void main(String[] args) {
        findMinMax(
                Stream.of(2, 9, 5, 4, 8, 1, 3),
                Integer::compareTo,
                (min, max) -> System.out.println("min: " + min + " / max: " + max)
        );
    }

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {

        // Проверка на null аргументы
        if (stream == null || order == null || minMaxConsumer == null) {
            throw new NullPointerException("All arguments must be non-null");
        }

        // Внутренний класс для хранения пары min/max
        class MinMaxHolder {
            T min;
            T max;
            boolean hasElements = false;

            // Метод для обновления min/max при обработке нового элемента
            void update(T element) {
                if (!hasElements) {
                    // Первый элемент - инициализируем и min и max
                    min = element;
                    max = element;
                    hasElements = true;
                } else {
                    // Сравниваем с текущим min и max
                    if (order.compare(element, min) < 0) {
                        min = element;
                    }
                    if (order.compare(element, max) > 0) {
                        max = element;
                    }
                }
            }

            // Метод для объединения двух холдеров (полезно для параллельных потоков)
            MinMaxHolder combine(MinMaxHolder other) {
                if (!other.hasElements) {
                    return this;
                }
                if (!this.hasElements) {
                    return other;
                }

                MinMaxHolder result = new MinMaxHolder();
                result.hasElements = true;
                result.min = order.compare(this.min, other.min) <= 0 ? this.min : other.min;
                result.max = order.compare(this.max, other.max) >= 0 ? this.max : other.max;
                return result;
            }
        }

        // Обрабатываем поток и собираем результаты
        MinMaxHolder result = stream.reduce(
                new MinMaxHolder(), // Начальное значение
                (holder, element) -> {
                    holder.update(element);
                    return holder;
                },
                MinMaxHolder::combine // Комбинатор для параллельных потоков
        );

        // Вызываем consumer с результатами
        if (result.hasElements) {
            minMaxConsumer.accept(result.min, result.max);
        } else {
            minMaxConsumer.accept(null, null);
        }
    }
}