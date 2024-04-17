package uz.supersite;

import java.util.NavigableSet;
import java.util.TreeSet;
public class NavigableExample {
    public static void main(String[] args) {
        NavigableSet<Integer> navigableSet = new TreeSet<>();

        navigableSet.add(5);
        navigableSet.add(2);
        navigableSet.add(8);
        navigableSet.add(1);
        navigableSet.add(10);

        System.out.println("Dastlabki ro'yxati: " + navigableSet);
        System.out.println("6 ga teng yoki kichkinasi: " +navigableSet.floor(6));
        System.out.println("3 ga teng yoki kattasi: " +navigableSet.ceiling(3));
        System.out.println("4 dan kichkinasi: " +navigableSet.lower(4));
        System.out.println("7 dan kichigi: " +navigableSet.lower(7));
        System.out.println("7 dan kattasi: " +navigableSet.higher(7));

        System.out.println("Birinchi elementini o'chirish: " +navigableSet.pollFirst());
        System.out.println("Oxirgi elementini o'chirish: " +navigableSet.pollLast());

        System.out.println("Keyingi ro'yxati: " + navigableSet);
    }
}
