package fr.iutinfo.skeleton.auth;

import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.User;
import fr.iutinfo.skeleton.api.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {
    final static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
        String auth = containerRequest.getHeaderString(HttpHeaders.AUTHORIZATION);
        String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
        String uriAuth = containerRequest.getUriInfo().getRequestUri().getUserInfo();
        logger.debug("uriAuth : " + uriAuth);
        logger.debug("auth : " + auth);

        if (auth != null) {
            String[] loginPassword = BasicAuth.decode(auth);
            logger.debug("login : " + loginPassword[0]);
            logger.debug("password : " + loginPassword[1]);
            if (loginPassword == null || loginPassword.length != 2) {
            	throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"").entity("Ressource requires login.").build());
            }

            UserDao dao = BDDFactory.getDbi().open(UserDao.class);
            try {
    			dao.createUserTable();
    			User admin = new User(0, "admin", "admin");
    			admin.setPassword("admin");
    			dao.insert(admin);
    		} catch (Exception e) { }
            
            User user = dao.findByName(loginPassword[0]);
            if(user != null && !user.isGoodPassword(loginPassword[1]) || user == null) {
            	 throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Take & Wash\"").entity("Ressource requires login.").build());
            }

            containerRequest.setSecurityContext(new AppSecurityContext(user, scheme));
        } else {
        	containerRequest.setSecurityContext(new AppSecurityContext(User.getAnonymousUser(), scheme));
        }
    }
}
