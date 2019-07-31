package my.minicandycrash;

import java.util.Scanner;

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
 * 
 * @author Richard Albán Fernández
 *
 */
public class MiniCandyCrash {

	public static void main(String[] args) {
		
		int modo;
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
					break;
				case 2: 
					System.out.println("Has seleccionado el Tablero Intermedio.");
					break;
				case 3: 
					System.out.println("Has seleccionado el Tablero Difícil.");
					break;
				case 4: 
					System.out.println("Has seleccionado el Tablero fijo.");
					break;
				case 0: 
					System.out.println("¡Hasta la próxima!");
					break;
				default:
					System.out.println("Por favor, seleccione una opción válida.");
			}
			
			System.out.println("\n");
			
		} while(modo != 0);
		
		teclado.close();
	}

}
