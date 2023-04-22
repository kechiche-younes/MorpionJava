package App.Controllers;

import java.util.ArrayList;
import java.util.List;

public class Table {
	// Liste de joueurs dans le jeu 
	List<Joueur> Players= new ArrayList<Joueur>();
	// La matrice de jeu qui contient les valeurs des champs ( 1 pour joueur 1 , -1 pour joueur 2 , 0 pour la case vide)
	int[][] matrixGamge = new int[3][3];

	// variable pour indique si le jeu est terminé 
	public Boolean finish=false;

	// constructeur pour initialiser la liste de joueurs et la matrice de jeu 
	public Table(ArrayList<Joueur> players) {
		super();
		this.Players = players;
		// initialisation de la matrice à 0
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				this.matrixGamge[i][j]=0;
			}
		}

	}

	public List<Joueur> getPlayers() {
		return Players;
	}

	public void setPlayers(List<Joueur> players) {
		Players = players;
	}

	public int[][] getMatrixGamge() {
		return matrixGamge;
	}

	public void setMatrixGamge(int[][] matrixGamge) {
		this.matrixGamge = matrixGamge;
	}

	// Setter pour la valeur d'un champ 
	public void setChoice(Integer field, int value) {


		Integer matrixValue = null;
		if (value == 1) {
			matrixValue = -1;
		} else if (value == 0) {
			matrixValue = 1;
		}
		// Calcul des indices de la matrice à partir de l'indice du champ dans le tableau visuel
		matrixGamge[field % 3][field / 3] = matrixValue;
	}
    // Vérifier si le jeu est terminé 
	public boolean checkBoard() {
		int sumDiagonal1 = 0;
		int sumDiagonalInvers = 0;
		int sumColonnes = 0;
		int sumLignes = 0;

		// Calcul de la somme sur la première diagonale de la matrice
		for (int i = 0; i < 3; i++) {
			sumDiagonal1 += matrixGamge[i][i];
		}
		for (int i = 0; i < 3; i++) {
			sumDiagonalInvers += matrixGamge[i][2 - i];
		}
		// Si la somme sur une des diagonales est égale à 3 ou -3, alors le jeu est terminé

		if (Math.abs(sumDiagonal1) == 3 || Math.abs(sumDiagonalInvers) == 3) {
			return true;
		}
		// Calcul de la somme des valeurs de chaque colonne et de chaque ligne
		for (int i = 0; i <3; i++) {
			for (int j = 0; j <= 2; j++) {
				sumColonnes += matrixGamge[j][i];
				sumLignes += matrixGamge[i][j];

			}
			 // Si la somme de valeurs absolues d'une colonne ou d'une ligne est égale à 3, on a un gagnant
			if (Math.abs(sumColonnes) == 3 || Math.abs(sumLignes) == 3) {
				return true;
			} else {
				 // Réinitialisation des sommes pour le prochain calcul
				sumColonnes = 0;
				sumLignes = 0;
			}
		}
		 // Si aucune des conditions de victoire n'est remplie, on retourne false
	
		return false;
	}
}
