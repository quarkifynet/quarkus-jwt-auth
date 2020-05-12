package tech.donau.quarkify.user;

import tech.donau.quarkify.data.User;
import tech.donau.quarkify.security.TokenService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    TokenService service;

    @POST
    @Path("/register")
    @Transactional
    public User register(User user) {
        user.persist(); //super simplified registration, no checks of uniqueness
        return user;
    }

    @GET
    @Path("/login")
    public String login(@QueryParam("login")String login, @QueryParam("password") String password) {
        User existingUser = User.find("login", login).firstResult();
        if(existingUser == null || !existingUser.password.equals(password)) {
            throw new WebApplicationException(Response.status(404).entity("No user found or password is incorrect").build());
        }
        return service.generateUserToken(existingUser.email, password);
    }
}
