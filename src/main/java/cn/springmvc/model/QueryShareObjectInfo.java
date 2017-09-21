package cn.springmvc.model;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

@Repository
public class QueryShareObjectInfo implements Serializable{
	
	private static final long serialVersionUID = -969551364572263511L;
	private String shareObjectID;
	private String shareObjectName;
	public String getShareObjectID() {
		return shareObjectID;
	}
	public void setShareObjectID(String shareObjectID) {
		this.shareObjectID = shareObjectID;
	}
	public String getShareObjectName() {
		return shareObjectName;
	}
	public void setShareObjectName(String shareObjectName) {
		this.shareObjectName = shareObjectName;
	}	
}
