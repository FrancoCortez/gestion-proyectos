package cl.gestion.proyecto.utils.secure;

import cl.gestion.proyecto.model.entities.application.RoleEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtils jwtUtils;

    public AuthenticationManager(final JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Attempts to authenticate the provided {@link Authentication}
     *
     * @param authentication the {@link Authentication} to test
     * @return if authentication is successful an {@link Authentication} is returned. If
     * authentication cannot be determined, an empty Mono is returned. If authentication
     * fails, a Mono error is returned.
     */
    public Mono<Authentication> authenticate(final Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username;
        try {
            username = jwtUtils.getUsernameFromToken(authToken);
        } catch (Exception e) {
            username = null;
        }
        if (username != null && jwtUtils.validateToken(authToken)) {
            Claims claims = jwtUtils.getAllClaimsFromToken(authToken);
            List<String> rolesMap = claims.get("role", List.class);
            List<RoleEntity> roles = new ArrayList<>();
            for (String rolemap : rolesMap) {
                roles.add(RoleEntity.valueOf(rolemap));
            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList())
            );
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}
