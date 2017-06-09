package heroku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.ACCEPTED;

/**
 * Created by vhphat on 6/9/2017.
 */
@RestController
@RequestMapping(value = "/api/")
public class TestController {
    private boolean isInitTable = false;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping(value = "init")
    public String init() {
        if (isInitTable) {
            return "Already created table structure";
        }

        isInitTable = true;
        String sql = "CREATE TABLE test (\n" +
                "     json   text NOT NULL\n" +
                ");";
        jdbcTemplate.execute(sql);
        return "Just created a new table 'test'";
    }

    @PostMapping(value = "save")
    @ResponseStatus(ACCEPTED)
    public Object save(@RequestBody String body) {
        System.out.println(body);
        String sql = "INSERT INTO test(json) VALUES (?)";

        jdbcTemplate.update(sql, body);
        return "Accepted";
    }

    @GetMapping(value = "hello")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "Vietnam")
                                    String name) {
        return "Hello " + name;
    }

}
