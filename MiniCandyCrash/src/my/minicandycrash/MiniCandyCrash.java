package my.minicandycrash;

import java.util.Scanner;
import static java.lang.Math.random;
import static java.lang.Math.floor;

/**
 * Juego que consiste en ir haciendo desaparecer tres bloques de 3 o más
 * bolas contiguas verticalmente de un mismo color de un tablero. Para ello, el jugador puede 
 * intercambiar dos bolas de posiciones contiguas, vertical u horizontalmente. Cuando se forma una
 * secuencia vertical de 3 o más bolas del mismo color, toda esa secuencia desaparece, y las bolas que
 * hay encima caen para rellenar los huecos. Para rellenar los nuevos huecos que quedan en la parte 
 * alta del tablero aparecen nuevas bolas de color aleatorio, de forma que el tablero siempre está lleno.
 * El juego termina cuando se ha hecho un cierto número de movimientos.
 * 
 * REGLAS DEL JUEGO
 * - El tablero estará formado por 9 filas y 9 columnas; inicialmente contiene piezas aleatorias.
 * 
 * - Las bolas pueden ser de 3 colores en la versión fácil, de 4 en la intermedia y de 5 en la difícil.
 * 
 * - El color de las piezas se representa con un número entero, pero se muestra en pantalla con 
 * 	 símbolos que sean fáciles de distinguir ('o', '*', '=', ...)
 * 
 * - Un bloque es un conjunto de 3 o más bolas del mismo color que se tocan verticalmente.
 * 
 * - Cada casilla se especifica con dos enteros, que corresponden a número de fila y número de
 * 	 columna, contando desde la fila 1, la de más abajo, y desde la columna 1, la de más a la
 * 	 izquierda.
 * 
 * - El jugador dará como entradas 4 enteros que se corresponden con dos casillas contiguas.
 * 
 * - Si al intercambiar el contenido de esas dos casillas no se forma ningún bloque, la jugada no
 * 	 tendrá efecto.
 * 
 * - Si tras el intercambio se forma algún bloque, se eliminarán todas las bolas de ese bloque, se
 * 	 desplazarán hacia abajo las que están encima rellenando los huecos y aparecerán por la parte 
 * 	 superior nuevas bolas. Con esos movimientos y la aparición de nuevas piezas, es posible que
 * 	 se formen bloque nuevos, en cuyo caso hay que eliminarlos todos.
 * 
 * - Cada bloque eliminado puntúa según el número de bolas que lo forman, del siguiente modo:
 * 		~ 3 bolas: 10 puntos.
 * 		~ 4 bolas: 20 puntos.
 * 		~ 5 bolas: 30 puntos.
 * 		~ 6 bolas: 40 puntos (puede ocurrir como consecuencia de la eliminación de algún
 * 		  bloque, según como caigan las piezas nuevas).
 * 		~ 7 bolas: 50 puntos (puede ocurrir, como en el caso anterior).
 * 
 * - Después de cada movimiento se mostrarán los puntos obtenidos y los acumulados hasta el
 * 	 momento. Al terminar el juego, se mostrarán los puntos, y el jugador verá de nuevo el menú
 * 	 inicial.
 * 
 * - El juego termina cuando se han hecho 10 movimientos.
 * 
 * - En cualquier momento del juego, el jugador puede terminar, insertando como jugada 0 0 0 0.
 *   La puntuación será la alcanzada hasta ese momento.
 *   
 * OBSERVACIONES SOBRE EL PROGRAMA
 * - El tablero no debe presentar bloques cuando el jugador vaya a realizar un movimiento.
 * 
 * - El tablero siempre debe tener alguna jugada posible.
 * 
 * 
 * @author Richard Albán Fernández
 *
 */

public class MiniCandyCrash {
	// Para este juego, el mínimo número de filas y columnas debe ser 3.
	final static int FILAS = 9;
	final static int COLUMNAS = 9; 
	
