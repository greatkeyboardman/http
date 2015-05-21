package com.ctqh.mobile.common;


/**
 * 一对一关系
 * @author lizhiwei
 *
 */
public class KeyValue<K,V> {
	public K key;
	public V value;
	
	public KeyValue(K key,V value){
		this.key = key;
		this.value = value;
	}
	public KeyValue(){
	}
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	
}
