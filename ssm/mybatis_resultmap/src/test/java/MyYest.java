import com.atguigu.mybatis.mappers.DeptMapper;
import com.atguigu.mybatis.mappers.EmpMapper;
import com.atguigu.mybatis.pojo.Dept;
import com.atguigu.mybatis.pojo.Emp;
import com.atguigu.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class MyYest {

    @Test
    public void test1 () {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp empByEmpId = mapper.getEmpByEmpId(1);
        System.out.println(empByEmpId);
    }

    @Test
    public void test2 () {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp empByEmpId = mapper.getEmpAndDeptByEmpId(1);
        System.out.println(empByEmpId);
    }

    @Test
    public void test3 () {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp empByEmpId = mapper.getEmpAndDeptByStepOne(1);
        System.out.println(empByEmpId.getEmpName());
    }

    @Test
    public void test4 () {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
        Dept deptAndEmpByDeptId = deptMapper.getDeptAndEmpByDeptId(1);
        System.out.println(deptAndEmpByDeptId);
    }

    @Test
    public void test5 () {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
        Dept deptAndEmpByDeptId = deptMapper.getDeptAndEmpByStepOne(1);
        System.out.println(deptAndEmpByDeptId.getDeptName());
    }

}
