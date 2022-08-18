import com.atguigu.mybatis.mappers.UserMapper;
import com.atguigu.mybatis.pojo.User;
import com.atguigu.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MyTest {

    @Test
    public void testGetUser() {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // User user = mapper.getUserByName("root");
        // User user = mapper.checkLogin("root", "123456");
        Map<String, Object> map = new HashMap<>();
        map.put("username", "root");
        map.put("password", "123456");
        User user = mapper.checkLogMap(map);
        System.out.println(user.toString());
        User user1 = new User(7,"root", "123456", 33, "男", "13245@qq.com");
        int num = mapper.insertUser(user1);
        System.out.println("num--> " + num);
        User user2 = mapper.checkLoginByParam("root", "1234567");
        System.out.println(user2.toString());
    }

}
