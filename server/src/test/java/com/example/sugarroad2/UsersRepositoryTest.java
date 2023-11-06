package com.example.sugarroad2;

import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.UsersRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //H2를 사용하지 않고, 외부 DB를 사용할거야!
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //순서를 지정하는 어노테이션 작성하기 위한 어노테이션
@DataJpaTest
public class UsersRepositoryTest {

    @Autowired //스프링이 알아서 의존성 주입
    private UsersRepository usersR; //얘를 테스트하기 위한 것

    @BeforeEach() //메서드 수행하기 전에 실행
    void pr() { //수행 역활 구간을 구분하기 위함
        System.out.println("=".repeat(80));
    }

    @Test
    @Order(1)
    void select(){
        List<Users> list = usersR.findAll();
        for(Users u : list){
            System.out.println(u.getUserId());
            System.out.println(u.getUserPassword());
            System.out.println(u.getNickname());
            System.out.println(u.getUserName());
            System.out.println(u.getUserEmail());
            System.out.println(u.getUserImagePath() != null ? u.getUserImagePath() : "없음");
        }
    }

    @Test //테스트 어노테이션만 있으면 테스트!
    @Order(2) //순서를 지정하는 어노테이션
    @Transactional
    void save() {
        Users UsersEntity = Users.builder()
                .userId("테스터ID")
                .userPassword("q1w2e3")
                .nickname("테스터별명")
                .userName("테스터")
                .userEmail("테스터@메일.com")
                .gender("male")
                .role("user")
                .status("생존")
                .build();

        usersR.save(UsersEntity);

        List<Users> list = usersR.findAll();
        for(Users u : list){
            System.out.println(u.getUserId());
            System.out.println(u.getUserPassword());
            System.out.println(u.getNickname());
            System.out.println(u.getUserName());
            System.out.println(u.getUserEmail());
            System.out.println(u.getUserImagePath() != null ? u.getUserImagePath() : "없음");

        }
    }
}
