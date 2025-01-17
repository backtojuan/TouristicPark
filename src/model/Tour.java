package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.PriorityQueue;
import java.util.LinkedList;
import datastructures.*;

/**
 * This class manage the necessary attributes and methods to create planned tours
 * @author Lina Johanna Salinas
 * @author Lina Marcela Acosta
 * @author Maria Diomar Ordo�ez
 * @author Juan Jos� Valencia
 * @version 1.0 - November/2019 
 */
public class Tour {

	private Location location;
	private String name;
	private String initDate;
	private String finalDate;

	private Artist artist;
	private AdjacencyListGraph<City> map;
	private AdjacencyListGraph<City> cost;
	
	public final static String AFRICA_PATH = "data/africa.txt";
	public final static String AUSTRALIA_PATH = "data/australia.txt";
	public final static String EUROPE_PATH = "data/europe.txt";
	public final static String AMERICA_PATH = "data/america.txt";
	public final static String ASIA_PATH = "data/asia.txt";
	
	public final static String AFRICA_VERTEX = "data/africaVertex.txt";
	public final static String AUSTRALIA_VERTEX = "data/australiaVertex.txt";
	public final static String EUROPE_VERTEX = "data/europeVertex.txt";
	public final static String AMERICA_VERTEX = "data/americaVertex.txt";
	public final static String ASIA_VERTEX = "data/asiaVertex.txt";
	
	private String path;
	
	/**
	 * <b>Tour Constructor</b>
	 * @param name the name of this tour
	 * @param initDate the beggining date for the tour
	 * @param finalDate the finishing date for the tour
	 * @throws IOException 
	 */
	public Tour(Location location,String name, String initDate, String finalDate, String path,String pathVertex) throws IOException {
		this.location = location;
		this.name = name;
		this.initDate = initDate;
		this.finalDate = finalDate;
		load(path,pathVertex);
	}
	
	/**
	 * This method returns the name of this tour
	 * <b>Pre:</b> the tour exists
	 * @return the name of this tour
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method returns the initial date of this tour
	 * <b>Pre:</b> the tour exists
	 * @return the initial date for the tour
	 */
	public String getInitDate() {
		return initDate;
	}

	/**
	 * This method returns the final date of this tour
	 * <b>Pre:</b> the tour exists
	 * @return the final date for the tour
	 */
	public String getFinalDate() {
		return finalDate;
	}
	
	/**
	 * This method returns the artist that is rehearsing for this tour
	 * <b>Pre:</b> the tour exists
	 * @return the artist for this tour
	 */
	public Artist getArtist() {
		return artist;
	}
	
	/**
	 * This method gives to the tour the artist that will present the tour
	 * <b>Pre:</b> the tour exists
	 * <b>Pos:</b> the artist for this tour is definitive
	 * @param artist the artist confirmed for the tour
	 */
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	/**
	 * This method gives to the tour a map with the best selected route to cover their tour
	 * <b>Pre:</b> the tour exists 
	 * <b>Pos:</b> the route for this tour is definitive
	 * @param map the seleced map with the route to cover this tour
	 */
	public void setMap(AdjacencyListGraph<City> map) {
		this.map = map;
	}
	
	/**
	 * This method loads the selected cities for this tour
	 * <b>Pre:</b> the tour exists
	 * <b>Pos:</b> the cities are loaded
	 * @param path the path from where the cities are going to be load
	 * @throws IOException in the case that the file that contains the cities cannot be loaded
	 */
	
	public void loadVertex(String pathVertex) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(new File(pathVertex)));
		String line = br.readLine();

		map = new AdjacencyListGraph<>(15);
		cost = new AdjacencyListGraph<>(15);
		while(line!=null) {
			String[] cities = line.split(",");

			int key = Integer.parseInt(cities[0]);
			String country = cities[1];
			String name = cities[2];

			Vertex<City> vertex = new Vertex<City>(key,new City(location,country,name));
			map.addVertex(vertex);
			cost.addVertex(vertex);
			line = br.readLine();
		}		
		br.close();
	}
	
	public void load(String path,String pathVertex) throws IOException{
		
		loadVertex(pathVertex);
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		String line = br.readLine();
				
		while(line!=null) {
			String[] cities = line.split(",");
			
			String name1 = cities[2];
			String name2 = cities[4];
			double distance = Double.parseDouble(cities[5]);
			double price = Double.parseDouble(cities[6]);
			
			Vertex<City> vertex = null;
			Vertex<City> vertex2 = null;
			for(int i = 0; i<map.getTotalVertices(); i++) {
				if((map.getVertices().get(i).getValue().getName()).equals(name1)) {
					vertex = map.getVertices().get(i);
				}if((map.getVertices().get(i).getValue().getName()).equals(name2)) {
					vertex2 = map.getVertices().get(i);
				}
			}
			map.addEdge(vertex, vertex2, distance);
			cost.addEdge(vertex, vertex2, price);
			line = br.readLine();
		}		
		br.close();
	}
	
	public AdjacencyListGraph<City> getMap(){
		return map;
	}
	
	public AdjacencyListGraph<City> getCost(){
		return cost;
	}
	@Override 
	/**
	 * This method gives the information for this object
	 * @return a String with the information 
	 */
	public String toString() {
		return "Name: " + name + " InitialDate " + initDate + " FinalDate " + finalDate;
	}
}