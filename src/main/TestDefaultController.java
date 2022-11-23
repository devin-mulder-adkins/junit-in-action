package main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestDefaultController {

	private DefaultController controller;
	
	
	
	@Before
    public void instantiate() throws Exception
    {
        controller = new DefaultController();
      
    }
	
	
	@Test
	public void testMethod() throws Exception {
		//throw new RuntimeException("implement me");
	}
	

	
	
	@Test
	public void testAddHandler() throws Exception {
		
		Request request = new SampleRequest();
		RequestHandler handler = new SampleHandler();
		controller.addHandler(request, handler);
		RequestHandler handler2 = controller.getHandler(request);
		assertSame ("Handler we set in controller should "
				+ "be the same handler we get", handler2, handler);
	}
	
	
	
	
	
	private class SampleRequest implements Request
	{

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Test";
		}
		
	}
	
	
	private class SampleHandler implements RequestHandler
	{

		@Override
		public Response process(Request request) throws Exception {
			// TODO Auto-generated method stub
			return new SampleResponse();
		}
		
	}
	
	
	private class SampleResponse implements Response
	{
		
	}

}
