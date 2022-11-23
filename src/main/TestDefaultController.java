package main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestDefaultController {

	private DefaultController controller;
	private Request request;
	private RequestHandler handler;
	
	
	
	@Before
    public void initialize() throws Exception
    {
        controller = new DefaultController();
        request = new SampleRequest();
        handler = new SampleHandler();
        controller.addHandler (request, handler);      
    }
	
	
	@Test
	public void testMethod() throws Exception {
		//throw new RuntimeException("implement me");
	}
	

	
	
	@Test
	public void testAddHandler() throws Exception {
		
		RequestHandler handler2 = controller.getHandler(request);
		assertSame ("Handler we set in controller should "
				+ "be the same handler we get", handler2, handler);
	}
	
	
	@Test
	public void testProcessRequest() throws Exception {
		
		Response response = controller.processRequest(request);
		assertNotNull("Must not return a null response", response) ;
		assertEquals(new SampleResponse(), response);
	}
	
	
	
	@Test
	public void testProcessRequestAnswersErrorResponse() throws Exception {
		
		SampleRequest request = new SampleRequest("testError");
		SampleExceptionHandler handler = new SampleExceptionHandler();
		controller.addHandler(request, handler);
		Response response = controller.processRequest(request);
		assertNotNull("Must not return a null response", response);
		assertEquals(ErrorResponse.class, response.getClass());
	}
	
	
	
	@Test(expected=RuntimeException.class)
	public void testGetHandlerNotDefined() throws Exception {
		
		SampleRequest request = new SampleRequest("testNotDefined");
		// 다음 줄에서 RuntimeException을 발생시킬 것이다.
		controller.getHandler(request);
	}
	
	
	@Test(expected=RuntimeException.class)
	public void testAddRequestDuplicateName() throws Exception {
		
		SampleRequest request = new SampleRequest();
		SampleHandler handler = new SampleHandler();
		// 다음 줄에서 RuntimeException을 발생시킬 것이다.
		controller.addHandler(request, handler);
	}
	
	
	
	
	@Test(timeout=30)
	public void testProcessMultipleRequestsTimeout() throws Exception {
		
		Request request;
		Response response = new SampleResponse();
		RequestHandler handler = new SampleHandler();
		for(int i=0; i< 99999; i++)
		{
			request = new SampleRequest(String.valueOf(i));
			controller.addHandler(request, handler);
			response = controller.processRequest(request);
			assertNotNull(response);
			assertNotSame(ErrorResponse.class, response.getClass());			
		}
	}
	
	
	
	
	
	private class SampleRequest implements Request
	{
		private static final String DEFAULT_NAME = "Test";
		
		private String name;
				
		public SampleRequest(String name)
		{
			this.name = name;
		}
		
		public SampleRequest()
		{
			this(DEFAULT_NAME);
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return this.name;
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
	
	
	
	private class SampleExceptionHandler implements RequestHandler
	{
		public Response process (Request request) throws Exception
		{
			throw new Exception ("error processing request");
		}
	}
	
	
	
	
	private class SampleResponse implements Response
	{
		private static final String NAME = "Test";
		
		public String getName()
		{
			return NAME;
		}
		
		public boolean equals(Object object) {
			
			boolean result = false;
			if (object instanceof SampleResponse) 
			{
				result = ((SampleResponse) object).getName().equals(getName());
			}
			return result;
		}
		
		public int hashCode ()
		{
			return NAME.hashCode();
		}
		
	}

}
