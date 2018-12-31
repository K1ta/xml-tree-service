package xmltree.service;

import one.nio.http.*;
import one.nio.server.AcceptorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.helpers.DefaultHandler;
import xmltree.converter.XmlTreeConverter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Service for converting xml to tree
 */
public class XmlTreeService {

    private final HttpServer server;
    private final Logger logger;

    /**
     * Constructor for creating new server
     *
     * @param port for listening
     * @throws IOException
     */
    public XmlTreeService(int port) throws IOException {
        logger = LoggerFactory.getLogger(XmlTreeService.class);
        server = new HttpServer(getConfig(port), this);
        logger.info("Service is created");
    }

    /**
     * Method for creating configuration for server
     *
     * @param port for listening
     * @return
     */
    private static HttpServerConfig getConfig(int port) {
        HttpServerConfig serverConfig = new HttpServerConfig();
        AcceptorConfig acceptorConfig = new AcceptorConfig();
        acceptorConfig.port = port;
        serverConfig.acceptors = new AcceptorConfig[]{acceptorConfig};
        return serverConfig;
    }

    /**
     * Method for starting server
     */
    public void start() {
        server.start();
        logger.info("Service is started");
    }

    /**
     * Method for stopping server
     */
    public void stop() {
        server.stop();
        logger.info("Service is stopped");
    }

    /**
     * Method for converting xml to tree
     *
     * @param request POST request with xml file to convert
     * @return tree as string if xml is valid; 405 if request method is not POST; 500 if any error occurs
     */
    @Path("/")
    public Response convert(Request request) {
        logger.debug(request.toString());
        if (request.getMethod() != Request.METHOD_POST) {
            Response response = new Response(Response.METHOD_NOT_ALLOWED, Response.EMPTY);
            response.addHeader("Allow: POST");
            return response;
        }
        Document doc;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new DefaultHandler());
            doc = builder.parse(new ByteArrayInputStream(request.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(Response.INTERNAL_ERROR, Response.EMPTY);
        }
        String res = XmlTreeConverter.getInstance().convert(doc);
        logger.debug(res);
        return Response.ok(res);
    }


}
