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

#  Scenario: login malo en plantilla
#    Given driver baseUrl + '/user/2'
#    And input('#username', 'dummy')
#    And input('#password', 'world')
#    When submit().click(".form-signin button")
#    Then match html('.error') contains 'Error en nombre de usuario o contraseña'

  @login_b
  Scenario: login correcto como b
    Given driver baseUrl + '/login'
    And input('#username', 'b')
    And input('#password', 'aa')
    When click(".form-signin button[type=submit]")
    Then waitForUrl(baseUrl + '/home')

  @login_a
  Scenario: login correcto como a
    Given driver baseUrl + '/login'
    And input('#username', 'a')
    And input('#password', 'aa')
    When click(".form-signin button[type=submit]")
    Then waitForUrl(baseUrl + '/home')

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

#  Scenario: logout after login
#    Given driver baseUrl + '/login'
#    And input('#username', 'a')
#    And input('#password', 'aa')
#    When click(".form-signin button[type=submit]")
#    Then waitForUrl(baseUrl + '/home')
#    When click("{button}logout")
#    Then waitForUrl(baseUrl + '/login')

  @logout_a
  Scenario: logout after login
    Given driver baseUrl + '/login'
    And input('#username', 'a')
    And input('#password', 'aa')
    When click(".form-signin button[type=submit]")
    Then waitForUrl(baseUrl + '/home')
    When click(".btn-logout")
    Then waitForUrl(baseUrl + '/login')

  @create_a
  Scenario: user a creates a game 
    Given driver baseUrl + '/login'
    And input('#username', 'a')
    And input('#password', 'aa')
    When click(".form-signin button[type=submit]")
    Then waitForUrl(baseUrl + '/home')
    When click("#create")
    Then waitForUrl(baseUrl + '/lobby/1075')

# @join_b
#  Scenario: user b joins a game 
#    Given driver baseUrl + '/login'
#    And input('#username', 'a')
#    And input('#password', 'aa')
#    When click(".form-signin button[type=submit]")
#    Then waitForUrl(baseUrl + '/home')
#    When click("#create")
#    Then waitForUrl(baseUrl + '/lobby/1076')
#    When click(".btn-logout")
#    Then waitForUrl(baseUrl + '/login')
#    And input('#username', 'b')
#    And input('#password', 'aa')
#    When click(".form-signin button[type=submit]")
#    Then waitForUrl(baseUrl + '/home')
#    When click("#play")
#    Then waitForUrl(baseUrl + '/join')
#    And input('#code', '1076')
#    * input('#code', Key.ENTER)
#    * waitForUrl(baseUrl + '/lobby/1076')

#  una prueba:
#  mvn test -Dtest=ExternalRunner -Dkarate.options='-t=@login_a'
#
#  todas las pruebas externas
#  mvn test -Dtest=ExternalRunner