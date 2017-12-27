package emplois;

public class Emploi {

	/**
	 * 0 = N° de l'offre
	 * 1 = Appellation d'emploi
	 * 2 = Employeur
	 * 3 = Nombre de poste(s)
	 * 4 = Scolarité
	 * 5 = Années d'expérience
	 * 6 = Lieu de travail
	 */

	String url;

	public String numeroDeLoffre = "";
	public String appellationDeLemploi = "";
	public String employeur = "";
	public String nombreDePostes = "";
	public String scolarite = "";
	public String annesDexperience = "";
	public String lieuDeTravail = "";

	@Override
	public String toString() {
		return "Emploi [url=" + url + ", numeroDeLoffre=" + numeroDeLoffre + ", appellationDeLemploi=" + appellationDeLemploi + ", employeur=" + employeur + ", nombreDePostes=" + nombreDePostes + ", scolarite=" + scolarite + ", annesDexperience=" + annesDexperience + ", lieuDeTravail="
				+ lieuDeTravail + "]";
	}

	public boolean contains(String s) {

		if (appellationDeLemploi.contains(s)) {
			return true;
		} else if (employeur.contains(s)) {
			return true;
		} else if (scolarite.contains(s)) {
			return true;
		} else if (lieuDeTravail.contains(s)) {
			return true;
		}

		return false;
	}

}
