package tech.donau.quarkify;

import tech.donau.quarkify.data.User;
import tech.donau.quarkify.security.Roles;
import javax.annotation.security.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class ExampleResource {

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String hello() {
        return "hello";
    }

    @GET
    @Path("/me")
    @RolesAllowed({Roles.USER, Roles.SERVICE})
    public User me() {
        return User.find("email", securityContext.getUserPrincipal().getName()).firstResult();
    }

    @GET
    @Path("/admin")
    @RolesAllowed(Roles.ADMIN)
    public String adminTest() {
        return "If you see this text as a user, then something is broke";
    }

    @GET
    @Path("/void")
    @DenyAll
    public String nothing() {
        return "This method should always return 403";
    }

}
