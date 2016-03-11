package com.naivor.orderprogress;

public class ProgressEntity {
	private String statusName;
	private long statusTime;
	private boolean isExecute;
	
	public ProgressEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public ProgressEntity(String statusName, long statusTime,boolean isExecute) {
		this.isExecute=isExecute;
		this.statusName=statusName;
		this.statusTime=statusTime;
	}
	
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public long getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(long statusTime) {
		this.statusTime = statusTime;
	}

	public boolean isExecute() {
		return isExecute;
	}

	public void setExecute(boolean isExecute) {
		this.isExecute = isExecute;
	}

}
