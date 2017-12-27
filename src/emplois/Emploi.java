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

	//Simple search
	public boolean contains(String s) {

		if (searchAppellationDeLemploi(s)) {
			return true;
		} else if (searchEmployeur(s)) {
			return true;
		} else if (searchLieuDeTravail(s)) {
			return true;
		}

		return false;
	}

	public boolean searchUrlContains(String s) {
		return scs(s, url);
	}

	public boolean searchNumeroDeLoffre(String s) {
		return scs(s, numeroDeLoffre);
	}

	public boolean searchAppellationDeLemploi(String s) {
		return scs(s, appellationDeLemploi);
	}

	public boolean searchEmployeur(String s) {
		return scs(s, employeur);
	}

	public boolean searchNombreDePostes(String s) {
		return scs(s, nombreDePostes);
	}

	public boolean searchScolarite(String s) {
		return scs(s, scolarite);
	}

	public boolean searchAnnesDexperience(String s) {
		return scs(s, annesDexperience);
	}

	public boolean searchLieuDeTravail(String s) {
		return scs(s, lieuDeTravail);
	}

	public boolean scs(String search, String in) {
		return (search.toLowerCase().contains(in.toLowerCase()));
	}

}
