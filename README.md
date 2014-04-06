# Example of Simple login module
I created this example on behalf of a customer that uses the REST interface for JBoss BPM Suite. This login module allows a intermediate server use the REST interface as a particular user, but with a hardcoded password.

Make sure you have JBoss EAP maven repos configured.....

1. Build the project with maven
   mvn clean install

1. Replace or add a login module to the configuration of JBoss BPM Suite (e.g. $JBOSS_HOME/standalone/configuration/standalone.xml)
        <security-domain name="other" cache-type="default">
               <authentication>
                  <login-module code="com.redhat.examples.loginmodule.SimpleCustomLoginModule" flag="required" module="simpleloginmodule">
                      <module-option name="serverRole" value="admin"/>
                      <module-option name="serverPassword" value="qwerty67"/>
                  </login-module>
               </authentication>
            </security-domain>
            
2. Copy the content of the module into the module directory of JBoss BPM Suite

   cp -r target/module/* $JBOSS_HOME/modules/

3. (Re)start the BPM Suite.
4. Try to login with any username and the password configured in step 2. Verify that the user are assigned the correct role.

Please note that this module could be stacked with other modules.

Good luck
Thomas Qvarnström
