package main;

public interface RequestHandler {
	Response process( Request request ) throws Exception;
}