	public static void main(String[] args) {
		
		int modo;
		int colores = 0;
		boolean tableroFijo = false;
		Scanner teclado = new Scanner(System.in);
		
		do {
			System.out.println("---------- MINI_CANDY_CRASH ----------\n");
			System.out.println("Elija tipo de tablero:");
			System.out.println("   1. Fácil");
			System.out.println("   2. Intermedio");
			System.out.println("   3. Difícil");
			System.out.println("   4. Tablero fijo");
			System.out.println("   0. Salir");

			modo = teclado.nextInt();
			
			System.out.println();
			
			switch(modo) {
				case 1:
					System.out.println("Has seleccionado el Tablero Fácil.");
					colores = 3;
					tableroFijo = false;
					break;
				case 2: 
					System.out.println("Has seleccionado el Tablero Intermedio.");
					colores = 4;
					tableroFijo = false;
					break;
				case 3: 
					System.out.println("Has seleccionado el Tablero Difícil.");
					colores = 5;
					tableroFijo = false;
					break;
				case 4: 
					System.out.println("Has seleccionado el Tablero fijo.");
					colores = 3;
					tableroFijo = true;
					break;
				case 0: 
					System.out.println("¡Hasta la próxima!");
					break;
				default:
					System.out.println("Por favor, seleccione una opción válida.");
			}
			
			System.out.println();
			
			if(modo == 1 || modo == 2 || modo == 3 || modo == 4) {
				jugar(teclado, colores, tableroFijo);
			}
			
			System.out.println("\n");
			
		} while(modo != 0);
		
		teclado.close();
	}

	/**
	 * Realiza todas las operaciones que afectan a la partida.
	 * 
	 * @param teclado lee entradas por teclado
	 * @param colores colores del tablero
	 * @param tableroFijo determina si el tablero debe ser el fijo o no
	 */
	private static void jugar(Scanner teclado, int colores, boolean tableroFijo) {
		int filaInicio, columnaInicio, filaFin, columnaFin;
		
		int[][] tablero = {{2, 1, 1, 1, 1, 1, 1, 1, 1},
						   {1, 2, 2, 2, 1, 2, 2, 2, 1},
						   {3, 2, 3, 3, 3, 1, 3, 3, 3},
						   {1, 1, 1, 1, 1, 1, 1, 1, 1},
						   {2, 2, 2, 2, 1, 2, 2, 1, 3},
						   {3, 3, 3, 3, 3, 3, 3, 3, 1},
						   {1, 1, 2, 1, 3, 1, 1, 1, 1},
						   {2, 2, 1, 2, 2, 2, 2, 1, 2},
						   {3, 2, 1, 3, 3, 3, 3, 3, 1}};
		
		if(!tableroFijo) {
			boolean tableroValido = false;
			
			while(!tableroValido) {
				tablero = crearTableroAleatorio(colores);
				tableroValido = comprobarSiTableroEsValido(tablero);
			}
		}
		
		imprimirTablero(tablero);
		
		// TODO poner bien condición del while. Imprimir el turno de juego.
		while (true) {
			do {
				System.out.print("\nIntroduzca un movimiento (FilaInicio ColumnaInicio FilaFin ColumnaFin): ");
				filaInicio = traducirFila(teclado.nextInt());
				columnaInicio = traducirColumna(teclado.nextInt());
				filaFin = traducirFila(teclado.nextInt());
				columnaFin = traducirColumna(teclado.nextInt());
			} while(!comprobarSiMovimientoEsValido(filaInicio, columnaInicio, filaFin, columnaFin));

		
			hacerMovimiento(tablero, filaInicio, columnaInicio, filaFin, columnaFin);
			imprimirTablero(tablero);
		}
	}

	/**
	 * Intercambia la posicion del tablero de cordenadas {@code filaInicio} y {@code columnaInicio} con las coordenadas
	 * de {@code filaFin} y {@code columnaFin}.
	 * 
	 * @param tablero tablero en el que se realiza el cambio
	 * @param filaInicio fila inicial
	 * @param columnaInicio columna inicial
	 * @param filaFin fila final
	 * @param columnaFin columna final
	 */
	private static void hacerMovimiento(int[][] tablero, int filaInicio, int columnaInicio, int filaFin,
			int columnaFin) {
		int aux;
		
		aux = tablero[filaInicio][columnaInicio];
		tablero[filaInicio][columnaInicio] = tablero[filaFin][columnaFin];
		tablero[filaFin][columnaFin] = aux;
	}

