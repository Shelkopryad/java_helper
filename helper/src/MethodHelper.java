import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class MethodHelper {

    private static final String green = "\u001B[32m";
    private static final String red = "\u001B[31m";

    static int binarySearch(List<Integer> list, int number) {
        int min = 0, count = 0;
        int max = list.size() - 1;
        int index;

        if (!list.contains(number)) {
            System.out.println(formatString("Искомое число в списке отсутствует", false));
            return -1;
        }

        while (true) {
            count++;
            int mid = (min + max)/2;
            if (list.get(mid) == number) {
                index = list.indexOf(number);
                System.out.println(formatString("Индекс искомого элемента [" + list.indexOf(number) + "]", true));
                System.out.println(formatString("Count = " + count, true));
                return index;
            }
            if (number < list.get(mid)) {
                max= mid;
            } else {
                min = mid;
            }
        }
    }

    static Object times(Object a, Object b) {
        if (b instanceof Integer) {
            if (a instanceof String) {
                StringBuilder result = new StringBuilder("");
                for (int i = 0; i < (Integer) b; i++) {
                    result.append(a);
                }
                return new String(result);
            }
            if (a instanceof Integer) {
                return ((Integer) a ) * ((Integer) b);
            }
            return formatString("Unsupported object " + a, false);
        } else {
            return formatString("Can't multiply \"" + a + "\" by non-int \"" + b + "\"", false);
        }
    }

    static void swap(int a, int b) {
        System.out.println("a = " + a + ", b = " + b);
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a = " + a + ", b = " + b);
    }

    static void printHeap() {
        long heapSize = Runtime.getRuntime().freeMemory();
        System.out.println(heapSize);
    }

    static String cardGenerator() {
        String base = "415428";
        for (int i = 0; i < 10; i++) {
            int pice = new Random().nextInt(9);
            base += pice;
        }

        int sum = 0;
        for (int i = 0; i < 16; i++) {
            int tmp = Integer.parseInt(String.valueOf(base.charAt(i)));
            if (i % 2 != 0) {
                sum += tmp;
            } else {
                if (tmp * 2 > 9) {
                    sum += tmp * 2 - 9;
                } else {
                    sum += tmp * 2;
                }
            }
        }

        if (sum % 10 == 0) {
            return base;
        } else {
            int[] intArr = new int[16];
            for (int i = 0; i < 16; i++) {
                intArr[i] = Integer.parseInt(String.valueOf(base.charAt(i)));
            }

            if (sum % 10 < 5) {
                if (intArr[15] < sum % 10) {
                    while (sum % 10 != 0) {
                        sum += 1;
                        intArr[15] += 1;
                    }
                } else {
                    while (sum % 10 != 0) {
                        sum -= 1;
                        intArr[15] -= 1;
                    }
                }
            }  else {
                while (sum % 10 != 0) {
                    sum += 1;
                    intArr[15] += 1;
                }
                intArr[15] = intArr[15] % 10;
            }

            base = "";
            for (int i : intArr) {
                base += String.valueOf(i);
            }
            return base;
        }
    }

    static void tryStream() {
        Collection<User> users = Arrays.asList(
                new User("Yamada", 12),
                new User("Tanaka", 54),
                new User(null, 21),
                new User("Kobayashi", 46),
                new User("Yamada", 10),
                new User("Makoto", null),
                new User("Sidzuko", 89),
                new User("Kayama", 48),
                new User("Totoro", 76),
                new User(null, null)
        );

        String name = users.stream()
                .filter((p) -> p.getName() != null)
                .max((p1, p2) -> p1.getAge().compareTo(p2.getAge()))
                .get()
                .getName();
        System.out.println("Name = " + name);

        long countName = users
                .parallelStream()
                .filter((p) -> p.getName() != null && p.getName().equals("Yamada"))
                .count();
        System.out.println("Count Yamada = " + countName);

        List<User> onlyK = users
                .stream()
                .filter((p) -> p.getName() != null && p.getName().startsWith("K"))
                .collect(Collectors.toList());
        onlyK.forEach((p) -> System.out.println(p.getName()));

        Integer ageSum = users
                .stream()
                .filter((p) -> p.getName() != null)
                .reduce(0, (sum, p) -> sum += p.getAge(), (sum1, sum2) -> sum1 + sum2);
        System.out.println("Ages = " + ageSum);

        users.stream().filter((p) -> p.getAge() > 18 && p.getAge() < 50).forEach((p) -> System.out.println(p.getName()));
    }

    static int sumAll(List<Integer> list, Predicate<Integer> p) {
        int total = 0;
        for (int num : list) {
            if (p.test(num)) {
                total += num;
            }
        }
        return total;
    }

    static void tryPredicate() {
        System.out.println(sumAll(getIntegerList(5, 56), n -> true));
        System.out.println(sumAll(getIntegerList(15, 87), n -> n % 2 == 0));
    }

    static List<File> getFile(String path, String filename) {
        File dir = new File(path);

        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(filename);
            }
        });
        return Arrays.asList(files);
    }

    static String deleteHtmlTags(String line) {
        return line.replaceAll("<[^>]*", "");
    }

    static List<Integer> getIntegerList(int startPoint, int endPoint) {
        List<Integer> list = new ArrayList<>();
        for (int i = startPoint; i < endPoint; i++) {
            list.add(i);
        }
        return list;
    }

    static List<String> getStringList(int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(getRandomString(5));
        }
        return list;
    }

    static String getRandomString(int length) {
        String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(data.length() - 1);
            stringBuilder.append(data.substring(index, index + 1));
        }
        return stringBuilder.toString();
    }

    static void printDate(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        System.out.println(sdf.format(date));
    }

    static long fib(int n) {
        return n < 1 ? 0 : n == 1 ? 1 : fib(n - 1) + fib(n - 2);
    }

    static long fatorial(int n) {
        return n == 0 ? 1 : n * fatorial(n - 1);
    }

    static String formatString(String source, boolean  condition) {
        String greenLine = String.format("%s%s%s", green, source, green);
        String redLine = String.format("%s%s%s", red, source, red);
        return condition ? greenLine : redLine;
    }
}
