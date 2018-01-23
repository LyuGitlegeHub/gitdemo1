package com.lyu.bookstore.bean;

public class T_order {
    private Integer oid;
    private String username;
    private Items items;
    
    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}
    
}