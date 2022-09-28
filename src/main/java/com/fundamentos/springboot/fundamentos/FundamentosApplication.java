package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import net.bytebuddy.asm.Advice;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

    private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

    private ComponentDependency componentDependency;
    private MyBean myBean;
    private MyBeanWithDependency myBeanWithDependency;
    private MyBeanWithProperties myBeanWithProperties;
    private UserPojo userPojo;
    private UserRepository userRepository;
    private UserService userService;

    public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
                                  MyBean myBean,
                                  MyBeanWithDependency myBeanWithDependency,
                                  MyBeanWithProperties myBeanWithProperties,
                                  UserPojo userPojo,
                                  UserRepository userRepository,
                                  UserService userService) {
        this.componentDependency = componentDependency;
        this.myBean = myBean;
        this.myBeanWithDependency = myBeanWithDependency;
        this.myBeanWithProperties = myBeanWithProperties;
        this.userPojo = userPojo;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FundamentosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //ejemplosAnteriores();
        saveUsersInDataBase();
        getInformationJpqlFromUser();
        saveWhithErrorTransactional();
    }

    private void saveWhithErrorTransactional(){
        User test1 = new User("TestTranstactional1", "TestTransactional1@domain.com", LocalDate.now());
        User test2 = new User("TestTranstactional2", "TestTransactional2@domain.com", LocalDate.now());
        User test3 = new User("TestTranstactional3", "TestTransactional3@domain.com", LocalDate.now());
        User test4 = new User("TestTranstactional4", "TestTransactional4@domain.com", LocalDate.now());


        List<User> users = Arrays.asList(test1, test2, test3, test4);

    try {
        userService.saveTransactional(users);
    }catch(Exception e){
        LOGGER.error("Error dentro del metodo transaccional " + e);
        }
        userService.getAllUsers().stream()
                .forEach(user ->
                LOGGER.info("Este es el usuario dentro del metodo transaccional: " + user));
    }


    private void getInformationJpqlFromUser() {
        /*
        LOGGER.info("Usuario con el metodo findByUserEmail" +
                userRepository.findByUserEmail("julie@domain.com")
                        .orElseThrow(() -> new RuntimeException("No se encontró usuario")));

        userRepository.findAndSort("user", Sort.by("id").descending())
                .stream()
                .forEach(user -> LOGGER.info("Usuario con metodo sort " + user));

        userRepository.findByName("John")
                .stream()
                .forEach(user -> LOGGER.info("Usuario con query method " + user));

        LOGGER.info("Usuario con query method findByMailAndName" + userRepository.findByEmailAndName("daniela@domain.com", "Daniela")
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado")));

        userRepository.findByNameLike("%u%")
                .stream()
                .forEach(user -> LOGGER.info("Usuario findByNameLike:" + user));

        userRepository.findByNameOrEmail(null, "user10@domain.com")
                .stream()
                .forEach(user -> LOGGER.info("Usuario findByNameOrEmail" + user));*/

        userRepository
                .findByBirthDateBetween(LocalDate.of(2021, 3, 1), LocalDate.of(2021, 4, 2))
                .stream()
                .forEach(user -> LOGGER.info("Usuario con intervalo de fechas: " + user));

        userRepository
                .findByNameContainingOrderByIdDesc("user")
                .stream()
                .forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado: " + user));

       LOGGER.info("El usuario a aprtir del named parameter es: " + userRepository
                       .getAllByBirthDateAndEmail(LocalDate.of(2021, 07, 10),
                               "daniela@domain.com")
               .orElseThrow(() ->
                       new RuntimeException("No se encontró el usuario a partir del named parameter")));
    }

    private void saveUsersInDataBase() {
        User user1 = new User("John", "john@domain.com", LocalDate.of(2021, 03, 15));
        User user2 = new User("Julie", "julie@domain.com", LocalDate.of(2021, 05, 20));
        User user3 = new User("Daniela", "daniela@domain.com", LocalDate.of(2021, 07, 10));
        User user4 = new User("Oscar", "oscar@domain.com", LocalDate.of(2021, 07, 4));
        User user5 = new User("user5", "user5@domain.com", LocalDate.of(2021, 11, 25));
        User user6 = new User("user6", "user6@domain.com", LocalDate.of(2021, 2, 23));
        User user7 = new User("user7", "user7@domain.com", LocalDate.of(2021, 3, 13));
        User user8 = new User("user8", "user8@domain.com", LocalDate.of(2021, 6, 16));
        User user9 = new User("user9", "user9@domain.com", LocalDate.of(2021, 3, 7));
        User user10 = new User("user10", "user10@domain.com", LocalDate.of(2021, 9, 6));
        User user11 = new User("user11", "user11@domain.com", LocalDate.of(2021, 8, 21));
        User user12 = new User("user12", "user12@domain.com", LocalDate.of(2021, 10, 31));

        List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12);

        list.stream().forEach(userRepository::save);

    }

    private void ejemplosAnteriores() {
        componentDependency.saludar();
        myBean.print();
        myBeanWithDependency.printWithDependency();
        System.out.println(myBeanWithProperties.function());
        System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());
        try {
            //error
            int value = 10 / 0;
            LOGGER.debug("Mi valor :" + value);
        } catch (Exception e) {
            LOGGER.error("Esto es un erroral dividir por cero " + e);
        }
    }
}
