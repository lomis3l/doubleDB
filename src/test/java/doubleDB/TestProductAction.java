package doubleDB;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cn.lomis.po.two.Product;
import cn.lomis.util.JsonMapper;

@RunWith(SpringJUnit4ClassRunner.class) 
@WebAppConfiguration 
@ContextConfiguration(locations = {"classpath*:spring/spring.xml"})
public class TestProductAction extends TestParams {
	
	String module = "/product";
	
	@Test // add
	public void testAdd() throws Exception {
		Product product = new Product();
		product.setName("产品1号");
		product.setDesc("便宜");
		product.setPrice(100);
		resultActions = mockMvc.perform(MockMvcRequestBuilders.post(module, "json")
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonMapper.getInstance().toJson(product).getBytes()));
	}
	
	@Test // delete
	public void testDelete() throws Exception {
		resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(module + "/2"));
	}
	
	@Test // update
	public void testUpdate() throws Exception {
		Product product = new Product();
		product.setId(1L);
		product.setName("产品1号");
		product.setDesc("便宜");
		product.setPrice(110);
		resultActions = mockMvc.perform(MockMvcRequestBuilders.put(module, "json")
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonMapper.getInstance().toJson(product).getBytes()));
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
