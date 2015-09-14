package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;



@Entity
@DiscriminatorValue("GROUP")
@NamedQueries({
		@NamedQuery(name = "getAllRailwayGroups", query = "select o from RailwayGroup as o "
				+ "join o.railwayObjectType t "
				+ "where t.sysCode = :param1 "
				+ "order by o.fullName"),
		@NamedQuery(name = "getAllRailwayGroups.notInUser", query = "select o from RailwayGroup as o "
				+ "join o.railwayObjectType t "
				+ "where t.sysCode = :param1 "
				+ "and o.id not in(:param2) "
				+ "order by o.fullName")
	})
public class RailwayGroup extends RailwayObject implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RailwayGroup() {
		super();
	}
	
	
	
}
