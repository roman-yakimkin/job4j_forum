package ru.job4j.forum.comparator;

import ru.job4j.forum.model.TimeableEntity;

import java.util.Comparator;

/**
 * Class - comparator to sort timeable entities by time desc
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 25.08.2020
 * @version 1.0
 */
public class SortByTimeDescComparator implements Comparator<TimeableEntity> {
    @Override
    public int compare(TimeableEntity o1, TimeableEntity o2) {
        long time1 = o1.getChanged().getTimeInMillis() / 1000;
        long time2 = o2.getChanged().getTimeInMillis() / 1000;
        return (int) (time2 - time1);
    }
}