	/**
	 * Se comprueba si el movimiento a realizar es válido teniendo en cuenta las condiciones del enunciado. 
	 * Se comprueba que las filas y columnas no superen los límites del tablero, que no se pueda realizar un 
	 * movimiento hasta la misma posición y que los movimientos solo puedan ser contiguos, vertical u horizontalmente.
	 * @param filaInicio fila inicial
	 * @param columnaInicio columna inicial
	 * @param filaFin fila final
	 * @param columnaFin columna final
	 * 
	 * @return {@code true} si el movimiento es válido y {@code false} si no lo es
	 */
	private static boolean comprobarSiMovimientoEsValido(int filaInicio, int columnaInicio, int filaFin, int columnaFin) {
		boolean movimientoValido = false;
		
		if((filaInicio < 0 || filaInicio >= FILAS) || (columnaInicio < 0 || columnaInicio >= COLUMNAS) ||
				(filaFin < 0 || filaFin >= FILAS) || (columnaFin < 0 || columnaFin >= COLUMNAS)) {
			movimientoValido = false;
			System.out.println("Coordenadas fuera de limites.");
		}
		// Si las coordenadas iniciales son iguales que las coordenadas finales
		else if(filaInicio == filaFin && columnaInicio == columnaFin) {
			movimientoValido = false;
			System.out.println("mismas Coordenadas .");

		}
		// Borde superior
		else if(filaInicio == 0) {
			System.out.println("problema borde superior.");

			if(columnaInicio == 0) {
				if((filaFin == filaInicio + 1 && columnaFin == columnaInicio) || 
						(filaFin == filaInicio && columnaFin == columnaInicio + 1)) {
					movimientoValido = true;
				}
			}
			else if(columnaInicio == COLUMNAS - 1) {
				if((filaFin == filaInicio + 1 && columnaFin == columnaInicio) || 
						(filaFin == filaInicio && columnaFin == columnaInicio - 1)) {
					movimientoValido = true;
				}
			}
			else {
				if((filaFin == filaInicio && columnaFin == columnaInicio - 1) || 
						(filaFin == filaInicio && columnaFin == columnaInicio + 1) ||
						(filaFin == filaInicio + 1 && columnaFin == columnaInicio)) {
					movimientoValido = true;
				}
			}
		}
		// Borde inferior
		else if(filaInicio == FILAS - 1) {
			System.out.println("problema borde inferior.");

			if(columnaInicio == 0) {
				if((filaFin == filaInicio - 1 && columnaFin == columnaInicio) || 
						(filaFin == filaInicio && columnaFin == columnaInicio + 1)) {
					movimientoValido = true;
				}
			}
			else if(columnaInicio == COLUMNAS - 1) {
				if((filaFin == filaInicio - 1 && columnaFin == columnaInicio) || 
						(filaFin == filaInicio && columnaFin == columnaInicio - 1)) {
					movimientoValido = true;
				}
			}
			else {
				if((filaFin == filaInicio && columnaFin == columnaInicio - 1) || 
						(filaFin == filaInicio && columnaFin == columnaInicio + 1) ||
						(filaFin == filaInicio - 1 && columnaFin == columnaInicio)) {
					movimientoValido = true;
				}
			}
		}
		// Borde izquierdo
		else if(columnaInicio == 0) {
			System.out.println("problema borde izquierdo.");

			if((filaFin == filaInicio && columnaFin == columnaInicio + 1) || 				
					(filaFin == filaInicio - 1 && columnaFin == columnaInicio) ||
					(filaFin == filaInicio + 1 && columnaFin == columnaInicio)) {
				movimientoValido = true;
			}
		}
		// Borde derecho
		else if(columnaInicio == COLUMNAS - 1) {
			System.out.println("problema borde derecho.");

			if((filaFin == filaInicio && columnaFin == columnaInicio - 1) || 				
					(filaFin == filaInicio - 1 && columnaFin == columnaInicio) ||
					(filaFin == filaInicio + 1 && columnaFin == columnaInicio)) {
				movimientoValido = true;
			}
		}
		else {
			if((filaInicio == filaFin - 1 && columnaInicio == columnaFin) ||
					(filaInicio == filaFin + 1 && columnaInicio == columnaFin) ||
					(filaInicio == filaFin && columnaInicio == columnaFin - 1) ||
					(filaInicio == filaFin && columnaInicio == columnaInicio + 1)) {
				movimientoValido = true;
			}
		}
		
		if(!movimientoValido) {
			System.out.println("Movimiento inválido. Tiene que volver a introducir las coordenadas.");
		}
		
		return movimientoValido;
	}

