Feature: login en servidor

#
#  Este test funciona, pero no es de buena educación martillear una API externa
#
#  Scenario: login malo en github
#    Given driver 'https://github.com/login'
#    And input('#login_field', 'dummy')
#    And input('#password', 'world')
#    When submit().click("input[name=commit]")
#    Then match html('.flash-error') contains 'Incorrect username or password.'
#

  Scenario: login malo en plantilla
    Given driver baseUrl + '/user/2'
    And input('#username', 'dummy')
    And input('#password', 'world')
    When submit().click(".form-signin button")
    Then match html('.error') contains 'Error en nombre de usuario o contraseña'

  @login_b
  Scenario: login correcto como b
    Given driver baseUrl + '/login'
    And input('#username', 'b')
    And input('#password', 'aa')
    When submit().click(".form-signin button")
    Then waitForUrl(baseUrl + '/user/2')

  @login_a
  Scenario: login correcto como a
    Given driver baseUrl + '/login'
    And input('#username', 'a')
    And input('#password', 'aa')
    When submit().click(".form-signin button")
    Then waitForUrl(baseUrl + '/admin')

  @register_c
  Scenario: registro correcto como c
    Given driver baseUrl + '/register'
    And input('#username', 'c')
    And input('#password', 'aa')
    And input('#email', 'aa@aa.a')
    When click(".form-signin button[type=submit]")
    * screenshot();
    Then waitForUrl(baseUrl + '/user/new')
    And input('#username', 'c')
    And input('#password', 'aa')
    When click(".form-signin button[type=submit]")
    Then waitForUrl(baseUrl + '/home')

  Scenario: logout after login
    Given driver baseUrl + '/login'
    And input('#username', 'a')
    And input('#password', 'aa')
    When submit().click(".form-signin button")
    Then waitForUrl(baseUrl + '/admin')
    When submit().click("{button}logout")
    Then waitForUrl(baseUrl + '/login')

#  una prueba:
#  mvn test -Dtest=ExternalRunner -Dkarate.options='-t=@login_a'
#
#  todas las pruebas externas
#  mvn test -Dtest=ExternalRunner