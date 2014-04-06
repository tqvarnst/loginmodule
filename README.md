# Example of Simple login module
I created this example on behalf of a customer that uses the REST interface for JBoss BPM Suite. This login module allows a intermediate server to identify as a any user as long as an server password is provided. This can for exampl ebe used to authenticate with the REST service of BPM Suite.

###Prerequisites
1. Working installtion of JBoos EAP or JBoss BPM Suite

### To build and install

1. Make sure you have JBoss EAP maven repos configured. See [here](https://github.com/jboss-developer/jboss-eap-quickstarts/blob/6.3.x-develop/settings.xml) for and example settings.xml
 	
 
2. Build the project with maven
    
        mvn clean install

3. Replace or add a login module to the configuration of JBoss BPM Suite (e.g. $JBOSS_HOME/standalone/configuration/standalone.xml)
        
        <security-domain name="other" cache-type="default">
           <authentication>
              <login-module code="Remoting" flag="optional"> 
                 <module-option name="password-stacking" value="useFirstPass"/>
              </login-module>
              <login-module code="com.redhat.examples.loginmodule.SimpleCustomLoginModule" flag="sufficient" module="simpleloginmodule">
                 <module-option name="password-stacking" value="useFirstPass"/>
                 <module-option name="serverRole" value="admin"/>
                 <module-option name="serverPassword" value="qwerty67"/>
              </login-module>
              <login-module code="RealmDirect" flag="required">
                 <module-option name="password-stacking" value="useFirstPass"/>
              </login-module>
           </authentication>
        </security-domain>
            
2. Copy the content of the module into the module directory of JBoss BPM Suite

        cp -r target/module/* $JBOSS_HOME/modules/

3. (Re)start the Server.
4. Try to login with any username and the password configured in step 3. Verify that the user are assigned the correct role.

This configuration means that any username with password **qwerty67** will succed in login in. Users that knows their username and password can ofcourse login (currently using the builtin application realm authentication, but can be replaced with for example LDAP/AD or SPNEGO and/or Kerberos). 


*For security reasons it's recommende to use hashed password, but that's not what I'm trying to illustrate in this example. Even better would be to store the password in a secure store or for example an LDAP directory'*

Good luck, 

**Thomas Qvarnström**
