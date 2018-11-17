package model;

public class FloydWarshall {

	private double[][] matrizAdjacencia;

	private double[][] matrizSalida;

	public FloydWarshall(double[][] matriz) {
		this.matrizAdjacencia = matriz;
	}

	public void executar() {
		int n = matrizAdjacencia.length;

		this.matrizSalida = new double[n][n];

		for (int k = 0; k < n; k++) {
			System.out.println("\n--- ITERACAO" + (k+1));
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i != j && j != k) {
						if (matrizAdjacencia[i][k] > 0 && matrizAdjacencia[k][j] > 0) {
							
							if (matrizSalida[i][j] == 0) {
								this.matrizSalida[i][j] = 1;
							}
							
						}
					}
				}
			}
			
			imprimir(matrizSalida);
		}
	}

	public void imprimir(double[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				System.out.print(matriz[i][j] + " ");
			}

			System.out.println();
		}
	}

}
