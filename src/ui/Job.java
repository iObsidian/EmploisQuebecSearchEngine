package ui;

import org.apache.commons.lang.StringUtils;

import utils.StringUtil;

public class Job {

	/**
	 * 0 = N° de l'offre
	 * 1 = Appellation d'emploi
	 * 2 = Employeur
	 * 3 = Nombre de poste(s)
	 * 4 = Scolarité
	 * 5 = Années d'expérience
	 * 6 = Lieu de travail
	 */

	public String url;

	public String numeroDeLoffre = "";
	public String appellationDeLemploi = "";
	public String employeur = "";
	public String nombreDePostes = "";
	public String scolarite = "";
	public String annesDexperience = "";
	public String lieuDeTravail = "";

	public String html = "";

	@Override
	public String toString() {
		return "Emploi [url=" + url + ", numeroDeLoffre=" + numeroDeLoffre + ", appellationDeLemploi=" + appellationDeLemploi + ", employeur=" + employeur + ", nombreDePostes=" + nombreDePostes + ", scolarite=" + scolarite + ", annesDexperience=" + annesDexperience + ", lieuDeTravail="
				+ lieuDeTravail + "]";
	}

	//Simple search
	public boolean contains(String[] keywords) {

		if (searchAppellationDeLemploi(keywords)) {
			return true;
		} else if (searchEmployeur(keywords)) {
			return true;
		} else if (searchScolarite(keywords)) {
			return true;
		} else if (searchAnnesDexperience(keywords)) {
			return true;
		} else if (searchLieuDeTravail(keywords)) {
			return true;
		}

		return false;
	}

	public boolean searchUrlContains(String[] keywords) {
		return scs(keywords, url);
	}

	public boolean searchNumeroDeLoffre(String[] keywords) {
		return scs(keywords, numeroDeLoffre);
	}

	public boolean searchAppellationDeLemploi(String[] keywords) {
		return scs(keywords, appellationDeLemploi);
	}

	public boolean searchEmployeur(String[] keywords) {
		return scs(keywords, employeur);
	}

	public boolean searchNombreDePostes(String[] keywords) {
		return scs(keywords, nombreDePostes);
	}

	public boolean searchScolarite(String[] keywords) {
		return scs(keywords, scolarite);
	}

	public boolean searchAnnesDexperience(String[] keywords) {
		return scs(keywords, annesDexperience);
	}

	public boolean searchLieuDeTravail(String[] keywords) {
		return scs(keywords, lieuDeTravail);
	}

	public boolean scs(String search[], String in) {

		if (StringUtil.isBlank(search)) {
			return true;
		} else {

			boolean exists = false;

			if (!StringUtils.isBlank(in)) { //TODO remove
				for (String s : search) {

					if (!StringUtils.isBlank(s)) {
						if (in.toLowerCase().contains(s.toLowerCase())) {
							exists = true;
							break;
						}
					}

				}
			}

			return exists;
		}

	}

}
