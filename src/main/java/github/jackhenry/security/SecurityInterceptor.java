package github.jackhenry.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import github.jackhenry.db.AuthAccess;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTH_PROPERTY = "Authorization";
    private static final String AUTH_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method method = resourceInfo.getResourceMethod();

        if (method == null) {
            requestContext
                    .abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
            return;
        }

        if (!method.isAnnotationPresent(PermitAll.class)) {
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
            final List<String> authorization = headers.get(AUTH_PROPERTY);

            if (authorization == null || authorization.isEmpty()) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Access denied.").build());
                return;
            }

            final String bearerToken = authorization.get(0).replaceFirst(AUTH_SCHEME + " ", "");
            // test bearer token and get associated permissions
            String userRole = AuthAccess.instance().getTokenRole(bearerToken);

            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> roleSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                System.out.println("Accepted roles: " + roleSet.toString());
                System.out.println("User role: " + userRole);
                if (!roleSet.contains(userRole)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Insufficient permissions to access this resource").build());
                    return;
                }
            }
        }
    }


}
