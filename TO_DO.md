- acceder a BD (via modelo, thymeleaf, foreach sobre game.received) para mostrar todos los  mensajes del juego cuando te conectas a ese lobby. Especialmente bueno si no estás usando buen AJAX 
- poner bonito el unirse a partida: diálogo gordo con "introduce id aquí" para poder entrar. 
- seguridad ante todo: usa los topicCode (aleatorios, no adivinables) para unirte a partidas en lugar de los ids
- explica 


# Lista de cosas que faltan:
** [  ] ** Hacer que todos los campos sean configurables (actualizadas en la BBDD)
** [  ] ** Deshabilitar los botones para que solo los pueda modificar el anfitrión
** [  ] ** Continuar a la pantalla de jugar cuando el creador pulse a jugar
** [  ] ** Registrarse
** [  ] ** Mostrar el perfil
** [  ] ** Solo un usuario esta en la pantalla game y el resto como bystander
** [  ] ** Jugador que esté en la pantalla reciba un interfijo
** [  ] ** Jugador puede escribir y se realiza la validación de la palabra (paso a siguiente turno, en caso de que sea válida)
** [  ] ** Se acaba el tiempo, se resta una vida y se pasa al siguiente turno
** [  ] ** Finalizar el juego hasta que solo un jugador tenga vidas
** [  ] ** Mostrar el podio
** [  ] ** Chat y reportar usuarios
** [  ] ** Gestionar denuncias
** [  ] ** Unirse de manera más natural (poniendo el número de partida en lugar de con enlace o ambas)
** [  ] ** Web sockets