package ihotel.app.cucumber;

import ihotel.app.IhotelApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = IhotelApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
