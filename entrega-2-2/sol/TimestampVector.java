/*
* Copyright (c) Joan-Manuel Marques 2013. All rights reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
*
* This file is part of the practical assignment of Distributed Systems course.
*
* This code is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This code is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this code.  If not, see <http://www.gnu.org/licenses/>.
*/

package recipes_service.tsae.data_structures;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Arrays;

/**
 * @author Joan-Manuel Marques
 * December 2012
 *
 */
public class TimestampVector implements Serializable{

	private static final long serialVersionUID = -765026247959198886L;
	/**
	 * This class stores a summary of the timestamps seen by a node.
	 * For each node, stores the timestamp of the last received operation.
	 */
	
	
	private ConcurrentHashMap<String, Timestamp> timestampVector= new ConcurrentHashMap<String, Timestamp>();
	//private Object clonedVector;
	
	 
	public TimestampVector (List<String> participants){
		// create and empty TimestampVector
		for (Iterator<String> it = participants.iterator(); it.hasNext(); ){
			String id = it.next();
			// when sequence number of timestamp < 0 it means that the timestamp is the null timestamp
			timestampVector.put(id, new Timestamp(id, Timestamp.NULL_TIMESTAMP_SEQ_NUMBER));
		}
	}

	
    
	/**
	 * Updates the timestamp vector with a new timestamp. 
	 * @param timestamp
	 */
	public synchronized void updateTimestamp(Timestamp timestamp){
	
		/*
		 El mètode updateTimestamp ha d'actualitzar el vector posant el timestamp que es passa per paràmetre 
		 a la posició corresponent (la del hostid corresponent).
		*/
		if (timestamp != null) {
			//this.timestampVector.put(timestamp.getHostid(), timestamp);
			this.timestampVector.put(timestamp.getHostid(), timestamp);
	    }
			
	}
	
	/**
	 * merge in another vector, taking the elementwise maximum
	 * @param tsVector (a timestamp vector)
	 */
	public synchronized void updateMax(TimestampVector tsVector){
		
        for (String node : this.timestampVector.keySet()) {
            Timestamp otherTimestamp = tsVector.getLast(node);

            if (otherTimestamp == null) {
                continue;
            } else if (this.getLast(node).compare(otherTimestamp) < 0) {
                this.timestampVector.replace(node, otherTimestamp);
            }
        }
		
	}
	
	/**
	 * 
	 * @param node
	 * @return the last timestamp issued by node that has been
	 * received.
	 */
	public synchronized Timestamp getLast(String node){
		
		 return this.timestampVector.get(node);
	}
	
	/**
	 * merges local timestamp vector with tsVector timestamp vector taking
	 * the smallest timestamp for each node.
	 * After merging, local node will have the smallest timestamp for each node.
	 *  @param tsVector (timestamp vector)
	 */
	public synchronized void mergeMin(TimestampVector tsVector){
	
		 if (tsVector == null) {
	            return;
	        }
		 
	        for (Map.Entry<String, Timestamp> entry : tsVector.timestampVector.entrySet()) {
	            String node = entry.getKey();
	            Timestamp otherTimestamp = entry.getValue();
	            Timestamp thisTimestamp = this.getLast(node);
	            
	            if (thisTimestamp == null) {
	                this.timestampVector.put(node, otherTimestamp);
	            } else if (otherTimestamp.compare(thisTimestamp) < 0) {
	                this.timestampVector.replace(node, otherTimestamp);
	            }
	        }
	
	}
	
	/**
	 * clone
	 */
	
	//@Override
	public  TimestampVector clone(){
		
		
		/////////////////1. Crear la taula amb els participants////////////////////////////////
		
		//Crear taula utilitzant el constructor amb la mida del nº de participants. No vector.
		//String[] participants = new String[timestampVector.keySet().size()];
		
		
		
        //List<String> participantList = new ArrayList<String>();
		//List<String> participantList = null;

		
		////////Crear el nou TimestampVector amb els participants//////////
		String[]participants =timestampVector.keySet().toArray(new String[timestampVector.keySet().size()]);
		TimestampVector timeclone = new TimestampVector(Arrays.asList(participants));   

		for (String node : this.timestampVector.keySet()) {
	            Timestamp thisTimestamp = this.getLast(node);

//	            System.out.println("Timestamp antes update: " + timeclone.getLast(node));
	            timeclone.updateTimestamp(thisTimestamp);
//	            System.out.println("Timestamp despues update: '" + timeclone.getLast(node) + "' debe ser igual que '" + timestampVector.get(node) + "'");
	            
	       }
	    
//		System.out.println("Equal0: " + timeclone.toString()==timestampVector.toString());
//		System.out.println("Equal1: " + timestampVector.equals(timeclone));
//        System.out.println("Equal2: " + timeclone.equals(timestampVector));
      		
		return timeclone;
      		// return true;
 
		
	}
	


	public boolean equals(TimestampVector tsVector){

//	System.out.print("Inicio equals timestamp");
        if (tsVector == null) {
//       System.out.print(". Es null");
            return false;
 //       } else if (this == obj) {
 //           return true;
        } else if (!(tsVector instanceof TimestampVector)) {
 //       	System.out.print(". No es null pero no es Instancia de TimestampVector");
            return false;
        }

//        System.out.print(". No es null y es Instancia de TimestampVector");
        
        TimestampVector other = (TimestampVector) tsVector;
        
        if (!other.timestampVector.equals(this.timestampVector)) {
//        	System.out.println("Equal interno no es igual");
        	return false;

//        } else {
//        	if (this.timestampVector.equals(other.timestampVector)) 
 //       	{System.out.println("Equal tsVector");
 //       	}
 //                       
        }  
        
        return this.timestampVector.equals(other.timestampVector);
        
    }

	/**
	 * equals
	 */
//	public synchronized boolean equals(TimestampVector tsVector){
//		
//        if (this.timestampVector == tsVector.timestampVector) {
//        	System.out.println("Es nulo equals normal");
//            return true;
//        } else if (this.timestampVector == null || tsVector.timestampVector == null) {
//        	System.out.println("No es nulo y es objeto equals normal");
//            return false;
//        } else {
//            return this.timestampVector.equals(tsVector.timestampVector);
//        }
        
//	}


    
    
    
	/**
	 * toString
	 */
	@Override
	public synchronized String toString() {
		String all="";
		if(timestampVector==null){
			return all;
		}
		for(Enumeration<String> en=timestampVector.keys(); en.hasMoreElements();){
			String name=en.nextElement();
			if(timestampVector.get(name)!=null)
				all+=timestampVector.get(name)+"\n";
		}
		return all;
	}
}
