package border.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import border.repository.AdminUnitRepositoryImpl;

public class AccessHelper {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitRepositoryImpl.class);
	
	// Suppress default constructor for noninstantiability
	private AccessHelper() {
		throw new AssertionError();
	}

	public static boolean userAuthorized(String roleName) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// if user has sufficient privileges, then he's okay
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if (authority.getAuthority().equals(roleName)) {
				LOGGER.info("He's okay, open the form");
				return true;
			}
		}
		LOGGER.info("Meaningless simple user trying to access, throw him back");
		return false;
	}
	
	public static String getUserName() {
		String username = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null) {
			User user = (User) authentication.getPrincipal();
			username = user.getUsername();
		}
		return username;
	}
}
