
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
/* https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html
Parallel computing involves dividing a problem into subproblems,
  solving those problems simultaneously (in parallel, with each subproblem running in a separate thread),
  and then combining the results of the solutions to the subproblems.

  One difficulty in implementing parallelism in applications that use collections is that collections are not thread-safe,
  which means that multiple threads cannot manipulate a collection without introducing thread interference or memory consistency errors.
  The Collections Framework provides synchronization wrappers, which add automatic synchronization to an arbitrary collection, making it thread-safe.
  However, synchronization introduces thread contention. You want to avoid thread contention because it prevents threads from running in parallel.
  Aggregate operations and parallel streams enable you to implement parallelism with non-thread-safe collections provided that you do not modify the collection while you are operating on it.

  Note that parallelism is not automatically faster than performing operations serially, although it can be if you have enough data and processor cores.
  While aggregate operations enable you to more easily implement parallelism, it is still your responsibility to determine if your application is suitable for parallelism.
  */
public class TestStreamParalle {

    public static void main(String[] args) {

        Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8 };
        List<Integer> listOfIntegers =
                new ArrayList<>(Arrays.asList(intArray));
        listOfIntegers
                .stream()
                .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("listOfIntegers sorted in reverse order:");
        Comparator<Integer> normal = Integer::compare;
        Comparator<Integer> reversed = normal.reversed();
        Collections.sort(listOfIntegers, reversed);
        listOfIntegers
                .stream()
                .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("listOfIntegers:");

        System.out.println("Parallel stream");
        listOfIntegers
                .parallelStream()
                .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("Another parallel stream:");
        listOfIntegers
                .parallelStream()
                .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("With forEachOrdered:");
        listOfIntegers
                .parallelStream()
                .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");

        Person person = new Person();
        person.setName("Henok");
        person.setGender(Person.Sex.MALE);
        person.setBirthday(LocalDate.ofYearDay(1983, 23));
        person.setEmailAddress("Heni_37@gmail.com");


        Person person2 = new Person();
        person2.setName("Fasile");
        person2.setGender(Person.Sex.MALE);
        person2.setBirthday(LocalDate.ofYearDay(1983, 23));
        person.setEmailAddress("Heni_37@gmail.com");

        Person person3 = new Person();
        person3.setName("Hanna");
        person3.setGender(Person.Sex.FEMALE);
        person3.setBirthday(LocalDate.ofYearDay(1982, 23));
        person3.setEmailAddress("Heni_37@gmail.com");

        Person person4 = new Person();
        person4.setName("Helina");
        person4.setGender(Person.Sex.FEMALE);
        person4.setBirthday(LocalDate.ofYearDay(1982, 23));
        person4.setEmailAddress("Heni_37@gmail.com");


        List<Person> roster = new ArrayList< >();
        roster.add(person);
        roster.add(person2);
        roster.add(person3);
        roster.add(person4);
        // HashMap list of male persons only
        //The groupingBy operation

        Map<Person.Sex, List<Person> > malePersons = malePersonsOnly(roster);
        for (Map.Entry p: malePersons.entrySet()) {
            List<Person> listPersons = (List<Person>) p.getValue();
            for (Person pp: listPersons) {
                System.out.println("First Name :" +pp.getName() +" : " + " Sex :" + p.getKey());
            }
        }

    }

    private static Map<Person.Sex, List<Person>> malePersonsOnly(List<Person> roster) {
        return roster.parallelStream()
                .filter(person -> person.getGender() == Person.Sex.MALE)
                .collect(
                        Collectors.groupingByConcurrent(
                                Person::getGender));
    }
}
