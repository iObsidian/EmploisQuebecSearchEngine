package emplois;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ui.LoadingScreen;

public class RiotMotor {

	private static final String ROOT_ = "http://placement.emploiquebec.gouv.qc.ca/mbe/ut/rechroffr/listoffr.asp?mtcle=&cle=47&offrdisptoutqc=2&pp=1&date=3";

	//

	

	private LoadingScreen progressBar;

	public RiotMotor(LoadingScreen progressBar) {
		this.progressBar = progressBar;
	}

	public void reloadList() {

		List<Emploi> emplois = new ArrayList<>();

		ArrayList<String> pagesUrls = getAllPages();

		try {

			int current = 0;
			int total = pagesUrls.size();

			for (String pageUrl : pagesUrls) {

				current++;

				progressBar.reportTotal(current, total);

				System.out.println("Loading job offerings from list... " + current + "\\" + total);

				URL page = new URL(pageUrl);
				BufferedReader in = new BufferedReader(new InputStreamReader(page.openStream()));

				String inputLine;

				boolean isInsideJobOfferings = false;

				StringBuilder jobsRaw = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {

					if (inputLine.contains("<tbody>")) {

						System.out.println("Found beginning of list...");
						isInsideJobOfferings = true;

					} else if (inputLine.contains("</tbody>")) {

						System.out.println("Found end of list.");
						isInsideJobOfferings = false;

						break;
					}

					if (isInsideJobOfferings) {
						jobsRaw.append(inputLine);
					}

				}

				in.close();

				//

				String[] jobs = jobsRaw.toString().split("</tr>"); //Split jobs using </tr>

				for (String job : jobs) {

					String[] jobParams = job.split("</td>"); //Split jobs params using </td>

					Emploi emploi = new Emploi();

					for (int i = 0; i < jobParams.length; i++) {

						String line = jobParams[i];

						try {

							//System.out.println(i + " " + jobParams[i]);

							/**
							 * 0 = N° de l'offre
							 * 1 = Appellation d'emploi (& URL)
							 * 2 = Employeur
							 * 3 = Nombre de poste(s)
							 * 4 = Scolarité
							 * 5 = Années d'expérience
							 * 6 = Lieu de travail
							 */

							switch (i) {
							case 0:
								emploi.numeroDeLoffre = line.substring(line.indexOf("top;\">") + 7);
								break;
							case 1:
								emploi.url = line.substring(line.indexOf("<a href=\"") + 9, line.indexOf("CL=french\" >"));
								emploi.appellationDeLemploi = line.substring(line.indexOf("&CL=french\" >") + 13, line.indexOf("</a>"));
								break;
							case 2:
								emploi.employeur = line.substring(line.indexOf("<td>") + 4);
								break;
							case 3:
								emploi.nombreDePostes = line.substring(line.indexOf("<td>") + 4);
								break;
							case 4:
								emploi.scolarite = line.substring(line.indexOf("<td>") + 4);
								break;
							case 5:
								emploi.annesDexperience = line.substring(line.indexOf("<td>") + 4);
								break;
							case 6:
								emploi.lieuDeTravail = line.substring(line.indexOf("<td>") + 4);
								break;
							default:
								System.out.println("Error! Invalid emploi argument size " + i);
								break;
							}

						} catch (Exception e) {
							System.err.println("Error with job param line " + line);
							e.printStackTrace();
						}

					}

					emplois.add(emploi);
					progressBar.reportJobs(emplois.size());
					
					
				}
				
				//break; //remove after debug

			}

		} catch (IOException e) {
			System.err.println("Error while trying to get pages.");
			e.printStackTrace();
		}

		System.out.println("Found " + emplois.size() + " jobs.");

		progressBar.reportEnded(emplois);
	}

	/**
	 * @return a list of all pages with lists of jobs
	 * Utilises the navigation bar at the bottom of the first page of offers
	 */
	private ArrayList<String> getAllPages() {
		System.out.println("Getting all pages...");

		ArrayList<String> allPages = new ArrayList<>();
		String pagesRaw = null;

		try {

			URL emploisQuebecRoot = new URL(ROOT_);
			BufferedReader in = new BufferedReader(new InputStreamReader(emploisQuebecRoot.openStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("Suivant")) {
					System.out.println("Found pages.");
					pagesRaw = inputLine;
				}
			}

			in.close();

		} catch (IOException e) {
			System.err.println("Error while trying to get pages :");
			e.printStackTrace();
		}

		try {

			if (pagesRaw == null) {
				System.err.println("Could not get pages!");
				return null;
			} else {
				String pages[] = pagesRaw.split("&nbsp;&nbsp;");

				System.out.println("Found " + pages.length + " pages.");

				for (String s : pages) {
					s = s.replace("<a href=\"", "");
					s = s.substring(0, s.indexOf("french") + 6);

					allPages.add(s);
				}

				System.out.println("Raw pages parsed succesfully.");

			}

		} catch (Exception e) {
			System.err.println("Error while trying to clean raw page data...");
			e.printStackTrace();
		}

		return allPages;
	}

}
