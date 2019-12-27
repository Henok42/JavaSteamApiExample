
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/*https://docs.oracle.com/javase/tutorial/collections/streams/QandE/answers.html*/
public class TestStreamSequential {

    public static void main(String[] args) {
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

       
       for (Person p: roster) {
            System.out.println(p.getAge());
        }
        // Average
        double average = average(roster);
        System.out.println("average :" + average);

        // sum
        int sumAge = sumAge(roster);
        System.out.println("Sum of the age  :" + sumAge);

        // sum with reduce
        int sumReducedAge = sumReducationAge(roster);
        System.out.println("Sum of Age using reduce :" + sumReducedAge);

        // names of male members

        List<String> personCollection = nameOfMaleMemebers(roster);
        for (String p: personCollection) {
            System.out.println("Person :" + p);
        }

        // HashMap list of male persons only
        //The groupingBy operation

        Map<Person.Sex, List<Person> > malePersons = malePersonsOnly(roster);
        for (Map.Entry p: malePersons.entrySet()) {
            List<Person> listPersons = (List<Person>) p.getValue();
            for (Person pp: listPersons) {
                System.out.println("First Name :" +pp.getName() +" : " + " Sex :" + p.getKey());
            }
        }


        Map<Person.Sex, List<String>> namesByGender = namesByGender(roster);
        for (Map.Entry p: namesByGender.entrySet()) {
            List<String> listPersons = (List<String>) p.getValue();
            for (String name: listPersons) {
                System.out.println("First Name :" +name +" : " + " Sex :" + p.getKey());
            }
        }

        Map<Person.Sex, Integer> totalAgeByGender = totalAgeByGender(roster);
        for (Map.Entry p: totalAgeByGender.entrySet()) {
            Integer totalAge = (Integer) p.getValue();
            System.out.println("totalAgeByGender : " + totalAge);
        }


        Map<Person.Sex, Double> averageAgeByGender = averageAgeByGender(roster);
        for (Map.Entry p: averageAgeByGender.entrySet()) {
            Double averageAge = (Double) p.getValue();
            System.out.println("averageAgeByGender : " + averageAge);
        }
        
        /* The JDK contains many terminal operations (such as average, sum, min, max, and count) that return one value by combining the contents of a stream*/
    }

    private static Map<Person.Sex, Double> averageAgeByGender(List<Person> roster) {
        return roster.stream()
                .filter(person -> person.getGender() == Person.Sex.FEMALE)
                .collect(
                        Collectors.groupingBy(
                                Person::getGender,
                                Collectors.averagingDouble(
                                        Person::getAge)));
    }

    /*The reducing operation takes three parameters:
      identity: Like the Stream.reduce operation, the identity element is both the initial value of the reduction
      and the default result if there are no elements in the stream. In this example, the identity element is 0;
      this is the initial value of the sum of ages and the default value if no members exist.
      mapper: The reducing operation applies this mapper function to all stream elements.
      In this example, the mapper retrieves the age of each member.
      operation: The operation function is used to reduce the mapped values.
      In this example, the operation function adds Integer values.*/

    private static Map<Person.Sex, Integer> totalAgeByGender(List<Person> roster) {
        return roster.stream().filter(person -> person.getGender() == Person.Sex.FEMALE)
                .collect(
                        Collectors.groupingBy(
                                Person::getGender,
                        Collectors.reducing(
                                0,
                                Person::getAge,
                                Integer::sum)));
    }

    /*The groupingBy operation returns a map whose keys are the values that result from applying the lambda expression specified as
          its parameter (which is called a classification function). In this example, the returned map contains two keys, Person.Sex.MALE
          and Person.Sex.FEMALE. The keys' corresponding values are instances of List that contain the stream elements that, when processed
          by the classification function, correspond to the key value. For example, the value that corresponds to key Person.Sex.MALE is an
          instance of List that contains all male members.*/

    private static Map<Person.Sex, List<String>> namesByGender(List<Person> roster) {
        return roster.stream()
                .filter(person -> person.getGender() == Person.Sex.FEMALE)
                .collect(Collectors.
                        groupingBy(
                                Person::getGender,
                                Collectors.mapping(
                                        Person::getName,
                                        Collectors.toList())));
    }

    private static Map<Person.Sex, List<Person>> malePersonsOnly(List<Person> roster) {
        return roster.stream()
                .filter(person -> person.getGender() == Person.Sex.MALE)
                .collect(
                        Collectors.groupingBy(
                                Person::getGender));
    }

    private static List<String> nameOfMaleMemebers(List<Person> roster) {
        return roster.stream()
                .filter(person -> person.getGender() == Person.Sex.MALE)
                .map(p -> p.getName())
                .collect(Collectors.toList());
    }

    private static double average(List<Person> roster) {

        return roster.stream().filter(person -> person.getGender() == (Person.Sex.MALE))
                .mapToInt(Person::getAge)
                .average()
                .getAsDouble();
    }

    private static int sumAge(List<Person> roster){

        return roster.stream()
                .mapToInt(Person::getAge)
                .sum();
    }

    private static int sumReducationAge(Collection<Person> roster){

        return roster.stream()
                .mapToInt(Person::getAge)
                .reduce(0,(a, b)-> a+b);
    }

}
