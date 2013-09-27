/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Mosse
 */
@WebService(serviceName = "LyricsWebService")
public class LyricsWebService {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "getSong")
    public ArrayList<String> getSong(@WebParam(name = "artist") String artist) {
        return ControlClass.song(artist);
    }
}
