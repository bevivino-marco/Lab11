package it.polito.tdp.model;

import java.time.Duration;
import java.time.LocalTime;

public class Evento implements Comparable<Evento>{
 private LocalTime time ;
 private int num_persone;
 private Duration durata;
 private float tolleranza;
 private boolean restituzione;
 private int tavolo;
public Evento(LocalTime time, int num_persone, Duration durata, float tolleranza) {
	super();
	this.time = time;
	this.num_persone = num_persone;
	this.durata = durata;
	this.tolleranza = tolleranza;
	restituzione = false;
}
public Evento(LocalTime time, Boolean restituzione, int tavolo) {
	super();
	this.time = time;
	this.restituzione= restituzione;
	this.tavolo= tavolo;
}

public int getTavolo() {
	return tavolo;
}
public boolean isRestituzione() {
	return restituzione;
}

public LocalTime getTime() {
	return time;
}
public int getNum_persone() {
	return num_persone;
}
public Duration getDurata() {
	return durata;
}
public float getTolleranza() {
	return tolleranza;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((durata == null) ? 0 : durata.hashCode());
	result = prime * result + num_persone;
	result = prime * result + ((time == null) ? 0 : time.hashCode());
	long temp;
	temp = Double.doubleToLongBits(tolleranza);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Evento other = (Evento) obj;
	if (durata == null) {
		if (other.durata != null)
			return false;
	} else if (!durata.equals(other.durata))
		return false;
	if (num_persone != other.num_persone)
		return false;
	if (time == null) {
		if (other.time != null)
			return false;
	} else if (!time.equals(other.time))
		return false;
	if (Double.doubleToLongBits(tolleranza) != Double.doubleToLongBits(other.tolleranza))
		return false;
	return true;
}
@Override
public String toString() {
	if (num_persone==0) {
		return "Restituzione tavolo"+ tavolo +" alle ore"+ time+ ";\n";
	}else
	return String.format("Clienti serviti : time=%s, num_persone=%s, durata=%s, tolleranza=%s;\n", time, num_persone, durata,
			tolleranza);
}
@Override
public int compareTo(Evento e) {

	return this.time.compareTo(e.getTime());
}
 
}
