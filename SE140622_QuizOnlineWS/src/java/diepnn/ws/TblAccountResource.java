/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.ws;

import diepnn.tbl_account.TblAccount;
import diepnn.tbl_account.TblAccountBLO;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Delwyn
 */
@Path("tblAccount")
public class TblAccountResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public TblAccountResource() {
    }

    /**
     * Retrieves representation of an instance of diepnn.ws.TblAccountResource
     * @return an instance of java.lang.String
     */
    
    @Path("/checkIfAccountExisted")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public TblAccount checkIfAccountExisted(@QueryParam("email") String email){
        TblAccountBLO blo = new TblAccountBLO();
        TblAccount account = blo.checkIfMailExisted(email);
        if(account != null){
            account.setPassword("");
            return account;
        }    
        return null;
    }
    
    @Path("/checkLogin")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public TblAccount checkLogin(@QueryParam("email") String email, 
            @QueryParam("password") String password){
        TblAccountBLO blo = new TblAccountBLO();
        if(blo.checkLogin(email, password)){
            TblAccount user = blo.getUser();
            return user;
        }
        return null;
    }
    
    @Path("/signUp")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String signUp(@QueryParam("email") String email, 
            @QueryParam("password") String password,
            @QueryParam("fullname") String fullname){
        if(email != null && password != null && fullname != null){
            TblAccountBLO blo = new TblAccountBLO();
            if(blo.checkIfEmailExisted(email))
                return "Existed";
            if(blo.signUp(email, password, fullname.replaceAll("%20", " ")));
                return "true";
        }
        return "false";
    }
}
