// MailService.java
package com.example.task05;

import java.util.*;
import java.util.function.Consumer;

public class MailService<T> implements Consumer<MailContent<T>> {
    private final Map<String, List<T>> mailBox = new HashMap<>();

    @Override
    public void accept(MailContent<T> mailContent) {
        String to = mailContent.getTo();
        T content = mailContent.getContent();

        mailBox.computeIfAbsent(to, k -> new ArrayList<>()).add(content);
    }

    public Map<String, List<T>> getMailBox() {
        // Возвращаем "безопасную" версию Map, которая для отсутствующих ключей возвращает пустой список
        return new AbstractMap<String, List<T>>() {
            @Override
            public Set<Entry<String, List<T>>> entrySet() {
                return mailBox.entrySet();
            }

            @Override
            public List<T> get(Object key) {
                List<T> result = mailBox.get(key);
                return result != null ? result : Collections.emptyList();
            }
        };
    }
}