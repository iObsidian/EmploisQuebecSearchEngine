package automaticPrune;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.LevenshteinDistance;

/**
 * This is a test (idea) class for a generic 'pruner'.
 * 
 * The pruner takes in a list of objects with fields with the @Prune annotation.
 * 
 * Each field with the @Prune are compared to other fields with the @Prune annotation.
 * 
 * @author Alde
 *
 */
public class ReflectionValuePruner {

	private static final Logger log = LoggerFactory.getLogger(ReflectionValuePruner.class);

	public static int REPLACE_ABOVE_PERCENTAGE = 50;

	/**
	 * 
	 * 
	 * 
	 * Only works on objects that have fields with the @Prune annotation.
	 * 
	 * Using reflection, we iterate trough every fields, 
	 * and we compare least popular values with most popular values, 
	 * replacing the less common values with the most common value if the match is above a certain percentage.
	 * 
	 * @param objects List of objects to prune
	 */
	public static void prune(List<?> objects) {

		/* Quick fail */
		if (objects == null) {
			System.err.println("Invalid : list cannot be null.");
			return;
		} else if (objects.isEmpty()) {
			System.err.println("Invalid : list cannot be empty.");
			return;
		}

		/* Get fields of one object */
		Field[] fields = objects.get(0).getClass().getFields();

		/* If all fields are private, fields size will be 0. */
		if (fields.length == 0) {
			System.err.println("Error, no fields found. Are all the object's fields private?");
			return;
		}

		boolean hasAnnotation = false;

		for (Field f : objects.get(0).getClass().getFields()) {
			if (f.isAnnotationPresent(Prune.class)) {
				hasAnnotation = true;
				break;
			}
		}

		if (!hasAnnotation) {
			System.err.println("Annotation error : add the @Prune annotation to fields you want to prune.");
			return;
		}

		/* Structure : Field, Map<Object, Integer>
		 * Field being a field found on every objects
		 * Object being a value (only string is supported)
		 * Integer is the count of value on every objects
		 * */
		Map<Field, HashMap<Object, Integer>> fieldsValuesAndCount = new HashMap<>();

		/* Populate */
		for (Field f : fields) {
			fieldsValuesAndCount.put(f, new HashMap<Object, Integer>());
		}

		Iterator<?> iter = objects.iterator();
		while (iter.hasNext()) {
			Object j = iter.next();

			for (Field f : fields) {

				try {

					Object value = f.get(j);

					if (f.isAnnotationPresent(Prune.class)) {

						if (value instanceof String) {
							Map<Object, Integer> valueAndCount = fieldsValuesAndCount.get(f);

							if (valueAndCount.get(value) == null) {
								valueAndCount.put(value, 1); // Populate
							} else {
								valueAndCount.put(value, valueAndCount.get(value) + 1); // Increment
							}

							log.info(value + " : " + valueAndCount.get(value) + ".");

						} else {
							log.error(
									"Found a field that is not a String, ignoring. Consider implementing some kind of matching for other type than String here.");
						}

					}

				} catch (IllegalArgumentException e) {
					log.error("Exception : " + e);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					log.error("Exception : " + e);
					e.printStackTrace();
				}

			}

		}

		for (Object o : objects) {

			for (Field f : o.getClass().getFields()) {

				String value = "";
				try {
					value = (String) f.get(o);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}

				Iterator<Entry<Object, Integer>> xx = fieldsValuesAndCount.get(f).entrySet().iterator();
				while (xx.hasNext()) {
					Map.Entry<Object, Integer> pair2 = (Map.Entry<Object, Integer>) xx.next();

					if (pair2.getKey() instanceof String) {
						String value2 = (String) pair2.getKey();
						int count2 = pair2.getValue();

						if (count2 > fieldsValuesAndCount.get(f).get(value)) { // Only check if we're comparing against a more popular value

							int percentage = LevenshteinDistance.getPercentage(value, value2);

							if (percentage >= REPLACE_ABOVE_PERCENTAGE) {

								log.info("Apparently " + value2 + " is more popular than " + value + "! " + percentage
										+ "% match... Correcting value...");

								try {
									f.set(o, value2);
								} catch (IllegalArgumentException | IllegalAccessException e) {
									e.printStackTrace();
								}

							}
						}
					}

				}

			}

		}

	}
}
