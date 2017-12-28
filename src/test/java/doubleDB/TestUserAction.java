package doubleDB;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cn.lomis.po.one.User;
import cn.lomis.util.JsonMapper;

@RunWith(SpringJUnit4ClassRunner.class) 
@WebAppConfiguration 
@ContextConfiguration(locations = {"classpath*:spring/spring.xml"})
public class TestUserAction extends TestParams {
	
	String module = "/user";
	
	@Test // add
	public void testAdd() throws Exception {
		User user = new User();
		user.setName("悟空");
		user.setAge(20);
		user.setPwd("123456");
		resultActions = mockMvc.perform(MockMvcRequestBuilders.post(module, "json")
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonMapper.getInstance().toJson(user).getBytes()));
	}
	
	@Test // delete
	public void testDelete() throws Exception {
		resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(module + "/2"));
	}
	
	@Test // update
	public void testUpdate() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setName("唐僧");
		user.setAge(20);
		user.setPwd("1234567");
		resultActions = mockMvc.perform(MockMvcRequestBuilders.put(module, "json")
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonMapper.getInstance().toJson(user).getBytes()));
	}
	
	@Test // get
	public void testGet() throws Exception {
		resultActions = mockMvc.perform(MockMvcRequestBuilders.get(module + "/1"));
	}
	
	@Test // get
	public void getFind() throws Exception {
		resultActions = mockMvc.perform(MockMvcRequestBuilders.get(module));
	}
}
