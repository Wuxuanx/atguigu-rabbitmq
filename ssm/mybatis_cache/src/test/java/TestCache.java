import com.atguigu.mybatis.mappers.CacheMapper;
import com.atguigu.mybatis.pojo.Emp;
import com.atguigu.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class TestCache {

    /*
    * 一级3缓存是sqlsession
    * 一级缓存失效的情况:
    * 1. 不同sqlsession对应不同的一级缓存
    * 2. 同一个sqlsession不同的查询条件
    * 3. 同一个sqlsession两次查询期间进行过增删改其中任何一个操作
    * 4. 同一个sqlsession两次查询期间手动清空了缓存
    * */
    @Test
    public void testCache() {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        CacheMapper mapper = sqlSession.getMapper(CacheMapper.class);
        Emp emp1 = mapper.getEmpById(1);
        System.out.println(emp1);
        sqlSession.close();
        SqlSession sqlSession1 = SqlSessionUtil.getSqlSession();
        CacheMapper mapper1 = sqlSession1.getMapper(CacheMapper.class);
        Emp empById = mapper1.getEmpById(1);
        System.out.println(empById);
    }

}
