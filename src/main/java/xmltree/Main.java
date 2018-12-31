package xmltree;

import xmltree.service.XmlTreeService;

/**
 * Entry point
 */
public class Main {

    /**
     * Method for starting service
     *
     * @param args no args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        XmlTreeService service = new XmlTreeService(8080);
        service.start();
        Runtime.getRuntime().addShutdownHook(new Thread(service::stop));
    }

}