	/**
	 * Devuelve el verdadero valor de la fila, que es distinto del valor que aparece representado en el tablero y es
	 * con el que se trabaja internamente.
	 * 
	 * @param fila fila a traducir
	 * @return valor de la fila traducido a su verdadero valor
	 */
	private static int traducirFila(int fila) {
		return FILAS - fila;
	}

	/**
	 * Devuelve el verdadero valor de la columna, que es distinto del valor que aparece representado en el tablero y es
	 * con el que se trabaja internamente.
	 * 
	 * @param columna columna a traducir
	 * @return valor de la columna traducido a su verdadero valor
	 */
	private static int traducirColumna(int columna) {
		return columna - 1;
	}

	/**
	 * Imprime por consola el tablero de juego.
	 * 
	 * @param tablero tablero a imprimir
	 */
	private static void imprimirTablero(int[][] tablero) {
		
		System.out.print("   ");
		
		for(int i = 0; i < tablero[0].length; i++) {
			System.out.printf("%3d", i + 1);
		}
		
		System.out.println("\n");
		
		for(int i = 0; i < tablero.length; i++) {
			System.out.printf("%2d  ", tablero.length - i);
			
			for(int j = 0; j < tablero[0].length; j++) {
				System.out.printf("%2c ", convertirEnteroASimbolo(tablero[i][j]));
			}
			
			System.out.println();
		}
	}

	/**
	 * Convierte un entero a un símbolo. Para este juego, hay un máximo de 5 símbolos.
	 * 
	 * @param entero entero a convertir
	 * @return simbolo
	 */
	private static char convertirEnteroASimbolo(int entero) {
		char simbolo = ' ';
		
		switch(entero) {
		case 1: 
			simbolo = 'o';
			break;
		case 2: 
			simbolo = '*';
			break;
		case 3: 
			simbolo = '=';
			break;
		case 4: 
			simbolo = '%';
			break;
		case 5:
			simbolo = '&';
			break;
		}
		
		return simbolo;
	}

