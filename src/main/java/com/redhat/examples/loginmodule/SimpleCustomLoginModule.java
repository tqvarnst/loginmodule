package com.redhat.examples.loginmodule;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

public class SimpleCustomLoginModule extends UsernamePasswordLoginModule {
	private SimplePrincipal user;
	private boolean guestOnly = false;
	// see AbstractServerLoginModule
	private static final String SERVER_PASSWORD = "serverPassword";
	private static final String SERVER_ROLE = "serverRole";
	String serverPassword;
	String serverRole;

	private static final String[] ALL_VALID_OPTIONS = { SERVER_PASSWORD,
			SERVER_ROLE };
	
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, 	
			Map<String, ?> sharedState, Map<String, ?> options) {
		addValidOptions(ALL_VALID_OPTIONS);
		super.initialize(subject, callbackHandler, sharedState, options);
		serverPassword = (String) options.get(SERVER_PASSWORD);
		serverRole = (String) options.get(SERVER_ROLE);
	}

	protected Principal getIdentity() {
		Principal principal = user;
		if (principal == null)
			principal = super.getIdentity();
		return principal;
	}

	protected boolean validatePassword(String inputPassword,
			String expectedPassword) {
		boolean isValid = false;
		if (inputPassword == null) {
			guestOnly = true;
			return false;
		} else {
			isValid = inputPassword.equals(expectedPassword);
		}
		return isValid;
	}

	protected Group[] getRoleSets() throws LoginException {
		Group[] roleSets = { new SimpleGroup("Roles") };
		if (guestOnly == false) {	
			roleSets[0].addMember(new SimplePrincipal(serverRole));
		}
		return roleSets;
	}

	protected String getUsersPassword() throws LoginException {
		
		return serverPassword;
	}

	@Override
	public boolean logout() throws LoginException {
		Group[] groups = this.getRoleSets();
		subject.getPrincipals().remove(groups[0]);
		return super.logout();
	}
}