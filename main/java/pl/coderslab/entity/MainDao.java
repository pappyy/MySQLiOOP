package pl.coderslab.entity;

public class MainDao extends UserDao{
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = new User();
/*
        user.setEmail("patryk@gmail.com");
        user.setUserName("Patryk");
        user.setPassword("pass");
        userDao.create(user);


        User read = userDao.read(7);
        System.out.println(read);

        User userUpdate = userDao.read(7);
        userUpdate.setUserName("Jan");
        userUpdate.setEmail("Jan@gmail.com");
        userUpdate.setPassword("suppPass");
        userDao.update(userUpdate);


        userDao.delete(7);

 */
        User user2 = new User();
        user2.setUserName("Stefan");
        user2.setEmail("stefan@gmail.com");
        user2.setPassword("passPass");
        userDao.create(user2);
        User[] all = userDao.findAll();
        for (User usr : all) {
            System.out.println(usr);
        }
    }
}
