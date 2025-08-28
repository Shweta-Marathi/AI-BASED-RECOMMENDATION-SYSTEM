// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class Task4 {
   public Task4() {
   }

   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter movie genre (e.g., Action, Drama, Comedy,Horror,Fantasy): ");
      String inputGenre = scanner.nextLine();
      String csvFile = "src/Recommendation.csv";
      Map<String, List<Double>> movieRatings = new HashMap();

      String bestMovie;
      try {
         Throwable var6 = null;
         bestMovie = null;

         try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));

            try {
               br.readLine();

               String line;
               while((line = br.readLine()) != null) {
                  String[] parts = line.split(",");
                  if (parts.length >= 4) {
                     String genre = parts[1];
                     String movie = parts[2];
                     double rating = Double.parseDouble(parts[3]);
                     if (genre.equalsIgnoreCase(inputGenre)) {
                        movieRatings.putIfAbsent(movie, new ArrayList());
                        ((List)movieRatings.get(movie)).add(rating);
                     }
                  }
               }
            } finally {
               if (br != null) {
                  br.close();
               }

            }
         } catch (Throwable var21) {
            if (var6 == null) {
               var6 = var21;
            } else if (var6 != var21) {
               var6.addSuppressed(var21);
            }

            throw var6;
         }
      } catch (IOException var22) {
         System.out.println("Error reading CSV file: " + var22.getMessage());
         return;
      }

      if (movieRatings.isEmpty()) {
         System.out.println("No data found for this genre.");
      } else {
         Map<String, Double> avgRatings = new HashMap();
         Iterator var24 = movieRatings.keySet().iterator();

         while(var24.hasNext()) {
            bestMovie = (String)var24.next();
            List<Double> ratings = (List)movieRatings.get(bestMovie);
            double avg = ratings.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            avgRatings.put(bestMovie, avg);
         }

         bestMovie = (String)((Map.Entry)Collections.max(avgRatings.entrySet(), Entry.comparingByValue())).getKey();
         System.out.println("Best movie for genre \"" + inputGenre + "\" based on ratings: " + bestMovie);
         System.out.println("\nAll movies in this genre with average ratings:");
         avgRatings.entrySet().stream().sorted((a, b) -> {
            return Double.compare((Double)b.getValue(), (Double)a.getValue());
         }).forEach((e) -> {
            System.out.println((String)e.getKey() + ": " + e.getValue());
         });
         scanner.close();
      }
   }
}