	/**
	 * Devuelve un valor {@code boolean}, que será {@code true} o {@code false}, dependiendo de si el tablero se ajusta 
	 * a las condiciones del enunciado. Se comprueba que el tablero no presenta ningún bloque y que existe alguna jugada
	 * posible.
	 * 
	 * @param tablero tablero a comprobar
	 * @return {@code true} si el tablero se ajusta a las condiciones del enunciado, y {@code false} en caso contrario.
	 */
	private static boolean comprobarSiTableroEsValido(int[][] tablero) {
		boolean hayBloque = false;
		boolean hayJugada = false;
		boolean tableroValido = false;
		
		// Se comprueba que el tablero no presenta ningún bloque
		for(int i = 0; i < tablero.length - 2 & !hayBloque; i++) {
			for(int j = 0; j < tablero[0].length & !hayBloque; j++) {
				if(tablero[i][j] == tablero[i + 1][j] & tablero[i][j] == tablero[i + 2][j]) {
					hayBloque = true;
				}
			}
		}
		
		// Se comprueba que existe alguna jugada posible
		for(int i = 0; i < tablero.length & !hayJugada; i++) {
			for(int j = 0; j < tablero[0].length & !hayJugada; j++) {
				//System.out.println("tablero[" + i + "][" + j + "]");
				// Borde superior
				if(i == 0) {
					if(j == 0) {
						if(tablero[i][j] == tablero[i + 1][j + 1] &&  tablero[i][j] == tablero[i + 2][j + 1]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i + 1][j + 1] && tablero[i][j] == tablero[i + 2][j]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i + 1][j] && tablero[i][j] == tablero[i + 2][j + 1]) {
							hayJugada = true;
						}
					} else if(j == tablero[0].length - 1) {
						if(tablero[i][j] == tablero[i + 1][j - 1] &&  tablero[i][j] == tablero[i + 2][j - 1]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i + 1][j - 1] && tablero[i][j] == tablero[i + 2][j]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i + 1][j] && tablero[i][j] == tablero[i + 2][j - 1]) {
							hayJugada = true;
						}
					} else {
						if(tablero[i][j] == tablero[i + 1][j - 1] && tablero[i][j] == tablero[i + 2][j - 1]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i + 1][j + 1] && tablero[i][j] == tablero[i + 2][j + 1]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i + 1][j - 1] && tablero[i][j] == tablero[i + 2][j]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i + 1][j + 1] && tablero[i][j] == tablero[i + 2][j]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i + 1][j] && tablero[i][j] == tablero[i + 2][j - 1]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i + 1][j] && tablero[i][j] == tablero[i + 2][j + 1]) {
							hayJugada = true;
						}
					}
				}
				// Borde inferior
				else if(i == tablero.length - 1) {
					if(j == 0) {
						if(tablero[i][j] == tablero[i - 1][j + 1] &&  tablero[i][j] == tablero[i - 2][j + 1]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i - 1][j + 1] && tablero[i][j] == tablero[i - 2][j]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i - 1][j] && tablero[i][j] == tablero[i - 2][j + 1]) {
							hayJugada = true;
						}
					} else if(j == tablero[0].length - 1) {
						if(tablero[i][j] == tablero[i - 1][j - 1] &&  tablero[i][j] == tablero[i - 2][j - 1]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i - 1][j - 1] && tablero[i][j] == tablero[i - 2][j]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i - 1][j] && tablero[i][j] == tablero[i - 2][j - 1]) {
							hayJugada = true;
						}
					} else {
						if(tablero[i][j] == tablero[i - 1][j - 1] && tablero[i][j] == tablero[i - 2][j - 1]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i - 1][j + 1] && tablero[i][j] == tablero[i - 2][j + 1]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i - 1][j - 1] && tablero[i][j] == tablero[i - 2][j]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i - 1][j + 1] && tablero[i][j] == tablero[i - 2][j]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i - 1][j] && tablero[i][j] == tablero[i - 2][j - 1]) {
							hayJugada = true;
						}
						else if(tablero[i][j] == tablero[i - 1][j] && tablero[i][j] == tablero[i - 2][j + 1]) {
							hayJugada = true;
						}
					}
				}
				// Borde izquierdo
				else if(j == 0) {
					if(tablero[i][j] == tablero[i - 1][j + 1] && tablero[i][j] == tablero[i + 1][j + 1]) {
						hayJugada = true;
					}
				}
				// Borde derecho
				else if(j == tablero[0].length - 1) {
					if(tablero[i][j] == tablero[i - 1][j - 1] && tablero[i][j] == tablero[i + 1][j - 1]) {
						hayJugada = true;
					}
				}
				// Interior
				else {
					if(tablero[i][j] == tablero[i - 1][j - 1] && tablero[i][j] == tablero[i + 1][j - 1])  {
						hayJugada = true;
					}
					else if(tablero[i][j] == tablero[i - 1][j + 1] && tablero[i][j] == tablero[i + 1][j + 1]) {
						hayJugada = true;
					}
				}
			}
		}
		
		if (!hayBloque && hayJugada) {
			tableroValido = true;
		}
		
		return tableroValido;
	}

	/**
	 * Devuelve un tablero que contiene números enteros aleatorios.
	 * 
	 * @param colores colores que tiene el tablero
	 * @return tablero con números enteros aleatorios
	 */
	private static int[][] crearTableroAleatorio(int colores) {
		int[][] tableroAleatorio = new int[FILAS][COLUMNAS];
		
		for(int i = 0; i < tableroAleatorio.length; i++) {
			for(int j = 0; j < tableroAleatorio[0].length; j++) {
				tableroAleatorio[i][j] = generarNumeroAleatorio(1, colores);
			}
		}
		
		return tableroAleatorio;
	}

	/**
	 * Devuelve un número entero aleatorio comprendindo entre {@code numero1} y {@code numero2}, 
	 * ambos incluidos.
	 * 
	 * @param numero1 el primer valor
	 * @param numero2 el segundo valor
	 * @return {@code int} entre {@code numero1} y {@code numero2}
	 */
	private static int generarNumeroAleatorio(int numero1, int numero2) {
		int aux;
		
		if(numero1 > numero2) {
			aux = numero1;
			numero1 = numero2;
			numero2 = aux;
		}
		
		return (int) floor(random() * (numero2 - numero1 + 1) + numero1);
	}

}
