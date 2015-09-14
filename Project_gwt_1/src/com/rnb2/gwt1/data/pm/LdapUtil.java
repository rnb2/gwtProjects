package com.rnb2.gwt1.data.pm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

/**
 * 
 * @author budukh-rn
 * 03 мая 2013 г.	
 *
 */
public final class LdapUtil {

	public LdapUtil() {
	}
	
	public static  LdapPrincipal internalCreatePrincipal(SearchResult sr) throws NamingException  {
		//System.out.println("internal create LdapPrincipal from Ldap SearchResult");
		
		LdapPrincipal  ldapPrincipal = new LdapPrincipal(null);
		 
		Attributes attrs = sr.getAttributes();
		if (attrs != null) {
		//System.out.println("No. of found attributes: "+attrs.size());			
		for (NamingEnumeration ae = attrs.getAll();ae.hasMore();) {
			
			
			try {
				Attribute attr = (Attribute)ae.next();
				Field field = null;
				try {field = ldapPrincipal.getClass().getDeclaredField(attr.getID().trim());} catch (NoSuchFieldException e1) {}
				
				 if (field != null) {
					 //System.out.println("Found field in LdapPrincipal for attribute :"+attr.getID());
					 NamingEnumeration attrValues = attr.getAll();
					 
					 if (isImplementsInterface(field.getType(),Collection.class)) {
						 //System.out.println("Field is instance of Collection");
						 Collection collection = (Collection) (field.getType().isInterface() ? getCollectionImplementation(field.getType()).newInstance(): field.getType().newInstance());
						 
							for (NamingEnumeration e = attr.getAll();e.hasMore();) {
								Object value = e.next();
								//System.out.println("add value to collection:"+value);
								collection.add(value);
							}

							field.setAccessible(true);
							field.set(ldapPrincipal,collection);
						 
					 }else {
						 //System.out.println("Field is NOT instance of Collection");
						 if (attrValues.hasMore()) {
							 Object value = attrValues.next();
							 field.setAccessible(true);
							 //System.out.println("setting value:"+value);
							 field.set(ldapPrincipal,value);
						 }
					 }
					 
				 }
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
		
		
		}
		}
		
		
		return ldapPrincipal.getName()== null ? null : ldapPrincipal;
	}
	
	private static Class<?> getCollectionImplementation(Class<?> type) {
		if (type.equals(List.class)) {
			return ArrayList.class;
		}else if(type.equals(Set.class)) {
			return HashSet.class;
		}
		throw new IllegalArgumentException("Argument  must be Set or List");
	}


	private static boolean isImplementsInterface(Class<?> clazz, Class<?> _interface) {
		for (Class<?> cls :  clazz.getInterfaces()) {
			if (cls.equals(_interface)) {
				return true;
			}
		}
		return false;
	}

}
