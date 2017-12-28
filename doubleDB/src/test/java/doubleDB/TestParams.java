package doubleDB;

import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class TestParams {
	protected MockMvc mockMvc;
	protected ResultActions resultActions;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@After
	public void after() {
		try {
			MvcResult mvcResult = resultActions.andReturn();
			System.out.println(mvcResult.getRequest().getRequestURI());
			int ststus = mvcResult.getResponse().getStatus();
			Assert.assertEquals(200, ststus);
			if (ststus == 200) {
				String result = mvcResult.getResponse().getContentAsString();
				System.out.println(result);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}