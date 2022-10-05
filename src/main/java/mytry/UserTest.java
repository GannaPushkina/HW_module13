package mytry;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class UserTest {

    private static final String USERS = "https://jsonplaceholder.typicode.com/users";
    private static final String UPDATE_USER = "https://jsonplaceholder.typicode.com/users/4";
    private static final String DELETE_USER = "https://jsonplaceholder.typicode.com/users/5";
    private static final String GET_USER_BY_ID = "https://jsonplaceholder.typicode.com/users/6";

    public static void main(String[] args) throws IOException, InterruptedException {

        User firstUser = createFirstUser();
        User updatedFirstUser = updatedFirstUser();


        User createdUser = HttpUtilities.postUser(URI.create(USERS), firstUser);
        System.out.println(createdUser);

        User updatedUser = HttpUtilities.putUser(URI.create(UPDATE_USER), updatedFirstUser);
        System.out.println(updatedUser);

        System.out.println(HttpUtilities.deleteUser(URI.create(DELETE_USER), updatedUser));

        System.out.println(HttpUtilities.printListOfUsers(URI.create(USERS)));

        User userById = HttpUtilities.getUserById(URI.create(GET_USER_BY_ID));
        System.out.println(userById);


        HttpUtilities.getUserComments(5);

        List<Todo> allTodos = HttpUtilities.getUserTodos(URI.create(USERS), 7);
        System.out.println(allTodos);

    }

    private static User createFirstUser() {
        Geo geo = new Geo();
        geo.setLat("-37.3159");
        geo.setLng("81.1496");

        Address address = new Address();
        address.setStreet("Sesame");
        address.setSuite("HZ But Fun");
        address.setCity("Castle Rock");
        address.setZipcode("455-666");
        address.setGeo(geo);

        Company company = new Company();
        company.setName("Horns & Hooves");
        company.setCatchPhrase("Best horns & hooves");
        company.setBs("BS");

        User user = new User();
        user.setId(11);
        user.setName("Jonh First");
        user.setUsername("johnny_m");
        user.setEmail("jm@jm.com");
        user.setAddress(address);
        user.setPhone("33-55-666");
        user.setWebsite("jm.com");
        user.setCompany(company);
        return user;
    }

    private static User updatedFirstUser() {
        Geo geo = new Geo();
        geo.setLat("-37.3159");
        geo.setLng("81.1496");

        Address address = new Address();
        address.setStreet("Sesame");
        address.setSuite("HZ But Fun");
        address.setCity("Castle Rock");
        address.setZipcode("455-666");
        address.setGeo(geo);

        Company company = new Company();
        company.setName("Horns & Hooves");
        company.setCatchPhrase("Best horns & hooves");
        company.setBs("BS");

        User user = new User();
        user.setId(11);
        user.setName("Jonh Second");
        user.setUsername("johnny_m");
        user.setEmail("jm@jm.com");
        user.setAddress(address);
        user.setPhone("33-55-666");
        user.setWebsite("jm.com");
        user.setCompany(company);
        return user;
    }
}

//https://jsonplaceholder.typicode.com/users
