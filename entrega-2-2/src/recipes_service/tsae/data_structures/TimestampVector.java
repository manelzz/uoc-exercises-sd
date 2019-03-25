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
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Arrays;

import recipes_service.data.Operation;

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
			this.timestampVector.put(timestamp.getHostid(), timestamp);
	    }
			
	}
	
	/**
	 * merge in another vector, taking the elementwise maximum
	 * @param tsVector (a timestamp vector)
	 */
	public synchronized void updateMax(TimestampVector tsVector){
	}
	
	/**
	 * 
	 * @param node
	 * @return the last timestamp issued by node that has been
	 * received.
	 */
	public synchronized Timestamp getLast(String node){
		
		// return generated automatically. Remove it when implementing your solution 
		return null;
	}
	
	/**
	 * merges local timestamp vector with tsVector timestamp vector taking
	 * the smallest timestamp for each node.
	 * After merging, local node will have the smallest timestamp for each node.
	 *  @param tsVector (timestamp vector)
	 */
	public synchronized void mergeMin(TimestampVector tsVector){
	}
	
	/**
	 * clone
	 */
	public synchronized TimestampVector clone(){
		
		
		//Treure nº participants
		int participants =this.timestampVector.keySet().size();
		
		//Crear taula utilitzant el constructor amb la mida del nº de participants
		String[] taulaParticipants = new String[participants];
		
		// printing the list
		List<String> listaParticipants = Arrays.asList(taulaParticipants);
		   System.out.println("The list is:" + listaParticipants);
		   
//		taulaParticipants = timestampVector.keys()
		//Afegim els participants del TimestampVector a la taula taulaParticipants

//		List<String> list = new Arrays.asList(timestampVector.keySet());
				
//		for (Map<String, List<String>> entry : this.timestampVector.entrySet()){
//			taulaParticipants.add(item);
//		}
		
		
		for (int i=0; i<participants; i++){
    		taulaParticipants[i] = timestampVector.get(i).getHostid();
    		System.out.println("The timestamp is:" + taulaParticipants[i]);
    		//summary.updateTimestamp(operations.get(i).getTimestamp());
    	}
		
//		for (int i=0; i<operations.size(); i++){
//    		log.add(operations.get(i));
//    		cloneTimestampVector.updateTimestamp(operations.get(i).getTimestamp());
//    	}
		
		//Creem un nou TimestampVector del mateix tamany que l'actual, però buit.
		TimestampVector cloneTimestampVector = new TimestampVector(listaParticipants);
		
	
		//ConcurrentHashMap<String, Timestamp> cloneTimestampVector = new ConcurrentHashMap<String, Timestamp>();
//		for (Iterator<String> it = listaParticipants.iterator(); it.hasNext(); ){
//			//AÑADIR  AL cloneTimestampVector, el mismo registro del TimestampVector 
//			String cloneId = this.timestampVector.get(it);
//			Timestamp cloneTimestamp = this.timestampVector.get(it);
//			cloneTimestampVector..put(it.next(), new Vector<Operation>());
//		}
		
//		for (String node : this.timestampVector.keySet()){
//			String id = node.getValue();
//			this.timestampVector.put(id, new Timestamp(id, Timestamp.NULL_TIMESTAMP_SEQ_NUMBER));
//			//cloneTimestamp.updateTimestamp(cloneTimestamp);
//		}	
	//	List<String> list1 = Arrays.asList(cloneTimestampVector);

		          
		
		
//		List<String> participant = Arrays.asList(timestampVector.keySet());
//		for (Iterator<String> it = participants.iterator(); it.hasNext(); ){
//			String id = it.next();
			// when sequence number of timestamp < 0 it means that the timestamp is the null timestamp
//			this.timestampVector.put(id, new Timestamp(id, Timestamp.NULL_TIMESTAMP_SEQ_NUMBER));

		
        
        
		//Retorna la taula tipus TimestampVector amb tots els particpants.
        return cloneTimestampVector;

		
	}
	
	/**
	 * equals
	 */
	public synchronized boolean equals(TimestampVector tsVector){
		
        if (this.timestampVector == tsVector.timestampVector) {
            return true;
        } else if (this.timestampVector == null || tsVector.timestampVector == null) {
            return false;
        } else {
            return this.timestampVector.equals(tsVector.timestampVector);
        }
        
	}

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
