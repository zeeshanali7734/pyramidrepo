package com.onlineretail.model;

public class SubCategory {

	private int sid;
	private int cid;
	private String subcname;
	private String sdescription;
	private String status;

	public SubCategory() {

	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getSubcname() {
		return subcname;
	}

	public void setSubcname(String subcname) {
		this.subcname = subcname;
	}

	public String getSdescription() {
		return sdescription;
	}

	public void setSdescription(String sdescription) {
		this.sdescription = sdescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((subcname == null) ? 0 : subcname.hashCode());
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
		SubCategory other = (SubCategory) obj;
		if (subcname == null) {
			if (other.subcname != null)
				return false;
		} else if (!subcname.equals(other.subcname))
			return false;
		return true;
	}

}