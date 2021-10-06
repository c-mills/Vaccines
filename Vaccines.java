
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
 
public class Vaccines {
	
	public class Country{
		private int ID; //country node
		private int vaccine_threshold; //Threshold Ti for country with ID = i
		private int vaccine_to_receive; //total number of vaccines that the country will recieve from its allies
		private ArrayList<Integer> allies_ID; //list of IDs of countries allies
		private ArrayList<Integer> allies_vaccine; //num of vaccines each country will share with each of its allies
		//parallel lists ; the county is willing to share a total of allies_vaccine[i] vaccines with the country allies_ID[i]
		public Country() {
			this.allies_ID = new ArrayList<Integer>();
			this.allies_vaccine = new ArrayList<Integer>();
			this.vaccine_threshold = 0;
			this.vaccine_to_receive = 0;
		}
		public int get_ID() {
			return this.ID;
		}
		public int get_vaccine_threshold() {
			return this.vaccine_threshold;
		}
		public ArrayList<Integer> get_all_allies_ID() {
			return allies_ID;
		}
		public ArrayList<Integer> get_all_allies_vaccine() {
			return allies_vaccine;
		}
		public int get_allies_ID(int index) {
			return allies_ID.get(index);
		}
		public int get_allies_vaccine(int index) {
			return allies_vaccine.get(index);
		}
		public int get_num_allies() {
			return allies_ID.size();
		}
		public int get_vaccines_to_receive() {
			return vaccine_to_receive;
		}
		public void set_allies_ID(int value) {
			allies_ID.add(value);
		}
		public void set_allies_vaccine(int value) {
			allies_vaccine.add(value);
		}
		public void set_ID(int value) {
			this.ID = value;
		}
		public void set_vaccine_threshold(int value) {
			this.vaccine_threshold = value;
		}
		public void set_vaccines_to_receive(int value) {
			this.vaccine_to_receive = value;
		}
		
	}
	public class Country1{ //extends Country{
		private Country a;
		private int C_allie_index;
		private int allie_index;
		
		public Country1(Country a, int C_allie_index, int allie_index) {
			//this.setA(a);
			this.a=a;
			this.C_allie_index=C_allie_index;
			this.allie_index=allie_index;
		}
		
		public Country getA() {
			return this.a;
		}
 
		public void setA(Country a) {
			this.a = a;
		}
	}
	
	public int vaccines(Country[] graph){
		//Computing the exceed in vaccines per country.
		int count=graph.length-1;
		
		//bfs
		int[] cvisit=new int[graph.length];//for count
		int[] visited=new int[graph.length];//in general
		
		Queue<Country1> queue=new LinkedList<>();
	
		
		for (int b=0;b<graph[0].get_num_allies();b++) {
			int alliesQ = graph[0].get_allies_ID(b);
			queue.add(new Country1(graph[alliesQ-1],0,b));//adding first countries allies to queue to see if each one is affected
		}
		cvisit[0]=1;
		visited[0]=1;
		int a=0;
		int start=0;
		
		
		while(! queue.isEmpty()) {
			
			Country1 d=queue.poll();
			start=d.C_allie_index;
			a=d.allie_index;
			
			Country e=d.getA();
			
			if(visited[e.get_ID()-1]!=0 && cvisit[e.get_ID()-1]!=0) {
				continue;
			}
			if(visited[e.get_ID()-1]==0) {
				visited[e.get_ID()-1]=1;
			}
			int tot=e.vaccine_to_receive;
			e.vaccine_to_receive=tot-graph[start].get_allies_vaccine(a);
			
			
			if(e.vaccine_to_receive<e.vaccine_threshold) {
				if(cvisit[e.get_ID()-1]!=0) {
					continue;
				}
				else {
					cvisit[e.get_ID()-1]=1;
					count--;
				}
				
				for (int b=0;b<graph[e.get_ID()-1].get_num_allies();b++) {
					
					int alliesQ = graph[e.get_ID()-1].get_allies_ID(b);
					queue.add(new Country1(graph[alliesQ-1],e.get_ID()-1,b));//adding countries allies to queue to see if each one is affected
					
				}
			}
			
		}
		
		
		
		return count;
	}
	
 
	public void test(String filename) throws FileNotFoundException {
		Vaccines hern = new Vaccines();
		Scanner sc = new Scanner(new File(filename));
		int num_countries = sc.nextInt();
		Country[] graph = new Country[num_countries];
		for (int i=0; i<num_countries; i++) {
			graph[i] = hern.new Country(); 
		}
		for (int i=0; i<num_countries; i++) {
			if (!sc.hasNext()) {
                sc.close();
                sc = new Scanner(new File(filename + ".2"));
            }
			int amount_vaccine = sc.nextInt();
			graph[i].set_ID(i+1);
			graph[i].set_vaccine_threshold(amount_vaccine);
			int other_countries = sc.nextInt();
			for (int j =0; j<other_countries; j++) {
				int neighbor = sc.nextInt();
				int vaccine = sc.nextInt();
				graph[neighbor -1].set_allies_ID(i+1);
				graph[neighbor -1].set_allies_vaccine(vaccine);
				graph[i].set_vaccines_to_receive(graph[i].get_vaccines_to_receive() + vaccine);
			}
		}
		sc.close();
		int answer = vaccines(graph);
		System.out.println(answer);
	}
 
	public static void main(String[] args) throws FileNotFoundException{
		Vaccines vaccines = new Vaccines();
		vaccines.test(args[0]); // run 'javac Vaccines.java' to compile, then run 'java Vaccines testfilename'
	}
 
}
 