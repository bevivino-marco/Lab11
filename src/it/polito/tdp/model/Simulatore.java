package it.polito.tdp.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class Simulatore {
	//Modello -> Stato del sistema ad ogni passo
	private int tavoli_totali;
	private int tavoli_disponibili;
		

		//Tipi di evento/coda prioritaria
		// 1 solo evento
		private PriorityQueue<Evento> queue;
		
		//Parametri della simulazione
		private int LIMITE_EVENTI = 2000;
		Map <Integer, Integer> tavoli;
		private int c;
		private Random rand = new Random();
		LocalTime tempo;
		
		//Valori in output
		private int CLIENTI_TOT;
	    private int CLIENTI_INS;
	    private int CLIENTI_SOD;
	    
		
		
		
		

		public void init () {
			//impostazione dello stato iniziale
			c = 1;
			tavoli = new HashMap <>();
			tavoli.put(10 , 2 );
			tavoli.put(8 , 4 );
			tavoli.put(6 , 4 );
			tavoli.put(4 , 5 );
		    CLIENTI_TOT= 0;
		    CLIENTI_INS= 0;
		    CLIENTI_SOD= 0;
		    tempo = LocalTime.of(0, 0, 0);
			queue = new PriorityQueue<Evento>();
			float t = (rand.nextInt(8)+1)/10;
		//inserisco il primo evento
		    queue.add(new Evento (tempo,rand.nextInt(10)+1, Duration.ofMinutes(rand.nextInt(120)-rand.nextInt(60)), t));
		
		}
		public void run() {
			Evento e;
			//Estraggo un evento per volta dalla coda e lo eseguo,
			//finchè la coda non si svuota
			

		while(c<=2000){
				//ESEGUO L'EVENTO
			boolean sod = false;
			e = queue.poll();
			System.out.println(e) ;
			if (e.isRestituzione()) {
				if (tavoli.containsKey(e.getTavolo())) {
					tavoli.put(e.getTavolo(), tavoli.get(e.getTavolo())+1);
				}else tavoli.put(e.getTavolo(), 1);
			}else {
				
				
			int tavoloInUso=0;
			int persone = e.getNum_persone();
			float tolleranza = e.getTolleranza();
			Duration durata = e.getDurata();
			LocalTime time = e.getTime();
			if (persone >8) {
				if (tavoli.get(10)>0) {
					tavoli.put(10, tavoli.get(10)-1);
					sod =true;
					tavoloInUso=10;
				}else {
					if (tolleranza>=0.5) {
						sod =true;
					}//else CLIENTI_INS++;
				}
			} else if (persone <2) {
				if (tolleranza>=0.5) {
					sod =true;
				}//else CLIENTI_INS++;
			} else {
				
				switch (persone) {
				case 2:
					if (tavoli.get(4)>0) {
						tavoli.put(4, tavoli.get(4)-1);
						sod =true;
						tavoloInUso=4;
						}else {
							if (tolleranza>=0.5) {
								sod =true;
							}//else CLIENTI_INS++;
						}
					
					break;
				case 3: 
					if (tavoli.get(4)>0) {
						tavoli.put(4, tavoli.get(4)-1);
						//CLIENTI_SOD++;
						tavoloInUso=4;
						sod =true;
						} else if (tavoli.get(6)>0) {
							tavoli.put(6, tavoli.get(6)-1);
							sod =true;
							tavoloInUso=6;
							} else {
								if (tolleranza>=0.5) {
									sod =true;
								}//else CLIENTI_INS++;
							}
					break;
				case 4: 
					if (tavoli.get(4)>0) {
						tavoli.put(4, tavoli.get(4)-1);
						sod =true;
						tavoloInUso=4;
						} else if (tavoli.get(6)>0) {
							tavoli.put(6, tavoli.get(6)-1);
							sod =true;
							tavoloInUso=6;
							}else if (tavoli.get(8)>0) {
								tavoli.put(8, tavoli.get(8)-1);
								sod =true;
								tavoloInUso=8;
								} else {
								if (tolleranza>=0.5) {
									sod =true;
								}//else CLIENTI_INS++;
							}
					break;
				case 5:	
				   if (tavoli.get(6)>0) {
					tavoli.put(6, tavoli.get(6)-1);
					sod =true;
					tavoloInUso=6;
					} else if (tavoli.get(8)>0) {
						tavoli.put(8, tavoli.get(8)-1);
						sod =true;
						tavoloInUso=8;
						}else if (tavoli.get(10)>0) {
							tavoli.put(10, tavoli.get(10)-1);
							sod =true;
							tavoloInUso=10;
							} else {
							if (tolleranza>=0.5) {
								sod =true;
							}//else CLIENTI_INS++;
						} 
					
					break;
				case 6:
					if (tavoli.get(6)>0) {
						tavoli.put(6, tavoli.get(6)-1);
						sod =true;
						tavoloInUso=6;
						} else if (tavoli.get(8)>0) {
							tavoli.put(8, tavoli.get(8)-1);
							sod =true;
							tavoloInUso=8;
							}else if (tavoli.get(10)>0) {
								tavoli.put(10, tavoli.get(10)-1);
								sod =true;
								tavoloInUso=10;
								} else {
								if (tolleranza>=0.5) {
									sod =true;
								}//else CLIENTI_INS++;
							}
					break;
				case 7:
					if (tavoli.get(8)>0) {
						tavoli.put(8, tavoli.get(8)-1);
						sod =true;
						tavoloInUso=8;
						}else if (tavoli.get(10)>0) {
							tavoli.put(10, tavoli.get(10)-1);
							sod =true;
							tavoloInUso=10;
							} else {
							if (tolleranza>=0.5) {
								sod =true;
							}//else CLIENTI_INS++;
						}
					break;
				}
			if (sod==true) {
				CLIENTI_TOT+= persone;
				CLIENTI_SOD+=persone;
			    queue.add(new Evento (tempo.plus(durata),true , tavoloInUso));

			}else {
				CLIENTI_INS+=persone;
			}	
			}
			}	
	    queue.add(new Evento (tempo.plusMinutes(rand.nextInt(10)+1),rand.nextInt(10)+1, Duration.ofMinutes(rand.nextInt(120)-rand.nextInt(60)), rand.nextInt(9)/10));
		 

	    c++;
		}
}
		public List <Evento > getEventi(){
			List <Evento >l = new LinkedList <Evento>();
			l.addAll(queue);
			Collections.sort(l);
			return l;
		}
}
