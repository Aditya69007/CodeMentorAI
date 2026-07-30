package com.codementor.backend.dto.leetcode.calculator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.TreeSet;

@Component
public class CalendarCalculator {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public int calculateCurrentStreak(String submissionCalendar) {

        try {

            Map<String, Integer> submissions =
                    objectMapper.readValue(
                            submissionCalendar,
                            new TypeReference<>() {}
                    );

            TreeSet<LocalDate> dates = new TreeSet<>();

            for (String timestamp : submissions.keySet()) {

                long epoch = Long.parseLong(timestamp);

                dates.add(
                        Instant.ofEpochSecond(epoch)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                );

            }

            LocalDate today = LocalDate.now(ZoneOffset.UTC);

            LocalDate cursor = today;

            int streak = 0;

            while (dates.contains(cursor)) {

                streak++;

                cursor = cursor.minusDays(1);

            }

            return streak;

        } catch (Exception e) {

            return 0;

        }

    }

    public int calculateMaxStreak(String submissionCalendar) {

        try {

            Map<String, Integer> submissions =
                    objectMapper.readValue(
                            submissionCalendar,
                            new TypeReference<>() {}
                    );

            TreeSet<LocalDate> dates = new TreeSet<>();

            for (String timestamp : submissions.keySet()) {

                long epoch = Long.parseLong(timestamp);

                dates.add(
                        Instant.ofEpochSecond(epoch)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                );

            }

            int current = 0;

            int max = 0;

            LocalDate previous = null;

            for (LocalDate date : dates) {

                if (previous == null) {

                    current = 1;

                } else if (previous.plusDays(1).equals(date)) {

                    current++;

                } else {

                    current = 1;

                }

                max = Math.max(max, current);

                previous = date;

            }

            return max;

        } catch (Exception e) {

            return 0;

        }

    }

}